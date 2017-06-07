package com.galleryproject.activities;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.galleryproject.R;
import com.galleryproject.adapters.Albumview_adapter;
import com.galleryproject.model.Albumimages_data;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Ahsan Malik on 4/25/2017.
 */

public class Albumview extends AppCompatActivity {

    ArrayList<MediaObject> cursorData;
    ArrayList<Albumimages_data> albumarray = new ArrayList<>();
    GridView gridview;
    Albumview_adapter adapter;
    Toolbar toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.albumview);
        initialize();
        if (getIntent().getStringExtra("albumtype").equals("image"))
            initPhotoImages();
        else
            initVideos();

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("albumarray",String.valueOf(albumarray.get(position).getPath()));
                if (getIntent().getStringExtra("albumtype").equals("image"))
                   openImageInGallery(position);
                else
                    openVideoInGallery(position);

            }
        });

    }

    private void initialize() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getIntent().getStringExtra("albumname"));
        gridview = (GridView) findViewById(R.id.gridview);
    }

    private void openImageInGallery(int position)
    {
        File imgFile = new File(albumarray.get(position).getPath());
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(imgFile), "image/*");
        startActivity(intent);
    }

    private void openVideoInGallery(int position)
    {
        File imgFile = new File(albumarray.get(position).getPath());
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(imgFile), "video/*");
        startActivity(intent);
    }

    private void initVideos() {

        try {

            final String orderBy = MediaStore.Video.Media.DATE_TAKEN;

            String searchParams = null;

            String bucket = getIntent().getStringExtra("albumname");

            searchParams = "bucket_display_name = \"" + bucket + "\"";


            Cursor mPhotoCursor = getContentResolver().query(

                    MediaStore.Video.Media.EXTERNAL_CONTENT_URI, null,

                    searchParams, null, orderBy + " DESC");


            if (mPhotoCursor.getCount() > 0) {


                cursorData = new ArrayList<>();


                cursorData.addAll(Utils_album.extractMediaList(mPhotoCursor,

                        MediaType.PHOTO));


            }
            albumarray.clear();
            for (int i = 0; i < cursorData.size(); i++) {
                Log.i("albumpath", String.valueOf(cursorData.get(i).getPath()));
                albumarray.add(new Albumimages_data(cursorData.get(i).getPath()));
            }


            adapter = new Albumview_adapter(Albumview.this, albumarray, false);
            gridview.setAdapter(adapter);
            // setAdapter(mImageCursor);

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    private void initPhotoImages() {

        try {

            final String orderBy = MediaStore.Images.Media.DATE_TAKEN;

            String searchParams = null;

            String bucket = getIntent().getStringExtra("albumname");

            searchParams = "bucket_display_name = \"" + bucket + "\"";


            Cursor mPhotoCursor = getContentResolver().query(

                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null,

                    searchParams, null, orderBy + " DESC");


            if (mPhotoCursor.getCount() > 0) {


                cursorData = new ArrayList<>();


                cursorData.addAll(Utils_album.extractMediaList(mPhotoCursor,

                        MediaType.PHOTO));


            }
            albumarray.clear();
            for (int i = 0; i < cursorData.size(); i++) {
                Log.i("albumpath", String.valueOf(cursorData.get(i).getPath()));
                albumarray.add(new Albumimages_data(cursorData.get(i).getPath()));
            }


            adapter = new Albumview_adapter(Albumview.this, albumarray, false);
            gridview.setAdapter(adapter);
            // setAdapter(mImageCursor);

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {

            case android.R.id.home: {
                onBackPressed();
                return true;
            }
        }
        return false;
    }
}
