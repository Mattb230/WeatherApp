package com.bootsysoftware.weatherapp.controller;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;

import com.bootsysoftware.weatherapp.R;

/**
 * Created by Matthew Boydston on 9/15/2016.
 */
public class AlertDialogFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Context context = getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(context).setTitle(R.string.error_titleString)
                .setMessage(R.string.error_messageString)
                .setPositiveButton(R.string.okString, null);

        AlertDialog dialog = builder.create();
        return dialog;
    }
}
