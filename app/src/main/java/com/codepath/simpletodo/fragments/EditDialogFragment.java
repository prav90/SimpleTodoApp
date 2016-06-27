package com.codepath.simpletodo.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.codepath.simpletodo.R;
import com.codepath.simpletodo.models.TodoItem;
import com.codepath.simpletodo.utils.DateHelper;
import com.codepath.simpletodo.utils.Priority;

import org.parceler.Parcels;

import java.util.Calendar;

/**
 * Created by rpraveen on 6/26/16.
 */
public class EditDialogFragment extends DialogFragment {
    TodoItem mTodoItem;

    public interface OnTaskEditedListener {
        public void onTaskEdited();
    }
    public EditDialogFragment() {

    }

    public static EditDialogFragment newInstance(TodoItem todoItem, OnTaskEditedListener listener) {
        EditDialogFragment fragment = new EditDialogFragment();
        Bundle args = new Bundle();
        args.putParcelable("editTodoItem", Parcels.wrap(todoItem));
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        // request a window without the title
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.add_todo_fragment, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mTodoItem = (TodoItem) Parcels.unwrap(getArguments().getParcelable("editTodoItem"));
        super.onViewCreated(view, savedInstanceState);
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        final EditText etTasKName = (EditText) view.findViewById(R.id.etTaskName);
        final EditText etDueDate = (EditText) view.findViewById(R.id.etDueDate);
        final EditText etDueTime = (EditText) view.findViewById(R.id.etDueTime);
        final Spinner taskPriority = (Spinner) view.findViewById(R.id.taskPriority);
        taskPriority.setSelection(mTodoItem.getPriority().getVal());
        etTasKName.setText(mTodoItem.getTaskName());
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(mTodoItem.getDueDate());
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        final int hour = calendar.get(Calendar.HOUR_OF_DAY);
        final int minute = calendar.get(Calendar.MINUTE);
        etDueDate.setText(DateHelper.getFormatteDate(year, month, day));
        etDueTime.setText(DateHelper.getFormattedTime(hour, minute));
        etDueDate.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                                new DatePickerDialog.OnDateSetListener() {
                                    @Override
                                    public void onDateSet(
                                            DatePicker datePicker, int year, int month, int day) {
                                        etDueDate.setText(DateHelper.getFormatteDate(
                                                year, month, day)
                                        );
                                    }
                                }, year, month, day);
                        datePickerDialog.show();
                    }
                }
        );
        etDueTime.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(),
                                new TimePickerDialog.OnTimeSetListener() {
                                    @Override
                                    public  void onTimeSet(TimePicker view, int hour, int minute) {
                                        etDueTime.setText(
                                                DateHelper.getFormattedTime(hour, minute)
                                        );
                                    }
                                }, hour, minute, false);
                        timePickerDialog.show();
                    }
                }
        );
        Button cancelButton = (Button) view.findViewById(R.id.btCancel);
        Button saveButton = (Button) view.findViewById(R.id.btSave);
        cancelButton.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        getDialog().dismiss();
                    }
                }
        );
        saveButton.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        mTodoItem.setTaskName(etTasKName.getText().toString());
                        mTodoItem.setPriority(Priority.getValue(
                                taskPriority.getSelectedItem().toString()));
                        String dueDate = etDueDate.getText().toString();
                        String dueTime = etDueTime.getText().toString();
                        mTodoItem.setDueDate(DateHelper.parseDate(dueDate+ " " + dueTime));
                        mTodoItem.save();
                        OnTaskEditedListener listener = (OnTaskEditedListener) getTargetFragment();
                        listener.onTaskEdited();
                        getDialog().dismiss();
                    }
                }
        );
        Button createTaskButton = (Button) view.findViewById(R.id.createTaskButton);
        createTaskButton.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onResume() {
        // Get existing layout params for the window
        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        // Assign window properties to fill the parent
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
        // Call super onResume after sizing
        super.onResume();
    }

}
