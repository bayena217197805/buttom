package com.example.buttomenu;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import java.util.Arrays;
import java.util.Collections;

public class RoundFive extends Fragment {

    private ImageView[] cards;
    private int[] images = {
            R.drawable.eagle, R.drawable.gazelle, R.drawable.lionn1, R.drawable.owl,
            R.drawable.giraffe, R.drawable.turtle, R.drawable.gazelle, R.drawable.giraffe,
            R.drawable.owl, R.drawable.lionn1, R.drawable.turtle, R.drawable.eagle
            ,R.drawable.panda1, R.drawable.panda22, R.drawable.panda1, R.drawable.panda22
            ,R.drawable.rabite1, R.drawable.bird1, R.drawable.rabite1, R.drawable.bird1
            ,R.drawable.snake1, R.drawable.dolfen1, R.drawable.elephent1, R.drawable.cat1
            ,R.drawable.elephent1, R.drawable.snake1, R.drawable.cat1, R.drawable.dolfen1

    };
    private int firstCard, secondCard;
    private int firstIndex, secondIndex;
    private boolean isBusy = false;
    private int score = 0;
    private Handler handler = new Handler();
    // Timer variables
    private int timeRemaining = 60;  // Time in seconds (60 seconds)
    private boolean isGameOver = false;
    private TextView timerText;  // To display the timer on the screen
    private int helpCount = 0;  // Counter to track if help button is pressed
    private int[] helpIndexes = new int[2]; // Array to store the two cards


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_round_five, container, false);

        // Initialize ImageViews
        cards = new ImageView[]{
                view.findViewById(R.id.r5p1), view.findViewById(R.id.r5p2), view.findViewById(R.id.r5p3),
                view.findViewById(R.id.r5p4), view.findViewById(R.id.r5p5), view.findViewById(R.id.r5p6),
                view.findViewById(R.id.r5p7), view.findViewById(R.id.r5p8), view.findViewById(R.id.r5p9),
                view.findViewById(R.id.r5p10), view.findViewById(R.id.r5p11), view.findViewById(R.id.r5p12),
                view.findViewById(R.id.r5p13), view.findViewById(R.id.r5p14), view.findViewById(R.id.r5p15)
                , view.findViewById(R.id.r5p16), view.findViewById(R.id.r5p17), view.findViewById(R.id.r5p18),
                view.findViewById(R.id.r5p19), view.findViewById(R.id.r5p20),
                view.findViewById(R.id.r5p21), view.findViewById(R.id.r5p22),
                view.findViewById(R.id.r5p23), view.findViewById(R.id.r5p24),
                view.findViewById(R.id.r5p25), view.findViewById(R.id.r5p26),
                view.findViewById(R.id.r5p27), view.findViewById(R.id.r5p28)
        };

        // Shuffle images
        Collections.shuffle(Arrays.asList(images));
        timerText = view.findViewById(R.id.timerText);
        ImageView helpButton = view.findViewById(R.id.help_button);  // Assuming you have a button with this ID
        helpButton.setOnClickListener(v -> showHelp());

        // Show cards for 5 seconds before flipping back
        for (int i = 0; i < cards.length; i++) {
            cards[i].setImageResource(images[i]);
        }
        handler.postDelayed(this::hideCards, 10000);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (timeRemaining > 0 && !isGameOver) {
                    timeRemaining--;
                    timerText.setText("Time: " + timeRemaining + "s");
                    handler.postDelayed(this, 1000);  // Update every second
                } else if (timeRemaining == 0 && !isGameOver) {
                    // Game over: time is up and not all cards are matched
                    gameOver(false);
                }
            }
        }, 1000);

        // Set click listeners
        for (int i = 0; i < cards.length; i++) {
            final int index = i;
            cards[i].setOnClickListener(v -> handleCardClick(index));
        }

        return view;
    }


    private void handleCardClick(int index) {
        if (isBusy || cards[index].getTag() != null) return;

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
            score += 10;
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
        String message = won ? "You Win!" : "Time's up! You Lose!";
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
        // You can also add logic to stop the game, restart it, or show a dialog.
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