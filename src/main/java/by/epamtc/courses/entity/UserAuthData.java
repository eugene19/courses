package by.epamtc.courses.entity;

import java.util.Objects;

/**
 * Class entity of User for authentication
 *
 * @author DEA
 */
public class UserAuthData extends User {
    private static final long serialVersionUID = 8240457089283345327L;

    /**
     * Login of user
     */
    private String login;

    /**
     * Password of user
     */
    private transient String password;

    /**
     * Construct empty user
     */
    public UserAuthData() {
    }

    /**
     * Construct user auth as another user
     *
     * @param user another user
     */
    public UserAuthData(User user) {
        super(user);
    }

    /**
     * @return login of user
     */
    public String getLogin() {
        return login;
    }

    /**
     * Set login to user
     *
     * @param login new value of login
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * @return password of user
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set password to user
     *
     * @param password new value of password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Compares this object with another object for equality
     *
     * @param o the object with which to compare
     * @return true if objects are equal, else - false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        UserAuthData userAuthData = (UserAuthData) o;
        return Objects.equals(login, userAuthData.login) &&
                Objects.equals(password, userAuthData.password);
    }

    /**
     * Calculate hash code of object
     *
     * @return hash code of object
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), login, password);
    }

    /**
     * Make string representation of this user
     *
     * @return string representation of this user
     */
    @Override
    public String toString() {
        return "UserAuthData{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
