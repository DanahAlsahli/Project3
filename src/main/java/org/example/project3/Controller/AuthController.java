package org.example.project3.Controller;
import org.example.project3.Api.ApiResponse;
import org.example.project3.DTO.UpdateUserDTO;
import org.example.project3.Model.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.project3.Service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    @GetMapping("/getUsers")
    public ResponseEntity<?> getUsers(@AuthenticationPrincipal User user){
        return ResponseEntity.status(200).body(authService.getUsers());
    }


    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@AuthenticationPrincipal User user, @RequestBody @Valid UpdateUserDTO dto) {
        authService.updateUser(user.getId(), dto);

        return ResponseEntity.status(200).body(new ApiResponse("The User updated successfully"));
    }
}