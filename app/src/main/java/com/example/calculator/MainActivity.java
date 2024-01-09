package com.example.calculator;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    TextView input;
    ArrayList<String> expression = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        input = findViewById(R.id.input_field);
    }

    public void onClearButtonClick(View view) {
        input.setText("0");
        expression.clear();
    }

    public void onDeleteButtonClick(View view) {
        String currentText = input.getText().toString();
        if (!currentText.isEmpty() && !currentText.equals("0")) {
            input.setText(currentText.substring(0, currentText.length() - 1));
        }
    }

    public void onButtonClick(View view) {
        Button button = (Button) view;
        String data = button.getText().toString();

        String currentValue = input.getText().toString();

        if (currentValue.equals("0")) {
            input.setText(data);
            input.setTextColor( getResources().getColor(R.color.white));
        } else {
            input.setText(currentValue + data);
            input.setTextColor( getResources().getColor(R.color.white));
        }
    }

    public void onOperatorButtonClick(View view) {
        Button button = (Button) view;
        String data = button.getText().toString();
        String currentValue = input.getText().toString();

        // If the expression list is not empty, evaluate the expression

        expression.add(currentValue);
        expression.add(data);

        input.setText("0");
        input.setTextColor( getResources().getColor(R.color.white));
    }

    private void evaluateExpression() {
        if (expression.size() >= 3) {
            double result = Double.parseDouble(expression.get(0));

            for (int i = 1; i < expression.size(); i += 2) {
                String operator = expression.get(i);
                double num = Double.parseDouble(expression.get(i + 1));

                result = performOperation(result, operator, num);
            }

            // Display the final result
            input.setText(String.valueOf(result));
            input.setTextColor( getResources().getColor(R.color.green));
            expression.clear();

        }
    }

    public void onEqualsButtonClick(View view) {
        String currentValue = input.getText().toString();
        expression.add(currentValue);


            evaluateExpression();

    }


    private double performOperation(double num1, String operator, double num2) {
        switch (operator) {
            case "+":
                return num1 + num2;
            case "-":
                return num1 - num2;
            case "*":
                return num1 * num2;
            case "/":
                if (num2 != 0) {
                    return num1 / num2;
                } else {
                    // Division by zero error
                    Log.e("Calculator", "Division by zero error");
                    return 0;
                }
            default:
                return 0;
        }
    }
}
