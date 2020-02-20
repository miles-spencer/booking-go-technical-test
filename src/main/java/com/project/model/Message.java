package com.project.model;

import com.google.gson.annotations.SerializedName;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * A class for modeling the JSON message response from API
 */
public class Message {
  @SerializedName("supplier_id")
  private String supplier;

  @SerializedName("options")
  private List<Option> options;

  public String getSupplier() {
    return supplier;
  }

  public void setSupplier(String supplier) {
    this.supplier = supplier;
  }

  public List<Option> getOptions() {
    return options;
  }

  public void setOptions(List<Option> options) {
    this.options = options;
  }

  /**
   * Uses number of passengers to filter the output of the API
   * @return list of filtered options
   */
  public List<Option> filterOptions(int passengers) {
    List<Option> filteredList;

    if(passengers > 6) {
      filteredList = this.options.stream().filter(option -> option.getCarType()
        .equals("MINIBUS")).collect(Collectors.toList());
    } else if(passengers > 4) {
      filteredList = this.options.stream().filter(option -> option.getCarType()
        .equals("PEOPLE_CARRIER") || option.getCarType().equals("LUXURY_PEOPLE_CARRIER"))
        .collect(Collectors.toList());
    } else {
      filteredList = this.options.stream().filter(option -> option.getCarType().equals("STANDARD")
        || option.getCarType().equals("EXECUTIVE") || option.getCarType().equals("LUXURY"))
        .collect(Collectors.toList());
    }

    return filteredList;
  }

  /**
   * From the API responses that have been gathered finds the minimum price for each car type
   * @return list of minimum price options
   */
  public List<Option> minimumPrice() {
    List<Option> minimumList = new ArrayList<>();
    Option minimumOption = new Option();
    List<String> carTypes = Arrays.asList("STANDARD", "EXECUTIVE", "LUXURY", "PEOPLE_CARRIER",
      "LUXURY_PEOPLE_CARRIER", "MINIBUS");

    for(String carType : carTypes) {
      int minimum = Integer.MAX_VALUE;
      for(Option option : this.options) {
        if(option.getCarType().equals(carType) && option.getPrice() < minimum) {
          minimumOption = option;
          minimum = option.getPrice();
        }
      }
      if(minimumOption.getCarType() != null && minimumOption.getPrice() != null
        && minimumOption.getCarType().equals(carType)) {

        minimumList.add(minimumOption);
      }
    }

    return minimumList;
  }

  /**
   * Used to join two message API responses together
   * @return list of joined responses if both are available
   */
  public List<Option> joinMessages(Message message, Message messageTwo) {
    List<Option> joinedList;

    if(message == null && messageTwo == null) {
      return null;
    } else if(message == null) {
      return messageTwo.getOptions();
    } else if(messageTwo == null) {
      return message.getOptions();
    } else {
      joinedList = Stream.of(message.getOptions(), messageTwo.getOptions())
        .filter(Objects::nonNull).flatMap(Collection::stream).collect(Collectors.toList());
      return joinedList;
    }

  }

}
