package com.doublew.snowballfight.screens.battlescreen.objects;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.doublew.snowballfight.main.GameClass;
import com.doublew.snowballfight.support.AssetHandler;

/**
 * Created by Leonemsolis on 27/11/2017.
 */

public class PauseButton extends com.doublew.snowballfight.screens.commonobjects.ux.Button {
    public PauseButton(com.doublew.snowballfight.screens.commonobjects.ux.Callback callback) {
        super(callback, AssetHandler.pauseButtonUp, AssetHandler.pauseButtonDown, GameClass.GAME_WIDTH - 5 - 37, 5, 37, 40);
    }

    @Override
    public void render(SpriteBatch batch) {
        if(!isHidden) {
            batch.begin();
            if (isPressed) {
                batch.draw(down, bounds.x, bounds.y, bounds.width, bounds.height);
            } else {
                batch.draw(up, bounds.x, bounds.y, bounds.width, bounds.height);
            }
            batch.end();
        }
    }
}

