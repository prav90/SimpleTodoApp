package com.codepath.simpletodo.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.codepath.simpletodo.R;
import com.codepath.simpletodo.adapters.TodoItemsAdapter;
import com.codepath.simpletodo.models.TodoItem;

import java.util.ArrayList;

/**
 * Created by rpraveen on 6/25/16.
 */
public class DoneFragment extends TodoFragment {

    public static TodoFragment newInstance(int page) {
        return new DoneFragment();
    }

    @Override
    protected void readItems() {
        todoItems = (ArrayList<TodoItem>) TodoItem.getAllCompletedTasks();
    }

    public void onTodoItemCompletedDateChanged() {
        Toast.makeText(getContext(), "Task moved to TODO", Toast.LENGTH_SHORT).show();
        mCallback.onTodoItemStateChanged();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.todo_fragement_page, container, false);
        lvTodoItems = (ListView) view.findViewById(R.id.lvTodoItems);
        readItems();
        todoItemsAdapter = new TodoItemsAdapter(getActivity(), todoItems, this);
        lvTodoItems.setAdapter(todoItemsAdapter);
        setUpListViewListener();
        return view;
    }
}
