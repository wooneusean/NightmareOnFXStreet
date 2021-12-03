package com.oodj.vaccspace.models;

import textorm.Column;
import textorm.Model;
import textorm.Repository;

@Repository
public abstract class User extends Model {

    @Column
    private String name;

    @Column
    private String phone;

    @Column 
    private String email;

    @Column
    private String password;

    public User() {
    }

    /**
     * Creates a new User object
     *
     * @param name     name for the user
     * @param phone    phone number of the user
     * @param email    email for the user
     * @param password password to login, if {@code null} value provided, defaults to {@code email#xxxx} where *
     *                 {@code xxxx} is the last 4 digits of phone number
     */
    public User(String name, String phone, String email, String password) {
        this.name = name;
        this.phone = phone;
        this.email = email;

        if (password == null) {
            this.password = String.format("%s#%s", this.email, this.phone.substring(this.phone.length() - 4));
        } else {
            this.password = password;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
