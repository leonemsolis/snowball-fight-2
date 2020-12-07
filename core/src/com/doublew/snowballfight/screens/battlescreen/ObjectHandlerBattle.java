package com.doublew.snowballfight.screens.battlescreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import com.doublew.snowballfight.main.GameClass;
import com.doublew.snowballfight.screens.battlescreen.objects.BATTLE_MODE;
import com.doublew.snowballfight.screens.battlescreen.objects.particles.CrashParticleSystem;
import com.doublew.snowballfight.screens.battlescreen.objects.chars.EnemySave;
import com.doublew.snowballfight.screens.battlescreen.objects.ControlButton;
import com.doublew.snowballfight.screens.battlescreen.objects.PauseButton;
import com.doublew.snowballfight.screens.commonobjects.ux.Callback;
import com.doublew.snowballfight.support.AssetHandler;
import com.doublew.snowballfight.support.DataHandler;
import com.doublew.snowballfight.support.Sound;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Leonemsolis on 12/10/2017.
 */

public class ObjectHandlerBattle {
    public final float SPECIAL_CAST_1_TIME = 3f;
    public final float SPECIAL_CAST_2_TIME = 2f;

    public final float SPECIAL_SLIDE_SPEED = (GameClass.GAME_WIDTH * 1.2f) / SPECIAL_CAST_2_TIME;
    public float specialSliderX = GameClass.GAME_WIDTH;

    public BATTLE_MODE currentMode, savedBeforePauseMode;
    public ArrayList<com.doublew.snowballfight.screens.battlescreen.objects.chars.Enemy>enemies;
    public ArrayList<com.doublew.snowballfight.screens.battlescreen.objects.Snowball>snowballs;
    private ArrayList<com.doublew.snowballfight.screens.battlescreen.objects.chars.Enemy>savedEnemies;
    public com.doublew.snowballfight.screens.battlescreen.objects.chars.Hero hero;
    public ControlButton leftButton, centerButton, rightButton;
    public com.doublew.snowballfight.screens.commonobjects.ux.Button specialButton;

    public PauseButton pauseButton;

    public Rectangle finalTop, finalBottom;
    private float closingSpeedTop, closingSpeedBottom;

    public ArrayList <com.doublew.snowballfight.screens.battlescreen.objects.particles.ParticleSystem>particles;

    public ScreenBattle screen;

    public int lastSelectedSpecial = 0;

    public float modeTimer = 0;

    public int newStage = -1;

