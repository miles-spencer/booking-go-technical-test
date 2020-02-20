package com.project.model;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.project.app.SupplierRequest;
import com.project.config.Constants;

import java.util.HashMap;
import java.util.Map;

/**
 * A class for modeling the API and its web services
 */
public class Supplier {

  /**
   * Gets a list of taxi options from a supplier based on pickup and drop off location
   * @return taxi options from supplier
   */
  public Message getSupplierInformation(String supplier, int timeOut,
                                        String pickUp, String dropOff) {

    Map<String, Object> params = new HashMap<>();
    String response;
    Message message = null;

    params.put(Constants.PICK_UP, pickUp);
    params.put(Constants.DROP_OFF, dropOff);

    response = SupplierRequest.sendRequest(supplier, timeOut, Helper.getURLString(params));

    // System.out.println(response);

    Gson gson = new Gson();

    try {
      message = gson.fromJson(response, Message.class);
    } catch (JsonParseException jpe) {
      System.out.println(jpe.getMessage());
    }

    return message;
  }

}
