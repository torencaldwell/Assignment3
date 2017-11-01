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
    private String filepathreceive;

    String TAG = "ImageGridAdapter";

    ArrayList<Bitmap> mThumbIds;
    ArrayList<String> mFilePaths;
    int THUMBSIZE=0;

    public ImageGridAdapter(Context c, int _thumbsize){
        THUMBSIZE = _thumbsize;
        mThumbIds = new ArrayList<>();
        mFilePaths = new ArrayList<>();
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
            imageView.setLayoutParams(new GridView.LayoutParams(THUMBSIZE, THUMBSIZE));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }else{
            imageView = (ImageView)convertView;
        }

        imageView.setImageBitmap(mThumbIds.get(position));

        return imageView;
    }

    public Bitmap addBitmap(Bitmap bitmap_receive){
        mThumbIds.add(bitmap_receive);
        return bitmapreceive;
    }

    public String addFilePath(String filepath_receive){
        mFilePaths.add(filepath_receive);
        return filepathreceive;
    }

    public String getFilePath(int i){
        return mFilePaths.get(i);
    }
}
