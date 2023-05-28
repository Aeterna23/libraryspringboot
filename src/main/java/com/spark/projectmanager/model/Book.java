package com.spark.projectmanager.model;

import lombok.*;
import org.hibernate.annotations.Table;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Entity
@Table(appliesTo = "book")
public class Book {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "Book name cannot be empty.")
    @Size(min = 3, max = 30, message = "Please check that book name should be between 3 and 30 digits.")
    @Column(name = "name")
    private String name;

    @NotEmpty(message = "Book author name cannot be empty.")
    @Size(min = 3, max = 30, message = "Please check that author name should be between 3 and 30 digits.")
    @Column(name = "author")
    private String author;


    @Min(value = 1800, message = "Please check that book edition date. Should be more than 1800 year.")
    @Column(name = "edition")
    private int edition;

    @JoinColumn(name = "person_id", referencedColumnName = "id")
    @ManyToOne()
    private Person owner;

    @Column(name = "date_of_taking")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateOfTaking;

    @Transient
    private boolean isExpired;

}
