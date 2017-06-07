package com.galleryproject.adapters;

import android.app.Activity;
import android.content.Context;
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
public class Albumview_adapter extends BaseAdapter {

    Context context;
    ArrayList<Albumimages_data> albumlist;
    LayoutInflater mInflater;
    boolean iscountvisible;

    public Albumview_adapter(Context context, ArrayList<Albumimages_data> albumlist, boolean showcount) {
        this.context = context;
        mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        this.albumlist = albumlist;
        this.iscountvisible = showcount;
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
            convertView = mInflater.inflate(R.layout.albumview_item, null);
            holder = new Holder();
            holder.iv = (ImageView) convertView.findViewById(R.id.imageView);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        Glide.with(context).load(data.getPath()).into(holder.iv);


        return convertView;
    }

    private static class Holder {
        public ImageView iv;
    }


}

