package com.doublew.snowballfight.screens.battlescreen.objects.chars;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.doublew.snowballfight.main.GameClass;
import com.doublew.snowballfight.support.AssetHandler;
import com.doublew.snowballfight.support.Sound;

import java.util.Random;

/**
 * Created by Leonemsolis on 15/10/2017.
 *
 * centered x-coordinates for Battle Character
 */



public class Hero {
    private Rectangle bounds;
    private final float MAX_HP = 100;
    private float HP = MAX_HP;
    public int damage = 10;
    private Animation still, shiftLeft, shiftRight, aiming, throwing, winning;
    private com.doublew.snowballfight.screens.battlescreen.objects.chars.CHAR_MODE currentMode;
    private TextureRegion currentTexture = null;
    private final float TIME_STUNNED = 3f;
    private final float TIME_FROZEN = 2f;
    private final float SHIFT_TIME = 0.2f;
    private final float SHIFT_SPEED = 45 / 0.3f;
    private float shiftTimer = 0f;
    private float shiftDestX = 0f;
    private boolean request = false;

    public float criticalChance = .3f; //.3
    public float dodgeChance = .3f; //.3

    private final float THROW_TIME = 0.5f;
    private float throwTimer = 0f;

//    private float winningTimer = 4f;
    private float winningTimer = 1f;

    private com.doublew.snowballfight.screens.battlescreen.objects.chars.CHAR_MODE lastMode;

    private com.doublew.snowballfight.screens.battlescreen.objects.Snowball requestedSnowball;
    private Effects effects;

    // 0 - Boot
    // 1 - Boulder
    // 2 - Frozen Snowball
    // 3 - Icicle
    // 4 - Basic Snowball
    public int nextAttackModifier = 4;

    private int power = 0;
    private final float FILL_TIME = .4f;
    private float fillTimePassed = 0;

    private final float DYING_TIME = 4.0f;
    private float dyingTimer = 0;

    private final float DAMAGED_TIME = 1.4f;
    private float damagedTimer = 0;
    private final float FLICK_TIME = .1f;
    private float flickTimer = 0;
    private boolean flicker = false;

    public boolean readyToQuit = false;
    public boolean won = false;

    public int x;

    private Random r;


    public Hero() {
        effects = new Effects();
        currentMode = com.doublew.snowballfight.screens.battlescreen.objects.chars.CHAR_MODE.STILL;
        loadAnimationAssets();
        bounds = new Rectangle(GameClass.GAME_WIDTH / 2, GameClass.MID_POINT + 80, 45, 60);
        x = 0;
        r = new Random();
    }

    public void moveLeft() {
        if(bounds.x - 60 < 0) {
            return;
        }
        if(currentMode == com.doublew.snowballfight.screens.battlescreen.objects.chars.CHAR_MODE.THROWING) {
            lastMode = com.doublew.snowballfight.screens.battlescreen.objects.chars.CHAR_MODE.STILL;
        } else {
            lastMode = currentMode;
        }

        currentMode = com.doublew.snowballfight.screens.battlescreen.objects.chars.CHAR_MODE.SHIFT_LEFT;
        shiftTimer = SHIFT_TIME;
        shiftDestX = bounds.x - 45;
        x--;
    }

    public void restoreHP(int hp) {
        if(HP + hp <= MAX_HP) {
            HP += hp;
        } else {
            HP = MAX_HP;
        }
    }

    public void moveRight() {
        if(bounds.x + 60 > 320) {
            return;
        }
        if(currentMode == com.doublew.snowballfight.screens.battlescreen.objects.chars.CHAR_MODE.THROWING) {
            lastMode = com.doublew.snowballfight.screens.battlescreen.objects.chars.CHAR_MODE.STILL;
        } else {
            lastMode = currentMode;
        }
        currentMode = com.doublew.snowballfight.screens.battlescreen.objects.chars.CHAR_MODE.SHIFT_RIGHT;
        shiftTimer = SHIFT_TIME;
        shiftDestX = bounds.x + 45;
        x++;
    }

    public void fire() {
        if(currentMode == com.doublew.snowballfight.screens.battlescreen.objects.chars.CHAR_MODE.STILL || currentMode == com.doublew.snowballfight.screens.battlescreen.objects.chars.CHAR_MODE.THROWING) {
            currentMode = com.doublew.snowballfight.screens.battlescreen.objects.chars.CHAR_MODE.AIMING;
            power = 0;
        } else if(currentMode == com.doublew.snowballfight.screens.battlescreen.objects.chars.CHAR_MODE.AIMING) {
            if(power != 0) {
                throwTimer = THROW_TIME;
                currentMode = com.doublew.snowballfight.screens.battlescreen.objects.chars.CHAR_MODE.THROWING;
                request = true;
                requestedSnowball = new com.doublew.snowballfight.screens.battlescreen.objects.Snowball(bounds.x + 5, bounds.y + 20, bounds.x - 10, GameClass.MID_POINT - 111 + (7 - power) * 20, false, damage, x, 7 - power, nextAttackModifier);
                nextAttackModifier = 4;
            } else {
                throwTimer = .01f;
                currentMode = com.doublew.snowballfight.screens.battlescreen.objects.chars.CHAR_MODE.THROWING;
            }
            fillTimePassed = 0;
        }
    }

