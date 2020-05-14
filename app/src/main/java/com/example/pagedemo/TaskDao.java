package com.example.pagedemo;

import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;


@Dao
public interface TaskDao {
    @Insert
    void insertTasks(Task ...tasks);
    @Query("DELETE FROM task_table")
    void deleteAllTasks();
    @Query("SELECT * FROM task_table ORDER BY id")
    DataSource.Factory<Integer,Task> getAllTasks();

}
