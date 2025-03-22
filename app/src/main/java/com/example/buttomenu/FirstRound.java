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

public class FirstRound extends Fragment {

    private ImageView[] cards;
    private int[] images = {
            R.drawable.eagle, R.drawable.gazelle, R.drawable.lionn1, R.drawable.owl,
            R.drawable.eagle, R.drawable.gazelle, R.drawable.lionn1, R.drawable.owl
    };
    private int firstCard, secondCard;
    private int firstIndex, secondIndex;
    private boolean isBusy = false;
    private int score = 0;
    private Handler handler = new Handler();
    private int timeRemaining = 60;  // 60 ثانية فقط للمرحلة الأولى
    private boolean isGameOver = false;
    private TextView timerText;
    private int helpCount = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first_round, container, false);

        // ربط العناصر من الواجهة
        cards = new ImageView[]{
                view.findViewById(R.id.r1p1), view.findViewById(R.id.r1p2), view.findViewById(R.id.r1p3),
                view.findViewById(R.id.r1p4), view.findViewById(R.id.r1p5), view.findViewById(R.id.r1p6),
                view.findViewById(R.id.r1p7), view.findViewById(R.id.r1p8)
        };

        Collections.shuffle(Arrays.asList(images));  // خلط الصور

        timerText = view.findViewById(R.id.timerText);
        ImageView helpButton = view.findViewById(R.id.help_button);
        helpButton.setOnClickListener(v -> showHelp());

        // إظهار البطاقات لمدة 5 ثوانٍ قبل إخفائها
        for (int i = 0; i < cards.length; i++) {
            cards[i].setImageResource(images[i]);
        }
        handler.postDelayed(this::hideCards, 5000);

        // تشغيل العداد التنازلي
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

        // ضبط مستمع النقر لكل بطاقة
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

        // التحقق مما إذا كانت كل البطاقات قد تم كشفها
        boolean allMatched = true;
        for (ImageView card : cards) {
            if (card.getTag() == null) {
                allMatched = false;
                break;
            }
        }

        if (allMatched) {
            gameOver(true);
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
            Toast.makeText(getActivity(), "You Win! Moving to the next round...", Toast.LENGTH_SHORT).show();

            // تأخير بسيط لعرض رسالة الفوز قبل الانتقال
            handler.postDelayed(() -> {
                MainActivity.firstRoundFrame.setVisibility(View.INVISIBLE);
                MainActivity.secondRoundFrame.setVisibility(View.VISIBLE);
                MainActivity.thirdRoundFrame.setVisibility(View.INVISIBLE);
                MainActivity.signupFrame.setVisibility(View.INVISIBLE);
                MainActivity.loginFrame.setVisibility(View.INVISIBLE);
                MainActivity.instructionsFrame.setVisibility(View.INVISIBLE);
                MainActivity.detailsfram.setVisibility(View.INVISIBLE);
                MainActivity.roundFourFrame.setVisibility(View.INVISIBLE);
                MainActivity.roundFiveFrame.setVisibility(View.INVISIBLE);
            }, 2000);
        } else {
            Toast.makeText(getActivity(), "Time's up! You Lose!", Toast.LENGTH_SHORT).show();
        }
    }


    private void showHelp() {
        if (helpCount == 0) {
            int firstHelpIndex = -1, secondHelpIndex = -1;

            // البحث عن زوج متطابق غير مكشوف
            for (int i = 0; i < cards.length; i++) {
                if (cards[i].getTag() == null) {
                    for (int j = i + 1; j < cards.length; j++) {
                        if (cards[j].getTag() == null && images[i] == images[j]) {
                            firstHelpIndex = i;
                            secondHelpIndex = j;
                            break;
                        }
                    }
                }
                if (firstHelpIndex != -1) break;
            }

            if (firstHelpIndex != -1 && secondHelpIndex != -1) {
                // عرض البطاقتين المتطابقتين
                cards[firstHelpIndex].setImageResource(images[firstHelpIndex]);
                cards[secondHelpIndex].setImageResource(images[secondHelpIndex]);

                int finalFirstHelpIndex = firstHelpIndex;
                int finalSecondHelpIndex = secondHelpIndex;
                handler.postDelayed(() -> {
                    if (cards[finalFirstHelpIndex].getTag() == null)
                        cards[finalFirstHelpIndex].setImageResource(R.drawable.backcardd);
                    if (cards[finalSecondHelpIndex].getTag() == null)
                        cards[finalSecondHelpIndex].setImageResource(R.drawable.backcardd);
                }, 2000);
            }

            helpCount++; // السماح باستخدام زر المساعدة مرة واحدة فقط
        }
    }
}
