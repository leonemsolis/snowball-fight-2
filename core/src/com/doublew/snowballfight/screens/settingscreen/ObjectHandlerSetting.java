package com.doublew.snowballfight.screens.settingscreen;

import com.doublew.snowballfight.main.GameClass;
import com.doublew.snowballfight.support.AssetHandler;
import com.doublew.snowballfight.support.DataHandler;

import java.util.ArrayList;

/**
 * Created by Leonemsolis on 21/02/2018.
 */

public class ObjectHandlerSetting {

    public com.doublew.snowballfight.screens.commonobjects.ux.Button backButton;
    public ArrayList<com.doublew.snowballfight.screens.battlescreen.objects.particles.CrashParticleSystem> clickParticles;
    public com.doublew.snowballfight.screens.settingscreen.objects.Toggle sound, color;

    public ObjectHandlerSetting(final ScreenSetting screen) {
        backButton = new com.doublew.snowballfight.screens.commonobjects.ux.Button(new com.doublew.snowballfight.screens.commonobjects.ux.Callback() {
            @Override
            public void callback() {
                screen.goBack();
            }
        }, AssetHandler.backButtonUp, AssetHandler.backButtonDown, 0, 0, 80, 39);

        sound = new com.doublew.snowballfight.screens.settingscreen.objects.Toggle(AssetHandler.soundToggleOn, AssetHandler.soundToggleOff, GameClass.GAME_WIDTH / 2 - 71, GameClass.MID_POINT - 70, 142, 53, DataHandler.soundOn);
        color = new com.doublew.snowballfight.screens.settingscreen.objects.Toggle(AssetHandler.colorToggleOn, AssetHandler.colorToggleOff, GameClass.GAME_WIDTH / 2 - 71, GameClass.MID_POINT + 10, 142, 53, DataHandler.colorOn);

        clickParticles = new ArrayList<com.doublew.snowballfight.screens.battlescreen.objects.particles.CrashParticleSystem>();
    }

    public void update(float delta) {
        for (com.doublew.snowballfight.screens.battlescreen.objects.particles.CrashParticleSystem s:clickParticles) {
            s.update(delta);
        }
        proceedCompleteParticles();
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

    public void dispose() {

    }
}
