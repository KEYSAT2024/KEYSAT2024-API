package keysat.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;



@Entity(name = "userRoles")
@Table(name = "user_roles")
@Getter
@Setter
public class UserRoles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "user_id",
            referencedColumnName = "user_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "user_roles_user_id_fkey")
    )
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "role_id",
            referencedColumnName = "role_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "user_roles_role_id_fkey")
    )
    private Role role;

    @Column(name = "username")
    private String username;
}