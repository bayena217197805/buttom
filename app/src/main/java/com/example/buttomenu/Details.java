package com.example.buttomenu;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

public class Details extends Fragment {

    private TextView nameText, emailText, userNameText, phoneText;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private TextView scoreText;

    public Details() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_details, container, false);

        // ربط العناصر
        nameText = view.findViewById(R.id.name_value);
        scoreText = view.findViewById(R.id.score_value);
        userNameText = view.findViewById(R.id.username_value);
        phoneText = view.findViewById(R.id.phone_value);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        mAuth.addAuthStateListener(firebaseAuth -> {
            if (firebaseAuth.getCurrentUser() != null) {
                loadUserData();
            }
        });

        return view;
    }

    private void loadUserData() {
        if (mAuth.getCurrentUser() == null) {
        Toast.makeText(getActivity(), "لم يتم تسجيل الدخول", Toast.LENGTH_SHORT).show();
        return;
    }
        String email = mAuth.getCurrentUser().getEmail();

        db.collection("clinet")
                .whereEqualTo("Email", email)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        Map<String, Object> data = queryDocumentSnapshots.getDocuments().get(0).getData();

                        nameText.setText("Name: " + data.get("Name"));
                        scoreText.setText("Score: " + data.get("Score"));
                        userNameText.setText("UserName: " + data.get("UserName"));
                        phoneText.setText("Phone: " + data.get("Phone"));
                    } else {
                        Toast.makeText(getActivity(), "لا يوجد بيانات للمستخدم", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(getActivity(), "حدث خطأ أثناء تحميل البيانات", Toast.LENGTH_SHORT).show();
                });
    }
}
