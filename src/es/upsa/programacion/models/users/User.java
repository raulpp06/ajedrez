package es.upsa.programacion.models.users;

import java.time.LocalDateTime;

public class User extends AbstractUser {

    private int winCount;

    public User(String email, String password, LocalDateTime created) {
        super(email, password, created);
        this.winCount = 0;
    }

    public User(String email, String password) {
        this(email, password, LocalDateTime.now());
    }

    public void addWin() {
        this.winCount++;
    }

    public int getWinCount() {
        return winCount;
    }

    public int getWinPercentage() {
        return winCount == 0 ? 0 : (winCount * 100) / winCount;
    }
}
