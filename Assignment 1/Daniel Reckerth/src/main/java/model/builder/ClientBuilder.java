package model.builder;

import model.Client;

public class ClientBuilder {

  private final Client client;

  public ClientBuilder() {
    client = new Client();
  }

  public ClientBuilder setId(Long id) {
    client.setId(id);
    return this;
  }

  public ClientBuilder setName(String name) {
    client.setName(name);
    return this;
  }

  public ClientBuilder setCardNumber(String cardNumber) {
    client.setCardNumber(cardNumber);
    return this;
  }

  public ClientBuilder setSSN(String ssn) {
    client.setSSN(ssn);
    return this;
  }

  public ClientBuilder setAddress(String address) {
    client.setAddress(address);
    return this;
  }

  public Client build() {
    return client;
  }
}
