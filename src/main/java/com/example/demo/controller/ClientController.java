package com.example.demo.controller;

import com.example.demo.repository.ClientRepository;
import com.example.demo.domain.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/clients")
public class ClientController {

    @Autowired
    private ClientRepository clientRepository;

    @GetMapping
    public Iterable<Client> getAllClients() {
        return clientRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Client> getClientById(@PathVariable Long id) {
        return clientRepository.findById(id);
    }

    @PostMapping
    public Client addClient(@RequestBody Client client) {
        return clientRepository.save(client);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Client> updateClient(@PathVariable Long id, @RequestBody Client updatedClient) {
        Optional<Client> existingClientOptional = clientRepository.findById(id);

        if (existingClientOptional.isPresent()) {
            Client existingClient = existingClientOptional.get();


            existingClient.setFirstName(updatedClient.getFirstName());
            existingClient.setLastName(updatedClient.getLastName());
            existingClient.setEmail(updatedClient.getEmail());


            Client savedClient = clientRepository.save(existingClient);
            return ResponseEntity.ok(savedClient);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public void deleteClient(@PathVariable Long id) {
        clientRepository.deleteById(id);
    }
}
