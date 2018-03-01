package com.simpleweek.davidgong.simpleweek;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.davidgong.simpleweek.R;

/**
 * Created by David Gong on 1/31/2018.
 */

public class EditData extends AppCompatActivity {
    private static final String TAG = "EditDataActivity";
    private TextView saveEdit, toTimeEdit, fromTimeEdit, toTextEdit, fromTextEdit, deleteButton;
    private EditText taskNameEdit, taskDescriptionEdit;
    DatabaseHelper mDatabaseHelper;
    private String task_name, task_description, start_time, end_time;
    private int selectedID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_data_layout);

        init();

        Intent intent = getIntent();
        task_name = intent.getStringExtra("task_name");
        task_description = intent.getStringExtra("task_description");
        start_time = intent.getStringExtra("start_time");
        end_time = intent.getStringExtra("end_time");

        taskNameEdit.setText(task_name);
        taskDescriptionEdit.setText(task_description);
        fromTimeEdit.setText(start_time);
        toTimeEdit.setText(end_time);

        saveEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameOfTask = taskNameEdit.getText().toString();
                String descriptionOfTask = taskDescriptionEdit.getText().toString();
                String startTime = fromTimeEdit.getText().toString();
                String endTime = toTimeEdit.getText().toString();

                if (startTime.isEmpty() || endTime.isEmpty()) {
                    Toast.makeText(EditData.this, "Please put in the starting/end times.", Toast.LENGTH_SHORT).show();
                } else if (nameOfTask.isEmpty() || descriptionOfTask.isEmpty()) {
                    Toast.makeText(EditData.this, "Please enter in details for the task.", Toast.LENGTH_SHORT).show();
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

//        deleteButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                new AlertDialog.Builder(EditData.this)
//                        .setIcon(android.R.drawable.ic_dialog_alert)
//                        .setTitle("Delete this task?")
//                        .setMessage("Are you sure you want to delete this task?")
//                        .setNegativeButton("No", null)
//                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//
//                            }
//                        })
//            }
//        });

    }

    private void init() {
        saveEdit = (TextView) findViewById(R.id.saveEditButton);
        toTimeEdit = (TextView) findViewById(R.id.toTimeEdit);
        fromTimeEdit = (TextView) findViewById(R.id.fromTimeEdit);
        toTextEdit = (TextView) findViewById(R.id.toTextEdit);
        fromTextEdit = (TextView) findViewById(R.id.fromTextEdit);
        taskNameEdit = (EditText) findViewById(R.id.taskNameEdit);
        taskDescriptionEdit = (EditText) findViewById(R.id.taskDescriptionEdit);

    }
}
