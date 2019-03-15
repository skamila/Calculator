package skamila.calculator.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import skamila.calculator.R;

public class MenuFragment extends Fragment {

    private MenuEventListener menuEventListener;

    public MenuFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.menu, container, false);

        final Button simpleButton = view.findViewById(R.id.simple);
        simpleButton.setOnClickListener(simpleCalc);

        final Button advancedButton = view.findViewById(R.id.advanced);
        advancedButton.setOnClickListener(advancedCalc);

        final Button aboutButton = view.findViewById(R.id.about);
        aboutButton.setOnClickListener(about);

        final Button exitButton = view.findViewById(R.id.exit);
        exitButton.setOnClickListener(exit);

        return view;
    }

    @Override
    public void onAttach(Context context){

        super.onAttach(context);

        if(context instanceof MenuEventListener){
            menuEventListener = (MenuEventListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement MenuEventListener");
        }

    }

    public interface MenuEventListener{
        void onSimpleButtonPressed(View view);
        void onAdvancedCalcButtonPressed(View view);
        void onAboutButtonPressed(View view);
        void onExitButtonPressed(View view);
    }

    private View.OnClickListener simpleCalc = (View view) -> menuEventListener.onSimpleButtonPressed(view);
    private View.OnClickListener advancedCalc = (View view) -> menuEventListener.onAdvancedCalcButtonPressed(view);
    private View.OnClickListener about = (View view) -> menuEventListener.onAboutButtonPressed(view);
    private View.OnClickListener exit = (View view) -> menuEventListener.onExitButtonPressed(view);

}