package com.example.authentication.entity;

import com.example.authentication.entity.core.BaseEntity;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "accounts")
@NoArgsConstructor
@AllArgsConstructor
@Where(clause = "is_deleted = false")
public class Account extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false, unique = true)
	private String email;
	@Column(nullable = false)
	private String password;
	@Column(nullable = false)
	@ColumnDefault("0")
	private Boolean isActive;
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@JoinTable(name = "account_role",
			joinColumns = @JoinColumn(name = "account_id"),
			inverseJoinColumns = @JoinColumn(name = "role_id")
	)
	private List<Role> roles;
}
