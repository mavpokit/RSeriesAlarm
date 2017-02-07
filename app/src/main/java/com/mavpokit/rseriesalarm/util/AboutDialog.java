package com.mavpokit.rseriesalarm.util;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.widget.TextView;

import com.mavpokit.rseriesalarm.R;

/**
 * Created by Alex on 06.02.2017.
 */

public class AboutDialog extends DialogFragment {

    private com.mavpokit.rseriesalarm.objects.EditDeleteDialog.EditDeleteDialogClickListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final TextView message = new TextView(getActivity());
        // i.e.: R.string.dialog_message =>
        // "Test this dialog following the link to dtmilano.blogspot.com"
        final SpannableString s =
                new SpannableString(getText(R.string.about_message));
        Linkify.addLinks(s, Linkify.WEB_URLS);
        message.setText(s);
        message.setMovementMethod(LinkMovementMethod.getInstance());


        AlertDialog.Builder builder =
                new AlertDialog.Builder(getActivity());
        builder.setTitle(getString(R.string.about_title));
        builder.setView(message);
        builder.setPositiveButton(R.string.ok, null);
        builder.setCancelable(true);
        return builder.create();
    }

}