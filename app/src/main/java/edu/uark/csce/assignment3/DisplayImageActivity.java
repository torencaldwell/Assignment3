package edu.uark.csce.assignment3;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

public class DisplayImageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_display_image);


        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        String filePath = extras.getString("filepath");

        ImageView displayImage = (ImageView)findViewById(R.id.display_image);
        displayImage.setImageBitmap(BitmapFactory.decodeFile(filePath));

        displayImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
