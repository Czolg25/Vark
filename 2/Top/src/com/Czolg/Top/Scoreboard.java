package com.Czolg.Top;

import com.Czolg.Top.Data.Data;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;

import java.util.HashMap;
import java.util.List;

public class Scoreboard {
    private HashMap<Player, org.bukkit.scoreboard.Scoreboard> boards = new HashMap<>();
    private Data data = Main.getData();
    private String name = ChatColor.GREEN+"Top Kills";

    public void create(Player p){
        if(boards.get(p)==null){
            org.bukkit.scoreboard.Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
            board = getScores(board);
            p.setScoreboard(board);
            boards.put(p,board);
        }
    }

    public void refresh(){
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            updateScoreboard(onlinePlayer);
        }
    }

    private void updateScoreboard(Player p){
        if(boards.get(p) != null){
            org.bukkit.scoreboard.Scoreboard board = boards.get(p);
            Objective objective = board.getObjective(name);
            objective.unregister();
            getScores(board);
        }
    }

    private org.bukkit.scoreboard.Scoreboard getScores(org.bukkit.scoreboard.Scoreboard board){
        Objective objective = board.registerNewObjective(name,"dummy");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        int scores = 15;
        List<String> stats = data.getTopListBoard();
        Score score;
        for (String stat : stats) {
            score = objective.getScore(ChatColor.GREEN+"- "+stat);
            score.setScore(scores);
            scores--;
            if(scores == 0){
                return  board;
            }
        }
        return board;
    }
}
