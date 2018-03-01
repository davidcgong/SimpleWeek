package com.simpleweek.davidgong.simpleweek;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.simpleweek.davidgong.simpleweek.EditData;

import java.util.Collections;
import java.util.Set;

/**
 * Created by David Gong on 1/12/2018.
 */

public class FridayActivity extends DayActivity {
    public void insertData(String newEntry, String columnNumber) {
        boolean insertData = mDatabaseHelper.addData(newEntry, columnNumber, "friday_table");
        if (insertData) {
            toastMessage("Task has been successfully saved.");
        } else {
            toastMessage("An error has occurred.");
        }
    }
    protected void setListView() {
        Cursor cursor = mDatabaseHelper.getData("friday_table");
        int columnCount = 0;
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String taskNameData = cursor.getString(0);
            String taskDescriptionData = cursor.getString(1);
            String startTimeData = cursor.getString(2);
            String endTimeData = cursor.getString(3);

            taskNameArray.add(taskNameData);
            taskDescriptionArray.add(taskDescriptionData);
            startTimeArray.add(startTimeData);
            endTimeArray.add(endTimeData);

            columnCount++;
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
                i = new Intent(FridayActivity.this, CreateTask.class);
                startActivityForResult(i, 1);
            }
        });

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(FridayActivity.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Clear all tasks?")
                        .setMessage("Are you sure you want to delete all tasks?")
                        .setNegativeButton("No", null)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mDatabaseHelper.clearDatabase("friday_table");
                                finish();
                            }
                        })
                        .show();
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String name = parent.getItemAtPosition(position).toString();
                Log.d("dayactivity", "Clicked on" + name);

                Intent editIntent = new Intent(FridayActivity.this, EditData.class);
                startActivity(editIntent);
            }
        });
    }
//    @Override
//    public boolean onContextItemSelected(MenuItem item) {
//        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
//        switch(item.getItemId()) {
//            case R.id.delete:
//                taskNameArray.remove(info.position);
//                taskDescriptionArray.remove(info.position);
//                startTimeArray.remove(info.position);
//                endTimeArray.remove(info.position);
//                mDatabaseHelper.deleteItem(listView.getId(), "friday_table");
//                arrayAdapter.notifyDataSetChanged();
//                return true;
//            default:
//                return super.onContextItemSelected(item);
//        }
//    }
}
