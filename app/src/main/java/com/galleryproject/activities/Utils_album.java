package com.galleryproject.activities;

/**
 * Created by Ahsan Malik on 4/21/2016.
 */

import android.database.Cursor;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ahsan Malik on 25/04/2017.
 */

public class Utils_album {

    public static List<MediaObject> extractMediaList(Cursor cursor,
                                                     MediaType mediaType) {
        List<MediaObject> list = new ArrayList<MediaObject>();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                String filePath = cursor.getString(1);
                long creationDate = getCreationDate(filePath);

                MediaObject mediaObject = new MediaObject(cursor.getInt(0),
                        filePath, mediaType, creationDate);
                list.add(mediaObject);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return list;
    }

    private static long getCreationDate(String filePath) {
        File file = new File(filePath);
        return file.lastModified();
    }


}

