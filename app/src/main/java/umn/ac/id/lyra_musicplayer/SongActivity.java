package umn.ac.id.lyra_musicplayer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;

public class SongActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    ImageView menu;
    //ListView song;
    RecyclerView song;
    String[] items;
    Dialog popupmenu;

    private final LinkedList<String> songList = new LinkedList<>();
    private RecyclerView mRecyclerView;
    private SongListAdapter songAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song);

        //song = findViewById(R.id.song);
        mRecyclerView = (RecyclerView) findViewById(R.id.song);
        menu = (ImageView) findViewById(R.id.menu);

        runtimePermission();

        popupmenu = new Dialog(this);
        popupmenu.setContentView(R.layout.popup);
        popupmenu.show();

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                optionMenu(v);
            }
        });
    }

    public void optionMenu(View v){
        PopupMenu optionmenu = new PopupMenu(this, v);
        optionmenu.setOnMenuItemClickListener(this);
        optionmenu.inflate(R.menu.optionmenu);
        optionmenu.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()){
            case R.id.profile:
                Intent iprofile = new Intent(SongActivity.this, ProfileActivity.class);
                startActivity(iprofile);
                return true;
            case R.id.logout:
                Intent ilogout = new Intent(SongActivity.this, MainActivity.class);
                startActivity(ilogout);
                finish();
                return true;
            default:
                return false;
        }
    }

    public void runtimePermission(){
        Dexter.withContext(this).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                displaySong();
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                permissionToken.continuePermissionRequest();
            }
        }).check();
    }

    public ArrayList<File> findSong(File root){
        ArrayList<File> arrayList = new ArrayList<File>();
        File[] files = root.listFiles();

        for(File singleFile : files){
            if(singleFile.isDirectory() && !singleFile.isHidden()){
                arrayList.addAll(findSong(singleFile));
            }
            else {
                if(singleFile.getName().endsWith(".mp3")){
                    arrayList.add(singleFile);
                }
            }
        }
        return arrayList;
    }

    void displaySong(){
        final ArrayList<File> listSongs = findSong(Environment.getExternalStorageDirectory());
        items = new String[ listSongs.size() ];

        for(int i=0; i<listSongs.size(); i++){
            songList.add(listSongs.get(i).getName().toString());
            //items[i] = listSongs.get(i).getName().toString();
        }

        songAdapter = new SongListAdapter(this, songList);
        mRecyclerView.setAdapter(songAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);

        //song.setAdapter(myAdapter);
        /*
        song.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String songtitle = song.getItemAtPosition(position).toString();

                Intent startSong = new Intent(SongActivity.this, PlayingActivity.class);
                startSong.putExtra("songs", listSongs);
                startSong.putExtra("title", songtitle);
                startSong.putExtra("position", position);
                startActivity(startSong);
            }
        });

         */
    }
}