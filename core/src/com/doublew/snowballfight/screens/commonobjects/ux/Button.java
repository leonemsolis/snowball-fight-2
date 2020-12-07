package com.doublew.snowballfight.screens.commonobjects.ux;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.doublew.snowballfight.support.Sound;

/**
 * Created by Leonemsolis on 14/09/2017.
 *
 * Part of the UI - button
 */

public class Button {

    protected TextureRegion up, down;
    public Callback callback;
    protected boolean isPressed = false;
    public Rectangle bounds, boundsPressed;
    public boolean isHidden = false;

    public Button(Callback callback) {
        this.up = null;
        this.down = null;
        this.callback = callback;
        bounds = new Rectangle();
    }

    public Button(Callback callback, TextureRegion up, TextureRegion down, float x, float y, float width, float height) {
        this.up = up;
        this.down = down;
        this.callback = callback;
        bounds = new Rectangle(x, y, width, height);
    }

    public Button(Callback callback, TextureRegion up, TextureRegion down, float originX, float y, float width, float height, float width2, float height2) {
        this.up = up;
        this.down = down;
        this.callback = callback;
        bounds = new Rectangle(originX - width / 2, y, width, height);
        boundsPressed = new Rectangle(originX - width2 / 2, y, width2, height2);
    }

    public void hide() {
        isHidden = true;
    }

    public void show() {
        isHidden = false;
    }

    public void render(SpriteBatch batch) {
        if(!isHidden) {
            if(isPressed) {
                batch.draw(down, bounds.x, bounds.y, bounds.width, bounds.height);
            } else {
                batch.draw(up, bounds.x, bounds.y, bounds.width, bounds.height);
            }
        }
    }

    public void renderAlternative(SpriteBatch batch) {
        if(!isHidden) {
            if(isPressed) {
                if(boundsPressed != null) {
                    batch.draw(down, boundsPressed.x + 5, boundsPressed.y, boundsPressed.width, boundsPressed.height);
                }
            } else {
                batch.draw(up, bounds.x, bounds.y, bounds.width, bounds.height);
            }
        }
    }

    public boolean touchDown(int screenX, int screenY) {
        if(!isHidden) {
            if (bounds.contains(screenX, screenY)) {
                Sound.playButtonDown();
                isPressed = true;
                return true;
            }
        }
        return false;
    }

    public boolean touchUp(int screenX, int screenY) {
        if(!isHidden) {
            if (isPressed && bounds.contains(screenX, screenY)) {
                Sound.playButtonUp();
                callback.callback();
                isPressed = false;
                return true;
            }
            isPressed = false;
        }
        return false;
    }

}
