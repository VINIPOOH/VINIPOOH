package entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class User {
    private Long id;
    private String email;
    private RoleType roleType;
    private String password;
    private Long userMoneyInCents;
    private List<Delivery> waysWhereThisLocalityIsSend;
    private List<Delivery> waysWhereThisLocalityIsGet;

    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;


}
