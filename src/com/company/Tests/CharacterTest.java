package com.company;

import org.junit.Test;

import static org.junit.Assert.*;

public class CharacterTest {
    Map GameMap = new Map();
    private static com.company.Character Monster = null;
    private static Character Player = null;

    /**
     * finds a player and a monster in the map
     * used in all test´s
     */
    private void getCharacters(){
        for (Character cha : GameMap.Characters){
            if (Monster == null){
                if (cha instanceof Monster){
                    Monster = cha;
                }
            }
            if (Player == null){
                if (cha instanceof Player){
                    Player = cha;
                }
            }
            if (Monster != null && Player != null) break;
        }
    }

    /**
     * sets console input same as if user types in console;
     */
    public void MockUser(){
        String inputData = "\n";
        System.setIn(new java.io.ByteArrayInputStream(inputData.getBytes()));
    }

    /**
     * Check if a monster and a player can take "TakeDamage" in damage
     * Remember cannot check if equals damage amount, cause both has deflect chance.
     * @throws Exception
     */
    @Test
    public void testTakeDamage() throws Exception {
        getCharacters();
        MockUser();

        int TakeDamage = 10;

        int Hp = Monster.CurrentHealth;
        Monster.TakeDamage(TakeDamage);
        assertNotEquals(Hp, Monster.CurrentHealth);

        Hp = Player.CurrentHealth;
        Player.TakeDamage(TakeDamage);
        assertNotEquals(Hp, Player.CurrentHealth);
    }

    /**
     * check a ranged attack returns a damage amount
     * @throws Exception
     */
    @Test
    public void testRangedAtt() throws Exception {
        getCharacters();

        assertTrue(Monster.RangedAtt() > 0);
        assertTrue(Player.RangedAtt() > 0);
    }

    /**
     * check a melle attack returns a damage amount
     * @throws Exception
     */
    @Test
    public void testMeleeAtt() throws Exception {
        getCharacters();

        assertTrue(Monster.MeleeAtt() > 0);
        assertTrue(Player.MeleeAtt() > 0);
    }

    /**
     * check a magic attack returns a damage amount
     * @throws Exception
     */
    @Test
    public void testMagicAtt() throws Exception {
        getCharacters();

        assertTrue(Monster.MagicAtt() > 0);
        assertTrue(Player.MagicAtt() > 0);
    }

    /**
     * check if player and monster can heal, after they have been attacked
     * @throws Exception
     */
    @Test
    public void testHeal() throws Exception {
        getCharacters();

        MockUser();

        Monster.TakeDamage(50);
        Player.TakeDamage(50);
        int MonsterHp = Monster.CurrentHealth;
        int PlayerHp = Player.CurrentHealth;

        Monster.Heal(100);
        assertNotEquals(MonsterHp, Monster.CurrentHealth);
        assertTrue(Monster.CurrentHealth == Monster.Maxhealth());
        Player.Heal(100);
        assertNotEquals(PlayerHp, Player.CurrentHealth);
        assertTrue(Player.CurrentHealth == Player.Maxhealth());
    }

    /**
     * check if player and monster can calculate a random damage amount
     * with damage as max.
     * @throws Exception
     */
    @Test
    public void testRandomDamage() throws Exception {
        int Damage = Monster.MeleeAtt();
        assertTrue((Damage > Monster.randomDamage(Damage)));

        Damage = Player.MeleeAtt();
        assertTrue((Damage > Player.randomDamage(Damage)));
    }
}