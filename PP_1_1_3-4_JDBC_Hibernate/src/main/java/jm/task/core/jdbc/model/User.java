package jm.task.core.jdbc.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table (name= "Users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column
    private String name;

    @Column
    private String lastName;

    @Column

    private byte age;


    public User() {}

    public User(String name, String lastName, byte age) {
        this.name = name;
        this.lastName = lastName;
        this.age = (byte) age;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;

    }

    public byte getAge() {
        return age;
    }

    public void setAge(byte age) {
        this.age = age;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return age == user.age && Objects.equals(id, user.id) && Objects.equals(lastName, user.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, lastName, age);
    }

    @Override
    public String toString() {
        return "User [" +
                "id:" + id +
                ", name:'" + name + '\'' +
                ", lastName:'" + lastName + '\'' +
                ", age:" + age +
                ']';
    }
}

