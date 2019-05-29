package com.example.saar.Timeline_Events;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.saar.R;
import com.github.vipulasri.timelineview.TimelineView;

import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.TimeLineViewHolder> {

    private List<Event> dataList;

    public EventAdapter(List<Event> dataList) {
        this.dataList = dataList;
    }

    @Override
    public int getItemViewType(int position) {
        return TimelineView.getTimeLineViewType(position, getItemCount());
    }

    public class TimeLineViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout parent_layout;
        public TextView title, date, location;
        public TimelineView timelineView;

        public TimeLineViewHolder(View view, int viewType) {
            super(view);
            timelineView = view.findViewById(R.id.timeline);
            timelineView.initLine(viewType);
            parent_layout = view.findViewById(R.id.parent_layout);
            title = view.findViewById(R.id.title);
            location = view.findViewById(R.id.location);
            date = view.findViewById(R.id.date);
        }
    }

    @Override
    public TimeLineViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.event_list_row, parent, false);

        return new TimeLineViewHolder(itemView, viewType);
    }

    @Override
    public void onBindViewHolder(final TimeLineViewHolder holder, final int position) {
        holder.title.setText(dataList.get(position).getTitle());
        holder.date.setText(dataList.get(position).getDate());
        holder.location.setText(dataList.get(position).getLocation());
        holder.parent_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = holder.parent_layout.getContext();
                Intent intent = new Intent(context, EventDetails.class);
                intent.putExtra("title", dataList.get(position).getTitle());
                intent.putExtra("description", dataList.get(position).getDescription());
                intent.putExtra("location", dataList.get(position).getLocation());
                intent.putExtra("date", dataList.get(position).getDate());
                intent.putExtra("time", dataList.get(position).getTime());
                intent.putExtra("image_url", dataList.get(position).getImage_url());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

}
