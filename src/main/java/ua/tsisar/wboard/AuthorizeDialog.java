package ua.tsisar.wboard;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AuthorizeDialog extends DialogFragment {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private CheckBox rememberMeCheckBox;
    private IAuthorizeDialogListener listener;

    private final String TAG = "myLogs";

    public interface IAuthorizeDialogListener {
        void onAuthorized(boolean rememberMe);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getActivity() instanceof IAuthorizeDialogListener) {
            listener = (IAuthorizeDialogListener) getActivity();
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
                .setTitle("Authorize")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        authorize(
                                usernameEditText.getText().toString(),
                                passwordEditText.getText().toString(),
                                rememberMeCheckBox.isChecked()
                        );
                    }
                });
        return builder.create();
    }

    private void authorize(String stringUsername,
                           String stringPassword, boolean booleanRememberMe){
        Authorize authorize = new Authorize();
        authorize.setUsername(stringUsername);
        authorize.setPassword(stringPassword);
        authorize.setRememberMe(booleanRememberMe);

        Call<Token> tokenCall = App.getApi().authorize(authorize);
        tokenCall.enqueue(new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {
                Log.d(TAG, "Response: " + response.isSuccessful() + ", code: " + response.code());
                switch (response.code()){
                    case 200:
                        App.setIdToken(response.body().getIdToken());
                        if (listener != null) {
                            listener.onAuthorized(rememberMeCheckBox.isChecked());
                        }
                        break;
                    case 201:
                        break;
                    case 401:
                        break;
                    case 403:
                        break;
                    case 404:
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onFailure(Call<Token> call, Throwable throwable) {

            }
        });
    }

}