package umn.ac.id.lyra_musicplayer;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;

public class SongListAdapter extends RecyclerView.Adapter<SongListAdapter.SongViewHolder> {

    private final LinkedList<String> songList;
    private LayoutInflater mInflater;
    private final ArrayList<File> arraySongList;
    String mCurrent;

    SongListAdapter(Context context, LinkedList<String> songs, ArrayList<File> listSongs){
        mInflater = LayoutInflater.from(context);
        songList = songs;
        arraySongList = listSongs;
    }

    @NonNull
    @Override
    public SongListAdapter.SongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.song_list, parent, false);
        return new SongViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull SongListAdapter.SongViewHolder holder, int position) {
        mCurrent = songList.get(position);
        holder.song.setText(mCurrent);
    }

    @Override
    public int getItemCount() {
        return songList.size();
    }

    class SongViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public final TextView song;
        final SongListAdapter songAdapter;

        public SongViewHolder(@NonNull View itemView, SongListAdapter songAdapter) {
            super(itemView);
            song = itemView.findViewById(R.id.song);
            this.songAdapter = songAdapter;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v){
            int songPosition = getLayoutPosition();
            String element = songList.get(songPosition);

            Intent startSong = new Intent(v.getContext(), PlayingActivity.class);
            startSong.putExtra("songs", arraySongList);
            startSong.putExtra("title", element);
            startSong.putExtra("position", songPosition);
            v.getContext().startActivity(startSong);
        }
    }
}
