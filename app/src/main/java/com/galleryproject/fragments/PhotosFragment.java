package com.galleryproject.fragments;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.telephony.CellLocation;
import android.telephony.NeighboringCellInfo;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.galleryproject.R;
import com.galleryproject.activities.Albumview;
import com.galleryproject.adapters.Albumlist_adapter;
import com.galleryproject.model.Albumimages_data;

import java.util.ArrayList;
import java.util.List;


public class PhotosFragment extends Fragment {


    ArrayList<Albumimages_data> albumlist = new ArrayList<>();
    ArrayList<String> imageUrls;
    ArrayList<String> imageBuckets;
    Albumlist_adapter adapter;
    GridView gridview;

    public PhotosFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_photos, container, false);
        initialize(rootview);
        checkpermission();

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getActivity(), Albumview.class);
                i.putExtra("albumname", String.valueOf(albumlist.get(position).getAlbumname()));
                i.putExtra("albumtype", "image");
                startActivity(i);
            }
        });

        return rootview;


    }

    private void initialize(View rootview) {
        gridview = (GridView) rootview.findViewById(R.id.gridview);
    }

    private void imagesname() {

        Uri images = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] projection = new String[]{
                MediaStore.Images.Media.BUCKET_ID,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
                MediaStore.Images.Media.DATE_TAKEN,
                MediaStore.Images.Media.DATA
        };

        String BUCKET_ORDER_BY = MediaStore.Images.Media.DATE_MODIFIED + " DESC";
        String BUCKET_GROUP_BY = "1) GROUP BY 1,(2";
        Cursor imagecursor = getActivity().managedQuery(images,
                projection, // Which columns to return
                BUCKET_GROUP_BY,       // Which rows to return (all rows)
                null,       // Selection arguments (none)
                BUCKET_ORDER_BY        // Ordering
        );

        imageUrls = new ArrayList<String>();
        imageBuckets = new ArrayList<String>();
        Log.i("imagecount", String.valueOf(imagecursor.getCount()));
        if (imagecursor.getCount() > 0) {
            albumlist.clear();
            for (int i = 0; i < imagecursor.getCount(); i++) {
                imagecursor.moveToPosition(i);
                int bucketColumnIndex = imagecursor.getColumnIndex(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
                String bucketDisplayName = imagecursor.getString(bucketColumnIndex);
                imageBuckets.add(bucketDisplayName);
                int dataColumnIndex = imagecursor.getColumnIndex(MediaStore.Images.Media.DATA);
                imageUrls.add(imagecursor.getString(dataColumnIndex));
                albumlist.add(new Albumimages_data(imagecursor.getString(dataColumnIndex), bucketDisplayName, String.valueOf(photoCountByAlbum(bucketDisplayName))));
            }
            adapter = new Albumlist_adapter(getActivity(), albumlist, true);
            gridview.setAdapter(adapter);
        } else {
            Toast.makeText(getActivity(), "No Records", Toast.LENGTH_SHORT).show();
        }
    }

    private int photoCountByAlbum(String bucketName) {

        try {

            final String orderBy = MediaStore.Images.Media.DATE_TAKEN;

            String searchParams = null;

            String bucket = bucketName;

            searchParams = "bucket_display_name = \"" + bucket + "\"";


            // final String[] columns = { MediaStore.Images.Media.DATA,

            // MediaStore.Images.Media._ID };

            Cursor mPhotoCursor = getActivity().getContentResolver().query(

                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null,

                    searchParams, null, orderBy + " DESC");


            if (mPhotoCursor.getCount() > 0) {

                return mPhotoCursor.getCount();

            }

            mPhotoCursor.close();

        } catch (Exception e) {

            e.printStackTrace();

        }


        return 0;


    }

    private void checkpermission() {
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
                Toast.makeText(getActivity(), "Allow Read permission to access albums", Toast.LENGTH_SHORT).show();

            } else {

                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        1);

            }
        } else {
            imagesname();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    imagesname();


                } else {
                    Toast.makeText(getActivity(), "Allow Read permission to access albums", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
}
