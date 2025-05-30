package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class DB {

    private static Connection conn = null;

    public static Connection getConnection(){

        if(conn == null){

            try{
                Properties properties = loadProperties();
                String url = properties.getProperty("dburl");
                conn = DriverManager.getConnection(url, properties);
                return conn;
            } catch (SQLException e){
                throw new DbException(e.getMessage());
            }
        }
        return conn;
    }

    public static void closeConnection(){

        if(conn != null){

            try {
                conn.close();
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }
    }

    private static Properties loadProperties(){

        try(FileInputStream fs = new FileInputStream("db.properties")){
            Properties properties = new Properties();
            properties.load(fs);
            return properties;

        } catch (IOException e) {
            throw new DbException(e.getMessage());
        }
    }

    public static void closeStatement(Statement st){

        try{
            if(st != null){
                st.close();
            }

        } catch(SQLException e){
            throw new DbException(e.getMessage());
        }
    }

    public static void closeResultset(ResultSet rs){

        try{
            if(rs != null){
                rs.close();
            }

        } catch(SQLException e){
            throw new DbException(e.getMessage());
        }
    }
}