    public com.doublew.snowballfight.screens.battlescreen.objects.Snowball getRequestedSnowball() {
        return requestedSnowball;
    }

    public boolean isRequested() {
        return request;
    }

    public void requestCompleted() {
        request = false;
    }

    public boolean isFrozen() {
        return effects.containsFrozen();
    }

    public boolean isStunned() {
        return !effects.isEmpty() && !effects.containsFrozen();
    }

    public void update(float delta) {
        effects.update(delta);
        switch (currentMode) {
            case SHIFT_LEFT:
                if(shiftTimer > 0) {
                    shiftTimer -= delta;
                    bounds.x -= delta * SHIFT_SPEED;
                } else {
                    bounds.x = shiftDestX;
                    currentMode = lastMode;
                }
                break;
            case SHIFT_RIGHT:
                if(shiftTimer > 0) {
                    shiftTimer -= delta;
                    bounds.x += delta * SHIFT_SPEED;
                } else {
                    bounds.x = shiftDestX;
                    currentMode = lastMode;
                }
                break;
            case AIMING:
                if(power < 7) {
                    fillTimePassed += delta;
                    power = (int)(fillTimePassed / FILL_TIME);
                }
                break;
            case THROWING:
                throwTimer -= delta;
                if(throwTimer <= 0) {
                    currentMode = com.doublew.snowballfight.screens.battlescreen.objects.chars.CHAR_MODE.STILL;
                    throwing = new Animation(0.4f, AssetHandler.battleThrowing);
                }
                break;
            case DYING:
                effects.clearAllEffects();
                if(dyingTimer > 0) {
                    dyingTimer -= delta;
                } else {
                    if(!readyToQuit) {
                        readyToQuit = true;
                    }
                }
                break;
            case WINNING:
                if(!won) {
                    won = true;
                    effects.clearAllEffects();
                }
                if(winningTimer > 0) {
                    winningTimer -= delta;
                } else {
                    if(!readyToQuit) {
                        readyToQuit = true;
                    }
                }
                break;
        }
        if(damagedTimer > 0) {
            damagedTimer -= delta;
            if(flickTimer > 0) {
                flickTimer -= delta;
            } else{
                flickTimer = FLICK_TIME;
                flicker = !flicker;
            }
        }
    }

    public void render(float delta, float runTime, SpriteBatch batch) {
        if(!isFrozen()) {
            switch (currentMode) {
                case STILL:
                    currentTexture = (TextureRegion) still.getKeyFrame(runTime);
                    break;
                case SHIFT_LEFT:
                    currentTexture = (TextureRegion) shiftLeft.getKeyFrame(runTime);
                    break;
                case SHIFT_RIGHT:
                    currentTexture = (TextureRegion) shiftRight.getKeyFrame(runTime);
                    break;
                case AIMING:
                    drawModifierBadge(batch, runTime);
                    currentTexture = (TextureRegion) aiming.getKeyFrame(runTime);
                    if(bounds.x < GameClass.GAME_WIDTH / 2) {
                        batch.draw(AssetHandler.redSquare, bounds.x+30, bounds.y+bounds.height- 2 - (power * 8), 5, power * 8);
                        batch.draw(AssetHandler.powerBar, bounds.x+25, bounds.y+bounds.height-60, 15, 60);
                    } else {
                        batch.draw(AssetHandler.redSquare, bounds.x-35, bounds.y+bounds.height- 2 - (power * 8), 5, power * 8);
                        batch.draw(AssetHandler.powerBar, bounds.x-40, bounds.y+bounds.height-60, 15, 60);
                    }
                    break;
                case THROWING:
                    currentTexture = (TextureRegion) throwing.getKeyFrame(runTime);
                    break;
                case DYING:
                    currentTexture = AssetHandler.battleDying;
                    break;
                case WINNING:
                    currentTexture = (TextureRegion) winning.getKeyFrame(runTime);
                    break;
            }
            if(damagedTimer > 0 && flicker && (currentMode != com.doublew.snowballfight.screens.battlescreen.objects.chars.CHAR_MODE.DYING)) {
                batch.setColor(Color.RED);
                batch.draw(currentTexture, bounds.x-currentTexture.getRegionWidth()/2, bounds.y+bounds.height-currentTexture.getRegionHeight(), currentTexture.getRegionWidth(), currentTexture.getRegionHeight());
                batch.setColor(Color.WHITE);
            } else {
                batch.draw(currentTexture, bounds.x-currentTexture.getRegionWidth()/2, bounds.y+bounds.height-currentTexture.getRegionHeight(), currentTexture.getRegionWidth(), currentTexture.getRegionHeight());
            }
        } else {
            if(damagedTimer > 0 && flicker && (currentMode != com.doublew.snowballfight.screens.battlescreen.objects.chars.CHAR_MODE.DYING)) {
                batch.setColor(Color.RED);
                batch.draw(AssetHandler.frozen, bounds.x-currentTexture.getRegionWidth()/2, bounds.y+bounds.height-currentTexture.getRegionHeight(), currentTexture.getRegionWidth(), currentTexture.getRegionHeight());
                batch.setColor(Color.WHITE);
            } else {
                batch.draw(AssetHandler.frozen, bounds.x-currentTexture.getRegionWidth()/2, bounds.y+bounds.height-currentTexture.getRegionHeight(), currentTexture.getRegionWidth(), currentTexture.getRegionHeight());
            }
        }

        if(isStunned()) {
            batch.draw(AssetHandler.stunWhirligig, bounds.x- 18, bounds.y-23, 18, 9, 35, 18, 1, 1, - runTime * 100);
        }
    }

