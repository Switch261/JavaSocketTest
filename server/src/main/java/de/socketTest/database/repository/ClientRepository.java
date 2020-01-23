package de.socketTest.database.repository;

import de.socketTest.database.model.Client;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ClientRepository extends CrudRepository<Client, String> {
    List<Client> findAll();
    Optional<Client> findOneByName(String name);
}
