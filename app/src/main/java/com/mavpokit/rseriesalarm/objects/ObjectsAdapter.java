package com.mavpokit.rseriesalarm.objects;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mavpokit.rseriesalarm.R;
import com.mavpokit.rseriesalarm.data.model.AlarmObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex on 24.01.2017.
 */

public class ObjectsAdapter extends RecyclerView.Adapter<ObjectsAdapter.ViewHolder> {
    private List<AlarmObject> alarmObjects = new ArrayList<>();
    private ObjectClickListener objectClickListener;


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView objectNameTextView;
        public ViewHolder(View v) {
            super(v);
            objectNameTextView = (TextView)v.findViewById(R.id.text_view_object_name);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ObjectsAdapter(ObjectClickListener objectClickListener) {
        this.objectClickListener=objectClickListener;
    }

    public void setAlarmObjects(List<AlarmObject> alarmObjects) {
        this.alarmObjects = alarmObjects;
        notifyDataSetChanged();
    }

    public void addAlarmObject(AlarmObject alarmObject) {
        alarmObjects.add(alarmObjects.size(),alarmObject);
        notifyItemChanged(alarmObjects.size());
    }


    // Create new views (invoked by the layout manager)
    @Override
    public ObjectsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_objects, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.objectNameTextView.setText(alarmObjects.get(position).getName());
        holder.itemView.setOnLongClickListener(v ->
        {
            objectClickListener.onObjectClick(alarmObjects.get(position));
            return true;
        });

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return alarmObjects.size();
    }

    public interface ObjectClickListener{
        void onObjectClick(AlarmObject object);
    }
}
