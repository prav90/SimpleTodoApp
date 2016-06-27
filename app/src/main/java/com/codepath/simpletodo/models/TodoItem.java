package com.codepath.simpletodo.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.codepath.simpletodo.utils.Priority;

import org.parceler.Parcel;

import java.sql.Date;
import java.util.List;

/**
 * Created by rpraveen on 6/25/16.
 */

@Parcel (analyze = TodoItem.class)
@Table (name = "TodoItems")
public class TodoItem extends Model{

    @Column (name = "taskName", index = true)
    public String taskName;

    @Column (name = "dueDate", index = true)
    public Date dueDate;

    @Column (name = "priority", index = true)
    public Priority priority;

    @Column (name = "completedDate", index = true)
    public Date completedDate;

    public TodoItem() {
        super();
    }

    public TodoItem(String taskName, Priority priority, Date dueDate) {
        this.taskName = taskName;
        this.priority = priority;
        this.dueDate = dueDate;
    }

    public static List<TodoItem> getAllDueTasks() {
        return new Select()
                .from(TodoItem.class)
                .where("completedDate IS NULL")
                .orderBy("dueDate ASC, priority DESC")
                .execute();
    }

    public static List<TodoItem> getAllCompletedTasks() {
        return new Select()
                .from(TodoItem.class)
                .where("completedDate IS NOT null")
                .orderBy("completedDate DESC")
                .execute();
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public Date getCompletedDate() {
        return completedDate;
    }

    public void setCompletedDate(Date completedDate) {
        this.completedDate = completedDate;
    }

}
