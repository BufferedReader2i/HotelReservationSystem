package com.hotelreservation.dao;

import com.hotelreservation.dbutil.SQLHelper;
import com.hotelreservation.entity.*;
import com.hotelreservation.entity.Room;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SearchService {



    public static List<Hotel> searchHotels(String name, String city, String area, int min, int max, LocalDate checkIn,LocalDate checkOut) throws SQLException {
        List<Hotel> hotels = new ArrayList<>();
        List<Hotel> hotelList = new ArrayList<>();
        String sql = "SELECT * FROM hotel join (select  distinct room.HotelID from room  where Price BETWEEN ? AND ?) as r on hotel.HotelID = r.HotelID WHERE 1=1"; // 开始基本的查询
        StringBuilder whereClause = new StringBuilder();

        // 动态添加查询条件
        if (name != null && !name.isEmpty()) {
            whereClause.append(" AND Name LIKE ?");
        }
        if (city != null && !city.isEmpty()) {
            whereClause.append(" AND City LiKE ?");
        }
        if (area != null && !area.isEmpty()) {
            whereClause.append(" AND Address LIKE ?");
            
        }



        try  {
            Connection conn = SQLHelper.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql + whereClause.toString());


            int index = 1;
            if (true) {
                pstmt.setBigDecimal(index++, new BigDecimal(min));
                pstmt.setBigDecimal(index++, new BigDecimal(max));
            }
            if (!name.isEmpty()) {
                pstmt.setString(index++, "%" + name + "%");
            }
            if (!city.isEmpty()) {
                pstmt.setString(index++, "%" + city + "%");
            }
            if (!area.isEmpty()) {
                pstmt.setString(index++, "%" + area + "%");
            }


            ResultSet rs = pstmt.executeQuery();
            if (rs.isBeforeFirst()) System.out.println("j");;

            // 处理查询结果
            while (rs.next()) {
                Hotel hotel = new Hotel(
                        rs.getInt("HotelID"),
                        rs.getString("Name"),
                        rs.getString("Address"),
                        rs.getString("City"),
                        rs.getString("District")


                );
                //System.out.println(hotel.toString());
                hotels.add(hotel);
            }
            for (Hotel hotel:hotels){
                int HotelId=hotel.getHotelID();

                 sql = "SELECT RoomID\n" +
                         "FROM room\n" +
                         "JOIN roomtype r ON r.RoomTypeID = room.RoomTypeID\n" +
                         "WHERE RoomID NOT IN (\n" +
                         "    SELECT RoomID\n" +
                         "    FROM orders\n" +
                         "    WHERE  orders.Status='Booked' AND (? BETWEEN CheckInDate AND CheckOutDate\n" +
                         "    OR ? BETWEEN CheckInDate AND (CheckOutDate + INTERVAL 1 DAY)\n" +"or ( CheckInDate BETWEEN ? AND ?) )"+
                         ")AND  room.HotelID=? ;";

                 pstmt = conn.prepareStatement(sql);
                pstmt.setDate(1, Date.valueOf(checkIn));
                pstmt.setDate(2, Date.valueOf(checkOut));
                pstmt.setDate(3, Date.valueOf(checkIn));
                pstmt.setDate(4, Date.valueOf(checkOut));
                pstmt.setInt(5,HotelId);
                ResultSet rs2 = pstmt.executeQuery();
                if (rs2.isBeforeFirst())hotelList.add(hotel);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return hotelList;
    }

    public static List<Room> searchRooms(int Hotelid) {
        // SQL查询
        String sql = "SELECT\n" +
                "    \n" +
                "   distinct rt.Name AS name,\n" +
                "    rt.Description AS description,\n" +
                "    r.Price,\n rt.occupancy \n" +
                "FROM room r\n" +
                "JOIN roomtype rt ON r.RoomTypeID = rt.RoomTypeID\n" +
                "JOIN hotel h ON r.HotelID = h.HotelID\n" +
                "where r.HotelID= ?;";
        List<Room> rooms=new ArrayList<>();

        try (Connection conn = SQLHelper.getConnection();

             ) {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1,Hotelid);

            ResultSet rs = pstmt.executeQuery();


            // 将查询结果填充到TableView
            while (rs.next()) {
                Room room = new Room(
                        rs.getString("Name"),
                        rs.getString("Description"),

                        rs.getDouble("price"),
                        rs.getInt("occupancy")
                );

                rooms.add(room);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rooms;
    }
    public static List<Integer> searchAvabileRooms(LocalDate checkIn,LocalDate checkOut, int Hotelid,String RoomName) {
        // SQL查询
        String sql = "SELECT RoomID\n" +
                "FROM room\n" +
                "JOIN roomtype r ON r.RoomTypeID = room.RoomTypeID\n" +
                "WHERE RoomID NOT IN (\n" +
                "    SELECT RoomID\n" +
                "    FROM orders\n" +
                "    WHERE ? BETWEEN CheckInDate AND CheckOutDate\n" +
                "    OR ? BETWEEN CheckInDate AND (CheckOutDate + INTERVAL 1 DAY)\n" +"or ( CheckInDate BETWEEN ? AND ?)"+
                ")AND  room.HotelID=? AND r.Name=?;";
        List<Integer> rooms=new ArrayList<>();
        //System.out.println(sql);

        try (Connection conn = SQLHelper.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
        ) {

            //System.out.println(Date.valueOf(checkIn));
            pstmt.setDate(1, Date.valueOf(checkIn));
            pstmt.setDate(2, Date.valueOf(checkOut));
            pstmt.setDate(3, Date.valueOf(checkIn));
            pstmt.setDate(4, Date.valueOf(checkOut));
            pstmt.setInt(5,Hotelid);
            pstmt.setString(6,RoomName);

            ResultSet rs = pstmt.executeQuery();


            // 将查询结果填充到TableView
            while (rs.next()) {

                int id=rs.getInt("RoomID");
                System.out.println(id);

                rooms.add(id);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rooms;
    }
    public static String getuserLevel(String username) {



        try
        {

            Connection conn = SQLHelper.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("select UserLevel from user where UserName=?");

            pstmt.setString(1, username);



            ResultSet resultSet = pstmt.executeQuery();
            resultSet.next();
            return resultSet.getString(1);








        } catch (Exception e) {
            e.printStackTrace();

        }
        return "";
    }
    public static List<Order> getUserOrders(String userName) throws SQLException {
        List<Order> ordersList = new ArrayList<>();
        Connection conn = SQLHelper.getConnection();
        CallableStatement stmt = null;

        try {


            stmt = conn.prepareCall("{CALL GetUserOrders(?)}");

            stmt.setString(1, userName);

            stmt.execute();

            ResultSet rs = stmt.getResultSet();
            while (rs.next()) {
                Order order = new Order();
                order.setorderID(rs.getInt("OrderID"));
                order.setHotelName(rs.getString("HotelName"));
                order.setRoomTypeName(rs.getString("RoomTypeName"));
                order.setDescription(rs.getString("Description"));
                order.setCheckInDate(rs.getDate("CheckInDate"));
                order.setCheckOutDate(rs.getDate("CheckOutDate"));
                order.setTotalPrice(rs.getDouble("TotalPrice"));
                order.setStatus(rs.getString("Status"));
                order.setComment(rs.getString("Comment"));
                order.setOrderDate(rs.getTimestamp("Date"));
               // System.out.println(order.getOrderDate());

                ordersList.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close resources
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return ordersList;
    }
    public static List<Order> getOrders(int hotelID) throws SQLException {
        List<Order> ordersList = new ArrayList<>();
        Connection conn = SQLHelper.getConnection();
        CallableStatement stmt = null;

        try {


            stmt = conn.prepareCall("{CALL GetOrders(?)}");

            stmt.setInt(1, hotelID);

            stmt.execute();

            ResultSet rs = stmt.getResultSet();
            while (rs.next()) {
                Order order = new Order();
                order.setorderID(rs.getInt("OrderID"));
               // System.out.println(order.getorderID());
                order.setHotelName(rs.getString("HotelName"));
                order.setRoomTypeName(rs.getString("RoomTypeName"));
                order.setDescription(rs.getString("Description"));
                order.setCheckInDate(rs.getDate("CheckInDate"));
                order.setCheckOutDate(rs.getDate("CheckOutDate"));
                order.setTotalPrice(rs.getDouble("TotalPrice"));
                order.setStatus(rs.getString("Status"));
                order.setComment(rs.getString("Comment"));
                order.setOrderDate(rs.getTimestamp("Date"));
                 //System.out.println(order.getOrderDate());

                ordersList.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close resources
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return ordersList;
    }
    public static List<Order> getALLOrders() throws SQLException {
        List<Order> ordersList = new ArrayList<>();
        Connection conn = SQLHelper.getConnection();
        CallableStatement stmt = null;

        try {


            stmt = conn.prepareCall("{CALL GetALLOrders()}");



            stmt.execute();

            ResultSet rs = stmt.getResultSet();
            while (rs.next()) {
                Order order = new Order();
                order.setorderID(rs.getInt("OrderID"));
                //System.out.println(order.getorderID());
                order.setHotelName(rs.getString("HotelName"));
                order.setRoomTypeName(rs.getString("RoomTypeName"));
                order.setDescription(rs.getString("Description"));
                order.setCheckInDate(rs.getDate("CheckInDate"));
                order.setCheckOutDate(rs.getDate("CheckOutDate"));
                order.setTotalPrice(rs.getDouble("TotalPrice"));
                order.setStatus(rs.getString("Status"));
                order.setComment(rs.getString("Comment"));
                order.setOrderDate(rs.getTimestamp("Date"));
                //System.out.println(order.getOrderDate());

                ordersList.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close resources
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return ordersList;
    }


    public static void editOrderStatus(String status,int OrderID) {



        try
        {

            Connection conn = SQLHelper.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("update orders  set Status=? where OrderID=?");

            pstmt.setString(1, status);
            pstmt.setInt(2,OrderID);



            int resultSet = pstmt.executeUpdate();









        } catch (Exception e) {
            e.printStackTrace();

        }

    }
    public static void editOrderCommit(String commit,int OrderID) {



        try
        {

            Connection conn = SQLHelper.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("update orders  set Comment=? where OrderID=?");

            pstmt.setString(1, commit);
            pstmt.setInt(2,OrderID);



            int resultSet = pstmt.executeUpdate();









        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    public static List<Statics> getData(String procedureName, LocalDate begin, LocalDate end) {
        Connection conn = null;
        CallableStatement cstmt = null;
        try {
            conn = SQLHelper.getConnection();
            cstmt = conn.prepareCall("{call "+procedureName+"( ?, ?)}");

            // 设置输入参数
            cstmt.setDate(1, Date.valueOf(begin));
            cstmt.setDate(2, Date.valueOf(end));
            List<Statics> list =new ArrayList<>();


            // 执行存储过程
            boolean cnt=cstmt.execute();
            ResultSet rs = cstmt.getResultSet();
            while (rs.next()) {
                Statics data=new Statics();
                data.setName(rs.getString("dataName"));
                data.setValue(rs.getInt("Count"));
               // System.out.println(data.getName());

                list.add(data);
            }
            return list;





        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            // 关闭资源
            try {
                if (cstmt != null) cstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    public static List<Statics> getData2(String procedureName) {
        Connection conn = null;
        CallableStatement cstmt = null;
        try {
            conn = SQLHelper.getConnection();
            cstmt = conn.prepareCall("{call "+procedureName+"( )}");

            // 设置输入参数

            List<Statics> list =new ArrayList<>();


            // 执行存储过程
            boolean cnt=cstmt.execute();
            ResultSet rs = cstmt.getResultSet();
            while (rs.next()) {
                Statics data=new Statics();
                data.setName(rs.getString("dataName"));
                data.setValue(rs.getInt("Count"));
                // System.out.println(data.getName());

                list.add(data);
            }
            return list;





        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            // 关闭资源
            try {
                if (cstmt != null) cstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    public static void main(String[] args) throws SQLException {
        //System.out.println(        getuserLevel("neo"));
       /* String ageString="18";
        if (ageString == null || ageString.trim().isEmpty()||ageString.matches("^[+]?[1-9]\\\\d*$")) {

            System.out.println(false);
        }*/
       // getUserOrders("neo");
        //System.out.println(searchAvabileRooms(LocalDate.of(2024,7,2),LocalDate.of(2024,7,3),2,"单人间"));
        //getOrders(2);
       /* String proName = null;
        List<Statics> list =new ArrayList<>();
        LocalDate begin=LocalDate.of(2024,1,1);
        LocalDate end=LocalDate.of(2024,12,31);


        list= SearchService.getData("CountCitiesByOrder",begin,end);
        System.out.println(list.size());*/
        List<Statics> datas = SearchService.getData2("CountGuestsByGender" );
        for (Statics data:datas){
            System.out.println(data.getValue());
        }

    }
}

