package com.doublew.snowballfight.support;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.doublew.snowballfight.main.SCREENS;
import com.doublew.snowballfight.screens.battlescreen.ObjectHandlerBattle;
import com.doublew.snowballfight.screens.battlescreen.ScreenBattle;
import com.doublew.snowballfight.screens.battlescreen.objects.chars.Enemy;
import com.doublew.snowballfight.screens.battlescreen.objects.chars.EnemySave;
import com.doublew.snowballfight.screens.commonobjects.ux.Item;

import java.util.ArrayList;

/**
 * Created by Leonemsolis on 13/10/2017.
 *
 * Class that loads and saves all the data in the game
 */

public class DataHandler {

    //SETTINGS
    public static boolean soundOn = true;
    public static boolean colorOn = true;


    // META
    public static boolean saveAvailable = false;

    public static ArrayList<Item> items = new ArrayList<Item>();
    public static int gold = 0;
    public static boolean action = false;
    public static int maxStage = 1;
    public static int maxLevel = 1;
    public static boolean resurrectedOnce = false;

    // BATTLE
    public static ArrayList<EnemySave> savedEnemies = new ArrayList<EnemySave>();
    public static float heroHP = 100;
    public static int heroX = 0;
    public static int heroMana = 0;
    public static int currentStageID = 1;
    public static int currentStageLevel = 1;


    public static void prepareDataHandler() {
        Preferences p = Gdx.app.getPreferences("sbfr");
        saveAvailable = p.getBoolean("saveAvailable", false);
        Gdx.app.log("DataHandler, saveAvailable: ", saveAvailable+"");
    }

    public static void loadSettings() {
        Preferences p = Gdx.app.getPreferences("sbfr_settings");
        soundOn = p.getBoolean("soundOn", true);
        colorOn = p.getBoolean("colorOn", true);
    }

    public static void saveSettings() {
        Preferences p = Gdx.app.getPreferences("sbfr_settings");
        p.putBoolean("soundOn", soundOn);
        p.putBoolean("colorOn", colorOn);
        p.flush();
    }

    public static void loadData() {
        Preferences p = Gdx.app.getPreferences("sbfr");
        if(saveAvailable) {
            Gdx.app.log("DataHandler", "LOADING");
            action = p.getBoolean("action", false);
            maxStage = p.getInteger("maxStage", 1);
            maxLevel = p.getInteger("maxLevel", 1);
            resurrectedOnce = p.getBoolean("resurrectedOnce", false);

            gold = p.getInteger("gold", 0);
            items = deserializeItems(p.getString("items", ""));

            if(!action) {
                return;
            }


            currentStageID = p.getInteger("currentStageID", 1);
            currentStageLevel = p.getInteger("currentStageLevel", 1);
            savedEnemies = deserializeEnemyArray(p.getString("enemies", ""));
            heroHP = p.getFloat("heroHP", 100);
            heroMana = p.getInteger("heroMana", 3);
            heroX = p.getInteger("heroX", 0);
        } else {
            items = new ArrayList<Item>();
            gold = 0;
            action = false;
            maxStage = 1;
            maxLevel = 1;
            resurrectedOnce = false;

            // BATTLE
            savedEnemies = new ArrayList<EnemySave>();
            heroHP = 100;
            heroX = 0;
            heroMana = 0;
            currentStageID = 1;
            currentStageLevel = 1;
        }
    }

    public static void resetData() {
        Gdx.app.log("DataHandler", "Reset");
        Preferences p = Gdx.app.getPreferences("sbfr");
        p.remove("saveAvailable");
        p.remove("action");
        p.remove("maxLevelID");
        p.remove("maxStageID");
        p.remove("gold");
        p.remove("items");
        p.remove("enemies");
        p.remove("heroHP");
        p.remove("heroMana");
        p.remove("heroX");
        p.remove("currentStageID");
        p.remove("currentStageLevel");
        p.remove("maxStage");
        p.remove("maxLevel");
        p.remove("resurrectedOnce");
        p.flush();
    }

