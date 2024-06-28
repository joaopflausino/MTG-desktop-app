package com.mycompany.projeto_mtg_faculdade.Controller;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

public class DatabaseConnectionController {

    private Connection connection = null;
    private static final String path = System.getProperty("user.dir");
    private static final File config_file = new File(path + "/src/bancodados/configuracaobd.properties");

    public DatabaseConnectionController() {
        createConnection(); // Attempt to create the connection when the instance is created
    }

    public boolean createConnection() {
        try {
            JDBCUtil.init(config_file);
            connection = JDBCUtil.getConnection();
            connection.setAutoCommit(false); // Set auto-commit to false for transaction management

            DatabaseMetaData dbMeta = connection.getMetaData();
            System.out.println("Database Name: " + dbMeta.getDatabaseProductName());
            System.out.println("Database Version: " + dbMeta.getDatabaseProductVersion());
            System.out.println("URL: " + dbMeta.getURL());
            System.out.println("Driver: " + dbMeta.getDriverName());
            System.out.println("Driver Version: " + dbMeta.getDriverVersion());
            System.out.println("User: " + dbMeta.getUserName());

            return true;
        } catch (ClassNotFoundException | IOException | SQLException e) {
            System.out.println("Connection creation failed: " + e);
        }
        return false;
    }

    public boolean closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                return true;
            } catch (SQLException e) {
                System.err.println("Error closing connection: " + e);
            }
        }
        return false;
    }

    public Connection getConnection() {
        if (connection == null) {
            System.err.println("Connection is null, attempting to recreate connection.");
            createConnection();
        }
        return connection;
    }
}
