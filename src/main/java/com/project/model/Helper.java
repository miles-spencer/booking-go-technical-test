package com.project.model;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * A class to help build the URL string sent onto the API
 */
public class Helper {

  /**
   * Builds a URL string that is used in making a request to the API
   * @return URL String containing method name and parameters
   */
  public static String getURLString(Map<String, Object> params) {

    StringBuilder paramString = new StringBuilder();

    paramString.append("?");

    for(Map.Entry<String, Object> entry : params.entrySet()) {

      try {
        paramString.append(entry.getKey()).append("=");
        paramString.append(URLEncoder.encode(entry.getValue().toString(), "UTF-8"));
      } catch(UnsupportedEncodingException ex) {
        System.out.println(ex.getMessage());
      }
      paramString.append("&");
    }

    return paramString.toString();
  }

}
