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

public class SecondRound extends Fragment {

    private ImageView[] cards;
    private int[] images = {
            R.drawable.eagle, R.drawable.gazelle, R.drawable.lionn1, R.drawable.owl,
            R.drawable.giraffe, R.drawable.turtle, R.drawable.gazelle, R.drawable.giraffe,
            R.drawable.owl, R.drawable.lionn1, R.drawable.turtle, R.drawable.eagle
    };
    private int firstCard, secondCard;
    private int firstIndex, secondIndex;
    private boolean isBusy = false;
    private int score = 0;
    private Handler handler = new Handler();
    // Timer variables
    private int timeRemaining = 60;  // Time in seconds (30 seconds)
    private boolean isGameOver = false;
    private TextView timerText;  // To display the timer on the screen
    private int helpCount = 0;  // Counter to track if help button is pressed
    private int[] helpIndexes = new int[2]; // Array to store the two cards


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_second_round, container, false);

        // Initialize ImageViews
        cards = new ImageView[]{
                view.findViewById(R.id.r2p1), view.findViewById(R.id.r2p2), view.findViewById(R.id.r2p3),
                view.findViewById(R.id.r2p4), view.findViewById(R.id.r2p5), view.findViewById(R.id.r2p6),
                view.findViewById(R.id.r2p7), view.findViewById(R.id.r2p8), view.findViewById(R.id.r2p9),
                view.findViewById(R.id.r2p10), view.findViewById(R.id.r2p11), view.findViewById(R.id.r2p12)
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
        if (helpCount == 0) {
            // Randomly choose two unmatched cards
            int firstHelpIndex = -1;
            int secondHelpIndex = -1;

            // Find two unmatched cards
            for (int i = 0; i < cards.length; i++) {
                if (cards[i].getTag() == null) {
                    if (firstHelpIndex == -1) {
                        firstHelpIndex = i;
                    } else {
                        secondHelpIndex = i;
                        break;
                    }
                }
            }

            if (firstHelpIndex != -1 && secondHelpIndex != -1) {
                // Show the two cards temporarily
                cards[firstHelpIndex].setImageResource(images[firstHelpIndex]);
                cards[secondHelpIndex].setImageResource(images[secondHelpIndex]);
                helpIndexes[0] = firstHelpIndex;
                helpIndexes[1] = secondHelpIndex;

                // Hide them after 2 seconds
                int finalFirstHelpIndex = firstHelpIndex;
                int finalSecondHelpIndex = secondHelpIndex;
                handler.postDelayed(() -> {
                    cards[finalFirstHelpIndex].setImageResource(R.drawable.backcardd);
                    cards[finalSecondHelpIndex].setImageResource(R.drawable.backcardd);
                }, 2000);  // Hide after 2 seconds
            }

            helpCount++;  // Ensure the help button only works once
        }
    }
}