package com.leonov_dev.todostack.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface TaskDao {

    //-----------------Queries----------------------
    @Query("SELECT * FROM tasks")
    List<Task> getTasks();

    @Query("SELECT * FROM tasks WHERE task_id = :taskId")
    Task getTaskById(long taskId);

    @Query("SELECT * FROM tasks WHERE assigned_date = :assignedDate")
    List<Task> getTodaysTasks(long assignedDate);

    //-----------------Inserts----------------------
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTask(Task task);

    //-----------------Updates----------------------
    @Update
    void updateTask(Task task);

    //-----------------Deletes----------------------
    @Delete
    void deleteTask(Task task);
}
