package com.order.processing.domain;

public final class UserBuilder {
    private final User user;

    private UserBuilder() {
        user = new User();
    }

    public static UserBuilder builder() {
        return new UserBuilder();
    }

    public UserBuilder userId(Integer userId) {
        this.user.setUserId(userId);
        return this;
    }

    public UserBuilder name(String name) {
        this.user.setName(name);
        return this;
    }

    public User build() {
        return user;
    }
}
