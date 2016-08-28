package com.mygdx.utils.create;

import com.mygdx.entities.enemies.BigEnemy;
import com.mygdx.entities.enemies.Enemy;
import com.mygdx.entities.enemies.FastEnemy;
import com.mygdx.entities.enemies.InvisibleEnemy;
import com.mygdx.entities.enemies.NormalEnemy;
import com.mygdx.game.MyGame;
import com.mygdx.screen.GameScreen;

import java.util.ArrayList;


public class EnemyCreator {


    static float enemyRespawnTime,respawnCooldown = 1f;
    static float newEnemy = 300;


    public static void createEnemy(float delta) {
        if(enemyRespawnTime<=0){
            Enemy enemy;

            int enemyType = getRandomEnemy();

            switch (enemyType){
                case Enemy.INVISIBLE_ENEMY:
                    enemy = createInvisible();
                    break;
                case Enemy.FAST_ENEMY:
                    enemy = createFast();
                    break;
                case Enemy.BIG_ENEMY:
                    enemy = createBig();
                    break;
                default:
                    enemy = createNormal();
                    break;
            }

            enemy.randomize();
            GameScreen.entMan.enemies.add(enemy);
        }else{
            enemyRespawnTime-=delta;
        }
    }

    private static int getRandomEnemy(){
        int enemyType;
        float score = GameScreen.hudMan.score.getScore();

        if(score>=newEnemy*3) {
            double random = Math.random();
            if(random >= .877)
                enemyType = Enemy.INVISIBLE_ENEMY;
            else if (random >= .766)
                enemyType = Enemy.FAST_ENEMY;
            else if (random >= .633)
                enemyType = Enemy.BIG_ENEMY;
            else
                enemyType = Enemy.NORMAL_ENEMY;
        }else if(score>=newEnemy*2){
            double random = Math.random();
            if(random>=.8)
                enemyType = Enemy.FAST_ENEMY;
            else if(random>=.6)
                enemyType = Enemy.BIG_ENEMY;
            else
                enemyType = Enemy.NORMAL_ENEMY;
        }else if(score>=newEnemy){
            double random = Math.random();
            if(random>=.8)
                enemyType = Enemy.BIG_ENEMY;
            else
                enemyType = Enemy.NORMAL_ENEMY;
        }else{
            enemyType = Enemy.NORMAL_ENEMY;
        }

        return enemyType;
    }

    private static Enemy createInvisible(){
        int loc = -1;
        Enemy enemy;
        for(int i=0;i<GameScreen.entMan.enemyPool.size();i++)
            if(GameScreen.entMan.enemyPool.get(i).equals(Enemy.INVISIBLE_ENEMY))
                loc = i;
        if(loc>=0)
            enemy = GameScreen.entMan.enemyPool.remove(loc);
        else
            enemy = new InvisibleEnemy(0,0,GameScreen.entMan.world);
        enemyRespawnTime = (float) (Math.random()*1.25f);
        return enemy;
    }

    private static Enemy createFast(){
        int loc = -1;
        Enemy enemy;
        for(int i=0;i<GameScreen.entMan.enemyPool.size();i++)
            if(GameScreen.entMan.enemyPool.get(i).equals(Enemy.FAST_ENEMY))
                loc = i;
        if(loc>=0)
            enemy = GameScreen.entMan.enemyPool.remove(loc);
        else
            enemy = new FastEnemy(0,0,GameScreen.entMan.world);
        enemyRespawnTime = (float) (Math.random()*.7f*respawnCooldown);
        return enemy;
    }

    private static Enemy createBig(){
        int loc = -1;
        Enemy enemy;
        for(int i=0;i<GameScreen.entMan.enemyPool.size();i++)
            if(GameScreen.entMan.enemyPool.get(i).equals(Enemy.BIG_ENEMY))
                loc = i;
        if(loc>=0)
            enemy = GameScreen.entMan.enemyPool.remove(loc);
        else
            enemy = new BigEnemy(0,0, MyGame.WIDTH*.07f,GameScreen.entMan.world);
        enemyRespawnTime = (float) (Math.random()*1.5f*respawnCooldown);
        return  enemy;
    }

    private static Enemy createNormal(){
        int loc = -1;
        Enemy enemy;
        for(int i=0;i<GameScreen.entMan.enemyPool.size();i++)
            if(GameScreen.entMan.enemyPool.get(i).equals(Enemy.NORMAL_ENEMY))
                loc = i;
        if(loc>=0)
            enemy = GameScreen.entMan.enemyPool.remove(loc);
        else
            enemy = new NormalEnemy(0,0,GameScreen.entMan.world);
        enemyRespawnTime = (float) (Math.random()*1f);
        return enemy;
    }
}
