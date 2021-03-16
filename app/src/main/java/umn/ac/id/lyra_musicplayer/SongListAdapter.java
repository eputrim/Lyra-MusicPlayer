package umn.ac.id.lyra_musicplayer;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;

public class SongListAdapter extends RecyclerView.Adapter<SongListAdapter.SongViewHolder> {

    private final LinkedList<String> songList;
    private LayoutInflater mInflater;

    SongListAdapter(Context context, LinkedList<String> songs){
        mInflater = LayoutInflater.from(context);
        songList = songs;
    }

    @NonNull
    @Override
    public SongListAdapter.SongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.song_list, parent, false);
        return new SongViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull SongListAdapter.SongViewHolder holder, int position) {
        String mCurrent = songList.get(position);
        holder.song.setText(mCurrent);
    }

    @Override
    public int getItemCount() {
        return songList.size();
    }

    class SongViewHolder extends RecyclerView.ViewHolder{
        public final TextView song;
        final SongListAdapter songAdapter;

        public SongViewHolder(@NonNull View itemView, SongListAdapter songAdapter) {
            super(itemView);
            song = itemView.findViewById(R.id.song);
            this.songAdapter = songAdapter;
        }

    }
}