    private void drawModifierBadge(SpriteBatch batch, float runTime) {
        Color c = batch.getColor();
        batch.setColor(c.r, c.g, c.b, .4f);
            switch (nextAttackModifier) {
                case 0:
                    batch.draw(AssetHandler.itemsSmall[0], bounds.x - 6, bounds.y + currentTexture.getRegionHeight() + 4, 12, 12);
                    break;
                case 1:
                    batch.draw(AssetHandler.itemsSmall[1], bounds.x - 6, bounds.y + currentTexture.getRegionHeight() + 4, 12, 12);
                    break;
                case 2:
                    batch.draw(AssetHandler.itemsSmall[2], bounds.x - 6, bounds.y + currentTexture.getRegionHeight() + 4, 12, 12);
                    break;
                case 3:
                    batch.draw(AssetHandler.itemsSmall[3], bounds.x - 6, bounds.y + currentTexture.getRegionHeight() + 4, 12, 12);
                    break;
                case 4:
                    batch.draw(AssetHandler.itemsSmall[8], bounds.x - 6, bounds.y + currentTexture.getRegionHeight() + 4, 12, 12);
                    break;
            }

        c = batch.getColor();
        batch.setColor(c.r, c.g, c.b, 1f);
    }

    private void loadAnimationAssets() {
        still = new Animation(.3f, AssetHandler.battleStill);
        still.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

        shiftLeft = new Animation(.2f, AssetHandler.battleLeft);
        shiftLeft.setPlayMode(Animation.PlayMode.LOOP);

        shiftRight = new Animation(.2f, AssetHandler.battleRight);
        shiftRight.setPlayMode(Animation.PlayMode.LOOP);

        aiming = new Animation(.2f, AssetHandler.battleAiming);
        aiming.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

        throwing = new Animation(.4f, AssetHandler.battleThrowing);
        throwing.setPlayMode(Animation.PlayMode.LOOP);

        winning = new Animation(1f, AssetHandler.battleWinning);
        winning.setPlayMode(Animation.PlayMode.LOOP);
    }

    public com.doublew.snowballfight.screens.battlescreen.objects.chars.CHAR_MODE getCurrentMode()  {
        return currentMode;
    }

    public Rectangle getHitBox() {
        return new Rectangle(bounds.x-currentTexture.getRegionWidth()/2, bounds.y+bounds.height-currentTexture.getRegionHeight(), currentTexture.getRegionWidth(), currentTexture.getRegionHeight());
    }

    public float getHP() {
        return HP;
    }

    public float getMAX_HP() {
        return MAX_HP;
    }

    public boolean takeDamage(com.doublew.snowballfight.screens.battlescreen.objects.Snowball incoming) {
        if(r.nextFloat() < dodgeChance) {
            return false;
        }
        effects.addEffect(incoming.getModifier(), getTimeOfModifier(incoming.getModifier()));
        Sound.playHurt();
        if(HP - incoming.getDamage() <= 0) {
            currentMode = com.doublew.snowballfight.screens.battlescreen.objects.chars.CHAR_MODE.DYING;
            dyingTimer = DYING_TIME;
            won = false;
            HP = 0;
            return true;
        }
        HP -= incoming.getDamage();
        damagedTimer = DAMAGED_TIME;
        return true;
    }

    private float getTimeOfModifier(int modifierID) {
        switch (modifierID) {
            case 0:
                return TIME_STUNNED;
            case 1:
                return 0;
            case 2:
            case 3:
                return TIME_FROZEN;
        }
        return 0;
    }

    public Rectangle getBounds() {
        return  bounds;
    }

    public void setX(int xx) {
        this.x = xx;
        bounds.x += xx * 45;
    }

    public void setHP(float HP) {
        this.HP = HP;
    }

    public void winning() {
        currentMode = com.doublew.snowballfight.screens.battlescreen.objects.chars.CHAR_MODE.WINNING;
    }
}
