package com.calculator;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    String expression;
    TextView maintext;
    Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn0, btnplus, btnminus, btndivide, btnumn, btnclear, btnfloat, btnproc, btnequal, btndel;
    private int[] btnId = {
            R.id.btnclear, R.id.btndivide,R.id.btnproc, R.id.btndel,
            R.id.btn7, R.id.btn8, R.id.btn9, R.id.btnumn,
            R.id.btn4, R.id.btn5, R.id.btn6, R.id.btnminus,
            R.id.btn1, R.id.btn2, R.id.btn3, R.id.btnplus,
            R.id.btn0, R.id.btnfloat, R.id.btnequal
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
        maintext = findViewById(R.id.maintext);

        Calculator();
    }

    private double Calculator_Algorithm() {
        try {
            String text = maintext.getText().toString();
            String[] tokens = text.split("(?<=[-+*/])|(?=[-+*/])");
            if (tokens.length < 3) return 0;

            double result = Double.parseDouble(tokens[0]);

            double buf_res = 0;
            for (int i = 1; i < tokens.length - 1; i+=2)
            {
                double first, second;
                if(tokens[i].equals("*") || tokens[i].equals("/") ) {

                    if(tokens[i-1].contains("%"))
                    {
                        String buf = tokens[i-1].replaceAll("%","");
                        first = Double.parseDouble(buf);
                        first = first / 100;
                    }
                    else { first = Double.parseDouble(tokens[i-1]); }

                    if(tokens[i+1].contains("%"))
                    {
                        String buf = tokens[i+1].replaceAll("%","");
                        second = Double.parseDouble(buf);
                        second = second / 100;
                    }
                    else { second = Double.parseDouble(tokens[i+1]); }


                    buf_res = tokens[i].equals("*") ? first * second : first / second;
                    tokens[i-1] = null;
                    tokens[i] =  null;
                    tokens[i+1] = String.valueOf(buf_res);
                }

            }
            List<String> tokenList = new ArrayList<>(Arrays.asList(tokens));
            tokenList.removeIf(token -> token == null);
            String[] finalTokens = tokenList.toArray(new String[0]);

            for (int i = 1; i < finalTokens.length - 1; i += 2) {
                double nextNum;
                String operator = finalTokens[i];
                if (i + 1 >= tokens.length) break;
                if(finalTokens[i+1].contains("%"))
                {
                    String buf = finalTokens[i+1].replaceAll("%","");
                    nextNum = Double.parseDouble(buf);
                    nextNum = result *  nextNum / 100;
                }
                else { nextNum = Double.parseDouble(finalTokens[i + 1]); }

                switch (operator) {
                    case "+":
                        result += nextNum;
                        break;
                    case "-":
                        result -= nextNum;
                        break;
                    case "*":
                        result *= nextNum;
                        break;
                    case "/":
                        if(nextNum == 0){ return 0; }
                        result /= nextNum;
                        break;

                }
            }
            return result;
        } catch (Exception e) {
            return 0;
        }
    }

    private void Text_lastdel()
    {
        String text = maintext.getText().toString();
        if (!text.isEmpty()) {
            maintext.setText(text.substring(0, text.length() - 1));
        }


    }
    private void Calculator()
    {
        for (int i = 0; i < btnId.length; i++){

            Button btn = findViewById(btnId[i]);
            btn.setOnClickListener(v -> {
                switch(btn.getText().toString())
                {
                    case "0":
                        maintext.append("0");
                        break;
                    case "1":
                        maintext.append("1");
                        break;
                    case "2":
                        maintext.append("2");
                        break;
                    case "3":
                        maintext.append("3");
                        break;
                    case "4":
                        maintext.append("4");
                        break;
                    case "5":
                        maintext.append("5");
                        break;
                    case "6":
                        maintext.append("6");
                        break;
                    case "7":
                        maintext.append("7");
                        break;
                    case "8":
                        maintext.append("8");
                        break;
                    case "9":
                        maintext.append("9");
                        break;
                    case "+":
                        maintext.append("+");
                        break;
                    case "-":
                        maintext.append("-");
                        break;
                    case "/":
                        maintext.append("/");
                        break;
                    case "*":
                        maintext.append("*");
                        break;
                    case ".":
                        maintext.append(".");
                        break;
                    case "%":
                        maintext.append("%");
                        break;
                    case "C":
                        maintext.setText("");
                        break;
                    case "=":
                        double result = Calculator_Algorithm();
                        maintext.setText(String.valueOf(result));
                        break;
                    case "del":
                        Text_lastdel();
                        break;


                }
            });

        }


    }
}