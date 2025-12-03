// src/main/java/br/unitins/tp1/utils/HashUtil.java
package br.unitins.tp1.utils;

import io.quarkus.elytron.security.common.BcryptUtil; // Import BcryptUtil

public class HashUtil {

    // Method to hash a plaintext value using Bcrypt
    public static String hash(String value) {
        return BcryptUtil.bcryptHash(value);
    }

    // Method to verify a plaintext password against a Bcrypt hashed password
    public static boolean verifyPassword(String rawPassword, String hashedPassword) {
        // BcryptUtil.matches() handles the verification directly
        return BcryptUtil.matches(rawPassword, hashedPassword);
    }
}