package com.prework.todo.task;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.prework.todo.db.DBHelper;
import com.prework.todo.models.TodoModel;

/**
 * Created by duongthoai on 10/2/16.
 */

public class UpdateItemTask extends AsyncTaskLoader<Long> {
    final String TAG = UpdateItemTask.class.getSimpleName();
    DBHelper dbHelper;
    TodoModel todo;

    public UpdateItemTask(Context context, TodoModel data) {
        super(context);
        todo = data;
        dbHelper = new DBHelper(context);
    }

    @Override
    public Long loadInBackground() {
        Log.e(TAG, "loadInBackground");
        return dbHelper.updateTask(todo);
    }

    @Override
    public void deliverResult(Long data) {
        super.deliverResult(data);
        Log.e(TAG, "result: " + data);
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
