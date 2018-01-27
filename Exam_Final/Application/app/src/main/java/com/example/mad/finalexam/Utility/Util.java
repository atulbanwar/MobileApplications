package com.example.mad.finalexam.Utility;

import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.util.Patterns;

import org.ocpsoft.prettytime.PrettyTime;

import java.io.FileDescriptor;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Util {
    public static boolean isValidEmail(CharSequence emailString) {
        if (emailString == null) {
            return false;
        } else {
            return Patterns.EMAIL_ADDRESS.matcher(emailString).matches();
        }
    }

    public static Bitmap getBitmapFromUri(Uri uri, ContentResolver cr) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor = cr.openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();
        return image;
    }

    public static String getFormattedDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm a", Locale.US);
        return sdf.format(date);
    }

    public static String prettyDate(String dateContender) throws Exception {
        Date stringToDate = null;
        DateFormat formatter = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy", Locale.US);

        try {
            stringToDate = formatter.parse(dateContender);
        } catch (ParseException e) {
            throw new Exception("Given date and corresponding format doesn't match");
        }
        PrettyTime p = new PrettyTime();
        return p.format(stringToDate);
    }
}
