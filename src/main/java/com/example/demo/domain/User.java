package com.example.demo.domain;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "USER_TABLE")
public class User {
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int seqUser;
    private String userName;
    private String phone;
    private Integer userAge;
}
