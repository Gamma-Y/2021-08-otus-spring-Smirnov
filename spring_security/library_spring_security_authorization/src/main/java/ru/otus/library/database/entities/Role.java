package ru.otus.library.database.entities;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "role")
    private String role;
    @ToString.Exclude
    @ManyToMany(mappedBy = "roles")
    private List<User> users;

    @Override
    public String getAuthority() {
        return role;
    }
}
