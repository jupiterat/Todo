package com.prework.todo.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.prework.todo.R;
import com.prework.todo.models.TodoModel;

import java.util.ArrayList;

/**
 * Created by duongthoai on 10/2/16.
 */

public class TodoAdapter extends BaseAdapter {
    private ArrayList<TodoModel> items;
    private Context mContext;

    public TodoAdapter(Context ctx, ArrayList<TodoModel> data) {
        items = data;
        mContext = ctx;
    }

    @Override
    public int getCount() {
        if (items != null) {
            return items.size();
        }
        return 0;
    }

    @Override
    public TodoModel getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        TodoViewHolder viewHolder;
        View vi = convertView;
        if (convertView == null) {

            viewHolder = new TodoViewHolder();
            LayoutInflater inflater = LayoutInflater.from(mContext);
            vi = inflater.inflate(R.layout.todo_item, parent, false);
            viewHolder.todoName = (TextView) vi.findViewById(R.id.todo_title);
            viewHolder.todoPriority = (TextView) vi.findViewById(R.id.todo_priority);
            vi.setTag(viewHolder);
        } else {
            viewHolder = (TodoViewHolder) vi.getTag();
        }
        viewHolder.todoName.setText(items.get(i).getName());
        String priority = items.get(i).getPriority();
        if (priority.toLowerCase().equals("high")) {
            viewHolder.todoPriority.setTextColor(Color.parseColor("#ea7781"));
        } else if (priority.toLowerCase().equals("medium")) {
            viewHolder.todoPriority.setTextColor(Color.parseColor("#d09b4c"));
        } else {
            viewHolder.todoPriority.setTextColor(Color.parseColor("#26b998"));
        }
        viewHolder.todoPriority.setText(priority);
        return vi;
    }

    public class TodoViewHolder {
        private TextView todoName;
        private TextView todoPriority;
    }
}
