package com.example.calendarintroactivity;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;


public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder> {

    public Context context;
    private ArrayList<Event> events;
    public static int pos;

    public EventAdapter(ArrayList<Event> eventList, Context context) {
        this.events = eventList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.event, parent, false);
        return new ViewHolder(v, context);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {


        Event event = events.get(position);
        holder.textEvent.setText(" " + event.getEvent());

        holder.itemView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                pos = events.get(position).getId();
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        if (events != null) {
            return events.size();
        } else {
            return 0;
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        View view;
        TextView textEvent;
        Context context;

        public ViewHolder(View view, Context context) {
            super(view);
            this.view = view;
            this.context = context;
            textEvent = view.findViewById(R.id.textEvent);


        }
    }


}

