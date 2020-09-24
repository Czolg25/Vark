package com.Czolg.Top;

import com.Czolg.Top.Data.Data;
import com.Czolg.Top.Data.SQL;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main extends JavaPlugin {
    private static Data data = new Data();
    private SQL sql = new SQL(this);
    private static Scoreboard scoreboard = new Scoreboard();

    private Connection connection;
    private String host = "localhost";
    private String port = "3306";
    private String user = "root";
    private String password = "qq";
    private String database = "stats";
    public String table = "ranked";

    public static Scoreboard getScoreboard() {
        return scoreboard;
    }

    public static Data getData() {
        return data;
    }

    @Override
    public void onEnable() {
        try{
            mySQLSetup();
            sql.getData();
            Bukkit.getPluginManager().registerEvents(new Listener(this), this);
            refreshTop();
        }catch (NullPointerException e){
            System.out.println("[TOP] MySQL Error");
            System.out.println("Nie mozna polaczyc sie z baza danych");
        }
    }

    public void mySQLSetup() throws NullPointerException{
        try{
            synchronized (this){
                if(getConnection()!=null&&!getConnection().isClosed()) {
                    return;
                }else {
                    Class.forName("com.mysql.jdbc.Driver");
                    setConnection(
                            DriverManager.getConnection("jdbc:mysql://" + this.host + "/" + this.database,
                                    this.user, this.password)
                    );
                    System.out.println("[Top] MySQL connected");
                }
            }
        }catch (SQLException exception){
            exception.printStackTrace();
        }catch (ClassNotFoundException exception){
            exception.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    private void refreshTop(){
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                data.updateTop();
                scoreboard.refresh();
            }
        },0,2000);
    }
}
