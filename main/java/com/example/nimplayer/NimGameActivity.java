package com.example.nimplayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class NimGameActivity extends AppCompatActivity {
    private EditText pile1EditText, pile2EditText, pile3EditText, pile4EditText,nEditText,rEditText;
    private Button submitButton;

    private int pile1, pile2, pile3, pile4;
    private boolean isFirstTurn = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nEditText=findViewById(R.id.nedittext);
        rEditText=findViewById(R.id.redittext);
        pile1EditText = findViewById(R.id.pile1_edittext);
        pile2EditText = findViewById(R.id.pile2_edittext);
        pile3EditText = findViewById(R.id.pile3_edittext);
        pile4EditText = findViewById(R.id.pile4_edittext);
        submitButton = findViewById(R.id.submit_button);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFirstTurn) {
                    playComputerTurn();
                    isFirstTurn = false;
                } else {
                    playUserTurn();
                }
            }
        });

        initializePiles();
    }

    private void initializePiles() {
        pile1 = 1;
        pile2 = 3;
        pile3 = 5;
        pile4 = 7;

        updatePileEditText(pile1EditText, pile1);
        updatePileEditText(pile2EditText, pile2);
        updatePileEditText(pile3EditText, pile3);
        updatePileEditText(pile4EditText, pile4);
    }

    private void playUserTurn() {
        int selectedPile;
        int numToRemove;

        try {
            selectedPile = Integer.parseInt(nEditText.getText().toString());
            numToRemove = Integer.parseInt(rEditText.getText().toString());
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Please enter valid numbers", Toast.LENGTH_SHORT).show();
            return;
        }

        if (selectedPile < 1 || selectedPile > 4 || numToRemove < 1 || numToRemove > getPileCount(selectedPile)) {
            Toast.makeText(this, "Invalid move", Toast.LENGTH_SHORT).show();
            return;
        }

        updatePile(selectedPile, getPileCount(selectedPile) - numToRemove);

        if (isGameOver()) {
            showGameOverMessage(true);
        } else {
            playComputerTurn();
        }
    }

    private void playComputerTurn() {
                    // Implement your computer player logic here
                    // For simplicity, let's assume the computer always removes 1 from the first non-empty pile
                    int selectedPile = 0;
                    for (int i = 1; i <= 4; i++) {
                        if (getPileCount(i) > 0) {
                            selectedPile = i;
                break;
            }
        }

        updatePile(selectedPile, getPileCount(selectedPile) - 1);

        if (isGameOver()) {
            showGameOverMessage(false);
        }
    }

    private int getPileCount(int pile) {
        switch (pile) {
            case 1:
                return pile1;
            case 2:
                return pile2;
            case 3:
                return pile3;
            case 4:
                return pile4;
            default:
                return 0;
        }
    }

    private void updatePile(int pile, int count) {
        switch (pile) {
            case 1:
                pile1 = count;
                updatePileEditText(pile1EditText, pile1);
                break;
            case 2:
                pile2 = count;
                updatePileEditText(pile2EditText, pile2);
                break;
            case 3:
                pile3 = count;
                updatePileEditText(pile3EditText, pile3);
                break;
            case 4:
                pile4 = count;
                updatePileEditText(pile4EditText, pile4);
                break;
        }
    }

    private void updatePileEditText(EditText editText, int count) {
        editText.setText(String.valueOf(count));
    }

    private boolean isGameOver() {
        return pile1 == 0 && pile2 == 0 && pile3 == 0 && pile4 == 0;
    }

    private void showGameOverMessage(boolean isComputerWinner) {
        if (isComputerWinner) {
            Toast.makeText(this, "Computer wins!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "You win!", Toast.LENGTH_SHORT).show();
        }

        // Reset the game
        initializePiles();
        isFirstTurn = true;
    }
}