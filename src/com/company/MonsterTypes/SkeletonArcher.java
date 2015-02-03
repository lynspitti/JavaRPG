package com.company.MonsterTypes;

import com.company.Console;
import com.company.Monster;

public class SkeletonArcher extends Monster{
    @Override
    public int Attack() {
        int Damage = 0;

        int ran = Console.RandomInt(1, 3);
        switch (ran){
            case 1:
            case 3:
                Damage = RangedAtt();
                Console.Msg("The SkeletonArcher used Ranged attack and dealt " + Damage + " Damage", false);
                break;
            default:
                failAttack();
                break;
        }
        return Damage;
    }

    @Override
    public void failAttack(){
        Console.Msg("The Skeleton lost its arm and couldn't tighten the bow.", false);
    }
}
