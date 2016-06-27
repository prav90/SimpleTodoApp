package com.codepath.simpletodo.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.codepath.simpletodo.R;
import com.codepath.simpletodo.adapters.TodoItemsAdapter;
import com.codepath.simpletodo.models.TodoItem;

import java.util.ArrayList;

/**
 * Created by rpraveen on 6/25/16.
 */
public class TodoFragment extends Fragment implements
        TodoItemsAdapter.TodoItemsAdapterInterface, EditDialogFragment.OnTaskEditedListener {

    protected ArrayList<TodoItem> todoItems;
    protected TodoItemsAdapter todoItemsAdapter;
    protected ListView lvTodoItems;

    protected OnTodoItemStateChanged mCallback;

    public interface OnTodoItemStateChanged {
        public void onTodoItemStateChanged();
    }

    public static TodoFragment newInstance(int page) {
        return new TodoFragment();
    }

    public void onTodoItemCompletedDateChanged() {
        Toast.makeText(getContext(), "Task moved to Done", Toast.LENGTH_LONG).show();
        mCallback.onTodoItemStateChanged();
    }

    public void onTaskEdited() { mCallback.onTodoItemStateChanged(); }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallback = (OnTodoItemStateChanged) context;
    }
    protected void readItems() {
        todoItems = (ArrayList<TodoItem>) TodoItem.getAllDueTasks();
    }

    protected void setUpListViewListener() {
        lvTodoItems.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(
                            AdapterView<?> adapterView, View view, final int pos, long id) {
                        new AlertDialog.Builder(getActivity())
                                .setMessage("Are you sure you want to delete this task?")
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .setPositiveButton(
                                        R.string.yes,
                                        new DialogInterface.OnClickListener() {

                                            public void onClick(
                                                    DialogInterface dialog, int whichButton) {
                                                TodoItem removeItem = todoItems.get(pos);
                                                todoItems.remove(pos);
                                                removeItem.delete();
                                                todoItemsAdapter.notifyDataSetChanged();
                                        }})
                                .setNegativeButton(R.string.no, null)
                                .show();
                        return true;
                    }
                }
        );
        lvTodoItems.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(
                            AdapterView<?> adapterView, View view, int pos, long id) {
                        TodoItem editItem = todoItems.get(pos);
                        FragmentManager fm = getFragmentManager();
                        EditDialogFragment editDialog = EditDialogFragment.newInstance(
                                editItem,TodoFragment.this);
                        editDialog.setTargetFragment(TodoFragment.this, 300);
                        editDialog.show(fm, "Edit ToDo");
                    }
                }
        );
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
