package ua.tsisar.wboard;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RegistrationDialog extends DialogFragment {

    private EditText usernameEditText;
    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText passwordComfEditText;

    private final String TAG = "myLogs";

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_registration, null);
        usernameEditText = (EditText) view.findViewById(R.id.username_edittext);
        emailEditText = (EditText) view.findViewById(R.id.email_edittext);
        passwordEditText = (EditText) view.findViewById(R.id.password_edittext);
        passwordComfEditText = (EditText) view.findViewById(R.id.password_comf_edittext);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setView(view)
                .setTitle("Registration")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Register", null);

        return builder.create();
    }

    @Override
    public void onResume()
    {
        super.onResume();
        final AlertDialog alertDialog = (AlertDialog)getDialog();
        if(alertDialog != null)
        {
            alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    register(alertDialog);
                }
            });
        }
    }

    private void register(final AlertDialog alertDialog) {
        String login = usernameEditText.getText().toString();
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        User user = new User();

        user.setLogin(login);

        if(isValidEmail(email)){
            user.setEmail(email);
        }else{
            Message.makeText(getActivity(), "Error", "Please enter valid email!").show();
            return;
        }

        if (password.length() > 7) {
            if (password.equals(passwordComfEditText.getText().toString())) {
                if (isValidPassword(password)) {
                    user.setPassword(password);
                } else {
                    Message.makeText(getActivity(), "Error", "Your password is unreliable!").show();
                    return;
                }
            } else {
                Message.makeText(getActivity(), "Error", "Your passwords do not match!").show();
                return;
            }
        } else {
            Message.makeText(getActivity(), "Error", "Your password too short!").show();
            return;
        }

        Call<String> stringCall = App.getApi().registerAccount(user);
        stringCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.d(TAG, "Response: " + response.isSuccessful() + ", code: " + response.code() +
                        ", message: " + response.message());

                switch (response.code()){
                    case 201:
                        Message.makeText(getActivity(), "Registration saved!",
                                "Please check your email for confirmation.").show();
                        alertDialog.dismiss();
                        break;
                    default:
                        Message.makeText(getActivity(), "Error",
                                response.message() + ", status code: " + response.code()).show();
                        break;
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable throwable) {
                Message.makeText(getActivity(), "Error", throwable.getMessage()).show();
                Log.d(TAG, "onFailure: " + throwable.getMessage());
            }
        });
    }

    private static boolean isValidPassword(String password) {

        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();

    }

    private static boolean isValidEmail(String email) {
        if (TextUtils.isEmpty(email)) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
        }
    }
}