    public ObjectHandlerBattle(ScreenBattle s, boolean saved) {
        Gdx.app.log("GAME", "CURRENT LEVEL "+(s.stageID) + ":"+(s.stageLevel));
        screen = s;
        currentMode = BATTLE_MODE.BATTLE;
        savedBeforePauseMode = BATTLE_MODE.BATTLE;

        specialButton = new com.doublew.snowballfight.screens.commonobjects.ux.Button(new Callback() {
            @Override
            public void callback() {
                // USE SPECIAL
                lastSelectedSpecial = DataHandler.heroMana - 1;
                DataHandler.heroMana = 0;

                // Clear all snowballs
                snowballs.clear();

                currentMode = BATTLE_MODE.SPECIAL_CAST_1;
                modeTimer = SPECIAL_CAST_1_TIME;
                Sound.playMusic();
            }
        }, AssetHandler.specialButtonUp, AssetHandler.specialButtonDown, 3, GameClass.MID_POINT - 5, 314, 73);

        hero = new com.doublew.snowballfight.screens.battlescreen.objects.chars.Hero();

        leftButton = new ControlButton(1, new Callback() {
            @Override
            public void callback() {
                if(hero.getCurrentMode() == com.doublew.snowballfight.screens.battlescreen.objects.chars.CHAR_MODE.STILL || hero.getCurrentMode() == com.doublew.snowballfight.screens.battlescreen.objects.chars.CHAR_MODE.AIMING || hero.getCurrentMode() == com.doublew.snowballfight.screens.battlescreen.objects.chars.CHAR_MODE.THROWING) {
                    hero.moveLeft();
                }
            }
        });
        centerButton = new ControlButton(2, new Callback() {
            @Override
            public void callback() {
                if(hero.getCurrentMode() == com.doublew.snowballfight.screens.battlescreen.objects.chars.CHAR_MODE.STILL || hero.getCurrentMode() == com.doublew.snowballfight.screens.battlescreen.objects.chars.CHAR_MODE.AIMING || hero.getCurrentMode() == com.doublew.snowballfight.screens.battlescreen.objects.chars.CHAR_MODE.THROWING) {
                    hero.fire();
                }
            }
        });
        rightButton = new ControlButton(3, new Callback() {
            @Override
            public void callback() {
                if(hero.getCurrentMode() == com.doublew.snowballfight.screens.battlescreen.objects.chars.CHAR_MODE.STILL || hero.getCurrentMode() == com.doublew.snowballfight.screens.battlescreen.objects.chars.CHAR_MODE.AIMING || hero.getCurrentMode() == com.doublew.snowballfight.screens.battlescreen.objects.chars.CHAR_MODE.THROWING) {
                    hero.moveRight();
                }
            }
        });

        pauseButton = new PauseButton(new Callback() {
            @Override
            public void callback() {
                if(currentMode == BATTLE_MODE.BATTLE) {
                    screen.pause();
                } else if(currentMode == BATTLE_MODE.PAUSE) {
                    currentMode = savedBeforePauseMode;
                }
            }
        });

        savedEnemies = new ArrayList<com.doublew.snowballfight.screens.battlescreen.objects.chars.Enemy>();

        snowballs = new ArrayList<com.doublew.snowballfight.screens.battlescreen.objects.Snowball>();

        finalTop = new Rectangle(0, 0, 320, 0);
        finalBottom = new Rectangle(0, GameClass.GAME_HEIGHT, 320, GameClass.GAME_HEIGHT);

        // In seconds
        final float CLOSING_TIME = .6f;
        closingSpeedTop = (GameClass.MID_POINT + 80) / CLOSING_TIME;
        closingSpeedBottom = (GameClass.GAME_HEIGHT - (GameClass.MID_POINT + 145)) / CLOSING_TIME;


        if(saved) {
            enemies = new ArrayList<com.doublew.snowballfight.screens.battlescreen.objects.chars.Enemy>();
            for (EnemySave e: DataHandler.savedEnemies) {
                com.doublew.snowballfight.screens.battlescreen.objects.chars.Enemy toAdd = new com.doublew.snowballfight.screens.battlescreen.objects.chars.Enemy(-1, hero, e.isBoss);
                toAdd.loadFromSave(e);
                enemies.add(toAdd);
            }
            hero.setHP(DataHandler.heroHP);
            hero.setX(DataHandler.heroX);
        } else {
            generateEnemies(s.stageID, s.stageLevel);
//            enemies = new ArrayList<Enemy>();
//            enemies.add(new Enemy(hero, true));
//            enemies.add(new Enemy(hero, true));
//            enemies.add(new Enemy(hero, true));
//        enemies.add(new Enemy(hero, false));
//        enemies.add(new Enemy(hero, false));
//        enemies.add(new Enemy(hero, false));
        }


        sortEnemiesPerspective();

        particles = new ArrayList<com.doublew.snowballfight.screens.battlescreen.objects.particles.ParticleSystem>();
    }

    private void generateEnemies(int stageID, int stageLevel) {
        enemies = new ArrayList<com.doublew.snowballfight.screens.battlescreen.objects.chars.Enemy>();
        switch (stageID) {
            case 1:
                switch (stageLevel) {
                    case 1:
                        addEnemiesToLevel(0, 1);
                        break;
                    case 2:
                        addEnemiesToLevel(0, 2);
                        break;
                    default:
                        addEnemiesToLevel(0, 3);
                        break;
                }
                break;
            case 2:
                switch (stageLevel) {
                    case 1:
                        addEnemiesToLevel(0, 4);
                        break;
                    case 2:
                        addEnemiesToLevel(1, 0);
                        break;
                    default:
                        addEnemiesToLevel(1, 1);
                        break;
                }
                break;
            case 3:
                switch (stageLevel) {
                    case 1:
                        addEnemiesToLevel(1, 3);
                        break;
                    case 2:
                        addEnemiesToLevel(2, 0);
                        break;
                    default:
                        addEnemiesToLevel(1, 4);
                        break;
                }
                break;
            case 4:
                switch (stageLevel) {
                    case 1:
                        addEnemiesToLevel(2, 1);
                        break;
                    case 2:
                        addEnemiesToLevel(2, 2);
                        break;
                    default:
                        addEnemiesToLevel(3, 0);
                        break;
                }
                break;
        }
    }

