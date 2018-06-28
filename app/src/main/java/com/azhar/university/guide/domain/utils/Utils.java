package com.azhar.university.guide.domain.utils;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.File;

/**
 * Created by Yasser.Ibrahim on 6/27/2018.
 */

public class Utils {
    public static int getFileSizeFromFile(File file) {
        if (file == null)
            return -1;
        return Integer.parseInt(String.valueOf(file.length() / 1024));
    }

    public static int getFileSizeFromUri(Context context, Uri fileUri) {
        if (fileUri == null) {
            return -1;
        }

        String imagePath = getRealPathFromURI(context, fileUri);

        if (imagePath == null) {
            return -1;
        }

        File file = new File(imagePath);
        return Integer.parseInt(String.valueOf(file.length() / 1024));
    }

    public static String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }
}
