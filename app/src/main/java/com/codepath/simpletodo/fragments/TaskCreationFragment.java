package com.codepath.simpletodo.fragments;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.codepath.simpletodo.R;
import com.codepath.simpletodo.models.TodoItem;
import com.codepath.simpletodo.utils.DateHelper;
import com.codepath.simpletodo.utils.Priority;

import java.util.Calendar;

/**
 * Created by rpraveen on 6/26/16.
 */
public class TaskCreationFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.add_todo_fragment, parent, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Setup any handles to view objects here
        // EditText etFoo = (EditText) view.findViewById(R.id.etFoo);
        Button cancelButton = (Button) view.findViewById(R.id.btCancel);
        Button saveButton = (Button) view.findViewById(R.id.btSave);
        cancelButton.setVisibility(View.INVISIBLE);
        saveButton.setVisibility(View.INVISIBLE);
        final EditText etTasKName = (EditText) view.findViewById(R.id.etTaskName);
        final EditText etDueDate = (EditText) view.findViewById(R.id.etDueDate);
        final EditText etDueTime = (EditText) view.findViewById(R.id.etDueTime);
        final Spinner taskPriority = (Spinner) view.findViewById(R.id.taskPriority);
        final Calendar calendar = Calendar.getInstance();
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
        Button createTask = (Button) view.findViewById(R.id.createTaskButton);
        createTask.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        TodoItem newTask = new TodoItem();
                        newTask.setTaskName(etTasKName.getText().toString());
                        newTask.setPriority(Priority.getValue(
                                taskPriority.getSelectedItem().toString()));
                        String dueDate = etDueDate.getText().toString();
                        String dueTime = etDueTime.getText().toString();
                        newTask.setDueDate(DateHelper.parseDate(dueDate+ " " + dueTime));
                        newTask.save();
                        getActivity().setResult(getActivity().RESULT_OK);
                        getActivity().finish();
                    }
                }
        );
    }
}
