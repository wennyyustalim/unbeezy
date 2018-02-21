package com.sams.unbeezy.helpers;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by kennethhalim on 2/21/18.
 */

public class RequestSender {
    public static String sendRequest(String url, String method, String contentType, String payload)
            throws IOException {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestProperty("Content-Type", contentType);
        con.setDoOutput(true);
        con.setRequestMethod(method);
        if(method.equals("POST")) {
            DataOutputStream wr = new DataOutputStream(
                    con.getOutputStream());
            wr.writeBytes(payload);
            wr.flush();
            wr.close();
        }
        con.connect();
        StringBuffer response = new StringBuffer();
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        return response.toString();
    }

}
