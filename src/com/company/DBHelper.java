package com.company;

import javafx.scene.control.Tab;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by DBJ on 18-02-2015.
 */
public class DBHelper {
    private String host = "62.198.56.192";
    private String port = "3306";
    private String User = "daniel";
    private String Password = "abcd1234";
    private String db = "daniel";
    private String ConString = "jdbc:mysql://" + host + ":" + port + "/" + db;
    private String TableName = "Lynspitti";

    private boolean CheckDatabaseSetup(Connection con){
        try {
            DatabaseMetaData md = con.getMetaData();
            Boolean TableExists = false;
            ResultSet rs = md.getTables(null, null, "%", null);
            while (rs.next()) {
                String tablename = rs.getString("TABLE_NAME");
                if (tablename.equals(TableName)) {
                    TableExists = true;
                    if (!hasColumn(con,"Player_Name")){
                        CreateColumn(con, "Player_Name","VARCHAR(255)");
                    }
                    if (!hasColumn(con,"Player_Level")){
                        CreateColumn(con, "Player_Level","INTEGER");
                    }
                    if (!hasColumn(con,"Player_Strength")){
                        CreateColumn(con, "Player_Strength","INTEGER");
                    }
                    if (!hasColumn(con,"Player_DefensePower")){
                        CreateColumn(con, "Player_DefensePower","INTEGER");
                    }
                    if (!hasColumn(con,"Player_Intelligence")){
                        CreateColumn(con, "Player_Intelligence","INTEGER");
                    }
                    if (!hasColumn(con,"Player_Agility")){
                        CreateColumn(con, "Player_Agility","INTEGER");
                    }
                    if (!hasColumn(con,"Player_Experience")){
                        CreateColumn(con, "Player_Experience","INTEGER");
                    }
                    if (!hasColumn(con,"Player_CurrentHealth")){
                        CreateColumn(con, "Player_CurrentHealth","INTEGER");
                    }
                }
            }
            if (!TableExists){
                CreateTable(con);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean hasColumn(Connection con, String columnName) throws SQLException {
        Statement statement = con.createStatement();
        ResultSet results = statement.executeQuery("SELECT * FROM " +TableName);
        // Get resultset metadata
        ResultSetMetaData metadata = results.getMetaData();
        int columnCount = metadata.getColumnCount();
        // Get the column names; column indices start from 1

        for (int x = 1; x <= columnCount; x++) {
            String Name = metadata.getColumnName(x);
            if (columnName.equals(Name)) {
                return true;
            }
        }
        return false;
    }

    public Connection GetConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(ConString,User,Password);
            CheckDatabaseSetup(con);
            return con;
        }
        catch (SQLException e){
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ResultSet Select(String Select) {
        return Select(Select,null,null);
    }
    public ResultSet Select(String Select, String[] Something, String[] Equals){
        Connection con = GetConnection();
        return Select(con,Select,Something,Equals);
    }
    public ResultSet Select(Connection con, String Select, String[] Something, String[] Equals){
        try{
            String StatementString = "SELECT " +Select+ " FROM " + TableName;
            if (Something != null && Equals != null){
                StatementString += " WHERE ";
                for (int i = 0; i < Something.length; i++) {
                    if (i > 0)StatementString += " AND ";
                    StatementString += "`"+Something[i]+ "`"+ " = ?";
                }
            }
            PreparedStatement statement = con.prepareStatement(StatementString);
            if (Something != null && Equals != null){
                for (int i = 0; i < Something.length; i++) {
                    //statement.setString(x,Something[i]);
                    statement.setString(i+1,Equals[i]);
                }
            }
            return statement.executeQuery();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public void Update(){

    }

    public void CreateColumn(Connection con, String Column, String ColumnType){
        try {
            Statement st = con.createStatement();
            st.executeUpdate("ALTER TABLE " + TableName + " ADD " + Column + " " + ColumnType);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void CreateTable(Connection con){
        try {
            Statement stmt = con.createStatement();

            String sql = "CREATE TABLE " + TableName +
                    "(Player_Name VARCHAR(255) not NULL, " +
                    " Player_Level VARCHAR(255) NOT NULL, " +
                    " Player_Strength VARCHAR(255) NOT NULL, " +
                    " Player_DefensePower VARCHAR(255) NOT NULL, " +
                    " Player_Intelligence VARCHAR(255) NOT NULL, " +
                    " Player_Agility VARCHAR(255) NOT NULL, " +
                    " Player_Experience VARCHAR(255) NOT NULL, " +
                    " Player_CurrentHealth VARCHAR(255) NOT NULL, " +
                    " PRIMARY KEY ( Player_Name ))";

            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void Insert(String[] Something, String[] Equals){
        Connection con = GetConnection();
        Insert(con, Something, Equals);
    }
    public void Insert(Connection con, String[] Something, String[] Equals){
        String insertTableSQL = "INSERT INTO " + TableName + "(";
        try {
            if (Something != null && Equals != null){
                String Values = " VALUES(";
                for (int i = 0; i < Something.length; i++) {
                    if (i != 0) {
                        insertTableSQL += ", ";
                        Values += ", ";
                    }
                    insertTableSQL += "`" + Something[i] + "`";
                    Values += "?";
                }
                insertTableSQL += ") " + Values + ")";
            }
            PreparedStatement preparedStatement = con.prepareStatement(insertTableSQL);
            if (Something != null && Equals != null){
                for (int i = 0; i < Something.length; i++) {
                    //preparedStatement.setString((i+1),Something[i]);
                    preparedStatement.setString(/*Something.length+*/(i+1),Equals[i]);
                }
            }
            // execute insert SQL stetement
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
