package com.hotelreservation.dao;

import com.hotelreservation.dbutil.SQLHelper;

import java.sql.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 * @author humeishan
 * @version 1.0
 */
public class LoginService {
    public static boolean Login(String id, String password,int usertype) {
        Connection conn = null;
        CallableStatement cstmt = null;
        try {
            conn = SQLHelper.getConnection();
            cstmt = conn.prepareCall("{call VerifyLogin(?, ?, ?)}");

            // 设置输入参数
            cstmt.setString(1, id);
            cstmt.registerOutParameter(2, Types.VARCHAR);
            cstmt.setInt(3, usertype);

            // 执行存储过程
            boolean cnt=cstmt.execute();


            // 获取输出参数值
            String passwordParam = cstmt.getString(2);
            if(PasswordEncryptionService.checkPassword(password,passwordParam))
            {
                return true;
            }
            else
                return false;


        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            // 关闭资源
            try {
                if (cstmt != null) cstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    public static boolean createAccount(String username, String password) {


        // 密码加密
        String encryptedPassword = PasswordEncryptionService.hashPassword(password);

        // 将新用户数据存储到数据库
        try
        {

            Connection conn = SQLHelper.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO `user` (UserID,UserName,Password,Points,UserLevel) VALUES (null,?,?,0,'普通用户')");

            pstmt.setString(1, username);
            pstmt.setString(2, encryptedPassword);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean userExist(String username) {



        try
        {

            Connection conn = SQLHelper.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("select UserID from user where UserName=?");

            pstmt.setString(1, username);



            ResultSet resultSet = pstmt.executeQuery();
            if (resultSet.isBeforeFirst())return true;






            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public static int getHotelID(String username) {



        try
        {

            Connection conn = SQLHelper.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("select HotelID from hoteladmin where UserName=?");

            pstmt.setString(1, username);



            ResultSet resultSet = pstmt.executeQuery();
            resultSet.next();
            int hotelID=resultSet.getInt("HotelID");
            return hotelID;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }




    public static void main(String[] args) {
        //System.out.println(createAccount("li","12231"));
       // System.out.println(Login("neo","123",1));
        /*System.out.println(userExist("neo"));
        String sql = "SELECT hotel.ame,hotel. FROM Room WHERE RoomID NOT IN "
                + "(SELECT RoomID FROM orders WHERE ? BETWEEN CheckInDate AND CheckOutDate "
                + "OR ? BETWEEN CheckInDate AND (CheckOutDate + INTERVAL 1 DAY))  ";
        System.out.println(sql);*/
        System.out.println(getHotelID("wang"));
        ;
    }
}
