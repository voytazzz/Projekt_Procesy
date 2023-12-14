package com.example.demo.repository;

import com.example.demo.domain.Client;
import org.springframework.data.repository.CrudRepository;

public interface ClientRepository extends CrudRepository<Client, Long> {
}
//Wprowadzenie interfejsu ClientRepository spełnia zasadę otwarte/zamknięte
//Użycie interfejsu ClientRepository jest zgodne z zasadą DIP. Wyższy poziom modułu
// (ClientService) zależy od abstrakcji (interfejsu), a nie od szczegółów implementacji