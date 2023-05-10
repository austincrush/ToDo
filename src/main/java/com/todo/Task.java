package com.todo;

public class Task {

    private String taskInfo;
    private int userid;

    public Task(){

    }

    public Task( String taskInfo, int userid) {
        this.taskInfo = taskInfo;
        this.userid = userid;
    }

    public String getTaskInfo() {
        return taskInfo;
    }

    public void setTaskInfo(String taskInfo) {
        this.taskInfo = taskInfo;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }
}
