package com.doublew.snowballfight.screens.battlescreen.objects.chars;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.doublew.snowballfight.main.GameClass;
import com.doublew.snowballfight.support.AssetHandler;
import com.doublew.snowballfight.support.Sound;

import java.util.Random;


/**
 * Created by Leonemsolis on 20/10/2017.
 */

public class Enemy {
    private float MAX_HP = 20;
    public float HP;
    public int damage = 7;
    private TextureRegion texture;
    public int skinID;
    private final float ACTION_DELAY_TIME;
    private float lastActionTimePassed = 0;
    private com.doublew.snowballfight.screens.battlescreen.objects.chars.CHAR_MODE currentMode = com.doublew.snowballfight.screens.battlescreen.objects.chars.CHAR_MODE.STILL;
    public int x, y;
    private Hero hero;
    private Random random;
    private Animation still, shift, aiming;
    private TextureRegion throwing, dying;
    private boolean remove = false;
    private com.doublew.snowballfight.screens.battlescreen.objects.Snowball requestSnowball;
    private float modifiedSnowballChance = .3f;

    private final float TIME_SHIFT = 0.4f;
    private final float TIME_AIMING = 1.0f;
    private final float TIME_THROWING = 0.4f;
    private final float TIME_STUNNED = 6f;
    private final float TIME_FROZEN = 8f;
    private final float TIME_DYING = 3f;
    private boolean request = false;
    private final float SHIFT_SPEED_HOR = 45 / TIME_SHIFT;
    private final float SHIFT_SPEED_VER = 20 / TIME_SHIFT;
    private float shiftDestination = 0f;

    private final float FLICK_TIME = 0.03f;
    private float flickTimer = 0;
    private boolean flicker = false;

    private boolean movedY = false;

    private final float HP_BAR_TIMEOUT = 0.7f;
    private float HPBarTime = 0;
    private Rectangle bounds;

    public boolean isBoss = false;

    public com.doublew.snowballfight.screens.battlescreen.objects.chars.Effects effects;

    public Enemy(int stageID, Hero hero, boolean isBoss) {
        effects = new com.doublew.snowballfight.screens.battlescreen.objects.chars.Effects();
        random = new Random();
        this.isBoss = isBoss;
        this.hero = hero;
        if (isBoss) {
            if (stageID != -1) {
                switch (stageID) {
                    case 1:
                        MAX_HP = 50;
                        damage = 20;
                        break;
                    case 2:
                        MAX_HP = 56;
                        damage = 27;
                        break;
                    case 3:
                        MAX_HP = 62;
                        damage = 36;
                        break;
                    case 4:
                        MAX_HP = 64;
                        damage = 39;
                        break;
                }
            }
        } else {
            if (stageID != -1) {
                switch (stageID) {
                    case 1:
                        MAX_HP = 30;
                        damage = 9;
                        break;
                    case 2:
                        MAX_HP = 34;
                        damage = 12;
                        break;
                    case 3:
                        MAX_HP = 41;
                        damage = 17;
                        break;
                    case 4:
                        MAX_HP = 46;
                        damage = 19;
                        break;
                }
            }
        }
        HP = MAX_HP;

        texture = new TextureRegion();
        x = random.nextInt(7) - 3;
        y = random.nextInt(7);
        // DELAY = 0.7 ~ 1.3 seconds
        ACTION_DELAY_TIME = .1f;//random.nextFloat() * 0.5f + 0.7f;
        // Centered X, Standard Y
        bounds = new Rectangle(GameClass.GAME_WIDTH / 2 + ((x) * 45), GameClass.MID_POINT - 121 + y * 20, 45, 20);
        setSkin(-1);
//        MAX_HP = 1;
//        HP = MAX_HP;
    }

    public void loadFromSave(com.doublew.snowballfight.screens.battlescreen.objects.chars.EnemySave save) {
        this.isBoss = save.isBoss;
        this.x = save.x;
        this.y = save.y;
        this.damage = save.damage;
        this.HP = save.hp;
        bounds = new Rectangle(GameClass.GAME_WIDTH / 2 + ((x) * 45), GameClass.MID_POINT - 121 + y * 20, 45, 20);
        setSkin(save.skinID);
    }

