package com.example.demo.service;

import com.example.demo.domain.Client;
import com.example.demo.repository.ClientRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ClientService {
    @Autowired
    private ClientRepository clientRepository;

    public ClientService() {
    }

    public Iterable<Client> getAllClients() {
        return this.clientRepository.findAll();
    }

    public Optional<Client> getClientById(Long id) {
        return this.clientRepository.findById(id);
    }

    public Client addClient(Client client) {
        return (Client)this.clientRepository.save(client);
    }

    public ResponseEntity<Client> updateClient(Long id, Client updatedClient) {
        Optional<Client> existingClientOptional = this.clientRepository.findById(id);
        if (existingClientOptional.isPresent()) {
            Client existingClient = (Client)existingClientOptional.get();
            existingClient.setFirstName(updatedClient.getFirstName());
            existingClient.setLastName(updatedClient.getLastName());
            existingClient.setEmail(updatedClient.getEmail());
            Client savedClient = (Client)this.clientRepository.save(existingClient);
            return ResponseEntity.ok(savedClient);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public void deleteClient(Long id) {
        this.clientRepository.deleteById(id);
    }
}
