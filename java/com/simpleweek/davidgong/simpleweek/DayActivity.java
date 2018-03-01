package com.simpleweek.davidgong.simpleweek;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.simpleweek.davidgong.simpleweek.EditData;
import com.example.davidgong.simpleweek.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;

public class DayActivity extends AppCompatActivity {

    private android.support.v7.widget.Toolbar toolbar;
    protected ListView listView;
    protected TextView plusText, clearButton;
    protected FrameLayout frameLayout;
    protected RelativeLayout relativeLayout, mainRelativeLayout;
    protected FloatingActionButton fButton;
    protected ArrayAdapter arrayAdapter;
    protected ArrayList<String> taskNameArray = new ArrayList<String>();
    protected ArrayList<String> taskDescriptionArray = new ArrayList<String>();
    protected ArrayList<String> startTimeArray = new ArrayList<String>();
    protected ArrayList<String> endTimeArray = new ArrayList<String>();
    protected String taskName, taskDescription, startTime, endTime;
    protected int[] sorterHelper;
    protected String[] contextOptions;
    DatabaseHelper mDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day);

        setUI();
        init();
        mDatabaseHelper = new DatabaseHelper(this);
        setListView();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }


    protected void setUI() {
        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.DayToolbar);
        frameLayout = (FrameLayout) findViewById(R.id.frameLayout);
        relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout);
        fButton = (FloatingActionButton) findViewById(R.id.addButton);
        plusText = (TextView) findViewById(R.id.plusText);
        listView = (ListView) findViewById(R.id.listMain2);
        registerForContextMenu(listView);
        clearButton = (TextView) findViewById(R.id.clearButton);
        mainRelativeLayout = (RelativeLayout) findViewById(R.id.mainRelativeLayout);
        contextOptions = getResources().getStringArray(R.array.EditDelete);
    }

    protected void init() {
        setSupportActionBar(toolbar);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void insertData(String newEntry, String columnNumber) {
        boolean insertData = mDatabaseHelper.addData(newEntry, columnNumber, "monday_table");
        if (insertData) {
            toastMessage("Task has been successfully saved.");
        } else {
            toastMessage("An error has occurred.");
        }
    }

    protected void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                taskName = data.getStringExtra("task_name");
                taskDescription = data.getStringExtra("task_description");
                startTime = data.getStringExtra("start_time");
                endTime = data.getStringExtra("end_time");
                insertData(taskName, "1");
                insertData(taskDescription, "2");
                insertData(startTime, "3");
                insertData(endTime, "4");
                finish();
                startActivity(getIntent());
            }
        }
    }



    protected void setListView() {
        Cursor cursor = mDatabaseHelper.getData("monday_table");
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String taskNameData = cursor.getString(1);
            String taskDescriptionData = cursor.getString(2);
            String startTimeData = cursor.getString(3);
            String endTimeData = cursor.getString(4);

            taskNameArray.add(taskNameData);
            taskDescriptionArray.add(taskDescriptionData);
            startTimeArray.add(startTimeData);
            endTimeArray.add(endTimeData);

            cursor.moveToNext();
        }
        Set nullRemove = Collections.singleton(null);
        taskNameArray.removeAll(nullRemove);
        taskDescriptionArray.removeAll(nullRemove);
        startTimeArray.removeAll(nullRemove);
        endTimeArray.removeAll(nullRemove);

        Log.d("ArrayPrint", "taskNames " + taskNameArray.toString() + "taskDescriptions "
                + taskDescriptionArray.toString());


        arrayAdapter = new ArrayAdapter(this, taskNameArray, taskDescriptionArray,
                startTimeArray, endTimeArray);
        listView.setAdapter(arrayAdapter);

        fButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i;
                i = new Intent(DayActivity.this, CreateTask.class);
                startActivityForResult(i, 1);
            }
        });

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(DayActivity.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Clear all tasks?")
                        .setMessage("Are you sure you want to delete all tasks?")
                        .setNegativeButton("No", null)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mDatabaseHelper.clearDatabase("monday_table");
                                finish();
                            }
                        })
                        .show();
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String taskName = parent.getItemAtPosition(position).toString();
                String taskDescription = taskDescriptionArray.get(position);
                String startTime = startTimeArray.get(position);
                String endTime = endTimeArray.get(position);


                Log.d("dayactivity", "Clicked on " + taskName + " at position"
                        + Integer.toString(position));
                Log.d("dayactivity3", String.format("Includes %s (TD), %s (ST), %s (ET).", taskDescription, startTime, endTime));

                Intent editIntent = new Intent(DayActivity.this, EditData.class);
                editIntent.putExtra("task_name", taskName);
                editIntent.putExtra("task_description", taskDescription);
                editIntent.putExtra("start_time", startTime);
                editIntent.putExtra("end_time", endTime);
                startActivity(editIntent);
            }
        });
    }

    public class ArrayAdapter extends BaseAdapter {

        private Context theContext;
        private LayoutInflater layoutInflater;
        private TextView taskName, taskDescription, startTime, endTime, hyphen;
        private ArrayList<String> taskNameArray, taskDescriptionArray, startTimeArray, endTimeArray;

        public ArrayAdapter(Context context, ArrayList<String> taskName, ArrayList<String> taskDescription,
                            ArrayList<String> startTime, ArrayList<String> endTime) {
            theContext = context;
            taskNameArray = taskName;
            taskDescriptionArray = taskDescription;
            startTimeArray = startTime;
            endTimeArray = endTime;
            layoutInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return taskNameArray != null ? taskNameArray.size() : 0;
        }

        @Override
        public Object getItem(int position) {
            return taskNameArray.get(position);
        }

        @Override
        public long getItemId(int position) { return position; }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = layoutInflater.inflate(R.layout.itemlayoutday, null);
            }

            taskName = (TextView) convertView.findViewById(R.id.taskName);
            taskDescription = (TextView) convertView.findViewById(R.id.taskDescription);
            startTime = (TextView) convertView.findViewById(R.id.startTime);
            endTime = (TextView) convertView.findViewById(R.id.endTime);
            hyphen = (TextView) convertView.findViewById(R.id.hyphen);

            taskName.setText(taskNameArray.get(position));
            taskDescription.setText(taskDescriptionArray.get(position));
            startTime.setText(startTimeArray.get(position));
            endTime.setText(endTimeArray.get(position));
            return convertView;
        }
    }

}

