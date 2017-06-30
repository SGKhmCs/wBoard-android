package ua.tsisar.wboard;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TextView;


public class MessageDialog extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String message = getArguments().getString("message");
        String title = getArguments().getString("title");
        final Boolean finish = getArguments().getBoolean("finish", false);

        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_message, null);
        TextView messageTextView = (TextView) view.findViewById(R.id.message_textView);

        messageTextView.setText(message);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setView(view)
                .setTitle(title)
                .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(finish) {
                            getActivity().finish();
                        }
                    }
                });
        return builder.create();
    }
}