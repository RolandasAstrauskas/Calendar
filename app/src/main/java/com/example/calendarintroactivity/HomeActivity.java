package com.example.calendarintroactivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.CalendarView;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class HomeActivity extends AppCompatActivity {


    private RecyclerView event;
    private RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_home);

        CalendarView calendar = findViewById(R.id.calendarView);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String[] currentDate = {sdf.format(new Date(calendar.getDate()))};

        Database helper = new Database(HomeActivity.this);
        SQLiteDatabase database = helper.getReadableDatabase();
        DatabaseWorker worker = new DatabaseWorker(database);
        ArrayList<Event> events = (ArrayList<Event>) worker.selectEvent(currentDate);

        this.event = findViewById(R.id.recV);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        this.event.setLayoutManager(mLayoutManager);

        adapter = new EventAdapter(events, this);
        this.event.setAdapter(adapter);

        swipeEvent();
        viewCalendar();
        onResume();

    }

    public void onResume() {
        super.onResume();
        CalendarView calendar = (CalendarView) findViewById(R.id.calendarView);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String[] currentDate = {sdf.format(new Date(calendar.getDate()))};

        Database helper = new Database(HomeActivity.this);
        SQLiteDatabase database = helper.getReadableDatabase();
        DatabaseWorker worker = new DatabaseWorker(database);
        ArrayList<Event> events = (ArrayList<Event>) worker.selectEvent(currentDate);

        this.event = findViewById(R.id.recV);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        this.event.setLayoutManager(mLayoutManager);

        adapter = new EventAdapter(events, this);
        this.event.setAdapter(adapter);

    }

    private void swipeEvent() {
        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            RecyclerView event;

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                Database helper = new Database(HomeActivity.this);
                SQLiteDatabase database = helper.getReadableDatabase();
                DatabaseWorker worker = new DatabaseWorker(database);

                worker.deleteEvent(EventAdapter.pos);

                CalendarView calendar = (CalendarView) findViewById(R.id.calendarView);
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                String[] currentDate = {sdf.format(new Date(calendar.getDate()))};

                ArrayList<Event> events = (ArrayList<Event>) worker.selectEvent(currentDate);

                this.event = findViewById(R.id.recV);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(HomeActivity.this);
                this.event.setLayoutManager(mLayoutManager);

                adapter = new EventAdapter(events, HomeActivity.this);
                this.event.setAdapter(adapter);

            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(event);

    }

    private void viewCalendar() {
        CalendarView cal = findViewById(R.id.calendarView);
        cal.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                Intent intent = new Intent(HomeActivity.this, EventActivity.class);
                String date = String.valueOf(dayOfMonth) + "/" + String.valueOf(Integer.parseInt(String.valueOf(month)) + 1) + "/" + String.valueOf(year);
                intent.putExtra("date", date);
                startActivity(intent);
            }
        });
    }
}