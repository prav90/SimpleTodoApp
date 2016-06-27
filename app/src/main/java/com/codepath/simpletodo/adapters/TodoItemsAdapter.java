package com.codepath.simpletodo.adapters;

import android.content.Context;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.codepath.simpletodo.R;
import com.codepath.simpletodo.models.TodoItem;
import com.codepath.simpletodo.utils.DateHelper;
import com.codepath.simpletodo.utils.Priority;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by rpraveen on 6/25/16.
 */
public class TodoItemsAdapter extends ArrayAdapter<TodoItem> {
    private SimpleDateFormat formatter;
    private TodoItemsAdapterInterface fragment;
    private boolean isTodoList;
    public interface TodoItemsAdapterInterface {
        public void onTodoItemCompletedDateChanged();
    }
    public TodoItemsAdapter(
            Context context, ArrayList<TodoItem> items, TodoItemsAdapterInterface fragment) {
        super(context, 0, items);
        formatter = new SimpleDateFormat("EEE, MMM d, ''yy h:mm a");
        this.fragment = fragment;
        this.isTodoList = isTodoList;
    }

    private void setPriorityColor(TextView tvPriority, Priority priority) {
        switch (priority) {
            case LOW:
                tvPriority.setTextColor(ContextCompat.getColor(getContext(), R.color.yellow));
                break;
            case MEDIUM:
                tvPriority.setTextColor(ContextCompat.getColor(getContext(), R.color.orange));
                break;
            case HIGH:
                tvPriority.setTextColor(ContextCompat.getColor(getContext(), R.color.red));
                break;
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final TodoItem todoItem = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.todo_item_row, parent, false);
        }
        TextView tvTaskName = (TextView) convertView.findViewById(R.id.tvTaskName);
        TextView tvTaskPriority = (TextView) convertView.findViewById(R.id.tvPriority);
        TextView tvDueDate = (TextView) convertView.findViewById(R.id.tvDueDate);
        CheckBox cbTaskCompleted = (CheckBox) convertView.findViewById(R.id.cbTaskComplete);
        tvTaskName.setText(todoItem.getTaskName());
        tvTaskPriority.setText(todoItem.getPriority().getName());
        setPriorityColor(tvTaskPriority, todoItem.getPriority());
        if (todoItem.getCompletedDate() != null) {
            cbTaskCompleted.setChecked(true);
        } else {
            cbTaskCompleted.setChecked(false);
        }
        cbTaskCompleted.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(
                            CompoundButton compoundButton, final boolean checked) {
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            public void run() {
                                if (checked) {
                                    todoItem.setCompletedDate(DateHelper.getCurrentSQLDate());
                                } else {
                                    todoItem.setCompletedDate(null);
                                }
                                todoItem.save();
                                fragment.onTodoItemCompletedDateChanged();
                            }
                        }, 200);
                    }
                }
        );
        try {
            tvDueDate.setText(formatter.format(todoItem.getDueDate().getTime()));
            if (todoItem.getCompletedDate() != null) {
                tvDueDate.setText(formatter.format(todoItem.getCompletedDate().getTime()));
                tvDueDate.setTextColor(ContextCompat.getColor(getContext(), R.color.green));
            }
        } catch (Exception e) {
            tvDueDate.setText("");
        }
        return convertView;
    }
}
