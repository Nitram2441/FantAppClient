package com.example.fantapp;

import android.util.JsonReader;
import android.util.Log;

import com.example.fantapp.ui.FantService;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;


public class PostMessageTask extends AbstractAsyncTask<Listing, Void, List<Listing>> {
    private final static String CRLF = "\r\n";
    private final String boundary = "*****" + System.currentTimeMillis() + "*****";

    public PostMessageTask(OnPostExecute<List<Listing>> onPostExecute,
                           OnException onException) {
        super(onPostExecute, onException);
    }


    private void addFormField(BufferedWriter bw, String name, String value) throws IOException {
        bw.write("--");
        bw.write(boundary);
        bw.write(CRLF);
        bw.write("Content-Disposition: form-data; name=\"" + name + "\"");
        bw.write(CRLF);
        bw.write("Content-Type: text/plain; charset=UTF-8");
        bw.write(CRLF);
        bw.write(CRLF);
        bw.write(value);
        bw.flush();
    }

    @Override
    protected List<Listing> doInBackground(Listing... listings) {
        List<Listing> result = new ArrayList<>();

        for(Listing msg : listings) {
            try {
                System.out.println("setting up request!");
                // Setup request
                HttpURLConnection con = FantService.getInstance().getSecureConnection(FantService.CREATE_LISTING_URL);
                con.setDoInput(true);
                con.setDoOutput(true); // POST
                con.setUseCaches(false);
                con.setRequestProperty("Connection", "Keep-Alive");
                con.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);

                // Write message & conversationid
                OutputStream os = con.getOutputStream();
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
                //addFormField(bw,"conversationid", Long.toString(msg.getConversationId()));
                //addFormField(bw,"message", msg.getText());

                addFormField(bw, "title", msg.getTitle());
                bw.write(CRLF);
                addFormField(bw, "description", msg.getDescription());
                bw.write(CRLF);
                addFormField(bw, "price", Integer.toString(msg.getPrice()));


                // Write photos
                /*
                for(Photo photo : msg.getPhotos()) {
                    if(photo.getFile() != null) {
                        // Start content wrapper
                        bw.write("--" + boundary + CRLF);
                        bw.write("Content-Type: " + URLConnection.guessContentTypeFromName(photo.getFilename()));
                        bw.write(CRLF);
                        bw.write("Content-Disposition: form-data; name=\"image\"; filename=\"" +
                                photo.getFilename() + "\"");
                        bw.write(CRLF);
                        bw.write("Content-Transfer-Encoding: binary");
                        bw.write(CRLF);
                        bw.write(CRLF);
                        bw.flush();

                        // Copy photo-stream
                        int len;
                        try (InputStream is = new BufferedInputStream(new FileInputStream(photo.getFile()))) {
                            byte[] buff = new byte[2048];
                            while ((len = is.read(buff)) != -1) {
                                os.write(buff, 0, len);
                            }
                        }

                        // End and flush buffers
                        bw.write(CRLF);
                        bw.flush();
                    }
                }
*/
                // End content wrapper
                bw.write(CRLF);
                bw.write("--");
                bw.write(boundary);
                bw.write("--");
                bw.write(CRLF);
                bw.flush();
                bw.close();
                con.getOutputStream().close();

                // Get response
                if(con.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
                    JsonReader jr = new JsonReader(br);
                    //result.add(FantService.getInstance().loadMessage(jr));
                    System.out.println("http ok");
                }
                System.out.println("Response code: " + con.getResponseCode());
                con.disconnect();
            } catch (IOException e) {
                Log.e("PostMessageTask", "doInBackground: ", e);
            }
        }

        return result;
    }
}