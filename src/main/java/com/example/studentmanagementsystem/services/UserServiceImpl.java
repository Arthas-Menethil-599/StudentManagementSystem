package com.example.studentmanagementsystem.services;

import com.example.studentmanagementsystem.entities.DbUser;
import com.example.studentmanagementsystem.entities.Role;
import com.example.studentmanagementsystem.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {

    private BCryptPasswordEncoder bcryptPasswordEncoder;

    private UserRepository userRepository;

    @Override
    public DbUser getUser(String email) {
        return userRepository.findUserByEmail(email);
    }

    @Override
    public DbUser addUser(DbUser dbUser) {
        DbUser checkDbUser = userRepository.findUserByEmail(dbUser.getEmail());
        if (Objects.isNull(checkDbUser)) {
            dbUser.setPassword(bcryptPasswordEncoder.encode(dbUser.getPassword()));
            return userRepository.save(dbUser);
        }
        return null;
    }

    @Override
    public DbUser updateUser(DbUser dbUser) {
        return userRepository.save(dbUser);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<DbUser> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        DbUser dbUser = userRepository.findUserByEmail(email);
        if (Objects.nonNull(dbUser)) {
            User securityUser = new User(dbUser.getEmail(), dbUser.getPassword(), dbUser.getRoles());
            return securityUser;
        } else {
            return null;
        }
    }

    @Autowired
    public void setBcryptPasswordEncoder(BCryptPasswordEncoder bcryptPasswordEncoder) {
        this.bcryptPasswordEncoder = bcryptPasswordEncoder;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}