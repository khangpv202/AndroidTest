package com.example.AndroidTest.copyBeanTest;

/**
 * Created by khangpv on 10/25/2014.
 */
public class Manager extends Person
{
    private Long salary;

    public Manager(String name,int age,Long salary)
    {
        super(name,age);
        this.salary = salary;
    }
}
