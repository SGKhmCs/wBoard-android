package ua.tsisar.wboard;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PasswordDialog extends DialogFragment {

    private EditText passwordEditText;
    private EditText passwordComfEditText;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_password, null);
        passwordEditText = (EditText) view.findViewById(R.id.password_pd_edittext);
        passwordComfEditText = (EditText) view.findViewById(R.id.password_comf_pd_edittext);

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
        final AlertDialog alertDialog = (AlertDialog)getDialog();
        if(alertDialog != null)
        {
            alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    edit(alertDialog);
                }
            });
        }
    }

    private void edit(final AlertDialog alertDialog) {
        String password = passwordEditText.getText().toString();

        if (password.length() > 3) {
            if (password.equals(passwordComfEditText.getText().toString())) {
                if (!isValidPassword(password)) {
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

        Call<String> stringCall = App.getApi().changePassword(App.getToken().getIdTokenEx(), password);
        stringCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                switch (response.code()){
                    case 200:
                        Message.makeText(getActivity(), "Password saved!",
                                "Your password saved.").show();
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
            }
        });
    }

    private static boolean isValidPassword(String password) {

        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=\\S+$).{4,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();

    }
}