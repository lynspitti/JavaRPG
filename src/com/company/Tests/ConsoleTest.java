package com.company;

import com.company.MonsterTypes.Mage;
import com.company.MonsterTypes.SkeletonArcher;
import com.company.MonsterTypes.Slime;
import com.company.MonsterTypes.Zombie;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;

public class ConsoleTest {

    /**
     * sets console input same as if user types in console;
     */
    public void MockUser(String Message){
        String inputData = Message;
        System.setIn(new java.io.ByteArrayInputStream(inputData.getBytes()));
    }
    public void MockUser(){MockUser("\n");}

    /**
     * Test if Clear writes to console
     * @throws Exception
     */
    @Test
    public void testClear() throws Exception {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        Console.Clear();
        assertTrue(outContent.toString().length() > 0);
    }

    /**
     * check in msg writes to console with clear
     * @throws Exception
     */
    @Test
    public void testMsgWithClearParam() throws Exception {
        MockUser();
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        Console.Msg("Msg with Clear",true,true);
        assertTrue(outContent.toString().length() > 0);
    }

    /**
     * Check if msg writes specefic message to console with out clear
     * @throws Exception
     */
    @Test
    public void testMsgNoClearParam() throws Exception {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        Console.Msg("Msg with no Clear",false);
        assertEquals("Msg with no Clear\r\n",outContent.toString());
    }

    /**
     * Check if returns random Int between min and max
     * @throws Exception
     */
    @Test
    public void testRandomInt() throws Exception {
        int min = 2;
        int max = 5;
        int Ran = Console.RandomInt(min,max);
        assertTrue((Ran>=min)&&(Ran<max));
    }

    /**
     * Check if returns random Double between min and max
     * @throws Exception
     */
    @Test
    public void testRandomDouble() throws Exception {
        Double min = 2.5;
        Double max = 5.5;
        Double Ran = Console.RandomDouble(min, max);
        assertTrue((Ran>=min)&&(Ran<max));
    }

    /**
     * Check if ReadsInput from console
     * @throws Exception
     */
    @Test
    public void testReadLine() throws Exception {
        MockUser("Test Output");
        assertEquals("Test Output", Console.readLine());
    }

    /**
     * Check thats all interacts writes to console, takes input and returns right
     * @throws Exception
     */
    @Test
    public void testInteract() throws Exception {
        MockUser("1");
        assertEquals(1, Console.Interact(Dialog.Level));

        MockUser("up");
        Assert.assertEquals(MoveDir.Up, Console.Interact(Dialog.Move));

        MockUser("1");
        assertEquals(1,Console.Interact(Dialog.Attack));

        MockUser();
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        Console.Interact(Dialog.Wellcome);
        assertTrue(outContent.toString().length() > 0);
    }

    /**
     * check that writes to console
     * @throws Exception
     */
    @Test
    public void testYarhAsciiArt() throws Exception {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        Console.YarhAsciiArt();
        assertTrue(outContent.toString().length() > 0);
    }

    /**
     * check that writes to console
     * @throws Exception
     */
    @Test
    public void testBattleAsciiArt() throws Exception {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        Console.BattleAsciiArt(new Player(), new Slime());
        assertTrue(outContent.toString().length() > 0);

        outContent.reset();
        Console.BattleAsciiArt(new Player(),new Mage());
        assertTrue(outContent.toString().length() > 0);

        outContent.reset();
        Console.BattleAsciiArt(new Player(),new Zombie());
        assertTrue(outContent.toString().length() > 0);

        outContent.reset();
        Console.BattleAsciiArt(new Player(),new SkeletonArcher());
        assertTrue(outContent.toString().length() > 0);

        outContent.reset();
        Console.BattleAsciiArt(new Player(),new Player());
        assertTrue(outContent.toString().length() > 0);
    }
}