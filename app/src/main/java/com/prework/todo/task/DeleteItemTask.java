package com.prework.todo.task;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.prework.todo.db.DBHelper;

/**
 * Created by duongthoai on 10/2/16.
 */

public class DeleteItemTask extends AsyncTaskLoader<Integer> {
    final String TAG = DeleteItemTask.class.getSimpleName();
    DBHelper dbHelper;
    int todoId;

    public DeleteItemTask(Context context, int id) {
        super(context);
        todoId = id;
        dbHelper = new DBHelper(context);
    }

    @Override
    public Integer loadInBackground() {
        Log.e(TAG, "loadInBackground");
        return dbHelper.deleteTask(todoId);
    }

    @Override
    public void deliverResult(Integer data) {
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
