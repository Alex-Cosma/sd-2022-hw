package repository.client;

import model.Client;
import model.User;
import repository.Repository;

public interface ClientRepository extends Repository<Client, Long> {

    Client findByName(String name);

    Client findByCNP(String cnp);

    void removeAll();
}