    public void setSkin(int customSkinID) {
        if (isBoss) {
            if (customSkinID == -1) {
                skinID = random.nextInt(4);
            } else {
                skinID = customSkinID;
            }
            switch (skinID) {
                case 0:
                    still = new Animation(0.5f, AssetHandler.bossSkin1_still);
                    shift = new Animation(TIME_SHIFT, AssetHandler.bossSkin1_still);
                    aiming = new Animation(TIME_AIMING, AssetHandler.bossSkin1_still);
                    throwing = AssetHandler.bossSkin1_throwing;
                    dying = AssetHandler.bossSkin1_dying;
                    break;
                case 1:
                    still = new Animation(0.5f, AssetHandler.bossSkin2_still);
                    shift = new Animation(TIME_SHIFT, AssetHandler.bossSkin2_still);
                    aiming = new Animation(TIME_AIMING, AssetHandler.bossSkin2_still);
                    throwing = AssetHandler.bossSkin2_throwing;
                    dying = AssetHandler.bossSkin2_dying;
                    break;
                case 2:
                    still = new Animation(0.5f, AssetHandler.bossSkin3_still);
                    shift = new Animation(TIME_SHIFT, AssetHandler.bossSkin3_still);
                    aiming = new Animation(TIME_AIMING, AssetHandler.bossSkin3_still);
                    throwing = AssetHandler.bossSkin3_throwing;
                    dying = AssetHandler.bossSkin3_dying;
                    break;
                case 3:
                    still = new Animation(0.5f, AssetHandler.bossSkin4_still);
                    shift = new Animation(TIME_SHIFT, AssetHandler.bossSkin4_still);
                    aiming = new Animation(TIME_AIMING, AssetHandler.bossSkin4_still);
                    throwing = AssetHandler.bossSkin4_throwing;
                    dying = AssetHandler.bossSkin4_dying;
                    break;
            }
        } else {
            if (customSkinID == -1) {
                skinID = random.nextInt(2);
            } else {
                skinID = customSkinID;
            }
            if (skinID == 1) {
                // Skin 1
                still = new Animation(0.5f, AssetHandler.enemySkin1_still);
                shift = new Animation(TIME_SHIFT, AssetHandler.enemySkin1_still);
                aiming = new Animation(TIME_AIMING, AssetHandler.enemySkin1_still);
                throwing = AssetHandler.enemySkin1_throwing;
                dying = AssetHandler.enemySkin1_dying;
            } else {
                // Skin 2
                still = new Animation(0.5f, AssetHandler.enemySkin2_still);
                shift = new Animation(TIME_SHIFT, AssetHandler.enemySkin2_still);
                aiming = new Animation(TIME_AIMING, AssetHandler.enemySkin2_still);
                throwing = AssetHandler.enemySkin2_throwing;
                dying = AssetHandler.enemySkin2_dying;
            }
        }
        still.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
        shift.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
        aiming.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
    }

    public void requestCompleted() {
        request = false;
    }

    public boolean isRequested() {
        return request;
    }

    public com.doublew.snowballfight.screens.battlescreen.objects.Snowball getRequestSnowball() {
        return requestSnowball;
    }

