package jwt.auth.services;

import jwt.auth.dto.UserCredentialDto;
import jwt.auth.models.User;

public interface UserService {
    User getUserFromUsername(String username);
    User registerNewUserAccount(UserCredentialDto userCredentialDto);
}
