package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface BridgePatternInJdbc {
	public static void main(String[] args) throws SQLException {
		// DriverManager -- getConnection --> Connection -- prepareStatement --> PreparedStatement -- executeQuery --> ResultSet
		//             Bridge            Bridge                 Bridge                                   Bridge
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/world?useSSL=false&allowPublicKeyRetrieval=true", "root",
				"Secret_123");
		System.out.println(con.getClass());
		PreparedStatement stmnt = con.prepareStatement("select distinct continent from country");
		System.out.println(stmnt.getClass());
		ResultSet resultSet = stmnt.executeQuery();
		System.out.println(resultSet.getClass());
		
	}
}
