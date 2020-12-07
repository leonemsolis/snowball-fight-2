package com.doublew.snowballfight.screens.settingscreen.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.doublew.snowballfight.support.Sound;
/**
 * Created by Leonemsolis on 21/02/2018.
 */

public class Toggle {
    private TextureRegion on, off;
    private Rectangle bounds;
    private boolean activated, touchedDown;

    public Toggle(TextureRegion on, TextureRegion off, int x, int y, int width, int height, boolean activated) {
        this.on = on;
        this.off = off;
        bounds = new Rectangle(x, y, width, height);
        this.activated = activated;
        touchedDown = false;
    }

    public boolean isActivated() {
        return activated;
    }

    public void render(SpriteBatch batch) {
        if(activated) {
            batch.draw(on, bounds.x, bounds.y, bounds.width, bounds.height);
        } else {
            batch.draw(off, bounds.x, bounds.y, bounds.width, bounds.height);
        }
    }

    public boolean touchDown(int screenX, int screenY) {
        if(bounds.contains(screenX, screenY)) {
            Sound.playButtonDown();
            touchedDown = true;
            return true;
        }
        return false;
    }

    public boolean touchUp(int screenX, int screenY) {
        if(bounds.contains(screenX, screenY)) {
            if(touchedDown) {
                Sound.playButtonUp();
                touchedDown = false;
                //toggle
                activated = !activated;
                return true;
            }
        }
        touchedDown = false;
        return false;
    }
}
