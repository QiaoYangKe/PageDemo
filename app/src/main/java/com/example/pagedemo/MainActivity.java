package com.example.pagedemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ImageButton buttonPopulate;
    TaskDao taskDao;
    TasksDatabase tasksDatabase;
    MyPagedAdaper pagedAdaper;
    LiveData<PagedList<Task>> allTasksPaged;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);
        buttonPopulate = findViewById(R.id.button3);
        pagedAdaper = new MyPagedAdaper();
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(pagedAdaper);
        recyclerView.addOnItemTouchListener(new MyItemTouchListener(recyclerView) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
                TextView textView = findViewById(R.id.textView);
                Toast.makeText(MainActivity.this,textView.getText(),Toast.LENGTH_SHORT).show();
            }
        });
        tasksDatabase = TasksDatabase.getInstance(this);
        taskDao = tasksDatabase.getTaskDao();
        allTasksPaged = new LivePagedListBuilder<>(taskDao.getAllTasks(),2).build();
        allTasksPaged.observe(this, new Observer<PagedList<Task>>() {
            @Override
            public void onChanged(PagedList<Task> tasks) {
                pagedAdaper.submitList(tasks);
            }
        });
        initAsyncTask();
        buttonPopulate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initAsyncTask();
            }
        });
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.btn_take_photo:
                    System.out.println("btn_take_photo");
                    break;
                case R.id.btn_pick_photo:
                    System.out.println("btn_pick_photo");
                    break;
            }
        }
    };

    @SuppressLint("ResourceType")
    public void showPopFormBottom(View view) {
        TakeToolPopWin takeToolPopWin = new TakeToolPopWin(this, onClickListener);
        //showAtLocation(View parent, int gravity, int x, int y)
        takeToolPopWin.showAtLocation(findViewById(R.layout.activity_main), Gravity.CENTER, 0, 0);
    }

    public void initAsyncTask () {
        new ClearAsyncTask(taskDao).execute();
        Task[] tasks = new Task[1000];
        for (int i = 0; i<1000; i++) {
            Task task = new Task();
            task.setTaskName("测试数据" + i + "*******");
            tasks[i] = task;
        }
        new InsertAsyncTask(taskDao).execute(tasks);
    }

    static class InsertAsyncTask extends AsyncTask<Task,Void,Void> {
        TaskDao taskDao;

        InsertAsyncTask(TaskDao taskDao) {
            this.taskDao = taskDao;
        }

        @Override
        protected Void doInBackground(Task... tasks) {
            taskDao.insertTasks(tasks);
            return null;
        }
    }

    static class ClearAsyncTask extends AsyncTask<Void,Void,Void> {
        TaskDao taskDao;

        ClearAsyncTask(TaskDao taskDao) {
            this.taskDao = taskDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            taskDao.deleteAllTasks();
            return null;
        }
    }
}
