package org.example.project3.Service;
import lombok.RequiredArgsConstructor;
import org.example.project3.Api.ApiException;
import org.example.project3.DTO.UpdateUserDTO;
import org.example.project3.Model.User;
import org.example.project3.Repository.AuthRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthRepository authRepository;

    public List<User> getUsers(){
        return authRepository.findAll();
    }

    public void updateUser(Integer userId, UpdateUserDTO dto) {

        User user = authRepository.findUserById(userId);
        if (user==null){
            throw new ApiException("user not found");
        }
        if (authRepository.existsByUsernameAndIdNot(dto.getUsername(), userId)) {
            throw new ApiException("username already exists");
        }

        if (authRepository.existsByEmailAndIdNot(dto.getEmail(), userId)) {
            throw new ApiException("email already exists");
        }

        user.setUsername(dto.getUsername());
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());

        authRepository.save(user);
    }
}
