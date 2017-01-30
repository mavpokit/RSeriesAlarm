package com.mavpokit.rseriesalarm.util;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mavpokit.rseriesalarm.R;

/**
 * Created by Alex on 27.01.2017.
 */

public class InfoView extends CardView {

    private TextView infoTextView;
    private String message;

    public InfoView(Context context) {
        super(context);
        initializeViews(context);
    }

    public InfoView(Context context, AttributeSet attrs) {
        super(context, attrs);

        getMessageAttr(context, attrs);

        initializeViews(context);

    }

    private void getMessageAttr(Context context, AttributeSet attrs) {
        TypedArray typedArray;
        typedArray = context.obtainStyledAttributes(attrs, R.styleable.InfoView);
        message = typedArray.getString(R.styleable.InfoView_message);
        typedArray.recycle();
    }

    public InfoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getMessageAttr(context,attrs);
        initializeViews(context);
    }

    private void initializeViews(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.infotextview, this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        infoTextView=(TextView)this.findViewById(R.id.info_textview);
        infoTextView.setText(message);
    }
}
