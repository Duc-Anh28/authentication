package com.example.authentication.entity;

import com.example.authentication.entity.core.BaseEntity;
import com.example.authentication.request.account.CreateAccountRequest;
import com.example.authentication.util.CommonUtil;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "accounts")
@NoArgsConstructor
@AllArgsConstructor
public class Account extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "isActive", nullable = false)
    @ColumnDefault("0")
    private Boolean isActive;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JoinTable(name = "account_role",
            joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles;

    public List<Role> getRoles() {
        if (CommonUtil.isEmpty(roles)) return new ArrayList<>();
        return roles;
    }

    public Account(CreateAccountRequest request) {
        this.name = request.getName();
        this.email = request.getEmail();
    }

}
