package jwt.auth.services;

import jwt.auth.dto.UserCredentialDto;
import jwt.auth.models.User;
import jwt.auth.repos.UserRepo;
import jwt.auth.security.model.SecurityUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserDetailsService, UserService {

    @Autowired
    UserRepo userRepo;
    @Autowired
    PasswordEncoder passwordEncoder;

    //spring will call this
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return new SecurityUser(user);
    }

    @Override
    public User getUserFromUsername(String username) {
        try {
            User user = userRepo.findByUsername(username);
            return user;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User registerNewUserAccount(UserCredentialDto userCredentialDto) {
        // should check if the email is valid!!
        User user = new User();
        user.setUsername(userCredentialDto.getUsername());
        user.setEmail(userCredentialDto.getEmail());
        user.setPassword(passwordEncoder.encode(userCredentialDto.getPassword()));
        return userRepo.save(user);
    }
}