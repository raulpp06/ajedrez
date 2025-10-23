package es.upsa.programacion.controllers.users;

import es.upsa.programacion.models.users.AbstractUser;
import es.upsa.programacion.models.users.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserController {
    
    private List<AbstractUser> users = new ArrayList<>();
    private final File usersFile = new File("usuarios");

    public Optional<AbstractUser> login(String email, String password) {
        Optional<AbstractUser> usuario = users.stream()
                .filter(
                        u -> u.getEmail().equals(email)
                ).findFirst();
        if (usuario.isPresent()) {
            return usuario.get().isValidPassword(password) ? usuario : Optional.empty();
        }
        return Optional.empty();
    }
    
    public void register(String username, String password) {
        User user = new User(username, password);
        users.add(user);
    }

    public void finish() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(usersFile))) {
            oos.writeObject(users);
            System.out.println("Usuarios guardados correctamente.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadUsers() {
        if (!usersFile.exists()) {
            System.out.println("Archivo de usuarios no encontrado, se iniciará vacío.");
            return;
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(usersFile))) {
            users = (List<AbstractUser>) ois.readObject();
            System.out.println("Usuarios cargados correctamente.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
