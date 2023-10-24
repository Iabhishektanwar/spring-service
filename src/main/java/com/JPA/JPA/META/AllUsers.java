package com.JPA.JPA.META;

import lombok.Getter;
import org.hibernate.DuplicateMappingException;

import java.util.HashMap;
import java.util.Map;

import static org.hibernate.DuplicateMappingException.Type.PROPERTY;

public enum AllUsers {
    ABHISHEK(1, 27, "Abhishek Tanwar", true),
    PRIYANKA(2, 27, "Priyanka Tanwar", true),
    RUCHI(3, 26, "Ruchi Tanwar", false),
    PRADHYUMN(4, 16, "Pradhyumn Tanwar", false),
    MANJU(5, 51, "Manju Devi", true),
    PRADEEP(6, 52, "Pradeep Kumar", true),
    SHYAMWATI(7, 78, "Shyam Wati", true);

    @Getter
    public static final Map<Integer, AllUsers> users = new HashMap<>();

    static {
        for(AllUsers user : AllUsers.values()) {
            if(users.get(user.id) == null) {
                users.put(user.id, user);
            } else {
                throw new DuplicateMappingException(PROPERTY, user.name);
            }
        }
    }

    @Getter
    private final int id;
    @Getter
    private final int age;
    @Getter
    private final String name;
    @Getter
    private final Boolean isMarried;

    private AllUsers(int id, int age, String name, boolean isMarried) {
        this.id = id;
        this.age = age;
        this.name = name;
        this.isMarried = isMarried;
    }
}