    private int generateGold(int stageID) {
        switch (stageID) {
            case 1:
                return new Random().nextInt(7)+11;
            case 2:
                return new Random().nextInt(7)+17;
            case 3:
                return new Random().nextInt(7)+23;
            case 4:
                return new Random().nextInt(7)+29;
        }
        return 0;
    }

    private void addEnemiesToLevel(int bosses, int commons) {
        for(int i = 0; i < bosses; ++i) {
            addBoss();
        }

        for(int i = 0; i < commons; ++i) {
            addCommon();
        }
    }

    private void addCommon() {
        if(enemies == null) {
            return;
        }
        enemies.add(new com.doublew.snowballfight.screens.battlescreen.objects.chars.Enemy(screen.stageID, hero, false));
    }

    private void addBoss() {
        if(enemies == null) {
            return;
        }
        enemies.add(new com.doublew.snowballfight.screens.battlescreen.objects.chars.Enemy(screen.stageID, hero, true));
    }

    public void update(float delta) {
        switch (currentMode) {
            case BATTLE:
                hero.update(delta);
                for(com.doublew.snowballfight.screens.battlescreen.objects.particles.ParticleSystem s: particles) {
                    s.update(delta);
                }
                for(com.doublew.snowballfight.screens.battlescreen.objects.chars.Enemy e:enemies) {
                    e.update(delta);
                }
                for(com.doublew.snowballfight.screens.battlescreen.objects.Snowball s:snowballs) {
                    s.update(delta);
                }
                // Proceed and remove "arrived" last
                proceedRequests();
                proceedCompleteParticles();
                // If any enemy moved in y-axis, sort them
                for (com.doublew.snowballfight.screens.battlescreen.objects.chars.Enemy e:enemies) {
                    if(e.isMovedY()) {
                        sortEnemiesPerspective();
                        break;
                    }
                }

                if(enemiesDefeated() || hero.getCurrentMode() == com.doublew.snowballfight.screens.battlescreen.objects.chars.CHAR_MODE.DYING) {
                    if(enemiesDefeated()) {
                        hero.winning();
                        Sound.playVictory();
                    } else {
                        Sound.playLose();
                    }
                    currentMode = BATTLE_MODE.FINAL;
                }
                break;
            case PAUSE:
                break;
            case SPECIAL_CAST_1:
                if(modeTimer <= 0) {
                    currentMode = BATTLE_MODE.SPECIAL_CAST_2;
                    modeTimer = SPECIAL_CAST_2_TIME;
                    Sound.playSpecialSlide();
                } else {
                    modeTimer -= delta;
                }
                break;
            case SPECIAL_CAST_2:
                if(modeTimer <= 0) {
                    Sound.playExplosion();
                    currentMode = BATTLE_MODE.BATTLE;
                    modeTimer = 0;
                    specialSliderX = GameClass.GAME_WIDTH;
                    applySpecialEffect(lastSelectedSpecial);
                } else {
                    specialSliderX -= delta * SPECIAL_SLIDE_SPEED;
                    modeTimer -= delta;
                }
                break;
            case FINAL:
                hero.update(delta);
                if(hero.won) {
                    if(hero.readyToQuit) {
                        // GAME WIN
                        if(!tryToPromote()) {
                            int gold = generateGold(screen.stageID);
                            DataHandler.gold += gold;
                            switch (newStage) {
                                case 2:
                                    screen.gameClass.gotoScreenMap("Western Boys", "challenged you!!!");
                                    break;
                                case 3:
                                    screen.gameClass.gotoScreenMap("Southern Boys", "challenged you!!!");
                                    break;
                                case 4:
                                    screen.gameClass.gotoScreenMap("Northern Boys", "challenged you!!!");
                                    break;
                                default:
                                    screen.gameClass.gotoScreenMap("You earned", gold+" gold!");
                                    break;
                            }

                        } else {
                            // WIN THE GAME (END)
                            screen.gameClass.gotoScreenFinish(321);
                        }
                    }
                } else {
                    if(hero.readyToQuit) {
                        Gdx.app.log("GAME", "OVER");
                        screen.gameClass.gotoScreenFinish(-1);
                    } else {
                        if (finalTop.height <= GameClass.MID_POINT + 80) {
                            finalTop.height += closingSpeedTop * delta;
                        }
                        if (finalBottom.y >= GameClass.MID_POINT + 145) {
                            finalBottom.y -= closingSpeedBottom * delta;
                        }
                    }
                }
                break;
        }
    }

