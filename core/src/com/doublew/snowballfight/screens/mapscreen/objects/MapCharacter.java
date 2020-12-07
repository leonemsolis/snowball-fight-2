package com.doublew.snowballfight.screens.mapscreen.objects;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.doublew.snowballfight.main.GameClass;
import com.doublew.snowballfight.support.AssetHandler;
import com.doublew.snowballfight.support.Sound;

/**
 * Created by Leonemsolis on 08/10/2017.
 *
 * GameObject that appears on the MapScreen. Can be controlled by user,
 * also sends signals to MapScreen about currently selected stage/shop
 */

public class MapCharacter {
    private Animation stillAnimation, walkingAnimation;
    private int touchedDownX, touchedDownY;
    private boolean walking = false;
    private final float TIME_WALKING = .4f;
    private float walkingTimer = 0f;
    private float walkingSpeed = 0f;
    private float destination;
    private boolean walkingX = false;
    private Rectangle bounds;
    private int prevX, prevY, x, y;
    private boolean readyToWalk = false;

    private final boolean map[][] = {{false, false, true, false, false, false, true, false, false},
                                    {false, false, true, false, false, false, true, false, false},
                                    {true, true, true, true, true, true, true, true, true},
                                    {false, false, true, false, false, false, true, false, false},
                                    {false, false, true, false, false, false, true, false, false}};

    public MapCharacter() {
        bounds = new Rectangle(GameClass.GAME_WIDTH / 2 - 15, GameClass.MID_POINT - 25, 30, 40);

        stillAnimation = new Animation(0.3f, AssetHandler.mapCharacter_still);
        stillAnimation.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

        walkingAnimation = new Animation(0.1f, AssetHandler.mapCharacter_walking);
        walkingAnimation.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

        prevX = 4;
        prevY = 2;
        x = 4;
        y = 2;
    }

    public void setPosition(boolean itemStore) {
        if(itemStore) {
            prevX = 6;
            prevY = 1;
            x = 6;
            y = 1;
        } else {
            prevX = 2;
            prevY = 1;
            x = 2;
            y = 1;
        }

        switch (x) {
            case 0:
                bounds.x = GameClass.GAME_WIDTH / 2 - 85;
                break;
            case 1:
                bounds.x = GameClass.GAME_WIDTH / 2 - 67;
                break;
            case 2:
                bounds.x = GameClass.GAME_WIDTH / 2 - 50;
                break;
            case 3:
                bounds.x = GameClass.GAME_WIDTH / 2 - 32;
                break;
            case 4:
                bounds.x = GameClass.GAME_WIDTH / 2 - 15;
                break;
            case 5:
                bounds.x = GameClass.GAME_WIDTH / 2 + 2;
                break;
            case 6:
                bounds.x = GameClass.GAME_WIDTH / 2 + 20;
                break;
            case 7:
                bounds.x = GameClass.GAME_WIDTH / 2 + 37;
                break;
            case 8:
                bounds.x = GameClass.GAME_WIDTH / 2 + 55;
                break;
        }


        switch (y) {
            case 0:
                bounds.y = GameClass.MID_POINT - 65;
                break;
            case 1:
                bounds.y = GameClass.MID_POINT - 45;
                break;
            case 2:
                bounds.y = GameClass.MID_POINT - 25;
                break;
            case 3:
                bounds.y = GameClass.MID_POINT - 5;
                break;
            case 4:
                bounds.y = GameClass.MID_POINT + 2;
                break;
        }
    }

    public boolean notMoved() {
        return prevX == x && prevY == y;
    }

    public int selectedAreaCode() {
        if(x == 2 && y == 0) {
            return 5;
        }
        if(x == 6 && y == 0) {
            return 6;
        }
        if(x == 8 && y == 2) {
            return 1;
        }
        if(x == 6 && y == 4) {
            return 2;
        }
        if(x == 2 && y == 4) {
            return 3;
        }
        if(x == 0 && y == 2) {
            return 4;
        }
        return 0;
    }

