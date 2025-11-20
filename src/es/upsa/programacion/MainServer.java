package es.upsa.programacion;

import es.upsa.programacion.controllers.pieces.Board;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class MainServer {
    public static void main(String[] args) {
        int port = 6969; // choose any free port
        try (ServerSocket serverSocket = new ServerSocket(port, 50, InetAddress.getByName("0.0.0.0"))) {
            System.out.println("Chess server listening on port " + port + "...");

            try (Socket client = serverSocket.accept();
                 BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                 PrintWriter out = new PrintWriter(client.getOutputStream(), true)) {

                Board board = new Board();
                Color turn = Color.WHITE;

                out.println("Welcome to TCP Chess! Type 'quit' to exit.");

                while (true) {
                    out.println(board);
                    out.print(turn + " to move (e.g. e2 e4): ");
                    out.flush();

                    String move = in.readLine();
                    if (move == null || move.equalsIgnoreCase("quit")) break;

                    if (board.move(move, turn)) {
                        turn = (turn == Color.WHITE) ? Color.BLACK : Color.WHITE;
                    } else {
                        out.println("Invalid move, try again.");
                    }

                    if (board.isGameOver()) {
                        out.println("Game over!");
                        break;
                    }
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
