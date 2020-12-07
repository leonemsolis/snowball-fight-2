package com.doublew.snowballfight.screens.finish;

import com.badlogic.gdx.graphics.Color;
import com.doublew.snowballfight.main.GameClass;
import com.doublew.snowballfight.support.AssetHandler;
import com.doublew.snowballfight.support.DataHandler;
import com.doublew.snowballfight.support.Sound;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Leonemsolis on 09/01/2018.
 */

public class ObjectHandlerFinish {

    public com.doublew.snowballfight.screens.commonobjects.ux.Button backButton;
    public com.doublew.snowballfight.screens.commonobjects.ux.Prompt buyBack;
    public boolean gameOver = false;
    public ArrayList<com.doublew.snowballfight.screens.battlescreen.objects.particles.CrashParticleSystem>fireworks;

    public float fireworkTimer = 10f;
    public float FPS = 70; //Fireworks per second
    public boolean isWon;
    public Random r;
    public float fireworkSoundTimer = 0;


    public ObjectHandlerFinish(final ScreenFinish screen, boolean isWon) {
        r = new Random();
        this.isWon = isWon;
        if(!isWon) {
            if(!DataHandler.resurrectedOnce) {
                buyBack = new com.doublew.snowballfight.screens.commonobjects.ux.Prompt(new com.doublew.snowballfight.screens.commonobjects.ux.Callback() {
                    @Override
                    public void callback() {
                        //BUY BACK
                        DataHandler.gold = 0;
                        DataHandler.resurrectedOnce = true;
                        screen.goBack();
                    }
                }, new com.doublew.snowballfight.screens.commonobjects.ux.Callback() {
                    @Override
                    public void callback() {
                        //DIE
                        gameOver = true;
                        DataHandler.resetData();
                        buyBack.hide();
                    }
                }, new com.doublew.snowballfight.screens.commonobjects.ux.Callback() {
                    @Override
                    public void callback() {
                    }
                });
                buyBack.updatePrompt("Game Over!", "Continue for "+ DataHandler.gold+" gold?", "Sure!", "No..");
                buyBack.show();
            } else {
                DataHandler.resetData();
                gameOver = true;
            }
        } else {
            fireworks = new ArrayList<com.doublew.snowballfight.screens.battlescreen.objects.particles.CrashParticleSystem>();
        }

        backButton = new com.doublew.snowballfight.screens.commonobjects.ux.Button(new com.doublew.snowballfight.screens.commonobjects.ux.Callback() {
            @Override
            public void callback() {
                screen.goBack();
            }
        }, AssetHandler.backButtonUp, AssetHandler.backButtonDown, 0, 0, 80, 39);
    }

    public void update(float delta) {
        if(isWon) {
            if(fireworkTimer > 0) {
                if(fireworkSoundTimer <= 0) {
                    Sound.playFirework();
                    fireworkSoundTimer = 2f;
                } else {
                    fireworkSoundTimer -= delta;
                }
                fireworkTimer -= delta;
                int fireworksToAdd = (int) (FPS * delta);
                for(int i = 0; i < fireworksToAdd; ++i) {
                    fireworks.add(new com.doublew.snowballfight.screens.battlescreen.objects.particles.CrashParticleSystem(r.nextInt(GameClass.GAME_WIDTH - 60) + 30, r.nextInt(500) - 300 + GameClass.MID_POINT, generateColor(), generateColor(), .4f));
                }
            }

            for (com.doublew.snowballfight.screens.battlescreen.objects.particles.CrashParticleSystem f:fireworks) {
                f.update(delta);
            }
        }
    }

    public Color generateColor() {
        if(!DataHandler.colorOn) {
            int c = r.nextInt(4);
            switch (c) {
                case 0:
                    return Color.BLACK;
                case 1:
                    return Color.DARK_GRAY;
                case 2:
                    return Color.LIGHT_GRAY;
                case 3:
                    return Color.GRAY;
                case 4:
                    return Color.WHITE;
            }
        }
        int c = r.nextInt(11);
        switch (c) {
            case 0:
                return Color.RED;
            case 1:
                return Color.BROWN;
            case 2:
                return Color.CHARTREUSE;
            case 3:
                return Color.CORAL;
            case 4:
                return Color.CYAN;
            case 5:
                return Color.FIREBRICK;
            case 6:
                return Color.GOLD;
            case 7:
                return Color.GREEN;
            case 8:
                return Color.LIME;
            case 9:
                return Color.TEAL;
            case 10:
                return Color.TAN;
        }
        return Color.WHITE;
    }

    public void dispose() {
        buyBack.dispose();
    }
}
