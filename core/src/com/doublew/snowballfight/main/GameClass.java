package com.doublew.snowballfight.main;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.doublew.snowballfight.screens.battlescreen.ScreenBattle;
import com.doublew.snowballfight.screens.finish.ScreenFinish;
import com.doublew.snowballfight.screens.mapscreen.ScreenMap;
import com.doublew.snowballfight.screens.settingscreen.ScreenSetting;
import com.doublew.snowballfight.screens.store.ScreenStore;
import com.doublew.snowballfight.screens.titlescreen.ScreenTitle;
import com.doublew.snowballfight.support.AssetHandler;
import com.doublew.snowballfight.support.DataHandler;
import com.doublew.snowballfight.support.Sound;

/**
 * Created by Leonemsolis on 14/09/2017.
 *
 * GameClass - main module, which switches screens
 * also controls AssetManager
 */

public class GameClass extends Game {
	public static int GAME_WIDTH = 320;
    public static int GAME_HEIGHT;
    public static int MID_POINT;
    public static int DEVICE_WIDTH, DEVICE_HEIGHT;
    public static float SCALE;

    private SCREENS currentScreen;

	@Override
	public void create() {
        DEVICE_WIDTH = Gdx.graphics.getWidth();
        DEVICE_HEIGHT = Gdx.graphics.getHeight();

        SCALE = (float) DEVICE_WIDTH / (float) GAME_WIDTH;

        GAME_HEIGHT = (int)(DEVICE_HEIGHT / SCALE);

        MID_POINT = GAME_HEIGHT / 2;

        AssetHandler.loadAssets();
        DataHandler.loadSettings();

        Sound.playGreetings();

//        gotoScreenSetting();
        gotoScreenTitle();
//        gotoScreenMap("gold = 10");
//        gotoScreenBattle(1, 1, false);
//        gotoScreenFinish(1);
	}

	public void createNewGame() {
        DataHandler.resetData();
        DataHandler.prepareDataHandler();
        DataHandler.loadData();
        gotoScreenMap("Welcome to the", "new Adventure!");
    }

    public void loadExistingGame() {
        DataHandler.loadData();

        if(DataHandler.action) {
            gotoScreenBattle(DataHandler.currentStageID, DataHandler.currentStageLevel, true);
        } else {
            gotoScreenMap("Welcome back!", "");
        }
    }

    public void gotoScreenSetting() {
	    currentScreen = SCREENS.SETTINGS;
	    setScreen(new ScreenSetting(this));
    }

	public void gotoScreenTitle() {
        currentScreen = SCREENS.TITLE;
        setScreen(new ScreenTitle(this));
    }

    public void gotoScreenMap(String message1, String message2) {
        currentScreen = SCREENS.MAP;
        setScreen(new ScreenMap(this, message1, message2));
        save();
    }

    public void gotoScreenBattle(int stageID, int stageLevel, boolean saved) {
        currentScreen = SCREENS.BATTLE;
        DataHandler.currentStageID = stageID;
        DataHandler.currentStageLevel = stageLevel;
        setScreen(new ScreenBattle(this, stageID, stageLevel, saved));
    }

    public void gotoScreenStore(boolean isDrugStore) {
        currentScreen = SCREENS.STORE;
        setScreen(new ScreenStore(isDrugStore, this));
    }

    public void gotoScreenFinish(int score) {
        currentScreen = SCREENS.FINISH;
        setScreen(new ScreenFinish(this, score));
    }

    @Override
    public void pause() {
        save();
        if(currentScreen == SCREENS.BATTLE) {
            this.screen.pause();
        }
    }

    public void save() {
        DataHandler.saveData(currentScreen, this.screen);
    }

	@Override
    public void dispose() {
        AssetHandler.dispose();
    }
}
