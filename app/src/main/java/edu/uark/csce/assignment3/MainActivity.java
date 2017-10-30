package edu.uark.csce.assignment3;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.hardware.camera2.*;

public class MainActivity extends AppCompatActivity {

    boolean PERMISSION_CAMERA_GRANTED = false;
    String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ContextCompat.checkSelfPermission( this, android.Manifest.permission.CAMERA ) != PackageManager.PERMISSION_GRANTED ){
            ActivityCompat.requestPermissions( this, new String[] {android.Manifest.permission.CAMERA}, 1);
        }else if(ContextCompat.checkSelfPermission( this, android.Manifest.permission.CAMERA ) == PackageManager.PERMISSION_GRANTED){
            PERMISSION_CAMERA_GRANTED = true;
        }

        if(PERMISSION_CAMERA_GRANTED){
            openCamera();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults){
        switch (requestCode){
            case 1:{
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    PERMISSION_CAMERA_GRANTED = true;
                    openCamera();
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
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    //Check if the device has a camera
    private boolean checkCameraHardware(Context context){
        if(context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA))
            return true;
        return false;
    }

    //Open the call dispatchTakePictureIntent and
    public void openCamera(){
        TextView textview = (TextView)findViewById(R.id.cam_textview);

        if (checkCameraHardware(getApplicationContext())) {
            textview.setText("I have a camera");
            Log.i(TAG, "Opening Camera");
            dispatchTakePictureIntent();
        } else {
            textview.setText("I don't have a camera");
        }
    }


}
