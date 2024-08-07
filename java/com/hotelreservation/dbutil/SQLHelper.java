package com.hotelreservation.dbutil;

import java.sql.*;
import java.time.LocalDate;
import java.util.StringJoiner;

public class SQLHelper {
	private static String driver = "com.mysql.jdbc.Driver";
	private static String url = "jdbc:mysql://localhost:3305/hotelreservationsystem?useSSL=false&serverTimezone=UTC";
	private static String user = "root", pwd = "123456";
	private static Connection conn = null;



	
	public static void closeConnection() {
		try {
			if (conn != null && !conn.isClosed())
				conn.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/* 执行select语句 */
	public static ResultSet executeQuery(String sql) {
		ResultSet rs = null;
		try {
			conn = DriverManager.getConnection(url, user, pwd);
			Statement cmd = conn.createStatement();
			rs = cmd.executeQuery(sql);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return rs;
	}

	/* 执行更新操作 */
	public static int executeUpdate(String sql) {
		int r = 0;
		try {
			Connection conn = DriverManager.getConnection(url, user, pwd);
			Statement cmd = conn.createStatement();
			r = cmd.executeUpdate(sql);
			conn.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return r;
	}

	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(url, user,pwd);
	}

	public static void callStoredProcedure(String procedureName, Object... params) throws SQLException {
		String callString = "{call " + procedureName + "(" + getParamsList(params) + ")}";
		try (Connection conn = getConnection();
			 CallableStatement cstmt = conn.prepareCall(callString)) {
			setParams(cstmt, params);
			cstmt.execute();
		}
	}

	private static String getParamsList(Object... params) {
		if (params == null || params.length == 0) {
			return "";
		}
		StringJoiner sj = new StringJoiner(",");
		for (Object param : params) {
			sj.add("?");
		}
		return sj.toString();
	}

	private static void setParams(CallableStatement cstmt, Object... params) throws SQLException {
		for (int i = 0; i < params.length; i++) {
			if (params[i] instanceof String) {
				cstmt.setString(i + 1, (String) params[i]);
			} else if (params[i] instanceof Integer) {
				cstmt.setInt(i + 1, (Integer) params[i]);
			} else if (params[i] instanceof Double) {
				cstmt.setDouble(i + 1, (Double) params[i]);
			} else if (params[i] instanceof LocalDate) {
				cstmt.setDate(i + 1, Date.valueOf((LocalDate) params[i]));
			}
		}
	}

	public static void closeResource(ResultSet rs, Statement stmt, Connection conn) {
		try {
			if (rs != null) rs.close();
			if (stmt != null) stmt.close();
			if (conn != null) conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}




	public static int executeQuery(String sql, Object... params) throws SQLException {

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			conn = DriverManager.getConnection(url, user, pwd);
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "student");

           /* // 设置查询参数（如果有）
            if (params != null && params.length > 0) {
                for (int i = 0; i < params.length; i++) {
                    pstmt.setObject(i + 1, params[i]);
                }
            }*/

			rs = pstmt.executeQuery();

			// 处理查询结果
			while (rs.next()) {
				String s =rs.getString(1);
				System.out.println(s);


			}
		} finally {
			// 确保资源被关闭
			closeResource(rs, pstmt, conn);
		}

		return 0;
	}
}