    public void applySpecialEffect(int specialID) {
        for (com.doublew.snowballfight.screens.battlescreen.objects.chars.Enemy e:enemies) {
            switch (specialID) {
                case 0:
                    e.takeDamage(new com.doublew.snowballfight.screens.battlescreen.objects.Snowball(0,0,0,0,false, hero.damage, 0, 0, 0));
                    break;
                case 1:
                    e.takeDamage(new com.doublew.snowballfight.screens.battlescreen.objects.Snowball(0,0,0,0,false, hero.damage, 0, 0, 1));
                    break;
                case 2:
                    e.takeDamage(new com.doublew.snowballfight.screens.battlescreen.objects.Snowball(0,0,0,0,false, hero.damage, 0, 0, 3));
                    break;
            }
        }
    }

    // Check if need to promote, promote if succeed
    public boolean tryToPromote() {
        if(screen.stageID == DataHandler.maxStage && screen.stageLevel == DataHandler.maxLevel) {
            if(DataHandler.maxStage != 4 || DataHandler.maxLevel != 3) {
                if(DataHandler.maxStage == 4 && DataHandler.maxLevel == 4) {
                    return false;
                }

                if(screen.stageLevel != 3) {
                    DataHandler.maxLevel++;
                } else {
                    DataHandler.maxStage++;
                    DataHandler.maxLevel = 1;
                    newStage = DataHandler.maxStage;
                }
                Gdx.app.log("GAME", "NEW PROGRESS "+ DataHandler.maxStage + ":"+ DataHandler.maxLevel);
            } else {
//                // TODO: 03/01/2018 WON
                int gold = generateGold(screen.stageID);
                DataHandler.gold += gold;

                Gdx.app.log("GAME", "VICTORY");
                if(screen.stageLevel == 3) {
                    DataHandler.maxLevel++;
                    return true;
                }
                return false;
            }
        }
        return false;
    }

    private void proceedRequests() {
        ArrayList<com.doublew.snowballfight.screens.battlescreen.objects.Snowball>sToRemove = new ArrayList<com.doublew.snowballfight.screens.battlescreen.objects.Snowball>();
        ArrayList<com.doublew.snowballfight.screens.battlescreen.objects.chars.Enemy>eToRemove = new ArrayList<com.doublew.snowballfight.screens.battlescreen.objects.chars.Enemy>();
        // Process "create snowball" request
        // Check them (add to "toAdd" array)
        if(hero.isRequested()) {
            Sound.playFire();
            snowballs.add(hero.getRequestedSnowball());
            hero.requestCompleted();
        }
        for(com.doublew.snowballfight.screens.battlescreen.objects.chars.Enemy e:enemies) {
            if(e.isRequested()) {
                Sound.playFire2();
                snowballs.add(e.getRequestSnowball());
                e.requestCompleted();
            }
        }

        // Check(add to toRemove list) all arrived snowballs
        for (com.doublew.snowballfight.screens.battlescreen.objects.Snowball s: snowballs) {
            if(s.isArrived()) {
                sToRemove.add(s);
            }
        }
        for (com.doublew.snowballfight.screens.battlescreen.objects.chars.Enemy e: enemies) {
            if(e.readyToBeRemoved()) {
                eToRemove.add(e);
            }
        }

        if(!sToRemove.isEmpty()) {
            proceedSnowballHits();
            for (com.doublew.snowballfight.screens.battlescreen.objects.Snowball s: sToRemove) {
                snowballs.remove(s);
            }
            sToRemove.clear();
        }
        if(!eToRemove.isEmpty()) {
            for(com.doublew.snowballfight.screens.battlescreen.objects.chars.Enemy e: eToRemove) {
                enemies.remove(e);
            }
            eToRemove.clear();
        }
    }

