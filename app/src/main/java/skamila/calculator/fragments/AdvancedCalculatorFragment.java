package skamila.calculator.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import skamila.calculator.R;

public class AdvancedCalculatorFragment extends SimpleCalculatorFragment {

    public AdvancedCalculatorFragment() {};

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.advanced,
                container, false);

        setListeners(view);
        this.display = view.findViewById(R.id.display);

        if(savedInstanceState != null){
            display.setText((savedInstanceState.getString("onDisplay")));
        }

        return view;
    }

    @Override
    protected void setListeners(View view){

        super.setListeners(view);

        view.findViewById(R.id.powY).setOnClickListener(operationButtonClick);

        view.findViewById(R.id.percent).setOnClickListener(fastOperationButtonClick);
        view.findViewById(R.id.root).setOnClickListener(fastOperationButtonClick);
        view.findViewById(R.id.sin).setOnClickListener(fastOperationButtonClick);
        view.findViewById(R.id.cos).setOnClickListener(fastOperationButtonClick);
        view.findViewById(R.id.tan).setOnClickListener(fastOperationButtonClick);
        view.findViewById(R.id.pow2).setOnClickListener(fastOperationButtonClick);
        view.findViewById(R.id.ln).setOnClickListener(fastOperationButtonClick);
        view.findViewById(R.id.log).setOnClickListener(fastOperationButtonClick);

    }

}