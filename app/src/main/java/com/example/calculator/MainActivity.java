package com.example.calculator;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private EditText editText;
    private String currentInput = "";
    private double operand1 = Double.NaN;
    private String operator = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);

        // Définir les listeners pour les boutons numériques
        int[] numericButtonIds = {
                R.id.button0, R.id.button1, R.id.button2,
                R.id.button3, R.id.button4, R.id.button5,
                R.id.button6, R.id.button7, R.id.button8, R.id.button9
        };
        for (int buttonId : numericButtonIds) {
            findViewById(buttonId).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Button button = (Button) v;
                    currentInput += button.getText().toString();
                    editText.setText(currentInput);
                }
            });
        }

        // Définir les listeners pour les boutons d'opérations
        int[] operationButtonIds = {
                R.id.buttonAdd, R.id.buttonSubtract,
                R.id.buttonMultiply, R.id.buttonDivide
        };
        for (int buttonId : operationButtonIds) {
            findViewById(buttonId).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    handleOperation((Button) v);
                }
            });
        }

        // Bouton égal
        findViewById(R.id.buttonEquals).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateResult();
            }
        });

        // Bouton effacer
        findViewById(R.id.buttonClear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearCalculator();
            }
        });

        // Bouton point décimal
        findViewById(R.id.buttonDot).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!currentInput.contains(".")) { // Vérifiez qu'il n'y a pas de points dupliqués
                    currentInput += ".";
                    editText.setText(currentInput);
                }
            }
        });




        // Bouton plus-moins
        findViewById(R.id.buttonPlusMinus).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!currentInput.isEmpty()) {
                    double value = Double.parseDouble(currentInput);
                    value = -value; // Alternez entre positif et négatif
                    currentInput = String.valueOf(value);
                    editText.setText(currentInput);
                }
            }
        });

        // Bouton racine carrée
        findViewById(R.id.buttonRacine).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleSquareRoot();
            }
        });




        // Bouton pourcentage
        findViewById(R.id.buttonPercent).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!currentInput.isEmpty()) {
                    double value = Double.parseDouble(currentInput);
                    value /= 100; // Convertir en pourcentage
                    currentInput = String.valueOf(value);
                    editText.setText(currentInput);
                }
            }
        });
    }

    private void handleOperation(Button operationButton) {
        if (!currentInput.isEmpty()) {
            if (!Double.isNaN(operand1)) {
                calculateResult();
            }
            operand1 = Double.parseDouble(currentInput);
            operator = operationButton.getText().toString();
            currentInput = "";
        }
    }
    private void handleSquareRoot() {
        if (!currentInput.isEmpty()) {
            double value = Double.parseDouble(currentInput);
            if (value >= 0) { // Assurez-vous que le nombre est positif ou nul pour éviter les erreurs
                value = Math.sqrt(value); // Calculer la racine carrée
                currentInput = String.valueOf(value);
                editText.setText(currentInput);
            } else {
                editText.setText("Erreur");
            }
        }
    }

    private void calculateResult() {
        if (!currentInput.isEmpty() && !Double.isNaN(operand1)) {
            double operand2 = Double.parseDouble(currentInput);
            double result = Double.NaN;

            switch (operator) {
                case "+":
                    result = operand1 + operand2;
                    break;
                case "-":
                    result = operand1 - operand2;
                    break;
                case "*":
                    result = operand1 * operand2;
                    break;
                case "/":
                    if (operand2 != 0) {
                        result = operand1 / operand2;
                    } else {
                        editText.setText("Erreur");
                    }
                    break;
            }

            if (!Double.isNaN(result)) {
                editText.setText(String.valueOf(result));
                operand1 = result;
                currentInput = "";
            }
        }
    }

    private void clearCalculator() {
        operand1 = Double.NaN;
        operator = "";
        currentInput = "";
        editText.setText("");
    }
}