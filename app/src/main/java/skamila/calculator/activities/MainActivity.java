package skamila.calculator.activities;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.math.BigDecimal;

import skamila.calculator.R;
import skamila.calculator.fragments.AboutFragment;
import skamila.calculator.fragments.AdvancedFragment;
import skamila.calculator.fragments.CalculatorButtonListener;
import skamila.calculator.fragments.MenuFragment;
import skamila.calculator.fragments.SimpleFragment;

public class MainActivity extends AppCompatActivity implements MenuFragment.MenuEventListener, CalculatorButtonListener {

    private final FragmentManager fragmentManager = getSupportFragmentManager();
    private String actualOperation;
    private String actualNumberOnDisplay;
    private BigDecimal actualValue;
    private BigDecimal prevValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        if (savedInstanceState == null) {

            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.main_container, new MenuFragment());
            fragmentTransaction.commit();

        }

        this.actualValue = convertToBigDecimal(0);
        this.prevValue = convertToBigDecimal(0);
        this.actualNumberOnDisplay = "0";
        this.actualOperation = "";

    }

    @Override
    public void onSimpleCalcButtonPressed(View view) {

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_container, new SimpleFragment());
        fragmentTransaction.addToBackStack(null);   // dzięki temu zapisujemy replace na stosie i możemy się cofnąć
        fragmentTransaction.commit();

    }

    @Override
    public void onAdvancedCalcButtonPressed(View view) {

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_container, new AdvancedFragment());
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

        final TextView display = view.getRootView().findViewById(R.id.display);

        if (convertToBigDecimal(actualNumberOnDisplay).equals(convertToBigDecimal(0))) {
            try{
                Integer.parseInt(actualNumberOnDisplay);
                this.actualNumberOnDisplay = String.valueOf(checkNumber(view));
            } catch(NumberFormatException e){
                this.actualNumberOnDisplay += checkNumber(view);
            }
        } else {
            this.actualNumberOnDisplay += checkNumber(view);
        }

        printOnDisplay(display);

    }

    @Override
    public void onDotClick(View view) {

        final TextView display = view.getRootView().findViewById(R.id.display);

        try {
            Integer.parseInt(this.actualNumberOnDisplay);
            this.actualNumberOnDisplay += ".";
            display.setText(display.getText() + ".");
        } catch (NumberFormatException e) {
        }

    }

    @Override
    public void onOperationClick(View view) {

        final TextView display = view.getRootView().findViewById(R.id.display);

        if(!this.actualNumberOnDisplay.equals("")){
            onEqualClick(view);
        }

        this.actualOperation = checkOperation(view);

        this.prevValue = convertToBigDecimal(this.actualNumberOnDisplay);
        this.actualNumberOnDisplay = "0";
        this.actualValue = convertToBigDecimal(actualNumberOnDisplay);

        printOnDisplay(display);

    }

    @Override
    public void onFastOperationClick(View view) {

        final TextView display = view.getRootView().findViewById(R.id.display);

        this.actualOperation = checkFastOperation(view);

        this.actualValue = convertToBigDecimal(actualNumberOnDisplay);
        this.actualValue = doFastOperation();
        this.actualNumberOnDisplay = String.valueOf(actualValue);

        this.prevValue = convertToBigDecimal(0);
        this.actualOperation = "";

        printOnDisplay(display);

    }

    @Override
    public void onEqualClick(View view) {

        final TextView display = view.getRootView().findViewById(R.id.display);

        actualValue = convertToBigDecimal(this.actualNumberOnDisplay);
        this.actualValue = doOperation();
        this.actualNumberOnDisplay = String.valueOf(actualValue);

        this.actualOperation = "";
        this.prevValue = convertToBigDecimal(0);

        printOnDisplay(display);

    }

    @Override
    public void onClearClick(View view){
        final TextView display = view.getRootView().findViewById(R.id.display);
        this.actualNumberOnDisplay = "0";
        this.actualValue = convertToBigDecimal(this.actualNumberOnDisplay);
        this.prevValue = convertToBigDecimal(0);
        this.actualOperation = "";
        printOnDisplay(display);
    }

    private void printOnDisplay(TextView display) {

            this.actualValue = convertToBigDecimal(this.actualNumberOnDisplay);

        if(this.actualOperation.equals("")){

            display.setText(String.valueOf(this.actualValue));

            if (this.actualValue.doubleValue() % 1 == 0) {
                int result = this.actualValue.intValue();
                display.setText(String.valueOf(result));
            } else {
                display.setText(String.valueOf(this.actualValue));
            }

        } else {

            String text = "";

            if (this.prevValue.doubleValue() % 1 == 0) {
                int result = this.prevValue.intValue();
                text += String.valueOf(result);
            } else {
                text += String.valueOf(this.prevValue);
            }

            text += this.actualOperation;

            text += this.actualNumberOnDisplay;

            display.setText(text);

        }
    }

    private BigDecimal doOperation() {
        switch (this.actualOperation) {
            case "+":
                return prevValue.add(actualValue);
            case "-":
                return prevValue.subtract(actualValue);
            case "×":
                return prevValue.multiply(actualValue);
            case "÷":
                if(this.actualValue.doubleValue() == 0){
                    // obsługa dzielenia przez 0
                    return prevValue;
                } else {
                    return prevValue.divide(actualValue);
                }
            case "^":
                return convertToBigDecimal(Math.pow(prevValue.doubleValue(), actualValue.doubleValue()));
        }

        return actualValue;
    }

    private BigDecimal doFastOperation() {
        switch (this.actualOperation) {
            case "±":
                return actualValue.multiply(convertToBigDecimal(-1));
            case "√":
                return convertToBigDecimal(Math.sqrt(actualValue.doubleValue()));
            case "%":
                return actualValue.divide(convertToBigDecimal(100));
            case "sin":
                return convertToBigDecimal(Math.sin(actualValue.doubleValue()/57.2957795));
            case "cos":
                return convertToBigDecimal(Math.cos(actualValue.doubleValue()/57.2957795));
            case "tan":
                return convertToBigDecimal(Math.tan(actualValue.doubleValue()/57.2957795));
            case "^2":
                return actualValue.pow(2);
            case "ln":
                return convertToBigDecimal(Math.log(actualValue.doubleValue()));
            case "log":
                return convertToBigDecimal(Math.log10(actualValue.doubleValue()));
        }

        return actualValue;
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
            return 0;
        }
    }

    private String checkOperation(View view) {

        if (view.getId() == view.getRootView().findViewById(R.id.add).getId()) {
            return "+";
        } else if (view.getId() == view.getRootView().findViewById(R.id.sub).getId()) {
            return "-";
        } else if (view.getId() == view.getRootView().findViewById(R.id.multi).getId()) {
            return "×";
        } else if (view.getId() == view.getRootView().findViewById(R.id.div).getId()) {
            return "÷";
        } else if (view.getId() == view.getRootView().findViewById(R.id.powY).getId()) {
            return "^";
        } else {
            return "";
        }
    }

    private String checkFastOperation(View view) {
        if (view.getId() == view.getRootView().findViewById(R.id.changeSign).getId()) {
            return "±";
        } else if (view.getId() == view.getRootView().findViewById(R.id.percent).getId()) {
            return "%";
        } else if (view.getId() == view.getRootView().findViewById(R.id.pow2).getId()) {
            return "^2";
        } else if (view.getId() == view.getRootView().findViewById(R.id.root).getId()) {
            return "√";
        } else if (view.getId() == view.getRootView().findViewById(R.id.log).getId()) {
            return "log";
        } else if (view.getId() == view.getRootView().findViewById(R.id.root).getId()) {
            return "ln";
        } else if (view.getId() == view.getRootView().findViewById(R.id.sin).getId()) {
            return "sin";
        } else if (view.getId() == view.getRootView().findViewById(R.id.cos).getId()) {
            return "cos";
        } else if (view.getId() == view.getRootView().findViewById(R.id.tan).getId()) {
            return "tan";
        } else {
            return "";
        }
    }

    private BigDecimal convertToBigDecimal(double number){
        return BigDecimal.valueOf(number);
    }

    private BigDecimal convertToBigDecimal(String number){
        return convertToBigDecimal(Double.parseDouble(number));
    }

}
