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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

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
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_authorize, null);
        usernameEditText = (EditText) view.findViewById(R.id.username_edittext);
        passwordEditText = (EditText) view.findViewById(R.id.password_edittext);
        rememberMeCheckBox = (CheckBox) view.findViewById(R.id.rememberme_checkbox);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setView(view)
                .setTitle("Sign in")
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
                    authorize(alertDialog);
                }
            });
        }
    }

    private void authorize(final AlertDialog alertDialog){
        Authorize authorize = new Authorize();
        authorize.setUsername(usernameEditText.getText().toString());
        authorize.setPassword(passwordEditText.getText().toString());
        authorize.setRememberMe( rememberMeCheckBox.isChecked());

        Call<Token> tokenCall = App.getApi().authorize(authorize);
        tokenCall.enqueue(new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {
                Log.d(TAG, "Response: " + response.isSuccessful() + ", code: " + response.code() +
                ", message: " + response.message());
                switch (response.code()){
                    case 200:
                        App.setIdToken(response.body().getIdToken());
                        if (listener != null) {
                            listener.onAuthorized(rememberMeCheckBox.isChecked());
                        }
                        alertDialog.dismiss();
                        break;
                    default:
                        Toast.makeText(getActivity(), "Status Code: " + response.code() + " - " +
                                response.message(), Toast.LENGTH_SHORT).show();
                        break;
                }
            }

            @Override
            public void onFailure(Call<Token> call, Throwable throwable) {
                Toast.makeText(getActivity(), "Error: " + throwable.getMessage(),
                        Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onFailure: " + throwable.getMessage());
            }
        });
    }

}