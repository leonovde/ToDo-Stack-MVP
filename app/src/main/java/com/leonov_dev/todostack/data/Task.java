package com.leonov_dev.todostack.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.leonov_dev.todostack.utils.DateConverter;

@Entity(tableName = "tasks")

public final class Task {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "task_id")
    public long mId;

    @ColumnInfo(name = "title")
    public String mTitle;

    @ColumnInfo(name = "description")
    public String mDescription;

    @ColumnInfo(name = "modify_date")
    public long modifyDate;

    @ColumnInfo(name = "assigned_date")
    public long mAssignedDate;

    @ColumnInfo(name = "reminder_condition")
    public String mReminderCondition;

//    //Used to get task of a particular date
//    @ColumnInfo(name = "modify_date_converted")
//    public Date mModifyDateConverted;

    public Task (String title, String description, long modifyDate, long assignedDate){
        this.mTitle = title;
        this.mDescription = description;
        this.modifyDate = modifyDate;
        this.mAssignedDate = assignedDate;
    }

    @Ignore
    public Task (String title, String description, long modifyDate, long assignedDate,
                 String reminderCondition){
        this.mTitle = title;
        this.mDescription = description;
        this.modifyDate = modifyDate;
        this.mAssignedDate = assignedDate;
        this.mReminderCondition = reminderCondition;
    }

    public long getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getDescription() {
        return mDescription;
    }

    public long getModifyDate() {
        return modifyDate;
    }

    public long getAssignedDate() {
        return mAssignedDate;
    }

    public String getReminderCondition() {return mReminderCondition;}

    //--------------------
    public void setId(long id) {
        this.mId = id;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public void setDescription(String description) {
        this.mDescription = description;
    }

    public void setReminderCondition(String condition){ this.mReminderCondition = condition;}

}
