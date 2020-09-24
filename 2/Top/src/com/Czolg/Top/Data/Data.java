package com.Czolg.Top.Data;

import com.Czolg.Top.Main;
import com.Czolg.Top.Scoreboard;

import java.util.*;

public class Data {
    private TopComparator topComparator = new TopComparator();
    private Scoreboard scoreboard = Main.getScoreboard();

    private HashMap<String,List<List<Object>>> kda = new HashMap<>();
    private HashMap<String,List<Object>> top = new HashMap<>();
    private HashMap<String,Integer> kills = new HashMap<>();
    private HashMap<String,Integer> deaths = new HashMap<>();
    private HashMap<String,String> klasa = new HashMap<>();
    private HashMap<String,String> uuid = new HashMap<>();

    private List<String> classes = new ArrayList<>();
    private List<String> players = new ArrayList<>();

    public void setKills(String p,int kills){
        this.kills.put(p,kills);
    }
    public int getKills(String p){
        return kills.get(p);
    }

    public void setDeaths(String p,int deaths){
        this.deaths.put(p,deaths);
    }
    public int getDeaths(String p){
        return deaths.get(p);
    }

    public void setUuid(String p,String uuid){
        this.uuid.put(p,uuid);
    }
    public String getUuid(String p){
        return uuid.get(p);
    }

    public void setKlasa(String p,String klasa){
        this.klasa.put(p,klasa);
    }
    public String getKlasa(String p){
        return klasa.get(p);
    }

    public void addKlassToArray(String klasa){
        if(!classes.contains(klasa)){
            this.classes.add(klasa);
        }
    }

    public void addPlayersToArray(String p){
        if(!players.contains(p)){
            this.players.add(p);
        }
    }
    public void updateTop(){
        kda.clear();
        if(players.size()!=0){
            for (String player : players) {
                float kill = getKills(player);
                float death = getDeaths(player);
                if((kill+death) >= 5){
                    float kda = kill/death;
                    String klasa = this.klasa.get(player);
                    if(this.kda.get(klasa)==null){
                        this.kda.put(klasa,new ArrayList<>());
                    }
                    this.kda.get(klasa).add(Arrays.asList(player,kda));
                }
            }
        }
        for (String aClass : classes) {
            if(this.kda.get(aClass)!=null){
                Collections.sort(this.kda.get(aClass),topComparator);
                List<List<Object>> top = this.kda.get(aClass);
                this.top.put(aClass, top.get(top.size() - 1));
            }
        }
    }
    public List<String> getTopListBoard(){
        List<String> topList = new ArrayList<>();
        for (String aClass : classes) {
            if(top.get(aClass) != null){
                String fistChar = aClass.split("")[0];
                topList.add(aClass.replaceFirst(fistChar,fistChar.toUpperCase()) + ": " + top.get(aClass).get(0));
            }
        }
        return topList;
    }
    public void updateTopList(float kda,String name){
        if(kda > (Float) top.get(getKlasa(name)).get(1)){
            top.put(getKlasa(name),Arrays.asList(name,kda));
            scoreboard.refresh();
        }
    }
}
