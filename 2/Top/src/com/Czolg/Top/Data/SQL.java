package com.Czolg.Top.Data;


import com.Czolg.Top.Data.Data;
import com.Czolg.Top.Main;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQL {
    private Main plugin;
    private Data data = Main.getData();

    public SQL(Main plugin){
        this.plugin = plugin;
    }

    public void newPlayer(Player p){
        try{
            String question = "INSERT INTO `"+plugin.table+"` (`uuid`, `nick`, `klasa`, `kills`, `deaths`) VALUES ('"+p.getUniqueId()+"','"+p.getName()+"','Brak',0,0);";
            plugin.getConnection().createStatement().executeUpdate(question);
            data.setKlasa(p.getName(),"Brak");
            data.setUuid(p.getName(),p.getUniqueId()+"");
            data.setKills(p.getName(),0);
            data.setDeaths(p.getName(),0);
            data.addKlassToArray("Brak");
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public void update(String name,String set){
        String question = "UPDATE `\"+plugin.table+\"` SET "+set+" WHERE nick = "+name+";";
        try {
            plugin.getConnection().createStatement().executeUpdate(question);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
    public void getData(){
        try{
            PreparedStatement statement = plugin.getConnection().prepareStatement("SELECT * FROM "+plugin.table);
            ResultSet result = statement.executeQuery();
            while (result.next()){
                String uuid = result.getString(1);
                String name = result.getString(2);
                String klasa = result.getString(3);
                int kills = result.getInt(4);
                int deaths = result.getInt(5);
                data.setKlasa(name,klasa);
                data.setDeaths(name,deaths);
                data.setKills(name,kills);
                data.setUuid(name,uuid);
                data.addKlassToArray(klasa);
                data.addPlayersToArray(name);
                data.addKlassToArray(klasa);
            }
        }catch (SQLException exception){
            exception.printStackTrace();
        }
        data.updateTop();
    }
}
