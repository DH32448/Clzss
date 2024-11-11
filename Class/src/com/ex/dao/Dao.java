package com.ex.dao;

import com.ex.util.Mysql;

import java.lang.reflect.Field;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class Dao {
    private static Connection connection = null;

    static {
        try {
            connection = Mysql.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static int save(Object obj) {
        Class<?> clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();

        StringBuilder columns = new StringBuilder();
        StringBuilder values = new StringBuilder();
        Object[] params = new Object[fields.length];

        // 构建列名和值部分
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            field.setAccessible(true);
            columns.append(field.getName()).append(", ");
            values.append("?, ");
            try {
                Object value = field.get(obj);
                if (value instanceof Date) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    value = sdf.format(value);
                }
                params[i] = value;
            } catch (IllegalAccessException e) {
                throw new RuntimeException("无法访问字段: " + field.getName(), e);
            }
        }

        // 去掉最后一个逗号
        columns.setLength(columns.length() - 2);
        values.setLength(values.length() - 2);

        // 构建 SQL 语句
        String tableName = "t_" + clazz.getSimpleName().toLowerCase();
        String sql = "INSERT INTO " + tableName + " (" + columns.toString() + ") VALUES (" + values.toString() + ")";

        // 执行 SQL 语句
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            for (int i = 0; i < params.length; i++) {
                statement.setObject(i + 1, params[i]);
            }
            int rowsAffected = statement.executeUpdate();
            System.out.println("插入成功，影响行数: " + rowsAffected);
            return rowsAffected;
        } catch (SQLException e) {
            throw new RuntimeException("SQL 执行失败", e);
        }
    }
    public static Map<String, Object> findOne(Class clazz, String sql) {
        Map<String, Object> result = new HashMap<>();

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {

                Field[] fields = clazz.getDeclaredFields();
                for (Field field : fields) {
                    field.setAccessible(true); // 允许直接访问私有的成员变量
                    String columnName = field.getName();
                    Object columnValue = resultSet.getObject(columnName);
                    result.put(columnName, columnValue);
                }
            }
        } catch (Exception e ) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }


    public static Object findOne2(Class clazz, String str) {
        Object obj = null;
        String sql = "SELECT * FROM " + clazz.getSimpleName() + " WHERE " + str;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                obj = clazz.newInstance();
                Field[] fields = clazz.getDeclaredFields();
                for (Field field : fields) {
                    field.setAccessible(true); // 允许直接访问私有的成员变量
                    String columnName = field.getName();
                    Object columnValue = resultSet.getObject(columnName);
                    field.set(obj, columnValue);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return obj;
    }

    public static Map<String, Object> copyObj2Map(Object obj) {
        if (obj == null) {
            return null;
        }
        Map<String, Object> map = new HashMap<>();
        Class<?> clazz = obj.getClass();
        try {
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                String fieldName = field.getName();
                Object fieldValue = field.get(obj);
                map.put(fieldName, fieldValue);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    public static Object copyMap2Obj(Class clazz, Map<String, Object> map) {
        if (clazz == null || map == null) {
            return null;
        }
        try {
            Object obj = clazz.newInstance();
            Field[] fields = clazz.getDeclaredFields();

            for (Field field : fields) {
                field.setAccessible(true);
                String fieldName = field.getName();
                if (map.containsKey(fieldName)) {
                    Object value = map.get(fieldName);
                    field.set(obj, value);
                }
            }

            return obj;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
