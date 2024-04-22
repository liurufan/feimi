package org.basic.feimi.infrastructure.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;

@Data
@Entity
@Table(name = "t_user", indexes = { @Index(name="index_generatedId", columnList = "generatedId") })
@EqualsAndHashCode
@ToString
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String generatedId;
    
    private String email;

    private String password;

    @Column(nullable = false)
    private Byte role;

    private Byte stopStatus;

    @Column(nullable = false)
    private Byte registrationStatus;

}
