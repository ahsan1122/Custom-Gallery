package com.galleryproject.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.galleryproject.R;
import com.galleryproject.model.Albumimages_data;

import java.util.ArrayList;


/**
 * Created by Ahsan Malik on 4/4/2016.
 */
public class Albumlist_adapter extends BaseAdapter {

    Context context;
    ArrayList<Albumimages_data> albumlist;
    LayoutInflater mInflater;
    boolean typevi_img;

    public Albumlist_adapter(Context context, ArrayList<Albumimages_data> albumlist, boolean type) {
        this.context = context;
        mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        this.albumlist = albumlist;
        this.typevi_img = type;
    }


    @Override
    public int getCount() {
        return albumlist.size();
    }

    @Override
    public Object getItem(int position) {
        return albumlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return albumlist.indexOf(getItem(position));
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final Holder holder;
        final Albumimages_data data = albumlist.get(position);

        convertView = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.albumlist_item, null);
            holder = new Holder();
            holder.ablnum_name = (TextView) convertView.findViewById(R.id.txtalbumname);
            holder.album_count = (TextView) convertView.findViewById(R.id.txtcount);
            holder.iv = (ImageView) convertView.findViewById(R.id.imageView);
            holder.imageView_icon = (ImageView) convertView.findViewById(R.id.imageView_icon);
            holder.countlayout = (RelativeLayout) convertView.findViewById(R.id.countlayout);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        holder.ablnum_name.setText(data.getAlbumname());
        if (typevi_img) {

            holder.imageView_icon.setImageResource(R.drawable.ic_photo_camera);
            if (Integer.parseInt(data.getAlbumcount()) <= 1)
                holder.album_count.setText(data.getAlbumcount() + " Photo");
            else
                holder.album_count.setText(data.getAlbumcount() + " Photos");
        } else {

            holder.imageView_icon.setImageResource(R.drawable.ic_video_camera);
            if (Integer.parseInt(data.getAlbumcount()) <= 1)
                holder.album_count.setText(data.getAlbumcount() + " Video");
            else
                holder.album_count.setText(data.getAlbumcount() + " Videos");
        }

        Log.i("image", String.valueOf(data.getPath()));
        Glide.with(context).load(data.getPath()).into(holder.iv);


        return convertView;
    }

    private static class Holder {
        public ImageView iv, imageView_icon;
        TextView ablnum_name, album_count;
        RelativeLayout countlayout;
    }


}

