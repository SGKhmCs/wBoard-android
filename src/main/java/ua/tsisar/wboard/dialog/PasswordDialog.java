package ua.tsisar.wboard.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;

import com.github.mrengineer13.snackbar.SnackBar;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ua.tsisar.wboard.R;

public class PasswordDialog extends DialogFragment{

    PasswordListener listener;

    public interface PasswordListener {
        void onPasswordChanged(String password);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getActivity() instanceof PasswordListener) {
            listener = (PasswordListener) getActivity();
        }
    }

    private EditText passwordEditText;
    private EditText passwordConfirmationEditText;


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_password, null);
        passwordEditText = (EditText) view.findViewById(R.id.password_editText);
        passwordConfirmationEditText = (EditText) view.findViewById(R.id.password_confirmation_editText);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setView(view)
                .setTitle("Password")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Continue", null);

        return builder.create();
    }

    @Override
    public void onResume()
    {
        super.onResume();
        AlertDialog alertDialog = (AlertDialog)getDialog();
        if(alertDialog != null){
            alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
                    .setOnClickListener((View v) -> edit());
        }
    }

    private void edit() {
        String password = passwordEditText.getText().toString();

        if (password.length() > 3) {
            if (password.equals(passwordConfirmationEditText.getText().toString())) {
                if (!isValidPassword(password)) {
                    errorMessage("Your password is unreliable!");
                    return;
                }
            } else {
                errorMessage("Your passwords do not match!");
                return;
            }
        } else {
            errorMessage("Your password too short!");
            return;
        }

        if (listener != null) {
            listener.onPasswordChanged(password);
            dismiss();
        }
    }

    private static boolean isValidPassword(String password) {

        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=\\S+$).{4,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();

    }

    private SnackBar errorMessage(String message){
        return new SnackBar.Builder(getActivity())
                .withMessage(message)
                .withStyle(SnackBar.Style.ALERT)
                .show();
    }
}