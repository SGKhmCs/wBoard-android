package ua.tsisar.wboard;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserSettingsDialog extends DialogFragment {
    private User user;

    private EditText firstName;
    private EditText lastName;
    private EditText email;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_user_settings, null);

        firstName = (EditText) view.findViewById(R.id.firstName_editText);
        lastName = (EditText) view.findViewById(R.id.lastName_editText);
        email = (EditText) view.findViewById(R.id.email_user_settings_editText);

        getAccount();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setView(view)
                .setTitle("User") // Login?
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Save", null);

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
                    saveAccount(alertDialog);
                }
            });
        }
    }

    private void getAccount(){
        Call<User> userCall = App.getApi().getAccount(App.getToken().getIdTokenEx());
        userCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                switch (response.code()){
                    case 200:
                        setValue(response.body());
                        break;
                    default:
                        Message.makeText(getActivity(), "Error",
                                response.message() + ", status code: " + response.code()).show();
                        break;
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable throwable) {
                Message.makeText(getActivity(), "Error", throwable.getMessage()).show();
            }
        });
    }

    public void saveAccount(final AlertDialog alertDialog){
        user.setFirstName(firstName.getText().toString());
        user.setLastName(lastName.getText().toString());

        if(isValidEmail(email.getText().toString())){
            user.setEmail(email.getText().toString());
        }else{
            Message.makeText(getActivity(), "Error", "Please enter valid email!").show();
            return;
        }

        Call<String> stringCall = App.getApi().saveAccount(App.getToken().getIdTokenEx(), user);
        stringCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                switch (response.code()){
                    case 200:
                        Message.makeText(getActivity(), "Saved!",
                                "Your settings saved.").show();
                        // Don't finish?
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

    private void setValue(User user){
        if(user != null){
            this.user = user;

            firstName.setText(user.getFirstName());
            lastName.setText(user.getLastName());
            email.setText(user.getEmail());
        }
    }

    private static boolean isValidEmail(String email) {
        if (TextUtils.isEmpty(email)) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
        }
    }
}