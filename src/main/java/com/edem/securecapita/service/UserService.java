package com.edem.securecapita.service;

import com.edem.securecapita.dto.UserDTO;
import com.edem.securecapita.model.User;

public interface UserService {
    UserDTO createUser(User user);
}
