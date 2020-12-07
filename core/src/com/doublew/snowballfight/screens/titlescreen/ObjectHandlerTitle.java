package com.doublew.snowballfight.screens.titlescreen;

import com.badlogic.gdx.Gdx;
import com.doublew.snowballfight.main.GameClass;
import com.doublew.snowballfight.support.AssetHandler;
import com.doublew.snowballfight.support.DataHandler;
import com.doublew.snowballfight.support.Sound;

import java.util.ArrayList;

/**
 * Created by Leonemsolis on 17/09/2017.
 *
 * Object Handler - keeps information about all objects
 * that are in current Screen, also Object Handler
 * need to be passed to certain Renderer
 */

public class ObjectHandlerTitle {
    public com.doublew.snowballfight.screens.commonobjects.ux.Button playButton, settingsButton, helpButton, likeButton;
    public com.doublew.snowballfight.screens.commonobjects.ux.Prompt continuePrompt;

    public float animationX = GameClass.DEVICE_WIDTH;
    public boolean isAnimated = false;
    public final float ANIMATION_SPEED = 300f;

    public ArrayList<com.doublew.snowballfight.screens.battlescreen.objects.particles.CrashParticleSystem> clickParticles;

    public ScreenTitle screen;



    public ObjectHandlerTitle(final ScreenTitle screen) {
        this.screen = screen;
        clickParticles = new ArrayList<com.doublew.snowballfight.screens.battlescreen.objects.particles.CrashParticleSystem>();

        continuePrompt = new com.doublew.snowballfight.screens.commonobjects.ux.Prompt(new com.doublew.snowballfight.screens.commonobjects.ux.Callback() {
            @Override
            public void callback() {
                screen.gameClass.loadExistingGame();
                continuePrompt.hide();
            }
        }, new com.doublew.snowballfight.screens.commonobjects.ux.Callback() {
            @Override
            public void callback() {
                screen.gameClass.createNewGame();
                continuePrompt.hide();
            }
        }, new com.doublew.snowballfight.screens.commonobjects.ux.Callback() {
            public void callback() {
                continuePrompt.hide();
            }
        });

        continuePrompt.updatePrompt("Continue?", "", "Yes", "No");

        DataHandler.prepareDataHandler();
        if(DataHandler.saveAvailable) {
            playButton = new com.doublew.snowballfight.screens.commonobjects.ux.Button(new com.doublew.snowballfight.screens.commonobjects.ux.Callback() {
                @Override
                public void callback() {
                    continuePrompt.show();
                }
            }, AssetHandler.battleWinning[0], AssetHandler.battleWinning[1], GameClass.GAME_WIDTH / 2, GameClass.MID_POINT-75, 100, 150, 115, 150);
        } else {
            playButton = new com.doublew.snowballfight.screens.commonobjects.ux.Button(new com.doublew.snowballfight.screens.commonobjects.ux.Callback() {
                @Override
                public void callback() {
                    screen.gameClass.createNewGame();
                }
            }, AssetHandler.battleWinning[0], AssetHandler.battleWinning[1], GameClass.GAME_WIDTH / 2, GameClass.MID_POINT-75, 100, 150, 115, 150);
        }

        settingsButton = new com.doublew.snowballfight.screens.commonobjects.ux.Button(new com.doublew.snowballfight.screens.commonobjects.ux.Callback() {
            @Override
            public void callback() {
                screen.gotoScreenSetting();
            }
        }, AssetHandler.settingsButtonUp, AssetHandler.settingsButtonDown, 30, GameClass.GAME_HEIGHT - 70, 40, 40);

        helpButton = new com.doublew.snowballfight.screens.commonobjects.ux.Button(new com.doublew.snowballfight.screens.commonobjects.ux.Callback() {
            @Override
            public void callback() {
                helpButton.hide();
                playDummyAnimation();
            }
        }, AssetHandler.helpButtonUp, AssetHandler.helpButtonDown, GameClass.GAME_WIDTH / 2 - 20, GameClass.GAME_HEIGHT - 70, 40, 40);
        likeButton = new com.doublew.snowballfight.screens.commonobjects.ux.Button(new com.doublew.snowballfight.screens.commonobjects.ux.Callback() {
            @Override
            public void callback() {
                Gdx.net.openURI("https://play.google.com/store/apps/details?id=com.leonemsolis.sbfr");
            }
        }, AssetHandler.likeButtonUp, AssetHandler.likeButtonDown, GameClass.GAME_WIDTH - 70, GameClass.GAME_HEIGHT - 70, 40, 40);
    }

    public void update(float delta) {
        if(isAnimated) {
            animationX -= delta * ANIMATION_SPEED;
            if(animationX <= 0) {
                isAnimated = false;
                screen.renderer.changeLabel("Easter egg was found!");
            }
        }

        for (com.doublew.snowballfight.screens.battlescreen.objects.particles.CrashParticleSystem s:clickParticles) {
            s.update(delta);
        }
        proceedCompleteParticles();
    }

    public void playDummyAnimation() {
        Sound.playSpecialSlide();
        isAnimated = true;
    }

    public void dispose() {
        continuePrompt.dispose();
    }


    private void proceedCompleteParticles() {
        ArrayList<com.doublew.snowballfight.screens.battlescreen.objects.particles.ParticleSystem> sToRemove = new ArrayList<com.doublew.snowballfight.screens.battlescreen.objects.particles.ParticleSystem>();
        for (com.doublew.snowballfight.screens.battlescreen.objects.particles.ParticleSystem s : clickParticles) {
            if(s.isComplete()) {
                sToRemove.add(s);
            }
        }

        if(!sToRemove.isEmpty()) {
            for (com.doublew.snowballfight.screens.battlescreen.objects.particles.ParticleSystem s:sToRemove) {
                s.dispose();
                clickParticles.remove(s);
            }
        }
    }
}
