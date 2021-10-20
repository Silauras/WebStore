package edu.sytoss.model.user;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.*;

public enum Role {
    ADMIN(new HashSet<Permission>(){{add(Permission.USER_ACCOUNT_READ); add(Permission.USER_ACCOUNT_READ);}}),
    BANNED(new HashSet<Permission>(){{add(Permission.USER_ACCOUNT_READ); }}),
    CUSTOMER(new HashSet<Permission>(){{add(Permission.USER_ACCOUNT_READ); }}),
    MODERATOR(new HashSet<Permission>(){{add(Permission.USER_ACCOUNT_READ);}}),
    NON_AUTHORIZED(new HashSet<Permission>(){{add(Permission.USER_ACCOUNT_READ);}}),
    SELLER(new HashSet<Permission>(){{add(Permission.USER_ACCOUNT_READ);}}),
    SHOP_OWNER(new HashSet<Permission>(){{add(Permission.USER_ACCOUNT_READ);}});


    private final Set<Permission> permissions;

    Role(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public Set<GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> simpleGrantedAuthorities = new HashSet<>();
        for (Permission permission:getPermissions()) {
            simpleGrantedAuthorities.add(new SimpleGrantedAuthority(permission.getPermission()));
        }
        return simpleGrantedAuthorities;
    }

}

