package me.refluxo.cloudapi.common;

import me.refluxo.cloudapi.common.mysql.MySQLService;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthHandler {

    private static MySQLService mysql;

    public static void init() {
        mysql = new MySQLService();
        mysql.executeUpdate("CREATE TABLE IF NOT EXISTS auth(uuid TEXT,isOnline BOOLEAN);");
    }

    private void addToList(String uuid) {
        if(!contains(uuid)) {
            mysql.executeUpdate("INSERT INTO auth(uuid,isOnline) VALUES ('" + uuid + "',false);");
        }
    }

    public boolean canJoin(String uuid) {
        if(!contains(uuid)) {
            addToList(uuid);
        }
        ResultSet rs = mysql.getResult("SELECT * FROM auth WHERE uuid = '" + uuid + "';");
        try {
            if(rs.next()) {
                return rs.getBoolean("isOnline");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void setOnline(String uuid, boolean online) {
        if(!contains(uuid)) {
            addToList(uuid);
        }
        mysql.executeUpdate("UPDATE auth SET isOnline = " + online +" WHERE uuid = '" + uuid + "';");
    }

    @SuppressWarnings("all")
    private boolean contains(String uuid) {
        ResultSet rs = mysql.getResult("SELECT * FROM auth WHERE uuid = '" + uuid + "';");
        try {
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
