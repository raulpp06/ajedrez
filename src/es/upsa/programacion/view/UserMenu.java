package es.upsa.programacion.view;

import es.upsa.programacion.controllers.users.UserController;
import es.upsa.programacion.models.users.AbstractUser;

import java.util.Optional;
import java.util.Scanner;

public class UserMenu {

    private UserController userController;
    private Scanner sc;

    public UserMenu() {
        this.userController = new UserController();
        this.sc = new Scanner(System.in);
    }

    public UserController getUserController() {
        return userController;
    }

    public void showMenu() {
        int elegido;
        Optional<AbstractUser> maybeUser = null;

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

            System.out.println("1. Jugar");
            System.out.println("2. Estadisticas");
            System.out.println("0. Cerrar sesion");

            do {
                opc = sc.nextInt();
                switch (opc) {
                    case 0:
                        //Cerrar sesion
                        break;
                    case 1:
                        //Jugar
                        break;
                    case 2:
                        //Ver estadisticas
                        break;
                }
            } while  (opc < 0 || opc > 2);
        });
    }
}
