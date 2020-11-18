package com.example.calendarintroactivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;


public class DatabaseWorker {

    private SQLiteDatabase database;
    EventsDatabaseContract.EventsEntry entry = new EventsDatabaseContract.EventsEntry();

    public DatabaseWorker(SQLiteDatabase database) {
        this.database = database;
    }

    public long insertEvent(Integer id, String event, String dataTimeRemind, String dateId) {
        ContentValues contentValues = new ContentValues();

        if (id != null) contentValues.put(entry.COLUMN_ID, id);

        contentValues.put(entry.COLUMN_EVENT, event);
        contentValues.put(entry.COLUMN_TIME, dataTimeRemind);
        contentValues.put(entry.COLUMN_DATE_ID, dateId);

        long newPaymentId = database.insert(entry.TABLE_NAME, null, contentValues);

        return newPaymentId;

    }

    public List<Event> selectEvent(String[] currentDate) {

        String[] columns = new String[]{
                entry.COLUMN_ID,
                entry.COLUMN_EVENT,
                entry.COLUMN_TIME,
                entry.COLUMN_DATE_ID};

        Cursor cursor = database.query(entry.TABLE_NAME, columns, entry.COLUMN_DATE_ID + "=?", currentDate, null, null, null);

        ArrayList<Event> events = new ArrayList<>();

        while (cursor.moveToNext()) {
            int idIndex = cursor.getColumnIndex(entry.COLUMN_ID);
            int id = cursor.getInt(idIndex);

            int eventIndex = cursor.getColumnIndex(entry.COLUMN_EVENT);
            String eventTask = cursor.getString(eventIndex);

            int timeIndex = cursor.getColumnIndex(entry.COLUMN_TIME);
            String time = cursor.getString(timeIndex);

            Event event = new Event(id, eventTask, time);
            events.add(event);
        }

        return events;
    }

    public void deleteEvent(Integer id) {

        database.delete(entry.TABLE_NAME, entry.COLUMN_ID + "=" + id, null);

    }

}


