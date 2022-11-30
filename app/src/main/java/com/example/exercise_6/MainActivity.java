package com.example.exercise_6;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.Random;
import java.util.Timer;

public class MainActivity extends AppCompatActivity {

    private TextView number;
    private TextView prime;
    private TextView nonPrime;
    private ImageView answerImage;
    private int selectedNumber;
    private int wrongAnswerCount = 0;
    int seconds = 0;
    Handler handler = new Handler();
    Runnable runnable;
    int delay = 3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        number = findViewById(R.id.primeNumberTV);
        prime = findViewById(R.id.primeTV);
        nonPrime = findViewById(R.id.nonPrimeTV);
        answerImage = findViewById(R.id.answerImageView);

        prime.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                nonPrime.setClickable(false);
                prime.setClickable(false);
                boolean isPrime = checkPrimeNumber(selectedNumber);

                if (isPrime) {
                    answerImage.setImageResource(R.drawable.right);
                }else{
                    answerImage.setImageResource(R.drawable.wrong);
                    wrongAnswerCount += 1;
                }
            }
        });
        nonPrime.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                nonPrime.setClickable(false);
                prime.setClickable(false);
                boolean isPrime = checkPrimeNumber(selectedNumber);

                if (isPrime ){
                    answerImage.setImageResource(R.drawable.wrong);
                    wrongAnswerCount += 1;
                }else{
                    answerImage.setImageResource(R.drawable.right);
                }
            }
        });

        startGame();
    }

    private static boolean checkPrimeNumber(int number) {

        if(number == 1 || number == 0){
            return false;
        }

        int i = 2;
        while (i * i <= number){
            if (number % i == 0){
                return false;
            }
            i = i + 1;
        }
        return true;
    }

    private void startGame(){
       seconds = 0;
      handler.removeCallbacks(runnable);
        nonPrime.setClickable(true);
        prime.setClickable(true);
        answerImage.setImageDrawable(null);
        Random randomNum = new Random();
        selectedNumber = randomNum.nextInt(1000);
        number.setText(Integer.toString(selectedNumber));
        startTimer();
    }

    private void startTimer(){
//        timer = new Timer();
//        new CountDownTimer(30000, 1000){
//            public void onTick(long millisUntilFinished){
//                timerAction();
//            }
//            public  void onFinish(){
////                timerAction();
//            }
//        }.start();

        handler.postDelayed(runnable = new Runnable() {
            public void run() {
                handler.postDelayed(runnable, delay);
                timerAction();
            }
        }, delay);
    }

   private void timerAction(){
       if(seconds == 3) {
            if (wrongAnswerCount == 5) {
                number.setText("");
                wrongAnswerCount = 0;
                answerImage.setImageDrawable(null);
                handler.removeCallbacks(runnable);

                new AlertDialog.Builder(this)
                        .setIcon(R.drawable.right)
                        .setPositiveButton("Reset", (dialog, which) -> startGame())
                        .setTitle("Game Over!!")
                        .setNegativeButton("OK", (dialog, which) -> number.setText("Game Over"))
                        .create()
                        .show();
            }else{
                startGame();
            }
        }else{
            seconds += 1;
        }
    }
}