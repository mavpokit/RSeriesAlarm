package com.mavpokit.rseriesalarm.objects;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.mavpokit.rseriesalarm.Consts;
import com.mavpokit.rseriesalarm.R;

/**
 * Created by Alex on 31.01.2017.
 */

public class EditDeleteDialog extends DialogFragment {

    private EditDeleteDialogClickListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder =
                new AlertDialog.Builder(getActivity());
        builder.setTitle(getArguments().getString(Consts.NAME));
        builder.setNeutralButton(R.string.cancel,null);
        builder.setPositiveButton(R.string.delete, (dialog, which) -> listener.onCLick(Consts.BTN_DELETE));
        builder.setNegativeButton(R.string.edit,(dialog, which) -> listener.onCLick(Consts.BTN_EDIT));
        return builder.create();
    }

    public void setListener(EditDeleteDialogClickListener listener) {
        this.listener = listener;
    }

    public interface EditDeleteDialogClickListener{
        void onCLick(int i);
    }
}
