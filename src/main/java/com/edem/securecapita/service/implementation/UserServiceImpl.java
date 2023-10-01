package com.edem.securecapita.service.implementation;

import com.edem.securecapita.dto.UserDTO;
import com.edem.securecapita.dtomapper.UserDTOMapper;
import com.edem.securecapita.model.User;
import com.edem.securecapita.repository.UserRepository;
import com.edem.securecapita.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository<User> userRepository;

    @Override
    public UserDTO createUser(User user) {
        return UserDTOMapper.fromUser(userRepository.create(user));
    }
}
