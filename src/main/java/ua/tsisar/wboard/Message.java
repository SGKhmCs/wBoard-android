package ua.tsisar.wboard;

import android.support.v7.app.AppCompatActivity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.Log;

/**
 * Created by pavel on 21.06.17.
 */

public class Message {
    private static final String TAG = "myLogs";
    private final Context context;
    private static MessageDialog dialog;

    public Message(Context context){
        this.context = context;
    }

    public static Message makeText(Context context, String stringTitle, String stringMessage){
        Message message = new Message(context);

        dialog = new MessageDialog();
        Bundle arguments = new Bundle();
        arguments.putString("title", stringTitle);
        arguments.putString("message", stringMessage);
        dialog.setArguments(arguments);

        return message;
    }

    public boolean show(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        if(fragmentManager != null) {
            dialog.show(getSupportFragmentManager(), "messageDialog");
            return true;
        }
        return false;
    }

    public void hide(){
        dialog.dismiss();
    }

    private FragmentManager getSupportFragmentManager() {
        try{
            final AppCompatActivity activity = (AppCompatActivity) context;
            return activity.getSupportFragmentManager();

        } catch (ClassCastException e) {
            Log.d(TAG, "Can't get the fragment manager with this");
        }
        return null;
    }
}
