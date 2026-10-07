package com.example;

import java.sql.DriverManager;

public class StudyBridgePattern {

	public static void main(String[] args) throws Exception {
		// DriverManager --> Connection --> PreparedStatement --> ResutSet
		//              Bridge         Bridge                Bridge
		var connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/world", "root", "Secret_123");
		System.err.println(connection.getClass());
		var statement = connection.prepareStatement("select * from country");
		System.err.println(statement.getClass());
		var resultSet = statement.executeQuery();
		System.err.println(resultSet.getClass());
		while(resultSet.next()) {
			
		}
	}

}
