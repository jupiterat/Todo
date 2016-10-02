package com.prework.todo.task;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.prework.todo.db.DBHelper;
import com.prework.todo.models.TodoModel;

import java.util.ArrayList;

/**
 * Created by duongthoai on 10/2/16.
 */

public class GetTodoTask extends AsyncTaskLoader<ArrayList<TodoModel>> {
    final String TAG = GetTodoTask.class.getSimpleName();
    DBHelper dbHelper;

    public GetTodoTask(Context context) {
        super(context);
        dbHelper = new DBHelper(context);
    }

    @Override
    public ArrayList<TodoModel> loadInBackground() {
        Log.e(TAG, "loadInBackground");
        return dbHelper.getAllTasks();
    }

    @Override
    public void deliverResult(ArrayList<TodoModel> data) {
        super.deliverResult(data);
        Log.e(TAG, "deliverResult");

    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Override
    protected void onStopLoading() {
        super.onStopLoading();
    }
}
