package ir.tildaweb.tildachat.utils;

import android.os.AsyncTask;
import android.util.Log;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.Random;


public class FileUploader extends AsyncTask<String, String, String> {

    private static String TAG = "FileUploader";
    private static OnFileUploaderListener onFileUploaderListener;
    private static final String ALLOWED_CHARACTERS = "0123456789qwertyuiopasdfghjklzxcvbnm";

    public void setOnFileUploaderListener(OnFileUploaderListener onFileUploaderListener) {
        this.onFileUploaderListener = onFileUploaderListener;
    }

    public interface OnFileUploaderListener {
        void onFileUploaded(String fileName);

        void onFileUploadError();

        void onFileUploadProgress(int id, int percent);
    }

    protected void onPreExecute() {
        Log.d(TAG, "doInBackground: Start Upload.");
    }

    @Override
    protected String doInBackground(String... params) {

        String uploadURL = "";
//        String uploadURL = Endpoints.BASE_URL_UPLOAD_FILE;
        String filePath = params[0];
        int fileRequestCode = Integer.parseInt(params[1]);
        String fileName = new Date().getTime() + "_nznv_" + filePath.substring(filePath.lastIndexOf("/") + 1);
        if (!isStringOnlyAlphabet(fileName)) {
            fileName = new Date().getTime() + "_nznv_" + getRandomString(5) + filePath.substring(filePath.lastIndexOf("."));
        }
        return sendFileToServer(uploadURL, filePath, fileName, fileRequestCode);
    }

    protected void onPostExecute(String result) {

        Log.d(TAG, "onPostExecute: " + result);
        if (result == null) {
            onFileUploaderListener.onFileUploadError();
        } else {
            onFileUploaderListener.onFileUploaded("NazmeNovin_file_" + result);
        }
    }

    public static boolean isStringOnlyAlphabet(String str) {
        return !str.equals("") && str.matches("^[a-zA-Z0-9]*$");
    }


    private static String getRandomString(final int sizeOfRandomString) {
        final Random random = new Random();
        final StringBuilder sb = new StringBuilder(sizeOfRandomString);
        for (int i = 0; i < sizeOfRandomString; ++i)
            sb.append(ALLOWED_CHARACTERS.charAt(random.nextInt(ALLOWED_CHARACTERS.length())));
        return sb.toString();
    }


    public static String sendFileToServer(String uploadURL, String filePath, String filename, int fileRequestCode) {
        String response;
        HttpURLConnection connection;
        DataOutputStream outputStream;
        // DataInputStream inputStream = null;

        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
//        DateFormat df = new SimpleDateFormat("yyyy_MM_dd_HH:mm:ss");

        int bytesRead, bytesAvailable, bufferSize, fileSize, uploadedSize = 0, percent;
        byte[] buffer;
        int maxBufferSize = 1024;
        try {
            FileInputStream fileInputStream = new FileInputStream(new File(filePath));

            URL url = new URL(uploadURL);
            connection = (HttpURLConnection) url.openConnection();

            // Allow Inputs & Outputs
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setUseCaches(false);
            connection.setChunkedStreamingMode(1024);
            // Enable POST method
            connection.setRequestMethod("POST");

            connection.setRequestProperty("Connection", "Keep-Alive");
            connection.setRequestProperty("Content-Type",
                    "multipart/form-data;boundary=" + boundary);

            outputStream = new DataOutputStream(connection.getOutputStream());
            outputStream.writeBytes(twoHyphens + boundary + lineEnd);

            String connstr;
            connstr = "Content-Disposition: form-data; name=\"upload_file\";filename=\""
                    + filename + "\"" + lineEnd;
            Log.i("Connstr", connstr);


            outputStream.writeBytes(connstr);
            outputStream.writeBytes(lineEnd);

            fileSize = fileInputStream.available();
            bytesAvailable = fileInputStream.available();
            bufferSize = Math.min(bytesAvailable, maxBufferSize);
            buffer = new byte[bufferSize];

            // Read file
            bytesRead = fileInputStream.read(buffer, 0, bufferSize);
            Log.d(TAG, bytesAvailable + "");
            try {
                while (bytesRead > 0) {
                    try {
                        outputStream.write(buffer, 0, bufferSize);
                        uploadedSize += bufferSize;
                    } catch (OutOfMemoryError e) {
                        e.printStackTrace();
                        response = "outofmemoryerror";
                        return response;
                    }
                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                    percent = (int) (((float) uploadedSize / (float) fileSize) * 100);
                    onFileUploaderListener.onFileUploadProgress(fileRequestCode, percent);

                }
            } catch (Exception e) {
                e.printStackTrace();
                response = "error";
                return response;
            }
            outputStream.writeBytes(lineEnd);
            outputStream.writeBytes(twoHyphens + boundary + twoHyphens
                    + lineEnd);

            // Responses from the server (code and message)
            int serverResponseCode = connection.getResponseCode();
            String serverResponseMessage = connection.getResponseMessage();
            Log.i("Server Response Code ", "" + serverResponseCode);
            Log.i("Server Response Message", serverResponseMessage);

            fileInputStream.close();
            outputStream.flush();
            outputStream.close();

            if (serverResponseCode == 200) {
                return filename;
            } else {
                return null;
            }
        } catch (Exception ex) {
            // Exception handling
            Log.e("Send file Exception", ex.getMessage() + "");
            ex.printStackTrace();
            return null;
        }

    }


}



