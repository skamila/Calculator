package skamila.calculator.activities;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.math.BigDecimal;

import skamila.calculator.Calculator;
import skamila.calculator.R;
import skamila.calculator.fragments.AboutFragment;
import skamila.calculator.fragments.AdvancedFragment;
import skamila.calculator.fragments.CalculatorButtonListener;
import skamila.calculator.fragments.MenuFragment;
import skamila.calculator.fragments.SimpleFragment;

public class MainActivity extends AppCompatActivity implements MenuFragment.MenuEventListener, CalculatorButtonListener {

    private final FragmentManager fragmentManager = getSupportFragmentManager();
    private String actualNumberOnDisplay;
    private Calculator calculator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        if (savedInstanceState == null) {

            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.main_container, new MenuFragment());
            fragmentTransaction.commit();

        }

        this.calculator = new Calculator();
        this.actualNumberOnDisplay = "0";

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

        if (new BigDecimal(actualNumberOnDisplay).equals(new BigDecimal(0))) {
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

        if(calculator.isActualOperation()){
            onEqualClick(view);
        }

        calculator.setActualValue(new BigDecimal(actualNumberOnDisplay));
        calculator.setActualOperation(checkOperation(view));
        calculator.archiveValue();
        actualNumberOnDisplay = String.valueOf(calculator.getActualValue());

        printOnDisplay(display);

    }

    @Override
    public void onFastOperationClick(View view) {

        final TextView display = view.getRootView().findViewById(R.id.display);

        calculator.setActualOperation(checkFastOperation(view));
        calculator.setActualValue(new BigDecimal(actualNumberOnDisplay));
        calculator.doOperation();
        actualNumberOnDisplay = String.valueOf(calculator.getActualValue());

        printOnDisplay(display);

    }

    @Override
    public void onEqualClick(View view) {

        final TextView display = view.getRootView().findViewById(R.id.display);

        calculator.setActualValue(new BigDecimal(actualNumberOnDisplay));
        calculator.doOperation();
        actualNumberOnDisplay = String.valueOf(calculator.getActualValue());

        printOnDisplay(display);

    }

    @Override
    public void onClearClick(View view){

        final TextView display = view.getRootView().findViewById(R.id.display);

        if(checkOperation(view).equals("c")){
            calculator.clear();
        } else {
            calculator.clearAll();
        }

        actualNumberOnDisplay = String.valueOf(calculator.getActualValue());

        printOnDisplay(display);
    }

    private void printOnDisplay(TextView display) {

        calculator.setActualValue(new BigDecimal(actualNumberOnDisplay));

        if(calculator.isActualOperation()){

            String text = "";

            if (calculator.getPrevValue().doubleValue() % 1 == 0) {
                int result = calculator.getPrevValue().intValue();
                text += String.valueOf(result);
            } else {
                text += String.valueOf(calculator.getPrevValue());
            }

            text += calculator.getActualOperation();

            text += this.actualNumberOnDisplay;

            display.setText(text);

        } else {

            display.setText(String.valueOf(calculator.getActualOperation()));

            if (calculator.getActualValue().doubleValue() % 1 == 0) {
                int result = calculator.getActualValue().intValue();
                display.setText(String.valueOf(result));
            } else {
                display.setText(String.valueOf(calculator.getActualValue()));
            }

        }
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
        } else if (view.getId() == view.getRootView().findViewById(R.id.ac).getId()){
            return "ac";
        } else if (view.getId() == view.getRootView().findViewById(R.id.c).getId()){
            return "c";
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

}
