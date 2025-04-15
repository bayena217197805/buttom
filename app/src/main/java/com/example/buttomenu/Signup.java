package com.example.buttomenu;

import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;

public class Signup extends Fragment {
    private TextInputEditText userName, name, phone, email, password, confirmPassword;
    private Button signup;
    private boolean fieldsEmpty = false;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private boolean nameLengthValid = false, emailValid = false, passwordsMatch = false, passwordValid = false, phoneValid = false;

    public Signup() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup, container, false);

        // ربط الحقول بالـ XML
        userName = view.findViewById(R.id.UserName);
        name = view.findViewById(R.id.Name);
        phone = view.findViewById(R.id.Phone);
        email = view.findViewById(R.id.email);
        password = view.findViewById(R.id.password);
        confirmPassword = view.findViewById(R.id.confirmPassword);
        signup = view.findViewById(R.id.signup);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        userName.setText("");
        name.setText("");
        phone.setText("");
        email.setText("");
        password.setText("");
        confirmPassword.setText("");


        signup.setOnClickListener(v -> {
            // التحقق من الحقول الفارغة
            fieldsEmpty = userName.getText().toString().trim().isEmpty() || name.getText().toString().trim().isEmpty() ||
                    phone.getText().toString().trim().isEmpty() || email.getText().toString().trim().isEmpty() ||
                    password.getText().toString().trim().isEmpty() || confirmPassword.getText().toString().trim().isEmpty();

            if (fieldsEmpty) {
                Toast.makeText(getActivity(), "يرجى ملء جميع الحقول", Toast.LENGTH_SHORT).show();
            } else {
                validateFields();
            }
        });

        return view;
    }

    private void validateFields() {
        // التحقق من طول الاسم
        String nameStr = name.getText().toString().trim();
        if (nameStr.length() > 4) {
            nameLengthValid = true;
        }

        // التحقق من صحة رقم الهاتف
        String phoneStr = phone.getText().toString().trim();
        if (phoneStr.matches("\\d{10}")) {
            phoneValid = true;
        }

        // التحقق من صحة البريد الإلكتروني
        String emailStr = email.getText().toString().trim();
        if (Patterns.EMAIL_ADDRESS.matcher(emailStr).matches()) {
            emailValid = true;
        }

        // التحقق من تطابق كلمة المرور
        String passwordStr = password.getText().toString().trim();
        String confirmPasswordStr = confirmPassword.getText().toString().trim();
        if (passwordStr.equals(confirmPasswordStr)) {
            passwordsMatch = true;
        }

        // التحقق من شروط كلمة المرور
        if (passwordStr.length() >= 6) {
            boolean hasUpperCase = false, hasLowerCase = false, hasDigit = false;

            for (int i = 0; i < passwordStr.length(); i++) {
                char c = passwordStr.charAt(i);
                if (Character.isUpperCase(c)) hasUpperCase = true;
                if (Character.isLowerCase(c)) hasLowerCase = true;
                if (Character.isDigit(c)) hasDigit = true;
            }

            if (hasUpperCase && hasLowerCase && hasDigit) {
                passwordValid = true;
            }
        }


        if (!nameLengthValid) {
            Toast.makeText(getActivity(), "الاسم يجب أن يكون أطول من 4 حروف", Toast.LENGTH_SHORT).show();
        } else if (!phoneValid) {
            Toast.makeText(getActivity(), "رقم الهاتف غير صالح. يجب أن يحتوي على 10 أرقام", Toast.LENGTH_SHORT).show();
        } else if (!emailValid) {
            Toast.makeText(getActivity(), "البريد الإلكتروني غير صالح", Toast.LENGTH_SHORT).show();
        } else if (!passwordValid) {
            Toast.makeText(getActivity(), "كلمة المرور يجب أن تحتوي على 6 حروف على الأقل، وحروف كبيرة، وحروف صغيرة، وأرقام", Toast.LENGTH_SHORT).show();
        } else if (!passwordsMatch) {
            Toast.makeText(getActivity(), "كلمة المرور غير متطابقة", Toast.LENGTH_SHORT).show();
        } else {
            // إذا كانت جميع البيانات صحيحة، نقوم بتسجيل المستخدم
            registerUser(email.getText().toString().trim(), password.getText().toString().trim());
        }
    }

    private void registerUser(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        addUserToFirestore(user);
                        Toast.makeText(getActivity(), "تم التسجيل بنجاح", Toast.LENGTH_SHORT).show();
                        MainActivity.loginFrame.setVisibility(View.VISIBLE);
                        MainActivity.homFrame.setVisibility(View.INVISIBLE);
                        MainActivity.instructionsFrame.setVisibility(View.INVISIBLE);
                        MainActivity.detailsfram.setVisibility(View.INVISIBLE);
                        MainActivity.firstRoundFrame.setVisibility(View.INVISIBLE);
                        MainActivity.roundFiveFrame.setVisibility(View.INVISIBLE);
                        MainActivity.roundFourFrame.setVisibility(View.INVISIBLE);
                        MainActivity.secondRoundFrame.setVisibility(View.INVISIBLE);
                        MainActivity.signupFrame.setVisibility(View.INVISIBLE);
                        MainActivity.thirdRoundFrame.setVisibility(View.INVISIBLE);
                        MainActivity.theEndFrame.setVisibility(View.INVISIBLE);
                    } else {
                        Toast.makeText(getActivity(), "فشل التسجيل: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void addUserToFirestore(FirebaseUser firebaseUser) {
        Map<String, Object> userMap = new HashMap<>();
        userMap.put("Name", name.getText().toString().trim());
        userMap.put("Email", email.getText().toString().trim());
        userMap.put("UserName", userName.getText().toString().trim());
        userMap.put("Phone", phone.getText().toString().trim());
        userMap.put("Score", 0);
        userMap.put( "Round",1);

        db.collection("clinet").document(userName.getText().toString().trim()).set(userMap);
        Log.d("TAG", "add user to firebasee");
    }
}
