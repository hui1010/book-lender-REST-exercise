package se.lexicon.huiyi.booklender.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
public class LibraryUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;

    private LocalDate RegDate;
    private String name;
    @Column(unique = true)
    private String email;

    public LibraryUser() {
    }

    public LibraryUser(LocalDate regDate, String name, String email) {
        RegDate = regDate;
        this.name = name;
        this.email = email;
    }

    public int getUserId() {
        return userId;
    }


    public LocalDate getRegDate() {
        return RegDate;
    }

    public void setRegDate(LocalDate regDate) {
        RegDate = regDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LibraryUser that = (LibraryUser) o;
        return userId == that.userId &&
                Objects.equals(RegDate, that.RegDate) &&
                Objects.equals(name, that.name) &&
                Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, RegDate, name, email);
    }

    @Override
    public String toString() {
        return "LibraryUser{" +
                "userId=" + userId +
                ", RegDate=" + RegDate +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}

