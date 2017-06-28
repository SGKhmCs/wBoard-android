package ua.tsisar.wboard;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

public class DialogSignOut extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle("Sign out") // current user?
                .setMessage("Do you want to sign out?")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Sign out", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        (getActivity()).finish();
                    }
                });

        return builder.create();
    }
}
