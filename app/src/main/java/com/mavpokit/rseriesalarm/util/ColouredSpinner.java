package com.mavpokit.rseriesalarm.util;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.mavpokit.rseriesalarm.R;

/**
 * Created by Alex on 27.01.2017.
 */

public class ColouredSpinner extends FrameLayout {

    private Spinner spinner;

    public ColouredSpinner(Context context) {
        super(context);
        initializeViews(context);
    }

    public ColouredSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);

        initializeViews(context);

    }


    public ColouredSpinner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initializeViews(context);
    }

    private void initializeViews(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.coloured_spinner, this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        spinner=(Spinner) this.findViewById(R.id.spinner_nvlsdkjf84gnjsd52093ksd);
    }

    public void setAdapter(SpinnerAdapter adapter){
        spinner.setAdapter(adapter);
    }

    public int getSelectedItemPosition(){
        return spinner.getSelectedItemPosition();
    }
    public String getSelectedItem(){
        return spinner.getSelectedItem().toString();
    }

}
