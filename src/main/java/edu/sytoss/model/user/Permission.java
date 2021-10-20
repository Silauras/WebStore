package edu.sytoss.model.user;

public enum Permission {
    USER_ACCOUNT_READ("userAccount:read"),
    USER_ACCOUNT_WRITE("userAccount:write");

    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
