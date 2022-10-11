package com.best.practice.BestPractice.entities;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "user_master")
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class UserMasterEntity extends AuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;
}
