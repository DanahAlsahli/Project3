package org.example.project3.Controller;
import lombok.RequiredArgsConstructor;
import org.example.project3.Api.ApiResponse;
import org.example.project3.Model.User;
import org.example.project3.Service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/account")
public class AccountController {

    private final AccountService accountService;


    @GetMapping("/getAccounts")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'EMPLOYEE')")
    public ResponseEntity<?> getAccounts() {

        return ResponseEntity.status(200).body(accountService.getAccounts());
    }


    @GetMapping("/getById/{accountId}")
    public ResponseEntity<?> getAccountById(@AuthenticationPrincipal User user, @PathVariable Integer accountId) {

        return ResponseEntity.status(200).body(accountService.getAccountById(user,accountId));
    }


    @GetMapping("/myAccounts")
    public ResponseEntity<?> getMyAccounts(@AuthenticationPrincipal User user) {

        return ResponseEntity.status(200).body(accountService.getMyAccounts(user.getId()));
    }


    @PostMapping("/create")
    public ResponseEntity<?> createAccount(@AuthenticationPrincipal User user) {
        accountService.createAccount(user.getId());

        return ResponseEntity.status(200).body(new ApiResponse("Your Account created successfully"));
    }


    @DeleteMapping("/delete/{accountId}")
    public ResponseEntity<?> deleteAccount(@AuthenticationPrincipal User user,@PathVariable Integer accountId) {
        accountService.deleteAccount(user.getId(),accountId);

        return ResponseEntity.status(200).body(new ApiResponse("The Account deleted successfully"));
    }


    @PutMapping("/activate/{accountId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> activateAccount(@PathVariable Integer accountId) {
        accountService.activateAccount(accountId);

        return ResponseEntity.status(200).body(new ApiResponse("The account activated successfully"));
    }


    @PutMapping("/deposit/{accountId}/{amount}")
    public ResponseEntity<?> deposit(@AuthenticationPrincipal User user,@PathVariable Integer accountId, @PathVariable Double amount) {
        accountService.deposit(user.getId(),accountId, amount);

        return ResponseEntity.status(200).body(new ApiResponse("Completed successfully"));
    }


    @PutMapping("/withdraw/{accountId}/{amount}")
    public ResponseEntity<?> withdraw(@AuthenticationPrincipal User user,@PathVariable Integer accountId, @PathVariable Double amount) {
        accountService.withdraw(user.getId(),accountId, amount);

        return ResponseEntity.status(200).body(new ApiResponse("Withdrawal completed successfully"));
    }


    @PutMapping("/transfer/{fromAccountId}/{toAccountId}/{amount}")
    public ResponseEntity<?> transfer(@AuthenticationPrincipal User user,@PathVariable Integer fromAccountId, @PathVariable Integer toAccountId, @PathVariable Double amount) {
        accountService.transfer(user.getId(),fromAccountId, toAccountId, amount);

        return ResponseEntity.status(200).body(new ApiResponse("Transfer completed successfully"));
    }


    @PutMapping("/block/{accountId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> blockAccount(@PathVariable Integer accountId) {
        accountService.blockAccount(accountId);

        return ResponseEntity.status(200).body(new ApiResponse("Account blocked successfully"));
    }
}
