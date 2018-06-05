package com.example.fernando.aeroproject;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by david on 24/05/18.
 */

public class Coneccion {
    public String url="http://192.168.1.10:8080/MovilServlets/ServletAereopuerto";
    public String getResultFromServlet(String text) {
        text=url+text;
        String result = "";
        InputStream in = callService(text);
        if (in != null) {
            try {
                String r=readFully(in);
                result = r;
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            result = "Error: El servidor no devuelve datos";
        }
        return result;
    }
    public InputStream callService(String SERVLET_URL) {
        InputStream in = null;
        try {
            URL url = new URL(SERVLET_URL);
            URLConnection conn = url.openConnection();

            HttpURLConnection httpConn = (HttpURLConnection) conn;
            httpConn.setRequestMethod("POST");
            httpConn.setDoInput(true);
            httpConn.setDoOutput(true);
            httpConn.connect();

            DataOutputStream dataStream = new DataOutputStream(conn
                    .getOutputStream());

            dataStream.writeBytes(SERVLET_URL);
            dataStream.flush();
            dataStream.close();

            int responseCode = httpConn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                in = httpConn.getInputStream();
            }
        } catch (Exception ex) {
            String a=ex.getMessage();
            a=a;
        }
        return in;
    }
    public String readFully(InputStream entityResponse) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length = 0;
        while ((length = entityResponse.read(buffer)) != -1) {
            baos.write(buffer, 0, length);
        }
        return baos.toString();
    }
}
