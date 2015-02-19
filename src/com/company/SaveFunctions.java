package com.company;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by DBJ on 18-02-2015.
 */
public class SaveFunctions {
    DBHelper helper = new DBHelper();
    public void SavePalyer(String Name, Player player){
        helper.Insert(new String[] {"Player_Name","Player_Level","Player_Strength","Player_DefensePower","Player_Intelligence","Player_Agility","Player_Experience","Player_CurrentHealth"},
                      new String[] {Name,Integer.toString(player.Level),Integer.toString(player.Strength),Integer.toString(player.DefensePower),Integer.toString(player.Intelligence),Integer.toString(player.Agility),Integer.toString(player.Experience),Integer.toString(player.CurrentHealth)});
    }

    public ArrayList<Player> LoadPlayers(){
        ArrayList<Player> LoadedPlayers = new ArrayList<Player>();
        while (true) {
            String PlayerName = (String)Console.Interact(Dialog.Load);
            if (PlayerName == "Quit") break;
            else {
                Player player = LoadPlayer(PlayerName);
                if (player == null) {
                    Console.Msg("Could not load Player: " + PlayerName,false,false);
                    continue;
                }
                else {
                    LoadedPlayers.add(player);
                    Console.Msg("Player: " + PlayerName,false,false);
                    Console.Msg("Have been loaded.",true,false);
                }
            }
        }
        return LoadedPlayers;
    }

    private Player LoadPlayer(String PlayerName) {
        ResultSet rs = helper.Select("*",new String[]{"Player_Name"},new String[]{PlayerName});
        try {
            while (rs.next()) {
                String Player_Name = rs.getString("Player_Name");
                if (Player_Name.equals(PlayerName)){
                    Player player = new Player();
                    player.Level = rs.getInt("Player_Level");
                    player.Strength = rs.getInt("Player_Strength");
                    player.DefensePower = rs.getInt("Player_DefensePower");
                    player.Intelligence = rs.getInt("Player_Intelligence");
                    player.Agility = rs.getInt("Player_Agility");
                    player.Experience = rs.getInt("Player_Experience");
                    player.CurrentHealth = rs.getInt("Player_CurrentHealth");
                    return  player;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
