package com.hotelreservation.dao;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordEncryptionService {

    /**
     * 哈希密码。
     *
     * @param plainTextPassword 明文密码
     * @return 哈希后的密码
     */
    public static String hashPassword(String plainTextPassword) {
        // 原始密码进行哈希处理
        return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt());
    }

    /**
     * 验证哈希后的密码与原始密码是否匹配。
     *
     * @param plainTextPassword 明文密码
     * @param hashedPassword 哈希后的密码
     * @return 验证结果
     */
    public static boolean checkPassword(String plainTextPassword, String hashedPassword) {
        // 验证明文密码与哈希密码是否匹配
        return BCrypt.checkpw(plainTextPassword, hashedPassword);
    }
}