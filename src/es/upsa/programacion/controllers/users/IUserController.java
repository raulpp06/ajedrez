package es.upsa.programacion.controllers.users;

import es.upsa.programacion.models.users.AbstractUser;

import java.util.Optional;

public interface IUserController {
    Optional<AbstractUser> login(String email, String password);

    void register(String username, String password);

    void finish();

    void loadUsers();
}