package com.doublew.snowballfight.screens.battlescreen.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.doublew.snowballfight.main.GameClass;
import com.doublew.snowballfight.support.AssetHandler;

/**
 * Created by Leonemsolis on 14/10/2017.
 */

public class ControlButton extends com.doublew.snowballfight.screens.commonobjects.ux.Button {
    private Rectangle renderingBounds;
    private int ID;
    public ControlButton(int ID, com.doublew.snowballfight.screens.commonobjects.ux.Callback callback) {
        super(callback);
        this.ID = ID;
        /*  ID:
            1 - left button
            2 - center button
            3 - right button
        */
        switch (ID) {
            case 1:
                up = AssetHandler.leftControlUp;
                down = AssetHandler.leftControlDown;
                renderingBounds = new Rectangle(20, GameClass.MID_POINT + 100, 16, 35);
                bounds = new Rectangle(0, GameClass.MID_POINT - 152, 106, GameClass.GAME_HEIGHT);
                break;
            case 2:
                up = AssetHandler.centerControlUp;
                down = AssetHandler.centerControlDown;
                renderingBounds = new Rectangle(GameClass.GAME_WIDTH / 2 - 18, GameClass.MID_POINT + 100, 35, 35);
                bounds = new Rectangle(106, GameClass.MID_POINT - 152, 108, GameClass.GAME_HEIGHT);
                break;
            case 3:
                up = AssetHandler.rightControlUp;
                down = AssetHandler.rightControlDown;
                renderingBounds = new Rectangle(GameClass.GAME_WIDTH - 36, GameClass.MID_POINT + 100, 16, 35);
                bounds = new Rectangle(214, GameClass.MID_POINT - 152, 106, GameClass.GAME_HEIGHT);
                break;
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        if(!isHidden) {
            if (isPressed) {
                batch.draw(down, renderingBounds.x, renderingBounds.y, renderingBounds.width, renderingBounds.height);
            } else {
                batch.draw(up, renderingBounds.x, renderingBounds.y, renderingBounds.width, renderingBounds.height);
            }
        }
    }


    @Override
    public boolean touchDown(int screenX, int screenY) {
        if(!isHidden) {
            if (bounds.contains(screenX, screenY)) {
                if(ID != 2) {
                    isPressed = true;
                }
                callback.callback();
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY) {
        if(!isHidden) {
            if (isPressed && bounds.contains(screenX, screenY)) {
                callback.callback();
                isPressed = false;
                return true;
            }
            isPressed = false;
        }
        return false;
    }
}
