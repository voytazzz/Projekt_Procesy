package com.example.demo.service;

import com.example.demo.domain.Client;
import com.example.demo.repository.ClientRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

//Kod w klasie ClientService jest zgodny z zasadą SRP, ponieważ odpowiada za operacje
// związane z encją Client. Ma jedno jasno zdefiniowane zadanie: obsługę klientów

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public Iterable<Client> getAllClients() {
        return this.clientRepository.findAll();
    }

    public Optional<Client> getClientById(Long id) {
        return this.clientRepository.findById(id);
    }

    public Client addClient(Client client) {
        return this.clientRepository.save(client);
    }

    public ResponseEntity<Client> updateClient(Long id, Client updatedClient) {
        Optional<Client> existingClientOptional = this.clientRepository.findById(id);
        if (existingClientOptional.isPresent()) {
            Client existingClient = existingClientOptional.get();
            existingClient.setFirstName(updatedClient.getFirstName());
            existingClient.setLastName(updatedClient.getLastName());
            existingClient.setEmail(updatedClient.getEmail());
            Client savedClient = this.clientRepository.save(existingClient);
            return ResponseEntity.ok(savedClient);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public void deleteClient(Long id) {
        this.clientRepository.deleteById(id);
    }
}
