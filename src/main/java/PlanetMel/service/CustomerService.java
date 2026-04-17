package PlanetMel.service;

import PlanetMel.domain.Customer;
import PlanetMel.repo.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public Customer findOrCreate(String name, String email) {
        Customer existing = customerRepository.findByEmail(email);
        if (existing != null) return existing;
        return customerRepository.save(new Customer(name, email));
    }
}