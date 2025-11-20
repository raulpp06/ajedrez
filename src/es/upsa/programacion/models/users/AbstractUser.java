package es.upsa.programacion.models.users;

import java.io.Serializable;
import java.time.LocalDateTime;

public abstract class AbstractUser implements Serializable {

    private String email;
    private String password;
    private final LocalDateTime created;

    public AbstractUser(String email, String password, LocalDateTime created) {
        this.email = email;
        this.password = password;
        this.created = created;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isValidPassword(String password) {
        return this.password.equals(password);
    }

    public LocalDateTime getCreated() {
        return created;
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof AbstractUser that)) return false;

        return email.equals(that.email) && password.equals(that.password) && created.equals(that.created);
    }

    @Override
    public int hashCode() {
        int result = email.hashCode();
        result = 31 * result + password.hashCode();
        result = 31 * result + created.hashCode();
        return result;
    }
}
