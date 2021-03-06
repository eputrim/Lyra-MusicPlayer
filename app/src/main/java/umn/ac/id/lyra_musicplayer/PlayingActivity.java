package umn.ac.id.lyra_musicplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

public class PlayingActivity extends AppCompatActivity {

    ImageView playpause, previous, next, back;
    TextView title;
    SeekBar seekbar;

    static MediaPlayer mediaPlayer;
    int position;
    String stitle;

    ArrayList<File> songs;
    Thread updateseekbar;

    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playing);

        playpause = (ImageView) findViewById(R.id.playpause);
        previous = (ImageView) findViewById(R.id.previous);
        next = (ImageView) findViewById(R.id.next);
        title = findViewById(R.id.title);
        seekbar = findViewById(R.id.seekbar);
        back = (ImageView) findViewById(R.id.back);

        updateseekbar = new Thread(){
            @Override
            public void run() {
                int duration = mediaPlayer.getDuration();
                int current = 0;

                while(current < duration){
                    try {
                        sleep(500);
                        current = mediaPlayer.getCurrentPosition();
                        seekbar.setProgress(current);
                    }
                    catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }
        };

        if(mediaPlayer != null){
            mediaPlayer.stop();
            mediaPlayer.release();
        }

        Intent i = getIntent();
        Bundle bundle = i.getExtras();
        songs = (ArrayList) i.getSerializableExtra("songs");
        stitle = songs.get(position).getName().toString();

        title.setText(i.getStringExtra("title"));
        title.setSelected(true);

        position = bundle.getInt("position", 0);
        Uri uri = Uri.parse(songs.get(position).toString());

        mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
        mediaPlayer.start();

        seekbar.setMax(mediaPlayer.getDuration());
        updateseekbar.start();
        seekbar.getProgressDrawable().setColorFilter(getResources().getColor(R.color.brown), PorterDuff.Mode.MULTIPLY);
        seekbar.getThumb().setColorFilter(getResources().getColor(R.color.lightbrown), PorterDuff.Mode.SRC_IN);

        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(seekBar.getProgress());
            }
        });

        playpause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayer.isPlaying()){
                    playpause.setImageResource(R.drawable.ic_play);
                    mediaPlayer.pause();
                }
                else {
                    playpause.setImageResource(R.drawable.ic_pause);
                    mediaPlayer.start();
                }
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();
                mediaPlayer.release();
                position = ((position+1)%songs.size());

                Uri uri = Uri.parse(songs.get(position).toString());
                mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);

                stitle = songs.get(position).getName().toString();
                title.setText(stitle);

                if(position == songs.size()){
                    next.animate().translationY(0).alpha(0).setDuration(350).start();
                    next.setEnabled(false);
                }
                else {
                    next.animate().translationY(0).alpha(1).setDuration(350).start();
                    next.setEnabled(true);
                }

                try{
                    mediaPlayer.start();
                }
                catch(Exception e){
                    startActivity(new Intent(getApplicationContext(), SongActivity.class));
                }
            }
        });

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();
                mediaPlayer.release();
                position = ((position-1)<0)?(songs.size()-1):(position-1);

                Uri uri = Uri.parse(songs.get(position).toString());
                mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);

                stitle = songs.get(position).getName().toString();
                title.setText(stitle);

                if(position == 0){
                    previous.animate().translationY(0).alpha(0).setDuration(350).start();
                    previous.setEnabled(false);
                }
                else {
                    previous.animate().translationY(0).alpha(1).setDuration(350).start();
                    previous.setEnabled(true);
                }

                try{
                    mediaPlayer.start();
                }
                catch(Exception e){
                    startActivity(new Intent(getApplicationContext(), SongActivity.class));
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}