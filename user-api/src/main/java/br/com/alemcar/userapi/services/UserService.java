package br.com.alemcar.userapi.services;

import br.com.alemcar.userapi.domain.User;

import java.util.List;

public interface UserService {
    User findById(Long id);
    List<User> findAll();
}
