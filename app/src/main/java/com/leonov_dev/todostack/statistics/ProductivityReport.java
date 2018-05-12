package com.leonov_dev.todostack.statistics;

public class ProductivityReport {

    private String taskTitle;
    private String taskTimeSpent;
    private long taskTimeSpentLong;

    public ProductivityReport (String title, String timeSpent, long timeSpentLong){
        taskTitle = title;
        taskTimeSpent = timeSpent;
        taskTimeSpentLong = timeSpentLong;
    }

    public String getTaskTimeSpent() {
        return taskTimeSpent;
    }

    public void setTaskDuration(String taskTimeSpent) {
        this.taskTimeSpent = taskTimeSpent;
    }

    public long getTaskTimeSpentLong() {
        return taskTimeSpentLong;
    }

    public void setTaskDurationLong(long taskTimeSpentLong ) {
        this.taskTimeSpentLong = taskTimeSpentLong ;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }
}
