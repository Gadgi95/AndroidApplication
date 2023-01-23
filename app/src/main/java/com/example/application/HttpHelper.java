package com.example.application;

import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpHelper {
    public void login(String username, String password, final Callback callback) {
        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... params) {
                String username = params[0];
                String password = params[1];
                try {
                    //ToDo create a JSON object with the login credentials
                    JSONObject jsonBody = new JSONObject();
                    jsonBody.put("username", username);
                    jsonBody.put("password", password);
                    //ToDo create the request body as a string
                    String requestBody = jsonBody.toString();
                    // create the request url
                    URL url = new URL("https://example.com/api/login");
                    //ToDo open the connection
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setDoOutput(true);
                    connection.setDoInput(true);
                    connection.setRequestProperty("Content-Type", "application/json");
                    //ToDo write the request body to the output stream
                    OutputStream outputStream = connection.getOutputStream();
                    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, "UTF-8");
                    outputStreamWriter.write(requestBody);
                    outputStreamWriter.flush();
                    outputStreamWriter.close();
                    outputStream.close();
                    //ToDo read the response from the input stream
                    InputStream inputStream = connection.getInputStream();
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                    StringBuilder response = new StringBuilder();
                    char[] buffer = new char[1024];
                    int len;
                    while ((len = inputStreamReader.read(buffer)) > 0) {
                        response.append(buffer, 0, len);
                    }
                    inputStreamReader.close();
                    inputStream.close();
                    //ToDo return the response
                    return response.toString();
                } catch (Exception e) {
                    //ToDo return the error message
                    return e.getMessage();
                }
            }

            @Override
            protected void onPostExecute(String response) {
                if (response.startsWith("Error:")) {
                    callback.onError(response);
                } else {
                    callback.onSuccess(response);
                }
            }
        }.execute(username, password);
    }

    public interface Callback {
        void onSuccess(String response);

        void onError(String errorMessage);
    }
}