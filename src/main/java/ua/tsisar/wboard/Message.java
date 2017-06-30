package ua.tsisar.wboard;

import android.support.v7.app.AppCompatActivity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.Log;

public class Message{
    private static final String TAG = "myLogs";
    private final Context context;
    private static MessageDialog dialog;
    private static Bundle arguments;

    public Message(Context context){
        this.context = context;
    }

    private AppCompatActivity getActivity() {
        return (AppCompatActivity) context;
    }

    private FragmentManager getSupportFragmentManager() {
        try{
            return getActivity().getSupportFragmentManager();
        } catch (ClassCastException e) {
            Log.d(TAG, "Can't get the fragment manager with this");
        }
        return null;
    }

    private static void setArguments(String stringTitle, String stringMessage){
        dialog = new MessageDialog();
        arguments = new Bundle();
        arguments.putString("title", stringTitle);
        arguments.putString("message", stringMessage);
    }

    private static void setArguments(String stringTitle, String stringMessage, boolean finish){
        dialog = new MessageDialog();
        arguments = new Bundle();
        arguments.putString("title", stringTitle);
        arguments.putString("message", stringMessage);
        arguments.putBoolean("finish", finish);
    }

    public static Message makeText(Context context, String stringTitle, String stringMessage){
        Message message = new Message(context);
        setArguments(stringTitle, stringMessage);
        return message;
    }

    public static Message makeText(Context context, String stringTitle, String stringMessage, boolean finish){
        Message message = new Message(context);
        setArguments(stringTitle, stringMessage, finish);
        return message;
    }

    public boolean show(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        if(fragmentManager != null) {
            dialog.setArguments(arguments);
            dialog.show(getSupportFragmentManager(), "messageDialog");
            return true;
        }
        return false;
    }

    public void hide(){
        dialog.dismiss();
    }
}
