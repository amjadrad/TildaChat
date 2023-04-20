package ir.tildaweb.tildachat.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import androidx.core.content.FileProvider;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import ir.tildaweb.tildachat.app.TildaChatApp;

public class FileDownloader extends AsyncTask<Object, String, String> {

    private static final String TAG = "FileDownloader";
    //    private ProgressDialog pd;
    private String pathFile = "";
    private Context context;
    private OnFileDownloadListener onFileDownloadListener;

    public void setOnFileDownloadListener(OnFileDownloadListener onFileDownloadListener) {
        this.onFileDownloadListener = onFileDownloadListener;
    }

    public interface OnFileDownloadListener {
        void onFileDownloaded();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected String doInBackground(Object... params) {
        int count;

        String filename = (String) params[0];
        context = (Context) params[1];
        String FILE_URL = (String) params[2];

//        pd = new ProgressDialog(context);
//        pd.setTitle("در حال دانلود فایل");
//        pd.setMessage("لطفا منتظر بمانید...");
//        pd.setMax(100);
//        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
//        pd.setCancelable(true);
//        pd.show();

        String fileUrl = FILE_URL + filename;
        Log.d(TAG, "doInBackground: " + fileUrl);
        try {
//            filename = "amjad.png";
            String fileUrlDirectory = filename.substring(0, filename.lastIndexOf("/"));
            Log.d(TAG, "doInBackground: " + fileUrlDirectory);
            String folderPathToCreate = "";
            String pathFolder = "";
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                pathFolder = context.getExternalFilesDir(null) + "/" + TildaChatApp._downloadFolder + "/";
                folderPathToCreate = pathFolder + fileUrlDirectory;
            } else {
                pathFolder = Environment.getExternalStorageDirectory() + "/" + TildaChatApp._downloadFolder + "/";
                folderPathToCreate = pathFolder + fileUrlDirectory;
            }
            pathFile = pathFolder + "/" + filename;
            File foldersToCreate = new File(folderPathToCreate);
            if (!foldersToCreate.exists()) {
                Log.d(TAG, "doInBackground: Create folder: " + foldersToCreate);
                boolean isCreated = foldersToCreate.mkdirs();
                Log.d(TAG, "doInBackground:created? " + isCreated);
            }

            URL url = new URL(fileUrl);
            URLConnection connection = url.openConnection();
            connection.connect();

            // this will be useful so that you can show a tipical 0-100 %
            // progress bar
            int lengthOfFile = connection.getContentLength();

            // download the file
            InputStream input = new BufferedInputStream(url.openStream());
            FileOutputStream output = new FileOutputStream(pathFile);

            byte[] data = new byte[1024]; //anybody know what 1024 means ?
            long total = 0;
            while ((count = input.read(data)) != -1) {
                total += count;
                // publishing the progress....
                // After this onProgressUpdate will be called
                publishProgress("" + (int) ((total * 100) / lengthOfFile));

                // writing data to file
                output.write(data, 0, count);
            }

            // flushing output
            output.flush();

            // closing streams
            output.close();
            input.close();


        } catch (Exception e) {
            Log.d("Error: ", e.getMessage());
        }

        return pathFile;
    }

    protected void onProgressUpdate(String... progress) {
        // setting progress percentage
//        pd.setProgress(Integer.parseInt(progress[0]));
    }

    @Override
    protected void onPostExecute(String pathFile) {
//        if (pd != null) {
//            pd.dismiss();
//        }

        onFileDownloadListener.onFileDownloaded();
        Log.d(TAG, "onPostExecute: Open file.");
    }

    public static void openFile(Context context, String pathFile) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                pathFile = context.getExternalFilesDir(null) +  "/"+ TildaChatApp._downloadFolder +"/" + pathFile;
            } else {
                pathFile = Environment.getExternalStorageDirectory() +  "/"+TildaChatApp._downloadFolder+"/" + pathFile;
            }
            File file = new File(pathFile);
            Log.d(TAG, "openFile: " + pathFile);
            String auth = context.getApplicationContext().getPackageName() + ".myprovider";
            Uri fileURI = FileProvider.getUriForFile(context, auth, file);
//            context.grantUriPermission(context.getPackageName(), fileURI, Intent.FLAG_GRANT_READ_URI_PERMISSION);
            final Intent intent = new Intent(Intent.ACTION_VIEW)
                    .setData(fileURI)
                    .addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Intent intentChooser = Intent.createChooser(intent, "انتخاب برنامه:");
            intentChooser.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intentChooser);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}

