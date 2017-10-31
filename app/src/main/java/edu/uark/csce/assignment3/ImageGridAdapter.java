package edu.uark.csce.assignment3;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by tccaldwe on 10/31/2017.
 */

public class ImageGridAdapter extends BaseAdapter {
    private Context mContext;
    private Bitmap bitmapreceive;

    String TAG = "ImageGridAdapter";

    ArrayList<Bitmap> mThumbIds;
    int THUMBSIZE=0;

    public ImageGridAdapter(Context c, int _thumbsize){
        THUMBSIZE = _thumbsize;
        Log.i(TAG, ""+THUMBSIZE);
        mThumbIds = new ArrayList<>();
        THUMBSIZE = 350;
        mContext = c;
    }

    @Override
    public int getCount(){
        return mThumbIds.size();
    }

    @Override
    public Object getItem(int position){
        return mThumbIds.get(position);
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        ImageView imageView;
        if(convertView == null){
            imageView = new ImageView(mContext);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }else{
            imageView = (ImageView)convertView;
        }

        imageView.setImageBitmap(mThumbIds.get(position));

        return imageView;
    }


    public Bitmap add(Bitmap bitmap_receive){
        mThumbIds.add(bitmap_receive);
        return bitmapreceive;
    }
}
