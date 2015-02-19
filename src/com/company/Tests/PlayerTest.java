package com.company;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;

public class PlayerTest {
    Player player = new Player();

    public void MockUser(String Message){
        String inputData = Message;
        System.setIn(new java.io.ByteArrayInputStream(inputData.getBytes()));
    }
    public void MockUser(){MockUser("\n");}

    /**
     * Check that all player.attack writes to console and returns damage amount
     * @throws Exception
     */
    @Test
    public void testAttack() throws Exception {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        MockUser("1");
        int damage = player.Attack();
        assertTrue(damage > 0 && outContent.toString().length() > 0);

        outContent.reset();
        MockUser("2");
        damage = player.Attack();
        assertTrue(damage > 0 && outContent.toString().length() > 0);

        outContent.reset();
        MockUser("3");
        damage = player.Attack();
        assertTrue(damage > 0 && outContent.toString().length() > 0);

        outContent.reset();
        MockUser("4");
        damage = player.Attack();
        assertTrue(damage == 0 && outContent.toString().length() > 0);
    }

    /**
     * Test that player heals to 100% after die
     * @throws Exception
     */
    @Test
    public void testDie() throws Exception {
        player.CurrentHealth = 1;
        player.Die(null);
        assertTrue(player.CurrentHealth == player.Maxhealth());
    }

    /**
     * check that player lvl is +1 after lvlup
     * @throws Exception
     */
    @Test
    public void testLvlUp() throws Exception {
        int lvl = player.Level;
        MockUser("1");
        player.LvlUp();
        assertTrue(lvl +1 == player.Level);
    }

    /**
     * check that player can get experiance and lvl up
     * @throws Exception
     */
    @Test
    public void testGetExperience() throws Exception {
        player.Experience = 0;
        int lvl = player.Level;
        player.getExperience(50);
        assertTrue(player.Experience == 50);
        MockUser("1");
        player.getExperience(51);
        assertTrue(player.Experience == 1 && player.Level == lvl +1);
    }
}