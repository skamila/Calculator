package skamila.calculator.activities;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;

import skamila.calculator.R;
import skamila.calculator.api.ReversePolishNotation;
import skamila.calculator.api.fast_operation.*;
import skamila.calculator.api.operation.*;
import skamila.calculator.fragments.*;
import skamila.calculator.api.exceptions.*;

public class MainActivity extends AppCompatActivity implements MenuFragment.MenuEventListener, CalculatorButtonListener {

    private final FragmentManager fragmentManager = getSupportFragmentManager();
    private String actualNumberOnDisplay;
    private int cClickCounter;
    private ReversePolishNotation calculator;
    private Operation operation;
    private FastOperation fastOperation;
    private boolean printZero;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        calculator = new ReversePolishNotation();

        if (savedInstanceState == null) {

            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.main_container, new MenuFragment());
            fragmentTransaction.commit();

            actualNumberOnDisplay = "0";

        } else {
            actualNumberOnDisplay = savedInstanceState.getString("actualNumberOnDisplay");
            //dorobic trzymanie operacji i wczesniejszej wartosci
        }

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle savedInstanceState) {

        super.onSaveInstanceState(savedInstanceState);

        savedInstanceState.putString("actualNumberOnDisplay", actualNumberOnDisplay);

    }

    @Override
    public void onSimpleCalcButtonPressed(View view) {

        calculator.clearAll();
        actualNumberOnDisplay = "0";
        cClickCounter = 0;

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_container, new SimpleCalculatorFragment());
        fragmentTransaction.addToBackStack(null);   // dzięki temu zapisujemy replace na stosie i możemy się cofnąć
        fragmentTransaction.commit();

    }

    @Override
    public void onAdvancedCalcButtonPressed(View view) {

        calculator.clearAll();
        actualNumberOnDisplay = "0";
        cClickCounter = 0;

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_container, new AdvancedCalculatorFragment());
        fragmentTransaction.addToBackStack(null);   // dzięki temu zapisujemy replace na stosie i możemy się cofnąć
        fragmentTransaction.commit();

    }

    @Override
    public void onAboutButtonPressed(View view) {

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_container, new AboutFragment());
        fragmentTransaction.addToBackStack(null);   // dzięki temu zapisujemy replace na stosie i możemy się cofnąć
        fragmentTransaction.commit();

    }

    @Override
    public void onExitButtonPressed(View view) {
        finish();
    }

    @Override
    public void onDigitClick(View view) {

        cClickCounter = 0;

        final TextView display = view.getRootView().findViewById(R.id.display);

        if (new BigDecimal(actualNumberOnDisplay).compareTo(new BigDecimal(999999)) < 0) {

            if (actualNumberOnDisplay.equals("0") || actualNumberOnDisplay.isEmpty()) {
                try {
                    Integer.parseInt(actualNumberOnDisplay);
                    this.actualNumberOnDisplay = String.valueOf(checkNumber(view));
                } catch (NumberFormatException e) {
                    this.actualNumberOnDisplay += checkNumber(view);
                }
                printOnDisplay(display);

            } else {
                this.actualNumberOnDisplay += checkNumber(view);
                printOnDisplay(display);
            }

        }

    }

    @Override
    public void onDotClick(View view) {

        cClickCounter = 0;

        final TextView display = view.getRootView().findViewById(R.id.display);

        try {
            Integer.parseInt(this.actualNumberOnDisplay);
            actualNumberOnDisplay += ".";
        } catch (NumberFormatException ignored) {
        }

        printOnDisplay(display);

    }

    @Override
    public void onOperationClick(View view) {

        cClickCounter = 0;
        printZero = false;

        final TextView display = view.getRootView().findViewById(R.id.display);

        if (operation != null) {
            onEqualClick(view);
        }

        checkOperation(view);
        calculator.putNumber(new BigDecimal(actualNumberOnDisplay));
        actualNumberOnDisplay = "0";

        printOnDisplay(display);

    }

    @Override
    public void onFastOperationClick(View view) {

        final TextView display = view.getRootView().findViewById(R.id.display);

        checkFastOperation(view);
        calculator.putNumber(new BigDecimal(actualNumberOnDisplay));

        try {
            actualNumberOnDisplay = String.valueOf(calculator.doFastOperation(fastOperation));
        } catch (NegativeNumberException e) {
            Toast.makeText(getApplicationContext(), "Do operacji nie można użyć liczby ujemnej.", Toast.LENGTH_SHORT).show();
        }

        calculator.deleteLastNumber();
        fastOperation = null;

        printOnDisplay(display);

    }

    @Override
    public void onEqualClick(View view) {

        if (operation == null) {
            return;
        }

        cClickCounter = 0;

        final TextView display = view.getRootView().findViewById(R.id.display);

        calculator.putNumber(new BigDecimal(actualNumberOnDisplay));

        try {
            calculator.doOperation(operation);
        } catch (DivByZeroException e) {
            Toast.makeText(getApplicationContext(), "Nie można wykonać dzielenia przez 0.", Toast.LENGTH_SHORT).show();
        } catch (NegativeNumberException e) {
            Toast.makeText(getApplicationContext(), "Do operacji nie można użyć liczby ujemnej.", Toast.LENGTH_SHORT).show();
        }


        actualNumberOnDisplay = String.valueOf(calculator.getLastNumber());
        calculator.deleteLastNumber();
        operation = null;
        printOnDisplay(display);

    }

    @Override
    public void onClearClick(View view) {

        final TextView display = view.getRootView().findViewById(R.id.display);

        if (checkCleanOption(view).equals("c") && ++cClickCounter < 2) {
            actualNumberOnDisplay = "0";
        } else {
            cClickCounter = 0;
            calculator.clearAll();
            actualNumberOnDisplay = "0";
        }

        printOnDisplay(display);

    }

    private void printOnDisplay(TextView display) {

        String text = "";

        if (operation != null) {

            text += String.valueOf(calculator.getLastNumber().intValue());
            text += operation.getSymbol();
        }

        if (operation == null || !actualNumberOnDisplay.equals("0") || printZero) {
            text += this.actualNumberOnDisplay;
        }

        display.setText(text);


    }

    private int checkNumber(View view) {

        if (view.getId() == view.getRootView().findViewById(R.id.num1).getId()) {
            return 1;
        } else if (view.getId() == view.getRootView().findViewById(R.id.num2).getId()) {
            return 2;
        } else if (view.getId() == view.getRootView().findViewById(R.id.num3).getId()) {
            return 3;
        } else if (view.getId() == view.getRootView().findViewById(R.id.num4).getId()) {
            return 4;
        } else if (view.getId() == view.getRootView().findViewById(R.id.num5).getId()) {
            return 5;
        } else if (view.getId() == view.getRootView().findViewById(R.id.num6).getId()) {
            return 6;
        } else if (view.getId() == view.getRootView().findViewById(R.id.num7).getId()) {
            return 7;
        } else if (view.getId() == view.getRootView().findViewById(R.id.num8).getId()) {
            return 8;
        } else if (view.getId() == view.getRootView().findViewById(R.id.num9).getId()) {
            return 9;
        } else {
            printZero = true;
            return 0;
        }
    }

    private void checkOperation(View view) {

        if (view.getId() == view.getRootView().findViewById(R.id.add).getId()) {
            operation = new Addition();
        } else if (view.getId() == view.getRootView().findViewById(R.id.sub).getId()) {
            operation = new Subtraction();
        } else if (view.getId() == view.getRootView().findViewById(R.id.multi).getId()) {
            operation = new Multiply();
        } else if (view.getId() == view.getRootView().findViewById(R.id.div).getId()) {
            operation = new Division();
        } else if (view.getRootView().findViewById(R.id.powY) != null
                && view.getRootView().findViewById(R.id.powY).getId() == view.getId()) {
            operation = new Exponentiation();
        }

    }

    private String checkCleanOption(View view) {

        if (view.getId() == view.getRootView().findViewById(R.id.ac).getId()) {
            return "ac";
        } else if (view.getId() == view.getRootView().findViewById(R.id.c).getId()) {
            return "c";
        } else {
            return "";
        }

    }

    private void checkFastOperation(View view) {
        if (view.getId() == view.getRootView().findViewById(R.id.changeSign).getId()) {
            fastOperation = new ChangeSignOperation();
        } else if (view.getRootView().findViewById(R.id.percent) != null
                && view.getId() == view.getRootView().findViewById(R.id.percent).getId()) {
            fastOperation = new Percent();
        } else if (view.getRootView().findViewById(R.id.pow2) != null
                && view.getId() == view.getRootView().findViewById(R.id.pow2).getId()) {
            fastOperation = new ToThePowerTwo();
        } else if (view.getRootView().findViewById(R.id.root) != null
                && view.getId() == view.getRootView().findViewById(R.id.root).getId()) {
            fastOperation = new Root();
        } else if (view.getRootView().findViewById(R.id.log) != null
                && view.getId() == view.getRootView().findViewById(R.id.log).getId()) {
            fastOperation = new Logarithm();
        } else if (view.getRootView().findViewById(R.id.ln) != null
                && view.getId() == view.getRootView().findViewById(R.id.ln).getId()) {
            fastOperation = new NaturalLogarithm();
        } else if (view.getRootView().findViewById(R.id.sin) != null
                && view.getId() == view.getRootView().findViewById(R.id.sin).getId()) {
            fastOperation = new Sine();
        } else if (view.getRootView().findViewById(R.id.cos) != null
                && view.getId() == view.getRootView().findViewById(R.id.cos).getId()) {
            fastOperation = new Cosine();
        } else if (view.getRootView().findViewById(R.id.tan) != null
                && view.getId() == view.getRootView().findViewById(R.id.tan).getId()) {
            fastOperation = new Tangent();
        }

    }

}
