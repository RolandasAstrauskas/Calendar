package com.example.calendarintroactivity;

public class EventsDatabaseContract {

    private EventsDatabaseContract() {
    }

    public static final class EventsEntry {

        public static final String TABLE_NAME = "events";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_EVENT = "event";
        public static final String COLUMN_TIME = "dataTimeRemind";
        public static final String COLUMN_DATE_ID = "date";

        public static final String CREATE_SQL = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY, " +
                COLUMN_EVENT + " TEXT, " +
                COLUMN_TIME + " TEXT, " +
                COLUMN_DATE_ID + " TEXT )";
    }
}



