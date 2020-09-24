package com.Czolg.Top;

import com.Czolg.Top.Data.Data;
import com.Czolg.Top.Data.SQL;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class Listener implements org.bukkit.event.Listener {
    private Main plugin;
    private Scoreboard scoreboard = Main.getScoreboard();
    private Data data = Main.getData();
    private SQL sql;

    public Listener(Main plugin){
        this.plugin = plugin;
        sql = new SQL(plugin);
    }

    @EventHandler
    public void join(PlayerJoinEvent e){
        Player p = e.getPlayer();
        if(data.getUuid(p.getName())==null){
            sql.newPlayer(p);
        }
        scoreboard.create(p);
    }
    @EventHandler
    public void kill(PlayerDeathEvent e){
        Player victim = e.getEntity();
        if(victim.getKiller() != null){
            Player attacker = victim.getKiller();
            int kills = data.getKills(attacker.getName())+1;
            int death = data.getDeaths(victim.getName())+1;
            data.setKills(attacker.getName(),kills);
            data.setDeaths(victim.getName(),death);
            float kkils = kills;
            float kdeaths = data.getDeaths(attacker.getName());
            data.updateTopList(kkils/kdeaths,attacker.getName());
            sql.update(attacker.getName(),"kills = "+kills);
            sql.update(victim.getName(),"deaths = "+death);
        }
    }
}