    public void update(float delta) {
        if(walking) {
            walkingTimer -= delta;
            if(walkingX) {
                bounds.x += delta * walkingSpeed;
            } else {
                bounds.y += delta * walkingSpeed;
            }
            if(walkingTimer <= 0) {
                walking = false;
                if(walkingX) {
                    bounds.x = destination;
                } else {
                    bounds.y = destination;
                }
            }
        }
    }

    public void render(float delta, float runTime, SpriteBatch batch) {
        if (walking) {
            batch.draw((TextureRegion)walkingAnimation.getKeyFrame(runTime), bounds.x, bounds.y, bounds.width, bounds.height);
        } else {
            batch.draw((TextureRegion)stillAnimation.getKeyFrame(runTime), bounds.x, bounds.y, bounds.width, bounds.height);
        }
    }

    public void touchDown(int screenX, int screenY) {
        touchedDownX = screenX;
        touchedDownY = screenY;
        readyToWalk = true;
    }

    public void touchUp(int screenX, int screenY) {
        if(readyToWalk) {
            Sound.playMapCharacterWalking();
            if(Math.abs(screenX - touchedDownX) > Math.abs(screenY - touchedDownY)) {
                if(screenX > touchedDownX) {
                    moveRight();
                } else {
                    moveLeft();
                }
            } else {
                if(screenY > touchedDownY) {
                    moveDown();
                } else {
                    moveUp();
                }
            }
        }
    }

    private void updatePosition() {
        if(walkingX) {
            switch (x) {
                case 0:
                    destination = GameClass.GAME_WIDTH / 2 - 85;
                    break;
                case 1:
                    destination = GameClass.GAME_WIDTH / 2 - 67;
                    break;
                case 2:
                    destination = GameClass.GAME_WIDTH / 2 - 50;
                    break;
                case 3:
                    destination = GameClass.GAME_WIDTH / 2 - 32;
                    break;
                case 4:
                    destination = GameClass.GAME_WIDTH / 2 - 15;
                    break;
                case 5:
                    destination = GameClass.GAME_WIDTH / 2 + 2;
                    break;
                case 6:
                    destination = GameClass.GAME_WIDTH / 2 + 20;
                    break;
                case 7:
                    destination = GameClass.GAME_WIDTH / 2 + 37;
                    break;
                case 8:
                    destination = GameClass.GAME_WIDTH / 2 + 55;
                    break;
            }
        } else {
            switch (y) {
                case 0:
                    destination = GameClass.MID_POINT - 65;
                    break;
                case 1:
                    destination = GameClass.MID_POINT - 45;
                    break;
                case 2:
                    destination = GameClass.MID_POINT - 25;
                    break;
                case 3:
                    destination = GameClass.MID_POINT - 5;
                    break;
                case 4:
                    destination = GameClass.MID_POINT + 2;
                    break;
            }
        }
        /*
        Мы считаем разницу в расстоянии, если нужно идти по Х
        то скорость расчитывается как (расстояние по Х / время)
        так же по У. По какому имено направлению идти решает метод
         */

        walkingTimer = TIME_WALKING;
        if(walkingX) {
            walkingSpeed = (destination - bounds.x) / walkingTimer;
        } else {
            walkingSpeed = (destination - bounds.y) / walkingTimer;
        }
        walking = true;
    }

    private void moveLeft() {
        if(x!=0 && map[y][x-1]) {
            prevX = x;
            prevY = y;
            x--;
        }
        walkingX = true;
        updatePosition();
        readyToWalk = false;
    }

    private void moveRight() {
        if(x!=8 && map[y][x+1]) {
            prevX = x;
            prevY = y;
            x++;
        }
        walkingX = true;
        updatePosition();
        readyToWalk = false;
    }

    private void moveUp() {
        if(y!=0 && map[y-1][x]) {
            prevX = x;
            prevY = y;
            y--;
        }
        walkingX = false;
        updatePosition();
        readyToWalk = false;
    }

    private void moveDown() {
        if(y!=4 && map[y+1][x]) {
            prevX = x;
            prevY = y;
            y++;
        }
        walkingX = false;
        updatePosition();
        readyToWalk = false;
    }

    public void undoMove() {
        Sound.playMapCharacterWalking();
        x = prevX;
        y = prevY;
        updatePosition();
    }

    public boolean isWalking() {
        return walking;
    }

}
