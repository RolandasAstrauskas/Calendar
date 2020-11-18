package com.example.calendarintroactivity;


import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import com.google.android.material.snackbar.Snackbar;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class EventActivity extends AppCompatActivity {

    private RecyclerView event;
    private RecyclerView.Adapter adapter;
    private ConstraintLayout constraintLayout;
    private Snackbar snackbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_event);

        Intent intent = getIntent();
        String date = intent.getStringExtra("date");
        final String[] selectDate = {date};

        Database helper = new Database(EventActivity.this);
        SQLiteDatabase database = helper.getReadableDatabase();
        final DatabaseWorker worker = new DatabaseWorker(database);
        ArrayList<Event> events = (ArrayList<Event>) worker.selectEvent(selectDate);

        this.event = findViewById(R.id.listEvent);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        this.event.setLayoutManager(mLayoutManager);

        adapter = new EventAdapter(events, this);
        this.event.setAdapter(adapter);

        buttonAdd();


    }

    Database helper = new Database(this);

    private void buttonAdd() {
        ImageButton btnS = findViewById(R.id.imageButtonAdd);
        btnS.setOnClickListener(new View.OnClickListener() {
            private RecyclerView event;

            @Override
            public void onClick(View v) {

                SQLiteDatabase database = helper.getReadableDatabase();
                DatabaseWorker worker = new DatabaseWorker(database);
                EditText txtEdit = findViewById(R.id.txtEdit);
                String text = txtEdit.getText().toString();


                if (text.length() >= 40) {
                    constraintLayout = findViewById(R.id.layout);
                    snackbar = Snackbar
                            .make(constraintLayout, "To long text!", Snackbar.LENGTH_SHORT);
                    snackbar.show();

                } else {

                    if (!text.equals("")) {
                        Intent intent = getIntent();
                        String date = intent.getStringExtra("date");
                        Date currentTime = Calendar.getInstance().getTime();

                        worker.insertEvent(null, text, toString().valueOf(currentTime), date);

                        constraintLayout = findViewById(R.id.layout);
                        snackbar = Snackbar
                                .make(constraintLayout, "Note saved!", Snackbar.LENGTH_SHORT);
                        snackbar.show();
                        txtEdit.setText("");

                        String[] selectDate = {date};
                        ArrayList<Event> events = (ArrayList<Event>) worker.selectEvent(selectDate);

                        this.event = findViewById(R.id.listEvent);
                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(EventActivity.this);
                        this.event.setLayoutManager(mLayoutManager);

                        adapter = new EventAdapter(events, EventActivity.this);
                        this.event.setAdapter(adapter);
                    } else {
                        constraintLayout = findViewById(R.id.layout);
                        snackbar = Snackbar
                                .make(constraintLayout, "Nothing to save!", Snackbar.LENGTH_SHORT);
                        snackbar.show();
                        txtEdit.setText(null);
                    }
                }
            }
        });
    }
}