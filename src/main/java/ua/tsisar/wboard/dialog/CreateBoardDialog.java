package ua.tsisar.wboard.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import ua.tsisar.wboard.dto.BoardDTO;
import ua.tsisar.wboard.R;

public class CreateBoardDialog extends DialogFragment{

    private CreateBoardDialogListener listener;
    private EditText nameEditText;
    private CheckBox isPublic;

    public interface CreateBoardDialogListener {
        void onCreateBoard(BoardDTO boardDTO);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getActivity() instanceof CreateBoardDialogListener) {
            listener = (CreateBoardDialogListener) getActivity();
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_board_name, null);
        nameEditText = (EditText) view.findViewById(R.id.name_editText);
        isPublic = (CheckBox) view.findViewById(R.id.public_board_checkBox);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setView(view)
                .setTitle("Create board")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Continue", (DialogInterface dialog, int which) -> {
                    if (listener != null) {
                        listener.onCreateBoard(
                                new BoardDTO(null, nameEditText.getText().toString(),
                                        isPublic.isChecked()));
                    }
                });

        return builder.create();
    }
}