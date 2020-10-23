package by.epamtc.courses.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Class entity of User
 *
 * @author DEA
 */
public class User implements Serializable {
    private static final long serialVersionUID = -6091193797468043184L;

    /**
     * Unique identifier of user
     */
    private int id;

    /**
     * Surname of user
     */
    private String surname;

    /**
     * Name of user
     */
    private String name;

    /**
     * Email of user
     */
    private String email;

    /**
     * Birthday of user
     */
    private LocalDate birthday;

    /**
     * Role of user
     */
    private UserRole role;

    /**
     * Path to photo of user
     */
    private String photoPath;

    /**
     * Construct empty user
     */
    public User() {
    }

    /**
     * Construct user as another user
     *
     * @param user another user
     */
    public User(User user) {
        this.id = user.id;
        this.surname = user.surname;
        this.name = user.name;
        this.email = user.email;
        this.birthday = user.birthday;
        this.role = user.role;
        this.photoPath = user.photoPath;
    }

    /**
     * @return unique identifier of user
     */
    public int getId() {
        return id;
    }

    /**
     * Set unique identifier to user
     *
     * @param id new value of unique identifier
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return surname of user
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Set surname to user
     *
     * @param surname new value of surname
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * @return name of user
     */
    public String getName() {
        return name;
    }

    /**
     * Set name to user
     *
     * @param name new value of name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return email of user
     */
    public String getEmail() {
        return email;
    }

    /**
     * Set email to user
     *
     * @param email new value of email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return birthday of user
     */
    public LocalDate getBirthday() {
        return birthday;
    }

    /**
     * Set birthday to user
     *
     * @param birthday new value of birthday
     */
    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    /**
     * @return role of user
     */
    public UserRole getRole() {
        return role;
    }

    /**
     * Set role to user
     *
     * @param role new value of role
     */
    public void setRole(UserRole role) {
        this.role = role;
    }

    /**
     * @return photo path of user
     */
    public String getPhotoPath() {
        return photoPath;
    }

    /**
     * Set photo path to user
     *
     * @param photoPath new value of photo path
     */
    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
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
        User user = (User) o;
        return id == user.id &&
                Objects.equals(surname, user.surname) &&
                Objects.equals(name, user.name) &&
                Objects.equals(email, user.email) &&
                Objects.equals(birthday, user.birthday) &&
                role == user.role &&
                Objects.equals(photoPath, user.photoPath);
    }

    /**
     * Calculate hash code of object
     *
     * @return hash code of object
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, surname, name, email, birthday, role, photoPath);
    }

    /**
     * Make string representation of this user
     *
     * @return string representation of this user
     */
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", surname='" + surname + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", birthday=" + birthday +
                ", role=" + role +
                ", photoPath='" + photoPath + '\'' +
                '}';
    }
}
