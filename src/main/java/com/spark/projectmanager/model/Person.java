package com.spark.projectmanager.model;


import lombok.*;
import org.hibernate.annotations.Table;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Entity
@Table(appliesTo = "person")
public class Person {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "Имя не должно быть пустым")
    @Size(min = 2, max = 100, message = "Имя должно быть от 2 до 100 символов длиной")
    @Column(name = "username")
    private String username;

    @NotEmpty(message = "Person full name cannot be empty.")
    @Size(min = 3, message = "Name should contain at least 3 digits.")
    @Column(name = "full_name")
    private String fullName;

    @Column(name = "birth_date")
    @Min(value = 1900, message = "Person is too old. Check the birth date please.")
    private Integer birthDate;

    @Column(name = "password")
    private String password;

    @OneToMany(mappedBy = "owner")
    private List<Book> books;

}
