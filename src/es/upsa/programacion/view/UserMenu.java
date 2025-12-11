package es.upsa.programacion.view;

import es.upsa.programacion.controllers.users.UserController;
import es.upsa.programacion.models.users.AbstractUser;
import es.upsa.programacion.models.users.User;

import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.Scanner;

public class UserMenu implements Menu {

    private final UserController userController = new UserController();
    private final Scanner sc = new Scanner(System.in);

    public UserController getUserController() {
        return userController;
    }

    public void start() {
        int elegido;
        Optional<AbstractUser> maybeUser = Optional.empty();

        System.out.println("Bienvenido al ajedrez\n");

        do {
            System.out.println("Selecciona opcion:");
            System.out.println("1. Registrar usuario");
            System.out.println("2. Iniciar sesión");

            do {
                elegido = sc.nextInt();
                switch (elegido) {
                    case 1:
                        System.out.println("Introduzca el email:");
                        String emailReg = sc.next();

                        System.out.println("Introduzca su contraseña:");
                        String passwordReg = sc.next();

                        userController.register(emailReg, passwordReg);
                        maybeUser = userController.login(emailReg, passwordReg);
                        break;
                    case 2:
                        System.out.println("Introduzca el email:");
                        String emailLog = sc.next();

                        System.out.println("Introduzca su contraseña:");
                        String passwordLog = sc.next();

                        maybeUser = userController.login(emailLog, passwordLog);

                        if (maybeUser.isEmpty()) {
                            System.out.println("Contraseña o usuario no existen");
                        }
                        break;
                    default:
                        System.out.println("Valor invalido!!");
                        break;
                }
            } while (elegido != 1 && elegido != 2);
        } while (maybeUser.isEmpty());

        maybeUser.ifPresent(user -> {
            int opc;
            System.out.println("Bienvenido " + user.getEmail());

            System.out.println("Selecciona opcion:");

            while (true) {
                System.out.println("\n1. Jugar");
                if (user instanceof User) System.out.println("2. Estadisticas");
                System.out.println("0. Cerrar sesion");

                opc = sc.nextInt();
                switch (opc) {
                    case 0:
                        return;
                    case 1:
                        BoardMenu boardMenu = new BoardMenu(user);
                        boardMenu.start();
                        break;
                    case 2:
                        if (user instanceof User uuser) {
                            System.out.println("Fecha de creación de usuario: " + user.getCreated().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
                            System.out.println("Partidas ganadas: " + uuser.getWinCount());
                            System.out.println("Porcentage de victorias: " + uuser.getWinPercentage() + "%");
                        } else {
                            throw new IllegalStateException("Unreachable");
                        }
                        break;
                }
            }
        });
    }
}
