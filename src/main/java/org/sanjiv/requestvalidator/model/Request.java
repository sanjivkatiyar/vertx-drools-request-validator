package org.sanjiv.requestvalidator.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.sanjiv.requestvalidator.masker.Mask;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Request {

  private String uuid;

  @JsonProperty("zip")
  private String zip;

  @JsonProperty("state")
  private String state;

  @Mask
  @JsonProperty("city")
  private String city;

  @Mask
  @JsonProperty("address")
  private String address;

  public String getUuid() {
    return uuid;
  }

  public void setUuid(String uuid) {
    this.uuid = uuid;
  }

  public String getZip() {
    return zip;
  }

  public void setZip(String zip) {
    this.zip = zip;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }
}
