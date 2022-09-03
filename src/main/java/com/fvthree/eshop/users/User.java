package com.fvthree.eshop.users;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fvthree.eshop.auth.models.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="users",
	uniqueConstraints = {
			@UniqueConstraint(columnNames="name"),
			@UniqueConstraint(columnNames="email")
})
public class User{

	@Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
        name = "UUID",	
        strategy = "org.hibernate.id.UUIDGenerator",
        parameters = {
            @org.hibernate.annotations.Parameter(
                name = "uuid_gen_strategy_class",
                value = "org.hibernate.id.uuid.CustomVersionOneStrategy"
            )
        }
    )
    @Type(type = "pg-uuid")
    @Column(name = "user_id", updatable = false, nullable = false)
    private UUID userId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @JsonIgnore
    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private boolean isActive;

    @Column(nullable = false)
    private String street;

    @Column(nullable = false)
    private String apartment;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String zip;

    @Column(nullable = false)
    private String country;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private Boolean isAdmin;

    @Column(nullable = false)
    private boolean isNotLocked;
    
    @Column(name = "is_verified", columnDefinition = "boolean default false", nullable = false)
	private boolean isVerified;
    
    @Type(type = "pg-uuid")
    @Column(name = "token", nullable = false, unique = true)
    private UUID token = UUID.randomUUID();
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @Column(nullable = false, updatable = false)
    private OffsetDateTime dateCreated;

    @Column(nullable = false)
    private OffsetDateTime lastUpdated;

    @PrePersist
    public void prePersist() {
        dateCreated = OffsetDateTime.now();
        lastUpdated = dateCreated;
    }

    @PreUpdate
    public void preUpdate() {
        lastUpdated = OffsetDateTime.now();
    }
    
    public User(String username, String email, String password)
    {
        this.name = username;
        this.email = email;
        this.password = password;
    }
}
