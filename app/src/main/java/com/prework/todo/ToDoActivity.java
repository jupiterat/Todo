package com.prework.todo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ToDoActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 1234;
    private ArrayList<String> items;
    ArrayAdapter<String> itemsAdapter;
    ListView lvItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);
        lvItems = (ListView) findViewById(R.id.lvItems);
        readItems();
        itemsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        lvItems.setAdapter(itemsAdapter);
        setupListViewListener();
    }

    /**
     * add some listview listeners to handle click, long click... event
     */
    private void setupListViewListener() {
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int pos, long l) {
                items.remove(pos);
                itemsAdapter.notifyDataSetChanged();
                writeItem();
                return true;
            }
        });
        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                Intent i = new Intent(ToDoActivity.this, EditItemActivity.class);
                i.putExtra("value", items.get(pos));
                i.putExtra("position", pos);
                startActivityForResult(i, REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            // Extract name value from result extras
            String value = data.getExtras().getString("value");
            int position = data.getExtras().getInt("position");
            Log.d("onActivityResult","return value: " + value);
            if(position != -1 && position < items.size()) {
                items.remove(position);
                items.add(position, value);
                itemsAdapter.notifyDataSetChanged();
                writeItem();
            }

        }
    }

    /**
     * add new item into listview
     */
    public void onAddItem(View v) {
        EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
        String itemText = etNewItem.getText().toString();
        if (itemText.isEmpty()) {
            return;
        }
        itemsAdapter.add(itemText);
        etNewItem.setText("");
        writeItem();
    }

    /**
     * open a file and read a newline-delimited list of items
     */
    private void readItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try {
            items = new ArrayList<String>(FileUtils.readLines(todoFile));

        } catch (IOException ex) {
            items = new ArrayList<String>();
        }
    }

    /**
     * save file and write a newline-delimited list of items
     */
    private void writeItem() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try {
            FileUtils.writeLines(todoFile, items);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
