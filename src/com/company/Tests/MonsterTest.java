package com.company;

import com.company.MonsterTypes.Slime;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;

public class MonsterTest {
    Monster monster = new Slime();

    /**
     * Check that Monster.Die returns xp based on a player / Defeater
     * @throws Exception
     */
    @Test
    public void testDie() throws Exception {
        Player Player = new Player();
        int xp = monster.Die(Player);
        assertTrue(xp > 0);
    }

    /**
     * Check that Failed attack writes to console
     * @throws Exception
     */
    @Test
    public void testFailAttack() throws Exception {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        monster.failAttack();
        assertTrue(outContent.toString().length() > 0);
    }

    /**
     * Check that LvlUp / sets Monster.Level + 1
     * @throws Exception
     */
    @Test
    public void testLvlUp() throws Exception {
        int Lvl = monster.Level;
        monster.LvlUp();
        assertTrue(monster.Level == Lvl +1);
    }
}