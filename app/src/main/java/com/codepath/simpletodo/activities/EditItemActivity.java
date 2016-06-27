package com.codepath.simpletodo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.codepath.simpletodo.R;

public class EditItemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        String edit_todo_item = getIntent().getStringExtra("edit_todo_item");
        EditText etEditTodo = (EditText) findViewById(R.id.mlTodoEdit);
        etEditTodo.setText(edit_todo_item);
    }

    public void onSave(View v) {
        EditText etEditTodo = (EditText) findViewById(R.id.mlTodoEdit);
        Intent data = new Intent();
        data.putExtra("edited_todo_item", etEditTodo.getText().toString());
        data.putExtra("edited_index", getIntent().getIntExtra("edit_index", 0));
        setResult(RESULT_OK, data);
        finish();
    }
}
