package ir.sarasaghaei.final_project.service;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

public class downloade extends AsyncTask<String,Integer,String> {
    ProgressDialog progressDialog;
    String path, medianame;
    Context context;
    // ------- class for downloade media in new thread -----------
    public downloade(Context context) {
        this.context = context;
    }

    public void downloade(String path, String medianame) {
        this.path = path;
        this.medianame = medianame;
        //------------- set progress dialog for downloade
        this.progressDialog = new ProgressDialog(context);
        this.progressDialog.setProgressStyle(progressDialog.STYLE_HORIZONTAL);
        this.progressDialog.setTitle("Please wait...");
        this.progressDialog.setMessage("downloading\t" + medianame);
        this.progressDialog.setMax(100);

        //------------ set Cancel button for stop daownload--------
        this.progressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (getStatus() == AsyncTask.Status.RUNNING )
                    cancel(true);
            }
        });
        execute(path);
    }



    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog.setProgress(0);
        progressDialog.show();
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        progressDialog.setProgress(values[0]);
    }



    @Override
    protected String doInBackground(String... urldownloade) {
        try {

            String urladdress = urldownloade[0];
            path = "/sdcard/" + medianame;
            URL url = new URL(urladdress);

            URLConnection urlConnection = url.openConnection();
            urlConnection.connect();
            int lenght = urlConnection.getContentLength();
            InputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());
            OutputStream outputStream = new FileOutputStream(path);

            // ---- creat buffer --------
            byte[] buffer = new byte[1024];
            int count = -1;
            int temp = 0;
            while ((count = inputStream.read(buffer)) != -1) {
                temp += count;
                publishProgress(temp * 100 / lenght);
                outputStream.write(buffer, 0, count);
            }
            outputStream.flush();
            outputStream.close();
            inputStream.close();
            return path;

        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }
}
