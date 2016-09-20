package com.prework.todo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

/**
 * Created by thoai on 9/20/2016.
 */
public class EditItemActivity extends AppCompatActivity {
    private EditText etItem;
    private int pos= -1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Edit item");
        setContentView(R.layout.activity_edit_item);
        String editvalue = getIntent().getStringExtra("value");
        pos = getIntent().getIntExtra("position", -1);
        etItem = (EditText) findViewById(R.id.etNewItem);
        etItem.setText(editvalue);
    }

    public void onSaveItem(View v) {
        Intent data = new Intent();
        data.putExtra("value", etItem.getText().toString());
        data.putExtra("position", pos);
        setResult(RESULT_OK, data);
        finish();
    }
}
