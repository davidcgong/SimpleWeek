package com.simpleweek.davidgong.simpleweek;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.davidgong.simpleweek.R;

import java.util.Calendar;

public class CreateTask extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText taskName, taskDescription;
    private TextView fromText, toText, saveButton, toTime, fromTime;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);

        setUI();
        init();
        timePicker();
        timePicker2();
        savePress();
    }

    private void setUI() {
        toolbar = (Toolbar) findViewById(R.id.TaskToolbar);
        fromTime = (TextView) findViewById(R.id.fromTime);
        toTime = (TextView) findViewById(R.id.toTime);
        taskName = (EditText) findViewById(R.id.taskName);
        taskDescription = (EditText) findViewById(R.id.taskDescription);
        fromText = (TextView) findViewById(R.id.fromText);
        toText = (TextView) findViewById(R.id.toText);
        saveButton = (TextView) findViewById(R.id.saveButton);
    }

    private void init() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Task Information");
    }

    private void savePress() {
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameOfTask = taskName.getText().toString();
                String descriptionOfTask = taskDescription.getText().toString();
                String startTime = fromTime.getText().toString();
                String endTime = toTime.getText().toString();

                if (startTime.isEmpty() || endTime.isEmpty()) {
                    Toast.makeText(CreateTask.this, "Please put in the starting/end times.", Toast.LENGTH_SHORT).show();
                } else if (nameOfTask.isEmpty() || descriptionOfTask.isEmpty()) {
                    Toast.makeText(CreateTask.this, "Please enter in details for the task.", Toast.LENGTH_SHORT).show();
                } else {
                    Intent sendBackData = new Intent();
                    sendBackData.putExtra("task_name", nameOfTask);
                    sendBackData.putExtra("task_description", descriptionOfTask);
                    sendBackData.putExtra("start_time", startTime);
                    sendBackData.putExtra("end_time", endTime);
                    setResult(Activity.RESULT_OK, sendBackData);
                    finish();
                }
            }
        });
    }

    private void timePicker() {
        fromTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar currentTime = Calendar.getInstance();
                int hour = currentTime.get(Calendar.HOUR_OF_DAY);
                int minute = currentTime.get(Calendar.MINUTE);
                TimePickerDialog timePicker;
                timePicker = new TimePickerDialog(CreateTask.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        int hour = hourOfDay % 12;
                        fromTime.setText(String.format("%02d:%02d %s", hour, minute,
                                hourOfDay < 12 ? "AM" : "PM"));
                    }
                }, hour, minute, false);
                timePicker.setTitle("Starting Time");
                timePicker.show();
            }
        });
    }

    private void timePicker2() {
        toTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar currentTime = Calendar.getInstance();
                int hour = currentTime.get(Calendar.HOUR_OF_DAY);
                int minute = currentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(CreateTask.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        int hour = hourOfDay % 12;
                        toTime.setText(String.format("%02d:%02d %s", hour, minute,
                                hourOfDay < 12 ? "AM" : "PM"));
                    }
                }, hour, minute, false);
                mTimePicker.setTitle("Starting Time");
                mTimePicker.show();
            }
        });
    }




}

