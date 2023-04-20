package ir.tildaweb.tildachat.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

import ir.tildaweb.tildachat.app.TildaChatApp;

public class FileUtils {

    private static final String TAG = "FileUtils";

    public static String convertImageToBase64(File file) {
        String extension = file.getAbsolutePath().substring(file.getAbsolutePath().lastIndexOf(".") + 1);
        Bitmap bitmap = BitmapFactory.decodeFile(String.valueOf(file));
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 60, byteArrayOutputStream);
        byte[] imageBytes = byteArrayOutputStream.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage + ":" + extension;
    }

    public static String convertFileToBase64(String path) {
        try {
            File file = new File(path);
            String extension = file.getAbsolutePath().substring(file.getAbsolutePath().lastIndexOf(".") + 1);
            byte[] buffer = new byte[(int) file.length() + 100];
            int length = new FileInputStream(file).read(buffer);
            String base64 = Base64.encodeToString(buffer, 0, length,
                    Base64.DEFAULT);
            return base64 + ":" + extension;
        } catch (Exception e) {
            return null;
        }
    }

    public static boolean isChatFileExists(Context context, String filename) {
        String pathFolder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            pathFolder = context.getExternalFilesDir(null) + "/" + TildaChatApp._downloadFolder + "/";
        } else {
            pathFolder = Environment.getExternalStorageDirectory() + "/" + TildaChatApp._downloadFolder + "/";
        }
        String pathFile = pathFolder + filename;
        Log.d(TAG, "isChatFileExists: " + pathFile);
        File file = new File(pathFile);
        Log.d(TAG, "isChatFileExists: " + file.exists());
        return file.exists();
    }

}
