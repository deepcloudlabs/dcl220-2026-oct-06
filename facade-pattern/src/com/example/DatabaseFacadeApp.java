package com.example;

import java.sql.SQLException;

public class DatabaseFacadeApp {

	public static void main(String[] args) throws SQLException {
		DatabaseFacade.getTableNames("jdbc:mysql://localhost:3306/world", "root", "Secret_123")
		              .forEach(System.out::println);

	}

}
