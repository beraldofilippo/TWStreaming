package com.beraldo.twstreaming.home;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.beraldo.twstreaming.R;
import com.beraldo.twstreaming.models.Status;

import java.util.LinkedList;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {
    private LinkedList<Status> mDataset;

    public HomeAdapter(LinkedList<Status> list) {
        mDataset = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card,
                parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Status status = mDataset.get(position);
        holder.itemStatus.setText(status.getText());
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public void addStatus(Status status) {
        if (mDataset.size() < 5) {
            mDataset.addFirst(status);
            notifyDataSetChanged();
        } else {
            mDataset.addFirst(status);
            mDataset.removeLast();
            notifyDataSetChanged();
        }
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView itemStatus;

        private ViewHolder(View itemView) {
            super(itemView);
            itemStatus = (TextView) itemView.findViewById(R.id.status_text);

        }
    }
}
