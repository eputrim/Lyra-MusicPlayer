package umn.ac.id.lyra_musicplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

public class SongActivity extends AppCompatActivity /*implements PopupMenu.OnMenuItemClickListener*/ {

    ListView song;
    String[] items;
    //Dialog popupmenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song);

        song = findViewById(R.id.song);

        runtimePermission();

        /*popupmenu = new Dialog(this);
        popupmenu.setContentView(R.layout.popup);
        popupmenu.show();*/
    }
/*
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
*/
    public void runtimePermission(){
        Dexter.withActivity(this).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE).withListener(new PermissionListener() {
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
            items[i] = listSongs.get(i).getName().toString();
        }
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);

        song.setAdapter(myAdapter);

        song.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String title = song.getItemAtPosition(position).toString();

                startActivity(new Intent(getApplicationContext(),SongActivity.class).putExtra("position",position).putExtra("songs",listSongs).putExtra("title",title));
                /*
                Intent startSong = new Intent(SongActivity.this, PlayingActivity.class);
                startSong.putExtra("songs", listSongs);
                startSong.putExtra("title", title);
                startSong.putExtra("position", position);
                startActivity(startSong);*/
            }
        });
    }
}