    public void update(float delta) {
        effects.update(delta);
        if (currentMode != com.doublew.snowballfight.screens.battlescreen.objects.chars.CHAR_MODE.DYING && HP <= 0) {
            lastActionTimePassed = TIME_DYING;
            currentMode = com.doublew.snowballfight.screens.battlescreen.objects.chars.CHAR_MODE.DYING;
            effects.clearAllEffects();
        }
        if (HPBarTime != 0) {
            if (HPBarTime - delta <= 0) {
                HPBarTime = 0;
            } else {
                HPBarTime -= delta;
            }
        }
        switch (currentMode) {
            case STILL:
                lastActionTimePassed += delta;
                if (lastActionTimePassed >= ACTION_DELAY_TIME) {
                    lastActionTimePassed = 0;
                    if(!effects.containsFrozen()) {
                        act();
                    }
                }
                break;
            case SHIFT_LEFT:
                bounds.x -= delta * SHIFT_SPEED_HOR;
                if (lastActionTimePassed <= 0) {
                    currentMode = com.doublew.snowballfight.screens.battlescreen.objects.chars.CHAR_MODE.STILL;
                    bounds.x = shiftDestination;
                }
                break;
            case SHIFT_RIGHT:
                bounds.x += delta * SHIFT_SPEED_HOR;
                if (lastActionTimePassed <= 0) {
                    currentMode = com.doublew.snowballfight.screens.battlescreen.objects.chars.CHAR_MODE.STILL;
                    bounds.x = shiftDestination;
                }
                break;
            case SHIFT_FRONT:
                bounds.y += delta * SHIFT_SPEED_VER;
                if (lastActionTimePassed <= 0) {
                    currentMode = com.doublew.snowballfight.screens.battlescreen.objects.chars.CHAR_MODE.STILL;
                    bounds.y = shiftDestination;
                }
                break;
            case SHIFT_BACK:
                bounds.y -= delta * SHIFT_SPEED_VER;
                if (lastActionTimePassed <= 0) {
                    currentMode = com.doublew.snowballfight.screens.battlescreen.objects.chars.CHAR_MODE.STILL;
                    bounds.y = shiftDestination;
                }
                break;
            case THROWING:
                if (flickTimer <= 0) {
                    flickTimer = FLICK_TIME;
                    flicker = !flicker;
                } else {
                    flickTimer -= delta;
                }
                if (lastActionTimePassed <= 0 && !request) {
                    currentMode = com.doublew.snowballfight.screens.battlescreen.objects.chars.CHAR_MODE.STILL;
                }
                break;
            case DYING:
                if (lastActionTimePassed <= 0) {
                    remove = true;
                }
                break;
            case AIMING:
                if (flickTimer <= 0) {
                    flickTimer = FLICK_TIME;
                    flicker = !flicker;
                } else {
                    flickTimer -= delta;
                }
                if (lastActionTimePassed <= 0) {
                    if(random.nextFloat() <= modifiedSnowballChance) {
                        requestSnowball = new com.doublew.snowballfight.screens.battlescreen.objects.Snowball(bounds.x, bounds.y + 40, hero.getBounds().x, hero.getBounds().y, true, damage, hero.x, 0, random.nextInt(4));
//                        requestSnowball = new Snowball(bounds.x, bounds.y + 40, hero.getBounds().x, hero.getBounds().y, true, damage, hero.x, 0, 0);
                    } else {
                        requestSnowball = new com.doublew.snowballfight.screens.battlescreen.objects.Snowball(bounds.x, bounds.y + 40, hero.getBounds().x, hero.getBounds().y, true, damage, hero.x, 0, 4);
                    }
                    request = true;
                    lastActionTimePassed = TIME_THROWING;
                    currentMode = com.doublew.snowballfight.screens.battlescreen.objects.chars.CHAR_MODE.THROWING;
                }
                break;
        }
        if (currentMode != com.doublew.snowballfight.screens.battlescreen.objects.chars.CHAR_MODE.STILL) {
            lastActionTimePassed -= delta;
        }
    }

    public boolean isMovedY() {
        return movedY;
    }

    public void movedYCompleted() {
        movedY = false;
    }

