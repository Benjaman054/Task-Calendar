package com.example.demo;

public class TaskSender {
    private String date;
    private String task;
    private int priority;


    public TaskSender(String date, String task, int priority) {
        this.date = date;
        this.task = task;
        this.priority = priority;
    }

    public TaskSender() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}

