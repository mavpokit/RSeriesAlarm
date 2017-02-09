package com.mavpokit.rseriesalarm.util;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.InputFilter;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.mavpokit.rseriesalarm.R;

import static android.text.InputType.TYPE_CLASS_NUMBER;
import static android.text.InputType.TYPE_CLASS_TEXT;
import static android.text.InputType.TYPE_NUMBER_VARIATION_NORMAL;
import static android.text.InputType.TYPE_NUMBER_VARIATION_PASSWORD;
import static android.text.InputType.TYPE_TEXT_VARIATION_PERSON_NAME;

/**
 * Created by Alex on 27.01.2017.
 */

public class ColouredEditText extends FrameLayout {

    private final String INPUT_TYPE_TEXT = "text";
    private final String INPUT_TYPE_NUMBER = "number";
    private final String INPUT_TYPE_PASSWORD = "numberPassword";

    private EditText editText;
    private String initialText;
    private String inputType;
    private int textSize;
    private int maxLenght;


    public ColouredEditText(Context context) {
        super(context);
        initializeViews(context);
    }

    public ColouredEditText(Context context, AttributeSet attrs) {
        super(context, attrs);

        getMessageAttr(context, attrs);

        initializeViews(context);

    }

    private void getMessageAttr(Context context, AttributeSet attrs) {
        TypedArray typedArray;
        typedArray = context.obtainStyledAttributes(attrs, R.styleable.ColouredEditText);

        initialText = typedArray.getString(R.styleable.ColouredEditText_initialText);

        inputType = typedArray.getString(R.styleable.ColouredEditText_inputType);
        if (inputType==null) inputType = INPUT_TYPE_TEXT;

        textSize = typedArray.getInteger(R.styleable.ColouredEditText_textSize,18);
        maxLenght = typedArray.getInteger(R.styleable.ColouredEditText_maxLength,100);

        typedArray.recycle();
    }

    public ColouredEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getMessageAttr(context,attrs);
        initializeViews(context);
    }

    private void initializeViews(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.coloured_edit_text, this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        editText=(EditText) this.findViewById(R.id.editText_nbnfsdfn2378hsjfksjdf8);
        editText.setText(initialText);
        editText.setTextSize(textSize);
        editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLenght)});
        switch (inputType){
            case INPUT_TYPE_TEXT:editText.setInputType(TYPE_CLASS_TEXT|TYPE_TEXT_VARIATION_PERSON_NAME); break;
            case INPUT_TYPE_NUMBER:editText.setInputType(TYPE_CLASS_NUMBER|TYPE_NUMBER_VARIATION_NORMAL); break;
            case INPUT_TYPE_PASSWORD:editText.setInputType(TYPE_CLASS_NUMBER|TYPE_NUMBER_VARIATION_PASSWORD); break;
            default: editText.setInputType(TYPE_CLASS_TEXT|TYPE_TEXT_VARIATION_PERSON_NAME); break;
        }
    }

    public String getText(){
        return editText.getText().toString();
    }
}
