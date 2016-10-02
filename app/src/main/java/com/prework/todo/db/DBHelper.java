package com.prework.todo.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.prework.todo.models.TodoModel;

import java.util.ArrayList;

/**
 * Created by duongthoai on 10/2/16.
 */

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "todo.db";
    public static final String TODO_TABLE_NAME = "todo";
    public static final String TODO_COLUMN_ID = "id";
    public static final String TODO_COLUMN_NAME = "name";
    public static final String TODO_COLUMN_DUE_DATE = "due_date";
    public static final String TODO_COLUMN_NOTE = "note";
    public static final String TODO_COLUMN_PRIORITY = "priority";
    public static final String TODO_COLUMN_STATUS = "status";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TODO_TABLE_NAME + " (" +
                    TODO_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    TODO_COLUMN_NAME + " TEXT, " +
                    TODO_COLUMN_DUE_DATE + " TEXT, " +
                    TODO_COLUMN_NOTE + " TEXT, " +
                    TODO_COLUMN_PRIORITY + " TEXT, " +
                    TODO_COLUMN_STATUS + " TEXT" + " )";

    private static final String SQL_DROP_ENTRIES = "DROP TABLE IF EXISTS " + TODO_TABLE_NAME;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TODO_TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public long insertTask(TodoModel data) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TODO_COLUMN_NAME, data.getName());
        contentValues.put(TODO_COLUMN_DUE_DATE, data.getDuedate());
        contentValues.put(TODO_COLUMN_NOTE, data.getNote());
        contentValues.put(TODO_COLUMN_PRIORITY, data.getPriority());
        contentValues.put(TODO_COLUMN_STATUS, data.getStatus());
        return db.insert(TODO_TABLE_NAME, null, contentValues);
    }

    public long updateTask(TodoModel data) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TODO_COLUMN_NAME, data.getName());
        contentValues.put(TODO_COLUMN_DUE_DATE, data.getDuedate());
        contentValues.put(TODO_COLUMN_NOTE, data.getNote());
        contentValues.put(TODO_COLUMN_PRIORITY, data.getPriority());
        contentValues.put(TODO_COLUMN_STATUS, data.getStatus());
        return db.update(TODO_TABLE_NAME, contentValues, "id = ? ", new String[]{Integer.toString(data.getId())});
    }

    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + TODO_TABLE_NAME + " where id = " + id + "", null);
        return res;
    }

    public Integer deleteTask(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TODO_TABLE_NAME,
                "id = ? ",
                new String[]{Integer.toString(id)});
    }

    public ArrayList<TodoModel> getAllTasks() {
        ArrayList<TodoModel> list = new ArrayList<>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + TODO_TABLE_NAME, null);
        res.moveToFirst();

        while (res.isAfterLast() == false) {
            TodoModel data = new TodoModel();
            data.setId(res.getInt(res.getColumnIndex(TODO_COLUMN_ID)));
            data.setName(res.getString(res.getColumnIndex(TODO_COLUMN_NAME)));
            data.setDuedate(res.getString(res.getColumnIndex(TODO_COLUMN_DUE_DATE)));
            data.setNote(res.getString(res.getColumnIndex(TODO_COLUMN_NOTE)));
            data.setPriority(res.getString(res.getColumnIndex(TODO_COLUMN_PRIORITY)));
            data.setStatus(res.getString(res.getColumnIndex(TODO_COLUMN_STATUS)));
            list.add(data);
            res.moveToNext();
        }
        return list;
    }


}
