package model.builder;

import model.Account;
import model.Client;

import java.util.List;

public class ClientBuilder {
    private Client client;

    public ClientBuilder(){
        client = new Client();
    }

    public ClientBuilder setName(String name) {
        client.setName(name);
        return this;
    }

    public ClientBuilder setIdNumber(String idNumber) {
        client.setIdNumber(idNumber);
        return this;
    }

    public ClientBuilder setPersonalNumericalCode(Integer personalNumericalCode) {
        client.setPersonalNumericalCode(personalNumericalCode);
        return this;
    }

    public ClientBuilder setAddress(String address) {
        client.setAddress(address);
        return this;
    }

    public ClientBuilder setAccount(List<Account> accounts) {
        client.setAccounts(accounts);
        return this;
    }

    public Client build(){
        return client;
    }
}
