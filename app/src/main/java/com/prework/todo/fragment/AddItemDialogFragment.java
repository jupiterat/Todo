package com.prework.todo.fragment;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.prework.todo.R;
import com.prework.todo.interfaces.DismissListener;
import com.prework.todo.models.TodoModel;
import com.prework.todo.task.InsertItemTask;
import com.prework.todo.task.UpdateItemTask;

/**
 * Created by thoai on 9/20/2016.
 */
public class AddItemDialogFragment extends DialogFragment implements LoaderManager.LoaderCallbacks<Long> {

    private final int MSG_DISMISS_DIALOG = 1212;
    private final int UPDATE_TASK = 1234;
    private final int ADD_TASK = 1235;
    private EditText mNameEdt;
    private EditText mNoteEdt;
    private DatePicker mDueDatePicker;
    private Spinner mPrioritySpiner;
    private Spinner mStatusSpiner;

    private DismissListener dismissListener;

    private TodoModel data;


    public static AddItemDialogFragment newInstance(TodoModel todoModel) {
        AddItemDialogFragment fragment = new AddItemDialogFragment();
        if (todoModel != null) {
            Bundle b = new Bundle();
            b.putSerializable("value", todoModel);
            fragment.setArguments(b);
        }
        return fragment;
    }

    public AddItemDialogFragment() {

    }

    public void setOnDismissListener(DismissListener listener) {
        dismissListener = listener;
    }

    @Override
    public void onStart() {
        super.onStart();


    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_FRAME, android.R.style.Theme_Holo_Light_DarkActionBar);
        if (getArguments() != null) {
            if (getArguments().getSerializable("value") != null) {
                data = (TodoModel) getArguments().getSerializable("value");
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_edit_item, container);
        getDialog().setTitle(getString(R.string.app_name));

        Toolbar toolbar = (Toolbar) v.findViewById(R.id.toolbar);
        mNameEdt = (EditText) v.findViewById(R.id.etName);
        mNoteEdt = (EditText) v.findViewById(R.id.etNote);
        mPrioritySpiner = (Spinner) v.findViewById(R.id.priority_list);
        //
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.priority, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mPrioritySpiner.setAdapter(adapter);
        //
        mStatusSpiner = (Spinner) v.findViewById(R.id.status_list);
        ArrayAdapter<CharSequence> statusdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.status, android.R.layout.simple_spinner_item);
        statusdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mStatusSpiner.setAdapter(statusdapter);

        mDueDatePicker = (DatePicker) v.findViewById(R.id.dpDueDate);
        if (data != null) {
            mNameEdt.setText(data.getName());
            mNoteEdt.setText(data.getNote());
            //
            int spinnerPosition = adapter.getPosition(data.getPriority());
            mPrioritySpiner.setSelection(spinnerPosition);
            //
            int spinnerPosition1 = statusdapter.getPosition(data.getStatus());
            mStatusSpiner.setSelection(spinnerPosition1);
            //
            String[] date = data.getDuedate().split("/");
            mDueDatePicker.updateDate(Integer.parseInt(date[0]), (Integer.parseInt(date[1]) - 1), Integer.parseInt(date[2]));
        }

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_save_item:
                        if (data != null) {
                            getLoaderManager().initLoader(UPDATE_TASK, null, AddItemDialogFragment.this).forceLoad();
                        } else {
                            getLoaderManager().initLoader(ADD_TASK, null, AddItemDialogFragment.this).forceLoad();
                        }
//
                        break;
                    case R.id.action_close_item:
                        dismiss();
                        break;
                }
                return true;
            }
        });
        toolbar.inflateMenu(R.menu.edit_menu);
        toolbar.setTitle(getString(R.string.app_name));
        toolbar.setTitleTextColor(Color.parseColor("#26b998"));
        return v;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (dismissListener != null) {
            if (data != null) {
                dismissListener.onDismissListener(false);
            } else {
                dismissListener.onDismissListener(true);
            }
        }
        data = null;
    }

    @Override
    public Loader<Long> onCreateLoader(int id, Bundle args) {
        TodoModel newData = new TodoModel();
        if (data != null) {
            newData.setId(data.getId());
        }
        newData.setName(mNameEdt.getText().toString());

        int day = mDueDatePicker.getDayOfMonth();
        int month = mDueDatePicker.getMonth();
        int year = mDueDatePicker.getYear();
        newData.setDuedate(year + "/" + (month + 1) + "/" + day);
//        Calendar calendar = Calendar.getInstance();
//        calendar.set(year, month, day);
        newData.setNote(mNoteEdt.getText().toString());
        newData.setPriority(mPrioritySpiner.getSelectedItem().toString());
        newData.setStatus(mStatusSpiner.getSelectedItem().toString());
        switch (id) {
            case ADD_TASK:
                return new InsertItemTask(getActivity(), newData);
            case UPDATE_TASK:
                return new UpdateItemTask(getActivity(), newData);
            default:
                return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<Long> loader, Long data) {
        handler.sendEmptyMessage(MSG_DISMISS_DIALOG);
    }

    @Override
    public void onLoaderReset(Loader<Long> loader) {

    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == MSG_DISMISS_DIALOG) {
                dismiss();
            }
        }
    };
}
