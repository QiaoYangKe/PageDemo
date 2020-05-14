package com.example.pagedemo;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
@Database(entities = {Task.class}, version = 1,exportSchema = false)
public abstract class TasksDatabase extends RoomDatabase {
    static TasksDatabase instance;
    static synchronized TasksDatabase getInstance(Context context) {
         if(instance == null) {
             instance = Room.databaseBuilder(context, TasksDatabase.class,"tasks_database")
                     .build();
         }
         return instance;
    }

    abstract TaskDao getTaskDao();
}
