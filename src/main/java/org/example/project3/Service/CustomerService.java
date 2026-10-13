package org.example.project3.Service;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.project3.Api.ApiException;
import org.example.project3.DTO.CustomerRegisterDTO;
import org.example.project3.Model.Account;
import org.example.project3.Model.Customer;
import org.example.project3.Repository.AuthRepository;
import org.example.project3.Repository.CustomerRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.example.project3.Model.User;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    private final AuthRepository authRepository;


    public List<Customer> getCustomers(){
        return customerRepository.findAll();
    }


    public Customer getCustomerById(Integer customerId) {

        Customer customer = customerRepository.findCustomerById(customerId);

        if (customer == null) {
            throw new ApiException("customer not found");
        }

        return customer;
    }


    @Transactional
    public void registerCustomer(CustomerRegisterDTO dto) {

        if (authRepository.existsByUsername(dto.getUsername())) {
            throw new ApiException("username already exists");
        }

        if (authRepository.existsByEmail(dto.getEmail())) {
            throw new ApiException("email already exists");
        }

        User user = new User();
        user.setUsername(dto.getUsername());

        String hash = new BCryptPasswordEncoder().encode(dto.getPassword());
        user.setPassword(hash);

        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setRole("CUSTOMER");

        authRepository.save(user);

        Customer customer = new Customer();
        customer.setPhoneNumber(dto.getPhoneNumber());
        customer.setUser(user);

        customerRepository.save(customer);
    }

    public void updateCustomer(Integer userId, String phoneNumber) {

        Customer customer = customerRepository.findCustomerByUserId(userId);

        if (customer == null) {
            throw new ApiException("customer not found");
        }

        if (phoneNumber == null || !phoneNumber.matches("^05[0-9]{8}$")) {
            throw new ApiException("invalid phone number");
        }

        customer.setPhoneNumber(phoneNumber);
        customerRepository.save(customer);
    }


    @Transactional
    public void deleteCustomer(Integer userId) {

        Customer customer = customerRepository.findCustomerByUserId(userId);
        if (customer == null) {
            throw new ApiException("customer not found");
        }

        for (Account account : customer.getAccounts()) {
            if (account.getBalance() !=0.0) {
                throw new ApiException("all accounts balance must be zero");
            }
        }
        customerRepository.delete(customer);
    }
}
