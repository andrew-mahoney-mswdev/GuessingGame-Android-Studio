package com.example.mahoneandr.guessinggame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView txtMessage;
    EditText editGuess;
    TextView txtUpdate;
    Button btnGiveUp;
    Button btnNewGame;
    Button btnNewGame2;

    Game game;
    int number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtMessage = (TextView)findViewById(R.id.txtMessage);
        editGuess = (EditText)findViewById(R.id.editGuess);
        txtUpdate = (TextView)findViewById(R.id.txtUpdate);
        btnGiveUp = (Button)findViewById(R.id.btnGiveUp);
        btnNewGame = (Button)findViewById(R.id.btnNewGame);

        game = new Game();
        number = game.getNumber();

        editGuess.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int id, KeyEvent e) {
                if (e.getAction() == 1) return false;

                String input = editGuess.getText().toString();
                editGuess.setText("");

                if (!Game.isNumeral(input)) {
                    txtUpdate.setText(input + " is not a number.");
                    return true;
                };

                int guess = Integer.parseInt(input);

                if (guess < number) {
                    txtUpdate.setText(guess + " is too low. Try again.");
                } else if (guess > number) {
                    txtUpdate.setText(guess + " is too high. Try again.");
                } else {
                    setContentView(R.layout.second_layout);

                    btnNewGame2 = (Button)findViewById(R.id.btnNewGame2);
                    btnNewGame2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.example.mahoneandr.guessinggame");
                            if (launchIntent != null) {
                                startActivity(launchIntent);
                            }
                            android.os.Process.killProcess(android.os.Process.myPid());
                        }
                    });
                }

                return true;
            }
        });

        btnGiveUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtUpdate.setText("I was thinking of " + number + ". Better luck next time.");
            }
        });

        btnGiveUp.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!editGuess.hasFocus()) {
                    editGuess.requestFocus();
                }
            }
        });

        btnNewGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                game.newGame();
                number = game.getNumber();
                txtUpdate.setText("");
            }
        });
    }
}
