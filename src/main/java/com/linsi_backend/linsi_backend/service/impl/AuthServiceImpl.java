package com.linsi_backend.linsi_backend.service.impl;



import com.linsi_backend.linsi_backend.exception.error.Error;
import com.linsi_backend.linsi_backend.exception.custom.UnauthorizeException;
import com.linsi_backend.linsi_backend.model.User;
import com.linsi_backend.linsi_backend.repository.UserRepository;
import com.linsi_backend.linsi_backend.service.AuthService;
import com.linsi_backend.linsi_backend.service.dto.request.UserDTOin;
import com.linsi_backend.linsi_backend.service.dto.response.UserDTO;
import com.linsi_backend.linsi_backend.service.mapper.UserMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    public void register(UserDTOin dto) {
        User user = UserMapper.MAPPER.toEntity(dto);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        userRepository.save(user);
    }

    public UserDTO authenticate(UserDTOin dto) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            dto.getEmail(),
                            dto.getPassword()
                    )
            );
            User user = userRepository.findByEmail(dto.getEmail()).orElseThrow();
            return new UserDTO(user.getId(), user.getEmail());
        }catch (Exception ex){
            throw new UnauthorizeException(Error.AUTH_ERROR);
        }
    }
}