package org.launchcode.nextchapter.models.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CreateClubFormDTO {

    @NotNull
    @NotBlank
    @Size(min = 3, max = 20, message = "Invalid club name. Must be between 3 and 20 characters.")
    private String displayName;

    @NotNull
    @NotBlank
    @Size(min = 5, max = 30, message = "Invalid password. Must be between 5 and 30 characters.")
    private String password;

    @NotNull
    @NotBlank
    @Size(min = 1, max = 100, message = "Invalid book title. Must be between 1 and 100 characters.")
    private String activeBook;

    private String verifyPassword;

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVerifyPassword() {
        return verifyPassword;
    }

    public void setVerifyPassword(String verifyPassword) {
        this.verifyPassword = verifyPassword;
    }

    public String getActiveBook() {
        return activeBook;
    }

    public void setActiveBook(String activeBook) {
        this.activeBook = activeBook;
    }
}
