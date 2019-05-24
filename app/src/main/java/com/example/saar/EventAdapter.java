package com.example.saar;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
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
        public TextView title, date, location;
        public TimelineView timelineView;

        public TimeLineViewHolder(View view, int viewType) {
            super(view);
            timelineView = view.findViewById(R.id.timeline);
            timelineView.initLine(viewType);
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
    public void onBindViewHolder(TimeLineViewHolder holder, int position) {
        holder.title.setText(dataList.get(position).getTitle());
        holder.date.setText(dataList.get(position).getDate());
        holder.location.setText(dataList.get(position).getLocation());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

}
