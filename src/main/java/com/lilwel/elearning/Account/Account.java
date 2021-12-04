package com.lilwel.elearning.Account;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(
        name = "account",
        uniqueConstraints =@UniqueConstraint(name="EmailUnique",columnNames = "email")
)
public class Account {
    public enum Role {
        STUDENT,
        Teacher
    }

    @Id
    @Column(updatable = false, nullable = false)
    private UUID id = UUID.randomUUID();
    @Column( nullable = false, name="email")
    private String email;
    @Column(name = "password_hash", nullable = false)
    private String password;
    @Column(nullable = false)
    private Role role;


}