    private void proceedSnowballHits() {
        // Check if snowball hit
        for (com.doublew.snowballfight.screens.battlescreen.objects.Snowball s: snowballs) {
            //  Check if object is instance of Snowball class (there is enemy's dead body in this list too)
            if(s.isArrived()) {
                if(s.isFromEnemy()) {
                    // New hero-snowball overlaps logic!
                    if(s.getSentX() == hero.x) {
                        if(hero.takeDamage(s)) {
                            // Hit
                            particles.add(new CrashParticleSystem(s.getBounds().x, s.getBounds().y, Color.GRAY, Color.WHITE, .2f));
                        } else {
                            // Miss
                            particles.add(new com.doublew.snowballfight.screens.battlescreen.objects.particles.DrownParticleSystem(hero.getBounds().x, hero.getBounds().y, "miss", false));
                        }
                    }
                } else {
                    for (com.doublew.snowballfight.screens.battlescreen.objects.chars.Enemy e: enemies) {
                        // New enemy-snowball overlaps logic!
                        if(Math.abs(s.getSentY() - e.y) <= 1 && s.getSentX() == e.x) {
                            particles.add(new CrashParticleSystem(s.getBounds().x, s.getBounds().y, Color.WHITE, Color.GRAY, .2f));
                            if(e.takeDamage(s)) {
                                // Critical
                                particles.add(new com.doublew.snowballfight.screens.battlescreen.objects.particles.DrownParticleSystem(e.getBounds().x, e.getBounds().y, "critical", true));
                            } else {
                                // Common hit
                            }

                        }
                    }
                }
            }
        }
    }

    private void proceedCompleteParticles() {
        ArrayList<com.doublew.snowballfight.screens.battlescreen.objects.particles.ParticleSystem>sToRemove = new ArrayList<com.doublew.snowballfight.screens.battlescreen.objects.particles.ParticleSystem>();
        for (com.doublew.snowballfight.screens.battlescreen.objects.particles.ParticleSystem s : particles) {
            if(s.isComplete()) {
                sToRemove.add(s);
            }
        }

        if(!sToRemove.isEmpty()) {
            for (com.doublew.snowballfight.screens.battlescreen.objects.particles.ParticleSystem s:sToRemove) {
                s.dispose();
                particles.remove(s);
            }
        }
    }

    private void sortEnemiesPerspective() {
        // Sort enemies by perspective
        // 1 - Save all enemies to list
        for (com.doublew.snowballfight.screens.battlescreen.objects.chars.Enemy e: enemies) {
            savedEnemies.add(e);
        }
        // If there's no enemy - don't proceed
        if(!savedEnemies.isEmpty()) {
            // 2 - Delete all enemies from original lists
            for (com.doublew.snowballfight.screens.battlescreen.objects.chars.Enemy e:savedEnemies) {
                enemies.remove(e);
            }
            // 3 - Sort saved list
            for(int i = 0; i < savedEnemies.size(); ++i) {
                for(int j = i + 1; j < savedEnemies.size(); ++j) {
                    if(savedEnemies.get(j).getBounds().y < savedEnemies.get(i).getBounds().y) {
                        com.doublew.snowballfight.screens.battlescreen.objects.chars.Enemy buffer = savedEnemies.get(i);
                        savedEnemies.set(i, savedEnemies.get(j));
                        savedEnemies.set(j, buffer);
                    }
                }
            }
            // 4 - Insert all enemies back and proceed movedY flag
            for (com.doublew.snowballfight.screens.battlescreen.objects.chars.Enemy e:savedEnemies) {
                e.movedYCompleted();
                enemies.add(e);
            }
            savedEnemies.clear();
        }
    }

    public boolean enemiesDefeated() {
        return enemies.isEmpty();
    }
}