    private void act() {
        int nextActionID;
        if(!effects.isEmpty()) {
            nextActionID = random.nextInt(4);
        } else {
            nextActionID = random.nextInt(5);
        }
        switch (nextActionID) {
            case 0:
                if (x != -3) {
                    x--;
                    shiftDestination = GameClass.GAME_WIDTH / 2 + ((x) * 45);
                    currentMode = com.doublew.snowballfight.screens.battlescreen.objects.chars.CHAR_MODE.SHIFT_LEFT;
                    lastActionTimePassed = TIME_SHIFT;
                }
                break;
            case 1:
                if (x != 3) {
                    x++;
                    shiftDestination = GameClass.GAME_WIDTH / 2 + ((x) * 45);
                    currentMode = com.doublew.snowballfight.screens.battlescreen.objects.chars.CHAR_MODE.SHIFT_RIGHT;
                    lastActionTimePassed = TIME_SHIFT;
                }
                break;
            case 2:
                if (y != 0) {
                    movedY = true;
                    y--;
                    shiftDestination = GameClass.MID_POINT - 121 + y * 20;
                    currentMode = com.doublew.snowballfight.screens.battlescreen.objects.chars.CHAR_MODE.SHIFT_BACK;
                    lastActionTimePassed = TIME_SHIFT;
                }
                break;
            case 3:
                if (y != 6) {
                    movedY = true;
                    y++;
                    shiftDestination = GameClass.MID_POINT - 121 + y * 20;
                    currentMode = com.doublew.snowballfight.screens.battlescreen.objects.chars.CHAR_MODE.SHIFT_FRONT;
                    lastActionTimePassed = TIME_SHIFT;
                }
                break;
            case 4:
                currentMode = com.doublew.snowballfight.screens.battlescreen.objects.chars.CHAR_MODE.AIMING;
                lastActionTimePassed = TIME_AIMING;
                break;
        }
    }

    public void renderLine(ShapeRenderer shape) {
        shape.line(bounds.x, bounds.y, hero.getBounds().x, hero.getBounds().y + 40);
    }

    public void render(float delta, float runTime, SpriteBatch batch) {
        if (!effects.containsFrozen()) {
            switch (currentMode) {
                case STILL:
                    texture = (TextureRegion) still.getKeyFrame(runTime);
                    break;
                case SHIFT_LEFT:
                case SHIFT_RIGHT:
                    texture = (TextureRegion) shift.getKeyFrame(runTime);
                    break;
                case AIMING:
                    if (flicker) {
                        batch.setColor(Color.RED);
                    }
                    texture = (TextureRegion) aiming.getKeyFrame(runTime);
                    break;
                case THROWING:
                    if (flicker) {
                        batch.setColor(Color.BLUE);
                    }
                    texture = throwing;
                    break;
//            case STUNNED:
//                texture = (TextureRegion) still.getKeyFrame(runTime);
//                break;
//            case FROZEN:
//                texture = (TextureRegion) still.getKeyFrame(runTime);
//                break;
                case DYING:
                    texture = dying;
                    break;
            }
            batch.draw(texture, bounds.x - texture.getRegionWidth() / 2, bounds.y, texture.getRegionWidth(), texture.getRegionHeight());
            if(!effects.isEmpty()) {
                batch.draw(AssetHandler.stunWhirligig, bounds.x- 18, bounds.y-23, 18, 9, 35, 18, 1, 1, - runTime * 100);
            }
        } else {
            TextureRegion t = (TextureRegion) still.getKeyFrame(0);
            batch.draw(AssetHandler.frozen, bounds.x - t.getRegionWidth() / 2, bounds.y, t.getRegionWidth(), t.getRegionHeight());
        }

        batch.setColor(Color.WHITE);


        if (HPBarTime != 0) {
            // Max length of HP bar - 40
            if (HP >= 0) {
                batch.draw(AssetHandler.redSquare, bounds.x - 20, bounds.y - 10, 40 * HP / MAX_HP, 5);
            }
        }
    }

    public Rectangle getHitBox() {
        return new Rectangle(bounds.x - texture.getRegionWidth() / 2, bounds.y, texture.getRegionWidth(), texture.getRegionHeight());
    }

    public boolean takeDamage(com.doublew.snowballfight.screens.battlescreen.objects.Snowball incoming) {
        if (currentMode != com.doublew.snowballfight.screens.battlescreen.objects.chars.CHAR_MODE.DYING) {
            HPBarTime = HP_BAR_TIMEOUT;
            effects.addEffect(incoming.getModifier(), getTimeOfModifier(incoming.getModifier()));
            Sound.playHit();
            if (random.nextFloat() < hero.criticalChance) {
                HP -= incoming.getDamage() * 2;
                return true;
            }
            HP -= incoming.getDamage();

        }
        return false;
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

    public boolean readyToBeRemoved() {
        return remove;
    }

    public Rectangle getBounds() {
        return bounds;
    }
}
