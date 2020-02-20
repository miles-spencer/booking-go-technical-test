package com.project.model;

import com.google.gson.annotations.SerializedName;

/**
 * A class for modeling the options part of the JSON response from API
 */
public class Option {
  private String supplier;

  @SerializedName("car_type")
  private String carType;

  @SerializedName("price")
  private Integer price;

  public String getSupplier() {
    return supplier;
  }

  public void setSupplier(String supplier) {
    this.supplier = supplier;
  }

  public String getCarType() {
    return carType;
  }

  public void setCarType(String carType) {
    this.carType = carType;
  }

  public Integer getPrice() {
    return price;
  }

  public void setPrice(Integer price) {
    this.price = price;
  }

  @Override
  public String toString() {
    return "{" + carType + "}" + " - " + "{" + price + "}";
  }

  public String supplierString() {
    return "{" + carType + "}" + " - " + "{" + supplier + "}" + " - " +"{" + price + "}";
  }

}
