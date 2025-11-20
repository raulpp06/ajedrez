package es.upsa.programacion.models.users;

import java.time.LocalDateTime;

public class Admin extends AbstractUser {

    public Admin(String email, String password, LocalDateTime created) {
        super(email, password, created);
    }
}
