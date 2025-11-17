package org.example.ProyectoPOO.model;

import java.math.*;
import java.time.*;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;
import org.openxava.annotations.*;
import org.openxava.model.*;

import lombok.*;


@MappedSuperclass
@Getter
@Setter

public class BaseEntity {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(length = 32)
    private String id;
}