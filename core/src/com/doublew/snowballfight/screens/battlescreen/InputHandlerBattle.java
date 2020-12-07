package com.doublew.snowballfight.screens.battlescreen;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Rectangle;
import com.doublew.snowballfight.main.GameClass;
import com.doublew.snowballfight.screens.blueprints.InputHandler;
import com.doublew.snowballfight.screens.battlescreen.objects.BATTLE_MODE;
import com.doublew.snowballfight.support.DataHandler;

/**
 * Created by Leonemsolis on 12/10/2017.
 */

public class InputHandlerBattle extends InputHandler {
    private ObjectHandlerBattle handler;
    private Rectangle[] itemSlots;

    public InputHandlerBattle(final ObjectHandlerBattle handler) {
        this.handler = handler;
        itemSlots  = new Rectangle[5];
        for(int i = 0; i < 5; ++i) {
            itemSlots[i] = new Rectangle(6 + 63 * i, GameClass.MID_POINT - 71 + 5, 56, 56);
        }
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        screenX = scale(screenX);
        screenY = scale(screenY);

        if(handler.currentMode != BATTLE_MODE.PAUSE) {
            if(!handler.hero.isFrozen()) {
                handler.leftButton.touchDown(screenX, screenY);
                handler.rightButton.touchDown(screenX, screenY);
                if(!handler.hero.isStunned()) {
                    handler.centerButton.touchDown(screenX, screenY);
                }
            }
        } else {
            if(handler.savedBeforePauseMode != BATTLE_MODE.SPECIAL_CAST_1 && handler.savedBeforePauseMode != BATTLE_MODE.SPECIAL_CAST_2) {
                if(DataHandler.heroMana != 0) {
                    handler.specialButton.touchDown(screenX, screenY);
                }
            }
        }

        if(handler.currentMode != BATTLE_MODE.FINAL) {
            handler.pauseButton.touchDown(screenX, screenY);
        }

        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        screenX = scale(screenX);
        screenY = scale(screenY);

        if(handler.currentMode != BATTLE_MODE.PAUSE) {
            handler.leftButton.touchUp(screenX, screenY);
            handler.centerButton.touchUp(screenX, screenY);
            handler.rightButton.touchUp(screenX, screenY);
        } else {
            if(handler.savedBeforePauseMode != BATTLE_MODE.SPECIAL_CAST_1 && handler.savedBeforePauseMode != BATTLE_MODE.SPECIAL_CAST_2) {
                handler.specialButton.touchUp(screenX, screenY);
                for (int i = 0; i < DataHandler.items.size(); ++i) {
                    if (itemSlots[i].contains(screenX, screenY)) {
                        handler.currentMode = handler.savedBeforePauseMode;

                        switch (DataHandler.items.get(i).id) {
                            case 0: // Boot
                                // Next attack disarm enemy
                                handler.hero.nextAttackModifier = 0;
                                break;
                            case 1: // Boulder
                                // Next attack deals double damage
                                handler.hero.nextAttackModifier = 1;
                                break;
                            case 2: // Frozen Snowball
                                // Next attack freeze enemy
                                handler.hero.nextAttackModifier = 2;
                                break;
                            case 3: // Icicle
                                // Next attack deals double damage & freeze enemy
                                handler.hero.nextAttackModifier = 3;
                                break;
                            case 4: // Bandage
                                // Restore 30 HP
                                handler.hero.restoreHP(30);
                                break;
                            case 5: // Pill
                                // Restore 1 MP
                                if (DataHandler.heroMana < 3) {
                                    DataHandler.heroMana++;
                                }
                                break;
                            case 6: // Painkiller
                                // Restore full HP
                                handler.hero.restoreHP(100);
                                break;
                            case 7: // Potion
                                // Double damage
                                handler.hero.damage *= 2;
                                break;
                        }

                        DataHandler.items.remove(i);
                    }
                }
            }
        }
        // TODO: 14/02/2018 Если у вас 1 единица маны, то умение нанесет урон всем оппонентам, а также их оглушит. \nЕсли у вас 2 единицы маны, то умение нанесет двойной урон всем оппонентам. \nЕсли у вас 3 единицы маны, то ваше умение нанесет всем оппонентам двойной урон, а также их заморозит.

        if(handler.currentMode != BATTLE_MODE.FINAL) {
            handler.pauseButton.touchUp(screenX, screenY);
        }


        return false;
    }

    // TODO: 17/11/2017 REMOVE?
    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.LEFT:
                handler.leftButton.callback.callback();
                break;
            case Input.Keys.RIGHT:
                handler.rightButton.callback.callback();
                break;
            case Input.Keys.UP:
            case Input.Keys.DOWN:
                handler.centerButton.callback.callback();
                break;
        }
        return false;
    }


}
