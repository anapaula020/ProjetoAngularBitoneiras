package br.unitins.tp1.util;

import io.quarkus.elytron.security.common.BcryptUtil;

public class HashUtil {
    public static String hash(String value) {
        return BcryptUtil.bcryptHash(value);
    }

    public static boolean verifyPassword(String rawPassword, String hashedPassword) {
        return BcryptUtil.matches(rawPassword, hashedPassword);
    }
}