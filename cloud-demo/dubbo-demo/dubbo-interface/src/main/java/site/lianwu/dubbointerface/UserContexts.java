package site.lianwu.dubbointerface;

import lombok.Data;

public class UserContexts {

    private final static ThreadLocal<User> userThreadLocal = new ThreadLocal<>();

    public static void setUser(User user) {
        userThreadLocal.set(user);
    }

    public static User getUser() {
        return userThreadLocal.get();
    }

    public static void removeUser() {
        userThreadLocal.remove();
    }

    @Data
    public static class User {

        private Long userId;

        private String username;

    }

}
