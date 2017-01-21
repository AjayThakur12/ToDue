package com.dagger.todo;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * Created by Harshit on 21/01/17.
 */

public class AddTodoDialogFragment extends DialogFragment {

    Spinner prioritySpinner;
    Spinner statusSpinner;
    EditText content;
    EditText title;
    View dialogView;
    static Note currentNote;
    UpdateItem updateItem;
    String[] priorities = {"Low", "Medium", "High"};
    String[] completionStatus = {"ToDo", "Done"};
    static Integer currentIndex;

    public static AddTodoDialogFragment getInstance(@Nullable Note note, @Nullable Integer index) {
        currentNote = note;
        currentIndex = index;
        return new AddTodoDialogFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        updateItem = (UpdateItem) getActivity();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        ArrayAdapter<String> priorityAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, priorities);
        ArrayAdapter<String> statusAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, completionStatus);
        prioritySpinner.setAdapter(priorityAdapter);
        statusSpinner.setAdapter(statusAdapter);
        dialogView = LayoutInflater.from(getContext()).inflate(R.layout.activity_add_note, null);
        title = (EditText) dialogView.findViewById(R.id.todo_title_editText);
        content = (EditText) dialogView.findViewById(R.id.todo_content_edittext);
        prioritySpinner = (Spinner) dialogView.findViewById(R.id.priority_spinners);
        statusSpinner = (Spinner) dialogView.findViewById(R.id.status_spinner);
        if (currentNote != null) {
            title.setText(currentNote.getTitle(), TextView.BufferType.EDITABLE);
            content.setText(currentNote.getContent(), TextView.BufferType.EDITABLE);
            prioritySpinner.setSelection(currentNote.getPriority());
            statusSpinner.setSelection(currentNote.isComplete());
        }
        return new AlertDialog.Builder(getContext())
                .setTitle("Add a new Note")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Note note = new Note(title.getText().toString(),
                                content.getText().toString(),
                                statusSpinner.getSelectedItemPosition(),
                                "TEST",
                                prioritySpinner.getSelectedItemPosition());
                            updateItem.updateItem(note,currentIndex);
                    }
                })
                .setView(dialogView)
                .create();
    }
}
