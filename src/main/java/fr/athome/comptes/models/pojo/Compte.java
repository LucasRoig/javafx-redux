package fr.athome.comptes.models.pojo;

import java.io.Serializable;
import java.util.Objects;

public class Compte implements Serializable {
    private String username;
    private String password;

    public Compte(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Compte comptes = (Compte) o;
        return Objects.equals(username, comptes.username) &&
                Objects.equals(password, comptes.password);
    }
}
