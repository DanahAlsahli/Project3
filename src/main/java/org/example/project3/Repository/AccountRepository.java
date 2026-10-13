package org.example.project3.Repository;
import org.example.project3.Model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

    Account findAccountById(Integer id);

    List<Account> findAccountsByCustomerId(Integer customerId);

    boolean existsByAccountNumber(String accountNumber);


}
