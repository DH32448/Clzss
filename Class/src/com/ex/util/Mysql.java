package com.ex.util;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Mysql {

    private static final String URL = "jdbc:mysql://localhost:3305/mark";
    private static final String USER = "root";
    private static final String PASSWORD = "123456";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver loaded successfully.");
        } catch (ClassNotFoundException e) {
            System.err.println("Driver not found: " + e.getMessage());
        }
    }

    /**
     * 获取数据库连接
     * @return Connection 对象
     * @throws SQLException 如果连接失败
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    /**
     * 关闭资源
     * @param closeable 要关闭的资源
     */
//    public static void close(AutoCloseable closeable) {
//        if (closeable != null) {
//            try {
//                closeable.close();
//            } catch (Exception e) {
//                System.err.println("Failed to close resource: " + e.getMessage());
//            }
//        }
//    }

    /**
     * 执行查询操作
     * @param sql 查询语句
     * @return ResultSet 结果集
     * @throws SQLException 如果执行失败
     */
    public static ResultSet executeQuery(String sql) throws SQLException {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            return resultSet;
        } catch (SQLException e) {
            throw e;
        }
    }

    /**
     * 执行更新操作（INSERT, UPDATE, DELETE）
     * @param sql 更新语句
     * @return 受影响的行数
     * @throws SQLException 如果执行失败
     */
    public static int executeUpdate(String sql) throws SQLException {
        Connection connection = null;
        Statement statement = null;

        try {
            connection = getConnection();
            statement = connection.createStatement();
            return statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw e;
        }
    }

    /**
     * 执行带参数的更新操作（INSERT, UPDATE, DELETE）
     * @param sql 更新语句
     * @param params 参数数组
     * @return 受影响的行数
     * @throws SQLException 如果执行失败
     */
    public static int executeUpdateWithParams(String sql, Object[] params) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(sql);

            for (int i = 0; i < params.length; i++) {
                preparedStatement.setObject(i + 1, params[i]);
            }

            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw e;
        }
    }

    public static void main(String[] args) {
        try {
            // 执行查询
            ResultSet resultSet = executeQuery("SELECT * FROM t_clz");
            while (resultSet.next()) {
                String id = resultSet.getString("clzno");
                String name = resultSet.getString("clzname");
                System.out.println("clzno: " + id + ", clzname: " + name);
            }

        } catch (SQLException e) {
            System.err.println("SQL Exception: " + e.getMessage());
        }
    }
}

