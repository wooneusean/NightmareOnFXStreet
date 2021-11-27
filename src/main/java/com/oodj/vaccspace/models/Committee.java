package com.oodj.vaccspace.models;

import textorm.Column;
import textorm.Repository;

@Repository
public class Committee extends User {

    @Column
    private int salary;

    public Committee() {
    }

    public Committee(String name, String phone, String email, String password, int salary) {
        super(name, phone, email, password);
        this.salary = salary;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }
}
