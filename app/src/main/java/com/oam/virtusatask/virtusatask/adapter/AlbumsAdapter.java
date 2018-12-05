package com.oam.virtusatask.virtusatask.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.oam.virtusatask.virtusatask.R;
import com.oam.virtusatask.virtusatask.model.Album;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AlbumsAdapter extends RecyclerView.Adapter<AlbumsAdapter.MyViewHolder> {
 
    private Context context;
    private List<Album> notesList;
 
    public class MyViewHolder extends RecyclerView.ViewHolder {
 
        @BindView(R.id.title)
        TextView title;
 

 
        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
 
 
    public AlbumsAdapter(Context context, List<Album> notesList) {
        this.context = context;
        this.notesList = notesList;
    }
 
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.album_item_row, parent, false);
 
        return new MyViewHolder(itemView);
    }
 
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Album note = notesList.get(position);
 
        holder.title.setText(note.getTitle());
 

    }
 
    @Override
    public int getItemCount() {
        return notesList.size();
    }
 
}