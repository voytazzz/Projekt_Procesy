package com.example.demo;

import com.example.demo.domain.Client;
import com.example.demo.repository.ClientRepository;
import com.example.demo.service.ClientService;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

public class ClientServiceTest {
    @Mock
    private ClientRepository clientRepository;
    @InjectMocks
    private ClientService clientService;

    public ClientServiceTest() {
    }

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddClient() {
        Client clientToAdd = new Client();
        clientToAdd.setFirstName("John");
        clientToAdd.setLastName("Doe");
        clientToAdd.setEmail("john.doe@example.com");
        Mockito.when((Client)this.clientRepository.save(clientToAdd)).thenReturn(clientToAdd);
        Client addedClient = this.clientService.addClient(clientToAdd);
        Assertions.assertEquals("John", addedClient.getFirstName());
        Assertions.assertEquals("Doe", addedClient.getLastName());
        Assertions.assertEquals("john.doe@example.com", addedClient.getEmail());
    }
    @Test
    public void testGetClientById() {
        Long clientId = 1L;
        Client client = new Client();
        client.setId(clientId);
        client.setFirstName("John");
        Mockito.when(this.clientRepository.findById(clientId)).thenReturn(Optional.of(client));
        Optional<Client> result = this.clientService.getClientById(clientId);
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals("John", ((Client)result.get()).getFirstName());
    }

    @Test
    public void testUpdateClient() {
        Long clientId = 1L;
        Client existingClient = new Client();
        existingClient.setId(clientId);
        existingClient.setFirstName("John");
        Client updatedClientData = new Client();
        updatedClientData.setFirstName("Jane");
        Mockito.when(this.clientRepository.findById(clientId)).thenReturn(Optional.of(existingClient));
        Mockito.when((Client)this.clientRepository.save(existingClient)).thenReturn(existingClient);
        ResponseEntity<Client> response = this.clientService.updateClient(clientId, updatedClientData);
        Assertions.assertEquals(200, response.getStatusCodeValue());
        Assertions.assertEquals("Jane", existingClient.getFirstName());
    }






}
