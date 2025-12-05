package br.unitins.tp1.utils;

import io.quarkus.elytron.security.common.BcryptUtil;

public class PasswordHasher {
    public static void main(String[] args) {
        String userPassword = "userpassword";
        String adminPassword = "adminpassword";

        System.out.println("Hash for '" + userPassword + "': " + BcryptUtil.bcryptHash(userPassword));
        System.out.println("Hash for '" + adminPassword + "': " + BcryptUtil.bcryptHash(adminPassword));
    }
}