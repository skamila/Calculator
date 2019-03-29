package skamila.calculator.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import skamila.calculator.R;

public class SimpleCalculatorFragment extends Fragment {

    protected CalculatorButtonListener buttonListener;
    protected TextView display;


    public SimpleCalculatorFragment() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.simple,
                container, false);

        setListeners(view);
        this.display = view.findViewById(R.id.display);

        if(savedInstanceState != null){
            display.setText((savedInstanceState.getString("onDisplay")));
        }

        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle savedInstanceState) {

        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString("onDisplay", (String) display.getText());

    }

    @Override
    public void onAttach (Context context) {

        super.onAttach(context);

        if(context instanceof CalculatorButtonListener){
            buttonListener = (CalculatorButtonListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implements CalculatorButtonListener");
        }
    }

    protected void setListeners(View view){

        view.findViewById(R.id.num0).setOnClickListener(digitButtonClick);
        view.findViewById(R.id.num1).setOnClickListener(digitButtonClick);
        view.findViewById(R.id.num2).setOnClickListener(digitButtonClick);
        view.findViewById(R.id.num3).setOnClickListener(digitButtonClick);
        view.findViewById(R.id.num4).setOnClickListener(digitButtonClick);
        view.findViewById(R.id.num5).setOnClickListener(digitButtonClick);
        view.findViewById(R.id.num6).setOnClickListener(digitButtonClick);
        view.findViewById(R.id.num7).setOnClickListener(digitButtonClick);
        view.findViewById(R.id.num8).setOnClickListener(digitButtonClick);
        view.findViewById(R.id.num9).setOnClickListener(digitButtonClick);

        view.findViewById(R.id.dot).setOnClickListener(dotButtonClick);

        view.findViewById(R.id.add).setOnClickListener(operationButtonClick);
        view.findViewById(R.id.sub).setOnClickListener(operationButtonClick);
        view.findViewById(R.id.multi).setOnClickListener(operationButtonClick);
        view.findViewById(R.id.div).setOnClickListener(operationButtonClick);

        view.findViewById(R.id.changeSign).setOnClickListener(fastOperationButtonClick);

        view.findViewById(R.id.equal).setOnClickListener(equalButtonClick);

        view.findViewById(R.id.ac).setOnClickListener(clearButtonClick);
        view.findViewById(R.id.c).setOnClickListener(clearButtonClick);

    }

    protected View.OnClickListener digitButtonClick = (View view) -> buttonListener.onDigitClick(view);
    protected View.OnClickListener dotButtonClick = (View view) -> buttonListener.onDotClick(view);
    protected View.OnClickListener operationButtonClick = (View view) -> buttonListener.onOperationClick(view);
    protected View.OnClickListener fastOperationButtonClick = (View view) -> buttonListener.onFastOperationClick(view);
    protected View.OnClickListener equalButtonClick = (View view) -> buttonListener.onEqualClick(view);
    protected View.OnClickListener clearButtonClick = (View view) -> buttonListener.onClearClick(view);

}