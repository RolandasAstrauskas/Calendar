package com.example.calendarintroactivity;

public class Event {

    private int id;
    private String event;
    private String dataTimeRemind;
    private String dateId;

    public Event(int id, String event, String dataTimeRemind) {
        this.id = id;
        this.event = event;
        this.dataTimeRemind = dataTimeRemind;

    }

    public int getId() {
        return id;
    }

    public String getEvent() {
        return event;
    }

}
