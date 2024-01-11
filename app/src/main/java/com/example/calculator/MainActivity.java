package com.example.calculator;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import com.google.gson.Gson;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    TextView displayField;
    private boolean isButtonProcessing = false;
    ImageButton history;
    ArrayList<String> historyExpression = new ArrayList<>();
   ArrayList<String> historyResult = new ArrayList<>();
    ArrayList<String> expression = new ArrayList<>();
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        displayField = findViewById(R.id.display_field);
        history = findViewById(R.id.historyButton);
        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                    displayField.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.white));
                    displayField.setText(null);
                try
                {
                    ArrayList<String> savedExpressions = MyPreferences.getHistoryExpressions(getApplicationContext());
                    ArrayList<String> savedResults = MyPreferences.getHistoryResults(getApplicationContext());

                    int size=savedExpressions.size();
                    int i =0;
                    displayField.setTextSize(35);

                    while (i < size){
                        String expressionText = savedExpressions.get(i);
                       String resultText = savedResults.get(i);
                        String currentText = displayField.getText().toString();

                        String displayText = expressionText + "=\n" +resultText+ "\n";
                        if (!currentText.isEmpty()) {
                            displayField.setText(currentText + "\n" + displayText);

                        } else {
                            displayField.setText(displayText);

                        }


                       i++;
                    }
                }
                catch (NullPointerException nullPointerException){

                    Toast.makeText(MainActivity.this,"No history",Toast.LENGTH_SHORT).show();
                }
                 catch (IndexOutOfBoundsException indexOutOfBoundsException){
                     Toast.makeText(MainActivity.this,"Not Found",Toast.LENGTH_SHORT).show();
                 }

            }
        });
    }

    public void onClearButtonClick(View view) {
        displayField.setTextColor( getResources().getColor(R.color.white));
        displayField.setText(null);
        expression.clear();
    }



    public void onDeleteButtonClick(View view) {
        displayField.setTextColor( getResources().getColor(R.color.white));
        String currentText = displayField.getText().toString();
        if (!currentText.isEmpty() && !currentText.equals("0")) {
            displayField.setText(currentText.substring(0, currentText.length() - 1));
        }
    }

    public void onButtonClick(View view) {
        displayField.setTextColor( getResources().getColor(R.color.white));
        Button button = (Button) view;
        String data = button.getText().toString();

        String currentValue = displayField.getText().toString();

        if (currentValue.isEmpty()) {
            displayField.setText(data);

        } else {
            displayField.setText(currentValue + data);

        }
    }
public void onButtonSignClick(View view){
        displayField.setText("-");


}
    public void onOperatorButtonClick(View view)
    {
        Button button = (Button) view;
        String data = button.getText().toString();
        String currentValue = displayField.getText().toString();
         if (currentValue.isEmpty())
         {
             Toast.makeText(MainActivity.this,"Invalid format used",Toast.LENGTH_SHORT).show();
         }
         else {
             expression.add(currentValue);
             expression.add(data);
             displayField.setText(null);
         }


         displayField.setTextColor( getResources().getColor(R.color.white));
    }
    public void onEqualsButtonClick(View view) {

        if(!isButtonProcessing){
            try {
                isButtonProcessing = true;
                String currentValue = displayField.getText().toString();
                expression.add(currentValue);
                String historyExpressionString = String.join("", expression);
                historyExpression.add(historyExpressionString);
                MyPreferences.saveHistoryExpressions(getApplicationContext(), historyExpression);
                evaluateExpression();
                isButtonProcessing = false;
            }catch (IllegalStateException illegalStateException){
                Toast.makeText(MainActivity.this,"Invalid format used",Toast.LENGTH_SHORT).show();

            }

        }


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
            historyResult.add(String.valueOf(result));
            MyPreferences.saveHistoryResults(getApplicationContext(),historyResult);
            displayField.setText(String.valueOf(result));
            displayField.setTextColor( getResources().getColor(R.color.green));
            expression.clear();

        }
    }



    private double performOperation(double num1, String operator, double num2) {
        switch (operator) {
            case "+":
                return num1 + num2;
            case "-":
                return num1 - num2;
            case "*":
                return num1 * num2;
            case "%":
                return num1 % num2;
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
