package com.dashboard.util;

import javax.enterprise.context.ApplicationScoped;

import org.mindrot.jbcrypt.BCrypt;
@ApplicationScoped
public class PasswordUtil {
    public static String hashPassword(String plainText) {
        return BCrypt.hashpw(plainText, BCrypt.gensalt());
    }

    public static boolean verifyPassword(String plainText, String hashed) {
        return BCrypt.checkpw(plainText, hashed);
    }
}
