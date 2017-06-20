package ua.tsisar.wboard;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RegistrationDialog extends DialogFragment {

    private EditText usernameEditText;
    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText passwordComfEditText;
    private ProgressBar strengthProgressBar;
    private IAuthorizeDialogListener listener;

    private final String TAG = "myLogs";

    public interface IAuthorizeDialogListener {
        void onRegistration(boolean rememberMe);
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
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_registration, null);
        usernameEditText = (EditText) view.findViewById(R.id.username_edittext);
        emailEditText = (EditText) view.findViewById(R.id.email_edittext);
        passwordEditText = (EditText) view.findViewById(R.id.password_edittext);
        passwordComfEditText = (EditText) view.findViewById(R.id.password_comf_edittext);

        strengthProgressBar = (ProgressBar) view.findViewById(R.id.strength_progressBar);

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

    private void register(final AlertDialog alertDialog){
        User user = new User();
        user.setLogin("userf");
        user.setEmail("userf@gmail.com");
        user.setPassword("userF");

        Call<String> stringCall = App.getApi().registerAccount(user);
        stringCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.d(TAG, "Response: " + response.isSuccessful() + ", code: " + response.code() +
                        ", message: " + response.message());

                switch (response.code()){
                    case 201:
                        Toast.makeText(getActivity(), "Registration saved! Please check your email for confirmation.", Toast.LENGTH_LONG).show();
                        alertDialog.dismiss();
                        break;
                    default:
                        Toast.makeText(getActivity(), "Status Code: " + response.code() + " - " +
                                response.message(), Toast.LENGTH_SHORT).show();
                        break;
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable throwable) {
                Log.d(TAG, "onFailure: " + throwable.getMessage());
            }
        });
    }

}