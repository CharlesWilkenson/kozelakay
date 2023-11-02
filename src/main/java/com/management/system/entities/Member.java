package com.management.system.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.Set;

@Entity
@Table(name = "tb_member")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Member extends AbstractAuditingEntity{
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;

    @Column( length = 50)
    private String firstname;

    @Column( length = 50)
    private String lastname;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Transient
    private String rePassword;

    @Column(unique = true)
    private String phone;

    @Column(unique = true, nullable = false, length = 50)
    private String email;

    private String description;

    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
    private Collection<Content> contents;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "tb_member_roles",
            joinColumns = {
                    @JoinColumn(name = "member_id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "role_id") })
    private Set<Role> roles;

}
