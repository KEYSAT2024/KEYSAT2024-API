package KEYSAT.Auth;

import jakarta.persistence.*;

@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long role_id;

    private String name;

    // Getters and setters
    public Long getId() {
        return role_id;
    }

    public void setId(Long id) {
        this.role_id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "authority")
    private String authority;

    // Constructors
    public Role() {
    }

    public Role(String username, String authority) {
        this.name = username;
        this.authority = authority;
    }
}