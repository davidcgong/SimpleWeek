package com.simpleweek.davidgong.simpleweek;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.davidgong.simpleweek.R;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private android.support.v7.widget.Toolbar toolbar;
    private ListView listView;
    private Calendar calendar;
    private TextView currentDayIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUI();
        init();
        setListView();
    }

    private void setUI() {
        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.MainToolbar);
        listView = (ListView) findViewById(R.id.listMain);
        listView.setClickable(true);
    }

    private void init() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Week-At-A-Glance");
    }

    @Override
    protected void onResume() {
        super.onResume();
        ListView listView = (ListView) findViewById(R.id.listMain);
        listView.setEnabled(true);
    }


    @Override
    protected void onPause() {
        super.onPause();
    }

    private void changeLVColor(ListView listView, int index) {
        TypedValue typedValue = new TypedValue();
        TypedArray labelColor = this.obtainStyledAttributes(typedValue.data, new int[] {R.attr.colorPrimaryDark,
                R.attr.color});
        int labelC = labelColor.getColor(0, 0);

        View v = listView.findViewWithTag(String.valueOf(index));
        v.setBackgroundColor(labelC);
    }


    private void setListView() {
        String[] day = getResources().getStringArray(R.array.Days);

        SimpleAdapter simpleAdapter = new SimpleAdapter(this, day);

//        Calendar calendar = Calendar.getInstance();
//        int currentDay = calendar.get(Calendar.DAY_OF_WEEK);
//        if (currentDay == Calendar.MONDAY) {
//            currentDayIndicator = (TextView) convertView.findViewById(R.id.currentDay);
//        }
//        if (currentDay == Calendar.TUESDAY) {
//            currentDayIndicator = (TextView) convertView.findViewById(R.id.currentDay);
//        }
//        if (currentDay == Calendar.WEDNESDAY) {
//            currentDayIndicator = (TextView) convertView.findViewById(R.id.currentDay);
//        }
//        if (currentDay == Calendar.THURSDAY) {
//            currentDayIndicator = (TextView) convertView.findViewById(R.id.currentDay);
//        }
//        if (currentDay == Calendar.FRIDAY) {
//            currentDayIndicator = (TextView) convertView.findViewById(R.id.currentDay);
//        }

        listView.setAdapter(simpleAdapter);
        listView.setClickable(true);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Makes sure that the activity for the days aren't opened more than once
                listView.setEnabled(false);
                Intent mondayIntent = new Intent(MainActivity.this, MondayActivity.class);
                Intent tuesdayIntent = new Intent(MainActivity.this, TuesdayActivity.class);
                Intent wednesdayIntent = new Intent(MainActivity.this, WednesdayActivity.class);
                Intent thursdayIntent = new Intent(MainActivity.this, ThursdayActivity.class);
                Intent fridayIntent= new Intent(MainActivity.this, FridayActivity.class);

                if (position == 0) {
                    startActivity(mondayIntent);
                }
                if (position == 1) {
                    startActivity(tuesdayIntent);
                }
                if (position == 2) {
                    startActivity(wednesdayIntent);
                }
                if (position == 3) {
                    startActivity(thursdayIntent);
                }
                if (position == 4) {
                    startActivity(fridayIntent);
                }
            }
        });
    }

    public class SimpleAdapter extends BaseAdapter {

        private Context theContext;
        private LayoutInflater layoutInflater;
        private TextView title, day;
        private String[] dayArray;

        public SimpleAdapter(Context context, String[] day) {
            theContext = context;
            dayArray = day;
            layoutInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return dayArray.length;
        }

        @Override
        public Object getItem(int position) {
            return dayArray[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = layoutInflater.inflate(R.layout.itemlayout, null);
            }
            convertView.setTag(String.valueOf(position));

            day = (TextView) convertView.findViewById(R.id.imageMain);
            day.setText(dayArray[position]);

            return convertView;
        }
    }
}
