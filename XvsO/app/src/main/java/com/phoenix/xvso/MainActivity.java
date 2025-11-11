package com.phoenix.xvso;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.BreakIterator;

public class MainActivity extends AppCompatActivity {

    private int n = 0, Xwin = 0, Owin = 0;
    private TextView countX, countO;
    private int[] btnId = {
            R.id.btn00, R.id.btn01, R.id.btn02,
            R.id.btn10, R.id.btn11, R.id.btn12,
            R.id.btn20, R.id.btn21, R.id.btn22
    };

    private int[][] winline = {
            {btnId[0], btnId[1], btnId[2]},
            {btnId[3], btnId[4], btnId[5]},
            {btnId[6], btnId[7], btnId[8]},
            {btnId[0], btnId[3], btnId[6]},
            {btnId[1], btnId[4], btnId[7]},
            {btnId[2], btnId[5], btnId[8]},
            {btnId[0], btnId[4], btnId[8]},
            {btnId[6], btnId[4], btnId[2]}
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        countX = findViewById(R.id.count_x);
        countO = findViewById(R.id.count_o);
        countX.setText(String.valueOf(Xwin));
        countO.setText(String.valueOf(Owin));

        XvsO();
    }

    private boolean checkWin() {
        for (int i = 0; i < winline.length; i++)
        {
            Button btn1 = findViewById(winline[i][0]);
            Button btn2 = findViewById(winline[i][1]);
            Button btn3 = findViewById(winline[i][2]);

            String text1 = (String) btn1.getText();
            String text2 = (String) btn2.getText();
            String text3 = (String) btn3.getText();

            if (!text1.isEmpty() && text1.equals(text2) && text2.equals(text3)) {
                if (text1.equals("X")) {
                    Toast.makeText(this,"ПОБЕДА Крестика X", Toast.LENGTH_LONG).show();
                    Xwin++;
                    countX.setText(String.valueOf(Xwin));
                } else {
                    Toast.makeText(this,"ПОБЕДА Нолика O", Toast.LENGTH_LONG).show();
                    Owin++;
                    countO.setText(String.valueOf(Owin));
                }

                return true;
            }

        }
        return false;

    }

    private void clearall()
    {
        for (int i = 0; i < btnId.length; i++)
        {
            Button button = findViewById(btnId[i]);
            button.setText("");
        }
    }

    private void XvsO() {
        for (int i = 0; i < btnId.length ; i++ ) {
            Button button = findViewById(btnId[i]);
            button.setOnClickListener(v -> {
                Button clickedButton = (Button) v;
                if (clickedButton.getText().toString().isEmpty()) {
                    if (n % 2 == 0) {
                        clickedButton.setText("X");
                        n++;
                    } else {
                        clickedButton.setText("O");
                        n++;
                    }
                }
                if(checkWin() || n == 9) {
                    n = 0;
                    clearall();
                }

            });
        }

    }
}