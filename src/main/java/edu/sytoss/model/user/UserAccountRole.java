package edu.sytoss.model.user;

public enum UserAccountRole {
    NON_AUTHORIZED("NON_AUTHORIZED"),
    CUSTOMER("CUSTOMER"),
    SHOP_OWNER("SHOP_OWNER"),
    SELLER("SELLER"),
    MODERATOR("MODERATOR"),
    ADMIN("ADMIN"),
    BANNED("BANNED");

    private final String url;


    UserAccountRole(String userAccountRole) {
        this.url = userAccountRole;
    }

    public String getUserAccountRole() {
        return url;
    }
}

