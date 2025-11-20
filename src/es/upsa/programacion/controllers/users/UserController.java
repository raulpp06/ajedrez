package es.upsa.programacion.controllers.users;

import es.upsa.programacion.models.users.AbstractUser;
import es.upsa.programacion.models.users.User;

import java.io.*;
import java.util.*;

public class UserController implements IUserController {

    private Set<AbstractUser> users = new HashSet<>();
    private final File usersFile = new File("usuarios");

    @Override
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

    @Override
    public void register(String email, String password) {
        User newUser = new User(email, password);

        if (!users.add(newUser)) {
            System.out.println("User already exists.");
        }
    }

    @Override
    public void finish() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(usersFile))) {
            oos.writeObject(new ArrayList<>(users));
            System.out.println("Usuarios guardados correctamente.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void loadUsers() {
        if (!usersFile.exists()) {
            System.out.println("Archivo de usuarios no encontrado, se iniciará vacío.");
            return;
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(usersFile))) {
            users = new HashSet<>((List<AbstractUser>) ois.readObject());
            System.out.println("Usuarios cargados correctamente.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
