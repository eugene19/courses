package by.epamtc.courses.entity;

import java.util.Objects;

public class UserData extends User {
    private static final long serialVersionUID = 8240457089283345327L;

    private String login;
    private String password;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
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
        if (!super.equals(o)) return false;
        UserData userData = (UserData) o;
        return Objects.equals(login, userData.login) &&
                Objects.equals(password, userData.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), login, password);
    }

    @Override
    public String toString() {
        return "UserData{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
