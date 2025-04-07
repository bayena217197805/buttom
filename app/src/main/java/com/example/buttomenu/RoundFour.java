package com.example.buttomenu;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class RoundFour extends Fragment {

    private ImageView[] cards;
    private int[] images = {
            R.drawable.eagle, R.drawable.gazelle, R.drawable.lionn1, R.drawable.owl,
            R.drawable.giraffe, R.drawable.turtle, R.drawable.gazelle, R.drawable.giraffe,
            R.drawable.owl, R.drawable.lionn1, R.drawable.turtle, R.drawable.eagle
            ,R.drawable.panda1, R.drawable.panda22, R.drawable.panda1, R.drawable.panda22
            ,R.drawable.rabite1, R.drawable.panda22, R.drawable.rabite1, R.drawable.panda22
    };
    private int firstCard, secondCard;
    private int firstIndex, secondIndex;
    private boolean isBusy = false;

    private Handler handler = new Handler();
    // Timer variables
    private int timeRemaining = 240;  // Time in seconds (60 seconds)
    private Button buttonstart;
    private boolean isGameOver = false;
    private TextView scoreText;
    private TextView timerText;  // To display the timer on the screen
    private int helpCount = 0;  // Counter to track if help button is pressed
    private int[] helpIndexes = new int[2]; // Array to store the two cards


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_round_four, container, false);

        // Initialize ImageViews
        cards = new ImageView[]{
                view.findViewById(R.id.r4p1), view.findViewById(R.id.r4p2), view.findViewById(R.id.r4p3),
                view.findViewById(R.id.r4p4), view.findViewById(R.id.r4p5), view.findViewById(R.id.r4p6),
                view.findViewById(R.id.r4p7), view.findViewById(R.id.r4p8), view.findViewById(R.id.r4p9),
                view.findViewById(R.id.r4p10), view.findViewById(R.id.r4p11), view.findViewById(R.id.r4p12),
                view.findViewById(R.id.r4p13), view.findViewById(R.id.r4p14), view.findViewById(R.id.r4p15)
                , view.findViewById(R.id.r4p16), view.findViewById(R.id.r4p17), view.findViewById(R.id.r4p18),
                view.findViewById(R.id.r4p19), view.findViewById(R.id.r4p20)
        };
        // تعطيل النقر على البطاقات في البداية
        for (ImageView card : cards) {
            card.setEnabled(false);  // تعطيل النقر على البطاقات
        }

        List<Integer> tempImages = new ArrayList<>();
        for (int image : images) {
            tempImages.add(image);
        }
        Collections.shuffle(tempImages);
        for (int i = 0; i < images.length; i++) {
            images[i] = tempImages.get(i);
        }
        timerText = view.findViewById(R.id.timerText);
        buttonstart=view.findViewById(R.id.buttonstart);
        scoreText = view.findViewById(R.id.scoreText);
        scoreText.setText("Score: " + MainActivity.score); // ضبط القيمة الأولية
        ImageView helpButton = view.findViewById(R.id.help_button);  // Assuming you have a button with this ID
        helpButton.setEnabled(false);
        helpButton.setOnClickListener(v -> showHelp());

        buttonstart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (ImageView card : cards) {
                    card.setEnabled(true);  // تمكين النقر على البطاقات بعد الضغط على Start
                }
                helpButton.setEnabled(true);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (timeRemaining > 0 && !isGameOver) {
                            timeRemaining--;
                            timerText.setText("Time: " + timeRemaining + "s");
                            handler.postDelayed(this, 1000);
                        } else if (timeRemaining == 0 && !isGameOver) {
                            gameOver(false);
                        }
                    }
                }, 1000);
            }
        });
        // Set click listeners
        for (int i = 0; i < cards.length; i++) {
            final int index = i;
            cards[i].setOnClickListener(v -> handleCardClick(index));
        }

        return view;
    }


    private void handleCardClick(int index) {
        if (isGameOver || isBusy || cards[index].getTag() != null) return;

        cards[index].setImageResource(images[index]);
        if (firstCard == 0) {
            firstCard = images[index];
            firstIndex = index;
        } else {
            secondCard = images[index];
            secondIndex = index;
            isBusy = true;
            handler.postDelayed(this::checkMatch, 1000);
        }
    }

    private void checkMatch() {
        if (firstCard == secondCard) {
            cards[firstIndex].setTag("matched");
            cards[secondIndex].setTag("matched");

        } else {
            cards[firstIndex].setImageResource(R.drawable.backcardd);
            cards[secondIndex].setImageResource(R.drawable.backcardd);
        }
        firstCard = secondCard = 0;
        isBusy = false;
        // Check if the game is over (all cards matched)
        boolean allMatched = true;
        for (ImageView card : cards) {
            if (card.getTag() == null) {
                allMatched = false;
                break;
            }
        }

        if (allMatched) {
            gameOver(true);  // Player wins
        }
    }

    private void hideCards() {
        for (ImageView card : cards) {
            card.setImageResource(R.drawable.backcardd);
        }
    }
    private void gameOver(boolean won) {
        isGameOver = true;
        if (won) {
            if (timeRemaining > 120) {
                MainActivity.score += 20;  // إذا حلها في أقل من نصف الوقت
            } else {
                MainActivity.score += 10;  // إذا حلها بعد نصف الوقت ولكن قبل انتهائه
            }

// خصم النقاط إذا استخدم المساعدة
            MainActivity.score -= (helpCount * 5);
            if (MainActivity.score < 0) MainActivity.score = 0; // لا نسمح بأن يكون الـ score سالبًا
            Toast.makeText(getActivity(), "You Win! Moving to the next round...", Toast.LENGTH_SHORT).show();
            scoreText.setText("Score: " + MainActivity.score); // ضبط القيمة الأولية
            MainActivity.currentRound = 5;            String userEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();
            FirebaseFirestore.getInstance().collection("clinet")
                    .whereEqualTo("Email", userEmail)
                    .get()
                    .addOnSuccessListener(queryDocumentSnapshots -> {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            String docId = queryDocumentSnapshots.getDocuments().get(0).getId();
                            FirebaseFirestore.getInstance().collection("clinet")
                                    .document(docId)
                                    .update("Score", MainActivity.score,"Round", MainActivity.currentRound);
                        }
                    });

            // تأخير بسيط لعرض رسالة الفوز قبل الانتقال
            handler.postDelayed(() -> {
                MainActivity.firstRoundFrame.setVisibility(View.INVISIBLE);
                MainActivity.secondRoundFrame.setVisibility(View.INVISIBLE);
                MainActivity.thirdRoundFrame.setVisibility(View.INVISIBLE);
                MainActivity.signupFrame.setVisibility(View.INVISIBLE);
                MainActivity.loginFrame.setVisibility(View.INVISIBLE);
                MainActivity.instructionsFrame.setVisibility(View.INVISIBLE);
                MainActivity.detailsfram.setVisibility(View.INVISIBLE);
                MainActivity.roundFourFrame.setVisibility(View.INVISIBLE);
                MainActivity.homFrame.setVisibility(View.INVISIBLE);
                MainActivity.roundFiveFrame.setVisibility(View.VISIBLE);
            }, 2000);
        } else {
            Toast.makeText(getActivity(), "Time's up! You Lose!", Toast.LENGTH_SHORT).show();
        }
    }
    private void showHelp() {
        // منع استخدام المساعدة إذا كان هناك بطاقة واحدة مكشوفة فقط
        if (helpCount == 0 && firstCard == 0) { // السماح بالمساعدة فقط إذا لم يتم كشف بطاقة بالفعل
            int firstHelpIndex = -1;
            int secondHelpIndex = -1;

            // البحث عن زوج متطابق غير مكشوف
            for (int i = 0; i < images.length; i++) {
                if (cards[i].getTag() == null) { // البطاقة غير مطابقة بعد
                    for (int j = i + 1; j < images.length; j++) {
                        if (cards[j].getTag() == null && images[i] == images[j]) { // وجدنا تطابقًا
                            firstHelpIndex = i;
                            secondHelpIndex = j;
                            break;
                        }
                    }
                }
                if (firstHelpIndex != -1 && secondHelpIndex != -1) {
                    break; // وجدنا زوجًا متطابقًا، لا داعي للمتابعة
                }
            }

            // إذا وجدنا زوجًا متطابقًا، نقوم بإظهاره
            if (firstHelpIndex != -1 && secondHelpIndex != -1) {
                cards[firstHelpIndex].setImageResource(images[firstHelpIndex]);
                cards[secondHelpIndex].setImageResource(images[secondHelpIndex]);

                // إخفاء البطاقتين بعد ثانيتين
                int finalFirstHelpIndex = firstHelpIndex;
                int finalSecondHelpIndex = secondHelpIndex;
                handler.postDelayed(() -> {
                    cards[finalFirstHelpIndex].setImageResource(R.drawable.backcardd);
                    cards[finalSecondHelpIndex].setImageResource(R.drawable.backcardd);
                }, 2000);

                helpCount++; // منع استخدام المساعدة أكثر من مرة
            }
        }
    }

}