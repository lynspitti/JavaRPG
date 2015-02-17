package com.company;

import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.*;

public class MapTest {
    private static Map GameMap = new Map();

    @Test
    public void testLoadNextMap() throws Exception {
        Object[][] GameMapCopy = GameMap.Map;
        GameMap.LoadNextMap();
        assertNotEquals(GameMap.Map,GameMapCopy);
    }

    @Test
    public void testWhereIs() throws Exception {
        com.company.Character Cha = GameMap.Characters.get(0);
        Point chaPoint = GameMap.whereIs(Cha);
        Object Cha2 = GameMap.fetchAt(chaPoint);
        assertTrue((Cha2 instanceof Character) && (Cha == Cha2));
    }

    @Test
    public void testFetchAt() throws Exception {
        Character Cha = GameMap.Characters.get(0);
        Point chaPoint = GameMap.whereIs(Cha);
        Object Cha2 = GameMap.fetchAt(chaPoint);
        assertTrue((Cha2 instanceof Character) && (Cha == Cha2));
    }

    @Test
    public void testAddToVisibleMap() throws Exception {
        Character Boss = null;
        Character Player = null;
        for (Character cha : GameMap.Characters){
            if (Boss == null){
                if (cha instanceof Monster){
                    if (((Monster) cha).isBoss){
                        Boss = cha;
                    }
                }
            }
            if (Player == null){
                if (cha instanceof Player){
                    Player = cha;
                }
            }
            if (Boss != null && Player != null) break;
        }

        Point OldBossPoint = GameMap.whereIs(Boss);
        Point NewBossPoint = (Point)OldBossPoint.clone();
        if (GameMap.VisibleMap[NewBossPoint.x][NewBossPoint.y+1] != "#"){
            NewBossPoint.y++;
        }
        else if (GameMap.VisibleMap[NewBossPoint.x][NewBossPoint.y-1] != "#"){
            NewBossPoint.y--;
        }
        else if (GameMap.VisibleMap[NewBossPoint.x +1][NewBossPoint.y] != "#"){
            NewBossPoint.x++;
        }
        else if (GameMap.VisibleMap[NewBossPoint.x -1][NewBossPoint.y] != "#"){
            NewBossPoint.x--;
        }
        GameMap.addToVisibleMap(NewBossPoint,false);
        assertNotEquals(GameMap.VisibleMap[OldBossPoint.x][OldBossPoint.y],"B");
        assertSame(GameMap.VisibleMap[NewBossPoint.x][NewBossPoint.y],"B");

        Point OldPlayerPoint = GameMap.whereIs(Player);
        Point NewPlayerPoint = (Point)OldPlayerPoint.clone();
        if (GameMap.VisibleMap[NewPlayerPoint.x][NewPlayerPoint.y+1] != "#"){
            NewPlayerPoint.y++;
        }
        else if (GameMap.VisibleMap[NewPlayerPoint.x][NewPlayerPoint.y-1] != "#"){
            NewPlayerPoint.y--;
        }
        else if (GameMap.VisibleMap[NewPlayerPoint.x +1][NewPlayerPoint.y] != "#"){
            NewPlayerPoint.x++;
        }
        else if (GameMap.VisibleMap[NewPlayerPoint.x -1][NewPlayerPoint.y] != "#"){
            NewPlayerPoint.x--;
        }
        GameMap.addToVisibleMap(NewPlayerPoint,true);
        String TestPose = (String)GameMap.VisibleMap[OldPlayerPoint.x][OldPlayerPoint.y];
        assertNotEquals(GameMap.VisibleMap[OldPlayerPoint.x][OldPlayerPoint.y],"X");
        assertSame(GameMap.VisibleMap[NewPlayerPoint.x][NewPlayerPoint.y],"X");
    }

    @Test
    public void testPrintVisibleMap() throws Exception {

    }

    @Test
    public void testAddNewCharacter() throws Exception {
        int AddAmount = 2;
        int length = GameMap.Characters.size();
        GameMap.addNewCharacter(AddAmount,5);
        assertTrue(GameMap.Characters.size() == length +AddAmount);
    }
}