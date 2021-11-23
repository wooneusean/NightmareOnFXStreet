package com.oodj.vaccspace.models;

import textorm.Column;
import textorm.Repository;

@Repository
public class Committee extends User {

    @Column
    private int Salary;

    public Committee() {
    }

    public Committee(String name, String phone, String email, String password, int salary) {
        super(name, phone, email, password);
        Salary = salary;
    }

    public int getSalary() {
        return Salary;
    }

    public void setSalary(int salary) {
        Salary = salary;
    }
}
