package com.prework.todo.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.prework.todo.R;
import com.prework.todo.fragment.AddItemDialogFragment;
import com.prework.todo.interfaces.DismissListener;
import com.prework.todo.models.TodoModel;
import com.prework.todo.task.DeleteItemTask;

public class ToDoDetailActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Integer> {

    private final int MSG_DISMISS = 1213;

    private TextView mTodoNameTxt;
    private TextView mTodoDueDateTxt;
    private TextView mTodoNoteTxt;
    private TextView mTodoPriorityTxt;
    private TextView mTodoStatusTxt;
    private TodoModel data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setIcon(R.drawable.ic_todo_small);
        setTitle(getString(R.string.app_name));
        setContentView(R.layout.activity_todo_detail);
        if (getIntent() != null) {
            data = (TodoModel) getIntent().getSerializableExtra("value");
        }
        mTodoNameTxt = (TextView) findViewById(R.id.etName);
        mTodoDueDateTxt = (TextView) findViewById(R.id.etDueDate);
        mTodoNoteTxt = (TextView) findViewById(R.id.etNote);
        mTodoPriorityTxt = (TextView) findViewById(R.id.priority);
        mTodoStatusTxt = (TextView) findViewById(R.id.status);
        if (data != null) {
            mTodoNameTxt.setText(data.getName());
            mTodoDueDateTxt.setText(data.getDuedate());
            mTodoNoteTxt.setText(data.getNote());
            mTodoPriorityTxt.setText(data.getPriority());
            mTodoStatusTxt.setText(data.getStatus());
        }

    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.detail_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_edit_item:
                showEditDialog();
                return true;
            case R.id.action_delete_item:
                showConfirmDialog();
                return true;
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showConfirmDialog() {
        AlertDialog myQuittingDialogBox = new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to delete this task")
                .setIcon(R.drawable.ic_warning)

                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.dismiss();
                        //
                        getSupportLoaderManager().initLoader(0, null, ToDoDetailActivity.this).forceLoad();
                    }

                })


                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                    }
                })
                .create();
        myQuittingDialogBox.show();
    }

    private void showEditDialog() {
        FragmentManager fm = getSupportFragmentManager();
        AddItemDialogFragment editNameDialogFragment = AddItemDialogFragment.newInstance(data);
        editNameDialogFragment.setOnDismissListener(new DismissListener() {
            @Override
            public void onDismissListener(boolean isSuccess) {
                finish();
            }
        });
        editNameDialogFragment.show(fm, "fragment_edit_name");
    }


    @Override
    public Loader<Integer> onCreateLoader(int id, Bundle args) {
        return new DeleteItemTask(this, data.getId());
    }

    @Override
    public void onLoadFinished(Loader<Integer> loader, Integer data) {
        handler.sendEmptyMessage(MSG_DISMISS);
    }

    @Override
    public void onLoaderReset(Loader<Integer> loader) {

    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == MSG_DISMISS) {
                finish();
            }
        }
    };


}
