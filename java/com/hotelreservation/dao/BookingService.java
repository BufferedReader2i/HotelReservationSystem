package com.hotelreservation.dao;

import com.hotelreservation.dbutil.SQLHelper;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.List;

/**
 * @author humeishan
 * @version 1.0
 */
public class BookingService {
    public static void bookRoom(String UserName, LocalDate checkIn, LocalDate checkOut, int totalPrice, String booked, LocalDate now, List<Integer> avabileRooms,int hotelID,int bookingCount) {
        // 定义SQL插入语句
        String sql = "INSERT INTO orders (UserID, RoomID,UserName, CheckInDate, CheckOutDate, TotalPrice, Status, Comment, Date,HotelID) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, NOW(),?)";

        try (Connection connection = SQLHelper.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            // 设置预处理语句的参数
            for (int i = 0; i < bookingCount; i++) {
                preparedStatement.setInt(1, getUserIdFromUserName(UserName)); // 根据用户名获取用户ID
                preparedStatement.setInt(2, avabileRooms.get(i)); // 根据入住日期获取房间ID
                preparedStatement.setString(3, UserName);
                preparedStatement.setDate(4, java.sql.Date.valueOf(checkIn));
                preparedStatement.setDate(5, java.sql.Date.valueOf(checkOut));
                preparedStatement.setBigDecimal(6, BigDecimal.valueOf(totalPrice*1.0/bookingCount));
                preparedStatement.setString(7, booked);
                preparedStatement.setString(8, "No Comment"); // 初始评论
                preparedStatement.setInt(9,hotelID); // 初始评论
                System.out.println(i);
                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Order created successfully.");
                } else {
                    System.out.println("Order creation failed.");
                }
            }



            // 执行插入操作

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static int getRoomIdFromCheckInDate(LocalDate checkIn) {
        return 0;
    }

    private static int getUserIdFromUserName(String userName) {
        String sql = "SELECT UserID FROM User WHERE Username = ?";
        try (Connection connection = SQLHelper.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, userName);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("UserID");
                } else {
                    // 如果没有找到用户，可以选择抛出异常或者返回一个特定的值（例如-1）
                    throw new IllegalArgumentException("User not found with username: " + userName);
                }
            }
        } catch (SQLException e) {
            // 处理数据库异常，例如打印日志或返回一个错误信息
            e.printStackTrace();
            throw new RuntimeException("Database error occurred while fetching user ID.", e);
        }
    }

    public static void main(String[] args) throws SQLException {
        //System.out.println(getUserIdFromUserName("neo"));
        //System.out.println(getOrderID());
      /*  int orderID = 2; // 假设的订单ID
        String name = "John Doe";
        String idNumber = "123456789012345678";
        Integer age = 30;
        String gender = "Male";
        String occupation = "Engineer";
        String educationLevel = "Master";
        String incomeStatus = "High";*/

        //addGuests(orderID, name, idNumber, age, gender, occupation, educationLevel, incomeStatus);
    }



    public static int getOrderID() throws SQLException {
        ResultSet resultSet=SQLHelper.executeQuery("SELECT MAX(OrderID) FROM orders;");
        resultSet.next();
        int id=resultSet.getInt(1);
        return id;

    }
    public static int getRoomTypeID() throws SQLException {
        ResultSet resultSet=SQLHelper.executeQuery("SELECT MAX(RoomTypeID) FROM roomType;");
        resultSet.next();

        int id=resultSet.getInt(1);
        System.out.println(id);
        return id;

    }

    public static void addGuests(int orderID, String name, String idNumber,
                                 Integer age, String gender, String occupation,
                                 String educationLevel, String incomeStatus) {
        // 定义SQL插入语句
        String sql = "INSERT INTO guest (OrderID, Name, IDNumber, Age, Gender, Occupation, EducationLevel, IncomeStatus) "
                + "VALUES (?, ?,?, ?, ?, ?, ?, ?)";

        try (Connection connection = SQLHelper.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, orderID);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, idNumber);
            preparedStatement.setInt(4, age != null ? age : 0); // 使用0或者默认值代替null
            preparedStatement.setString(5, gender != null ? gender : "");
            preparedStatement.setString(6, occupation != null ? occupation : "");
            preparedStatement.setString(7, educationLevel != null ? educationLevel : "");
            preparedStatement.setString(8, incomeStatus != null ? incomeStatus : "");
            int rowsAffected = preparedStatement.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addRooms(String name, String description, int hotelID , int occupancy, double price, int cnt) {
        // 定义SQL插入语句
        String sql = "INSERT INTO roomtype (name, description, hotelid, occupancy) "
                + "VALUES (?, ?,?, ?)";

        try (Connection connection = SQLHelper.getConnection();
            ) {

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
           preparedStatement.setString(1,name);
           preparedStatement.setString(2,description);
           preparedStatement.setInt(3,hotelID);
           preparedStatement.setInt(4,occupancy);
            int rowsAffected = preparedStatement.executeUpdate();

             sql = "INSERT INTO room ( roomtypeid, hotelid, price) "
                    + "VALUES (?,?, ?)";
            preparedStatement = connection.prepareStatement(sql);

            for (int i = 0; i < cnt; i++) {
                int rooTypeID=getRoomTypeID();
                preparedStatement.setInt(1,rooTypeID);
                preparedStatement.setInt(2,hotelID);
                preparedStatement.setDouble(3,price);

                rowsAffected = preparedStatement.executeUpdate();
            }





        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
