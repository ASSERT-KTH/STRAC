package core.data_structures.postgreSQL;

import com.google.gson.Gson;
import core.LogProvider;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class PostgreInterface {

    static PostgreInterface instance;

    private PostgreInterface(){

    }

    public Connection connection;
    public String dbName;

    public static void setup(String host, int port, String dbName, String user, String password, boolean createDB) throws SQLException {
        instance = new PostgreInterface();

        instance.dbName = dbName;

        if(createDB){
            instance.connection = DriverManager.getConnection
                    (String.format("jdbc:postgresql://%s:%s/", host, port), user, password);

            instance.createDB();

            instance.connection = DriverManager.getConnection
                    (String.format("jdbc:postgresql://%s:%s/%s", host, port, dbName), user, password);

            instance.createTables();
        }else{
            instance.connection = DriverManager.getConnection
                    (String.format("jdbc:postgresql://%s:%s/%s", host, port, dbName), user, password);
        }



    }

    private void close() throws SQLException {
        this.connection.close();
    }

    public void dropDB() throws SQLException {

        Statement st = connection.createStatement();

        String dropTable = String.format("DROP DATABASE IF EXISTS %s", dbName);
        st.execute(dropTable);

        st.close();
    }

    public <T> T executeScalarQuery(String query, T _default){
        try {
            Statement st = connection.createStatement();

            ResultSet set = st.executeQuery(query);
            set.next();
            //st.close();
            return (T)set.getObject(1);

        } catch (SQLException e) {
            LogProvider.info(e.getMessage());
            return _default;
        }
    }

    public <T> T executeScalarQuery(String query){
        try {
            Statement st = connection.createStatement();

            ResultSet set = st.executeQuery(query);
            set.next();
            //st.close();

            return (T)set.getObject(1);

        } catch (SQLException e) {
            throw  new RuntimeException(e.getMessage());
        }
    }


    public <T> List<T> executeCollection(String query, Class<T> clazz){
        try {
            Statement st = connection.createStatement();


            ResultSet set = st.executeQuery(query);

            List<T> result = new ArrayList<>();

            while(set.next()){

                String b64 =  set.getString(1);
                 result.add(new Gson().fromJson(b64, clazz));
            }

            set.close();

            st.close();

            return result;

        } catch (SQLException e) {
            throw  new RuntimeException(e.getMessage());
        }

    }


    public void executeQuery(String query){
        try {
            Statement st = connection.createStatement();

            st.execute(query);
            st.close();

        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private void createTables() throws SQLException {

        Statement st = connection.createStatement();

        String createTable = "CREATE TABLE TRACE" +
                "(id serial PRIMARY KEY," +
                "name TEXT NOT NULL," +
                "value TEXT NOT NULL," +
                "map TEXT," +
                "index INT NOT NULL" +
                ")";

        st.executeUpdate(createTable);
        st.close();


        LogProvider.info("Table created succesfully");
    }

    private void createDB() throws SQLException {

        // Creating DATABASE;
        Statement st = connection.createStatement();

        String createDBSQL = String.format("CREATE DATABASE %s", dbName);

        st.execute(createDBSQL);
        st.close();

        LogProvider.info("Database created succesfully");

        // Creating trace table


    }

    public static PostgreInterface getInstance(){

        if(instance == null)
            throw new RuntimeException("You must call setup function first");

        return instance;
    }

}
