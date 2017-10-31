package edu.uark.csce.assignment3;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.io.File;
import java.util.ArrayList;

public class ListPhotosActivity extends AppCompatActivity {

    String TAG = "ListPhotosActivity";
    GridView imageGrid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_photos);

        imageGrid = (GridView)findViewById(R.id.photo_grid_view);

        getImages();

        FloatingActionButton showMapButton = (FloatingActionButton)findViewById(R.id.show_map_FAB);

        showMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void getImages(){
        File imagesDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES + "/thumbnails/");
        File files[] = imagesDir.listFiles();

        final int THUMBNAILSIZE = 256;

        ImageGridAdapter gridAdapter = new ImageGridAdapter(getApplicationContext(), THUMBNAILSIZE);
        Log.i("Number of files", ""+files.length);
        for(int i=0; i<files.length; i++) {
            Log.i(TAG, files[i].getName());
            Bitmap thumbnail = ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(files[i].getAbsolutePath()), THUMBNAILSIZE, THUMBNAILSIZE);
            gridAdapter.add(thumbnail);
        }

        imageGrid.setAdapter(gridAdapter);

    }
}
