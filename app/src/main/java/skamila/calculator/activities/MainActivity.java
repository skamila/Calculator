package skamila.calculator.activities;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import skamila.calculator.R;
import skamila.calculator.fragments.AboutFragment;
import skamila.calculator.fragments.AdvancedFragment;
import skamila.calculator.fragments.CalculatorButtonListener;
import skamila.calculator.fragments.MenuFragment;
import skamila.calculator.fragments.SimpleFragment;

public class MainActivity extends AppCompatActivity implements MenuFragment.MenuEventListener, CalculatorButtonListener {

    private final FragmentManager fragmentManager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        if (savedInstanceState == null) {

            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.main_container, new MenuFragment());
            fragmentTransaction.commit();

        }

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
        String textOnDisplay = "";

        if(display.getText().equals("0")){
            textOnDisplay += checkNumber(view);
        } else {
            textOnDisplay += display.getText() + String.valueOf(checkNumber(view));
        }

        display.setText(textOnDisplay);

    }

    @Override
    public void onDotClick(View view) {

        final TextView display = view.getRootView().findViewById(R.id.display);

        try{
            Integer.parseInt((String) display.getText());
            display.setText(display.getText() + ".");
        } catch (NumberFormatException e){}
    }

    @Override
    public void onOperationClick(View view) {

    }

    private int checkNumber(View view){

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
}
