package edu.uark.csce.assignment3;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    String TAG = "MapsActivity";
    boolean PERMISSION_CAMERA_GRANTED = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        if (ContextCompat.checkSelfPermission( this, android.Manifest.permission.CAMERA ) != PackageManager.PERMISSION_GRANTED ){
            ActivityCompat.requestPermissions( this, new String[] {android.Manifest.permission.CAMERA}, 1);
        }else if(ContextCompat.checkSelfPermission( this, android.Manifest.permission.CAMERA ) == PackageManager.PERMISSION_GRANTED){
            PERMISSION_CAMERA_GRANTED = true;
        }

        FloatingActionButton camera_button = (FloatingActionButton)findViewById(R.id.camera_FAB);

        camera_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!checkCameraHardware(getApplicationContext())){
                    Toast toast = Toast.makeText(getApplicationContext(), "You need a camera to use this function", Toast.LENGTH_SHORT);
                    toast.show();
                }else{
                    dispatchTakePictureIntent();
                }
            }
        });

        FloatingActionButton list_images_button = (FloatingActionButton)findViewById(R.id.list_images_FAB);
        list_images_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ListPhotosActivity.class);
                startActivity(intent);
            }
        });
    }
    boolean MAP_READY = false;
    @Override
    public void onMapReady(GoogleMap googleMap){
        mMap = googleMap;
        ArrayList<Marker> markers= updateMap();
        MAP_READY = true;
    }

    @Override
    protected void onResume(){
        super.onResume();
        if(MAP_READY){
            updateMap();
        }
    }

    public ArrayList<Marker> updateMap(){
        ArrayList<Marker> markers= new ArrayList<>();
        File path = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File[] fileList = path.listFiles();
        for(int i=0; i<fileList.length; i++){
            try {
                ExifInterface exif = new ExifInterface(fileList[i].getPath());

                float[] lat_lng = new float[2];

                if(exif.getLatLong(lat_lng)) {
                    LatLng location = new LatLng(lat_lng[0], lat_lng[1]);
                    MarkerOptions options = new MarkerOptions().position(location);
                    mMap.addMarker(options);
                    Log.i("SET MARKER", location.toString());
                    Log.i("AT", options.getPosition().toString());
                }else{
                    Log.i("COULD NOT RETRIEVE INFO", fileList[i].getName());
                }
            }catch(IOException e){
                e.printStackTrace();
            }
        }
        return markers;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults){
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 1:{
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    PERMISSION_CAMERA_GRANTED = true;
                }else{
                    PERMISSION_CAMERA_GRANTED = false;
                    android.os.Process.killProcess(android.os.Process.myPid());
                    System.exit(1);

                }
            }
        }
    }

    static final int REQUEST_IMAGE_CAPTURE = 1;

    //Create a camera intent and start it
    private void dispatchTakePictureIntent(){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(takePictureIntent.resolveActivity(getPackageManager())!=null){
            File photoFile = null;
            try{
                photoFile = storeImageFile();
            }catch(IOException e){
                e.printStackTrace();
            }

            if(photoFile != null){
                Uri photoURI = FileProvider.getUriForFile(this, "edu.uark.csce.fileprovider", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    //Check if the device has a camera
    private boolean checkCameraHardware(Context context) {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA))
            return true;
        return false;
    }

    //Retrieves an image thumbnail
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){

        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
        }
    }

    String mCurrentPhotoPath;

    private File storeImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String fileName = "JPEG_"+timeStamp+"_";

        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(fileName, ".jpg", storageDir);

        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }
}
