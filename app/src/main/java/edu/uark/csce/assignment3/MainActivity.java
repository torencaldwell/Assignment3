package edu.uark.csce.assignment3;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.hardware.camera2.*;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView textview = (TextView)findViewById(R.id.cam_textview);
        if(checkCameraHardware(getApplicationContext())){
            textview.setText("I have a camera");
        }else{
            textview.setText("I don't have a camera");
        }
    }

    private boolean checkCameraHardware(Context context){
        if(context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA))
            return true;
        return false;
    }


}
