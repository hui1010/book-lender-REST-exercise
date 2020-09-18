package se.lexicon.huiyi.booklender.dto;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.Objects;

public class LibraryUserDto {
    public static final String EMAIL_PATTERN = "^\\w+@[a-zA-Z_]+?\\.[a-zA-Z]{2,3}$";
    @Null(message = "User id should not be present.")
    private Integer userId;

    @PastOrPresent(message = "The register date should not be in the future.")
    private LocalDate RegDate;

    @NotBlank(message = "Name is needed.")
    @Size(min = 2)
    private String name;

    @NotBlank(message = "Email is needed.")
    @Email(regexp = EMAIL_PATTERN)
    private String email;

    public LibraryUserDto() {
    }

    public LibraryUserDto(int userId, LocalDate regDate, String name, String email) {
        this.userId = userId;
        RegDate = regDate;
        this.name = name;
        this.email = email;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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
        LibraryUserDto that = (LibraryUserDto) o;
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
        return "LibraryUserDto{" +
                "userId=" + userId +
                ", RegDate=" + RegDate +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