    public static void saveData(SCREENS screenType, Screen screen) {
        switch (screenType) {
            case STORE:
            case FINISH:
            case TITLE:
            case MAP:
                action = false;
                break;
            case BATTLE:
                action = true;
                break;
        }

        if(!isMetaValuesChanged()) {
            return;
        }

        Gdx.app.log("DataHandler", "SAVING");

        Preferences p = Gdx.app.getPreferences("sbfr");

        // Save action if it is
        if(action) {
            ObjectHandlerBattle objects = ((ScreenBattle)(screen)).handler;
            p.putString("enemies", serializeEnemyArray(objects.enemies));
            p.putInteger("heroX", objects.hero.x);
            p.putFloat("heroHP", objects.hero.getHP());
            // TODO: 08/01/2018 ADD
            p.putInteger("heroMana", heroMana);
            p.putInteger("currentStageID", currentStageID);
            p.putInteger("currentStageLevel", currentStageLevel);
        }

        // Save meta
        p.putInteger("gold", gold);
        p.putBoolean("action", action);
        p.putBoolean("saveAvailable", true);
        p.putInteger("maxStage", maxStage);
        p.putInteger("maxLevel", maxLevel);
        p.putBoolean("resurrectedOnce", resurrectedOnce);
        p.putString("items", serializeItems(items));
        p.flush();
    }

    public static boolean isMetaValuesChanged() {
        if(gold != 0 ||
                maxStage != 1 ||
                maxLevel != 1 ||
                action != false ||
                items.size() > 0 ||
                resurrectedOnce != false) {
            return true;
        }
        return false;
    }

    public static ArrayList<Item> deserializeItems(String items) {
        ArrayList<Item>result = new ArrayList<Item>();
        for(int i = 0; i < items.length(); ++i) {
            result.add(new Item(Integer.parseInt(""+items.charAt(i))));
        }
        return result;
    }

    public static String serializeItems(ArrayList<Item>items) {
        String result = "";
        for (Item i:items) {
            result += i.id;
        }
        return result;
    }

    public static ArrayList<EnemySave> deserializeEnemyArray(String enemies) {
        ArrayList<EnemySave> result = new ArrayList<EnemySave>();

        for(int i = 0; i < enemies.length(); ++i) {
            if(enemies.charAt(i) == '{') {
                String sub = "";
                i++;
                sub = enemies.substring(i, enemies.length());
                String parsed = sub.substring(0, sub.indexOf(','));
                boolean isBoss = Boolean.parseBoolean(parsed);

                i += parsed.length() + 1;
                sub = enemies.substring(i, enemies.length());
                parsed = sub.substring(0, sub.indexOf(','));
                int x = Integer.parseInt(parsed);

                i += parsed.length() + 1;
                sub = enemies.substring(i, enemies.length());
                parsed = sub.substring(0, sub.indexOf(','));
                int y = Integer.parseInt(parsed);

                i += parsed.length() + 1;
                sub = enemies.substring(i, enemies.length());
                parsed = sub.substring(0, sub.indexOf(','));
                float hp = Float.parseFloat(parsed);

                i += parsed.length() + 1;
                sub = enemies.substring(i, enemies.length());
                parsed = sub.substring(0, sub.indexOf(','));
                int skinID = Integer.parseInt(parsed);

                i += parsed.length() + 1;
                sub = enemies.substring(i, enemies.length());
                parsed = sub.substring(0, sub.indexOf("}"));
                int damage = Integer.parseInt(parsed);

                result.add(new EnemySave(isBoss, x, y, hp, skinID, damage));
            }
        }
        return result;
    }

    public static String serializeEnemyArray(ArrayList<Enemy> enemies) {
        String result = "";
        for (Enemy e: enemies) {
            result += "{";
            result += e.isBoss;
            result += ",";
            result += e.x;
            result += ",";
            result += e.y;
            result += ",";
            result += e.HP;
            result += ",";
            result += e.skinID;
            result += ",";
            result += e.damage;
            result += "}";
        }
        return result;
    }

}