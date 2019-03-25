package skamila.calculator.fragments;

import android.view.View;

public interface CalculatorButtonListener {

    void onDigitClick(View view);
    void onDotClick(View view);
    void onOperationClick(View view);
    void onEqualClick(View view);
    void onFastOperationClick(View view);
    void onClearClick(View view);

}
