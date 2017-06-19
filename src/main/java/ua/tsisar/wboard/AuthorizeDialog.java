package ua.tsisar.wboard;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;


public class AuthorizeDialog extends DialogFragment {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private CheckBox rememberMeCheckBox;
    private ICredentialsDialogListener listener;

    public interface ICredentialsDialogListener {
        void onDialogPositiveClick(String username, String password, boolean rememberMe);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getActivity() instanceof ICredentialsDialogListener) {
            listener = (ICredentialsDialogListener) getActivity();
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_authenticate, null);
        usernameEditText = (EditText) view.findViewById(R.id.username_edittext);
        passwordEditText = (EditText) view.findViewById(R.id.password_edittext);
        rememberMeCheckBox = (CheckBox) view.findViewById(R.id.rememberme_checkbox);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setView(view)
                .setTitle("Sign In")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (listener != null) {
                            listener.onDialogPositiveClick(usernameEditText.getText().toString(),
                                    passwordEditText.getText().toString(), rememberMeCheckBox.isChecked());
                        }
                    }
                });
        return builder.create();
    }
}