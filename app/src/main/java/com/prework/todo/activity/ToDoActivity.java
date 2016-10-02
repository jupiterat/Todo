package com.prework.todo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.prework.todo.R;
import com.prework.todo.adapter.TodoAdapter;
import com.prework.todo.fragment.AddItemDialogFragment;
import com.prework.todo.interfaces.DismissListener;
import com.prework.todo.models.TodoModel;
import com.prework.todo.task.GetTodoTask;

import java.util.ArrayList;

public class ToDoActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<TodoModel>> {
    private static final int REQUEST_CODE = 1234;
    TodoAdapter itemsAdapter;
    ListView lvItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.ic_todo_small);
        setTitle(getString(R.string.app_name));
        setContentView(R.layout.activity_todo);

        lvItems = (ListView) findViewById(R.id.lvItems);
        setupListViewListener();

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (itemsAdapter == null) {
            getSupportLoaderManager().initLoader(0, null, this).forceLoad();
        } else {
            lvItems.setAdapter(itemsAdapter);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_item:
                showEditDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showEditDialog() {
        FragmentManager fm = getSupportFragmentManager();
        AddItemDialogFragment editNameDialogFragment = AddItemDialogFragment.newInstance(null);
        editNameDialogFragment.setOnDismissListener(new DismissListener() {
            @Override
            public void onDismissListener(boolean isSuccess) {
                if (isSuccess) {
                    getSupportLoaderManager().initLoader(0, null, ToDoActivity.this).forceLoad();
                }
            }
        });
        editNameDialogFragment.show(fm, "fragment_edit_name");
    }


    /**
     * add some listview listeners to handle click, long click... event
     */
    private void setupListViewListener() {
        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                Intent i = new Intent(ToDoActivity.this, ToDoDetailActivity.class);
                i.putExtra("value", itemsAdapter.getItem(pos));
                startActivityForResult(i, REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {

        }
    }

    @Override
    public Loader<ArrayList<TodoModel>> onCreateLoader(int id, Bundle args) {
        return new GetTodoTask(this);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<TodoModel>> loader, ArrayList<TodoModel> data) {
        itemsAdapter = new TodoAdapter(this, data);
        lvItems.setAdapter(itemsAdapter);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<TodoModel>> loader) {

    }
}
