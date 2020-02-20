package com.project.app;

import com.project.config.Constants;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * This class helps in building a url request to send to the api
 */
public class SupplierRequest {

  public static String sendRequest(String supplier, int timeOut, String request) {

    StringBuffer buffer = new StringBuffer();

    try {

      String apiUrl = Constants.API_URL + supplier + request;
      URL url = new URL(apiUrl);

      HttpURLConnection connection = (HttpURLConnection) url.openConnection();
      connection.setConnectTimeout(timeOut);
      connection.setReadTimeout(timeOut);
      int statusCode = connection.getResponseCode();

      if(statusCode != 500) {
        BufferedReader input = new BufferedReader(
          new InputStreamReader(connection.getInputStream(), "UTF-8"));

        String string;

        while ((string = input.readLine()) != null) {
          buffer.append(string);
        }
        input.close();
      } else {
        System.out.println(supplier + "api Has errored with 500 status code");
      }

    } catch (IOException ex) {
      System.out.println(supplier + "api " + ex.getMessage());
    }

    return buffer.toString();
  }

}
