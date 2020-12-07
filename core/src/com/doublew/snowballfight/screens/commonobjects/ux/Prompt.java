package com.doublew.snowballfight.screens.commonobjects.ux;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.doublew.snowballfight.main.GameClass;
import com.doublew.snowballfight.support.AssetHandler;

/**
 * Created by Leonemsolis on 29/12/2017.
 */

public class Prompt {
    private Rectangle bounds = new Rectangle(20, GameClass.MID_POINT-100, 280, 200);
    private Button leftButton, rightButton;
    private String message, leftLabel, rightLabel, message2;
    private float messageWidth, messageHeight, leftLabelWidth, leftLabelHeight, rightLabelWidth, rightLabelHeight, message2Width, message2Height;
    private final boolean isPrompt;
    public boolean isHidden;
    private BitmapFont blackFont, blueFont;

    private Callback cancelCallback;

    // Prompt
    public Prompt(Callback leftCallback, Callback rightCallback, Callback cancelCallback) {
        blackFont = new BitmapFont(AssetHandler.blackFont.getData(), AssetHandler.blackFont.getRegion(), AssetHandler.blackFont.usesIntegerPositions());
        blackFont.getData().setScale(0.7f, .7f);

        blueFont = new BitmapFont(AssetHandler.blueFont.getData(), AssetHandler.blueFont.getRegion(), AssetHandler.blueFont.usesIntegerPositions());
        blueFont.getData().setScale(0.7f, .7f);

        isPrompt = true;
        leftButton = new Button(leftCallback, AssetHandler.promptButtonUp, AssetHandler.promptButtonDown, 20+36, GameClass.MID_POINT + 25, 69, 45);
        rightButton = new Button(rightCallback, AssetHandler.promptButtonUp, AssetHandler.promptButtonDown, 20+176, GameClass.MID_POINT + 25, 69, 45);
        this.cancelCallback = cancelCallback;

        message = "";
        message2 = "";
        leftLabel = "";
        rightLabel = "";
        messageWidth = messageHeight = leftLabelWidth = leftLabelHeight = rightLabelWidth = rightLabelHeight = message2Width = message2Height = 0;
        hide();
    }

    // Notification
    public Prompt(Callback callback) {
        blueFont = new BitmapFont(AssetHandler.blueFont.getData(), AssetHandler.blueFont.getRegion(), AssetHandler.blueFont.usesIntegerPositions());
        blueFont.getData().setScale(0.7f, .7f);
        blackFont = new BitmapFont(AssetHandler.blackFont.getData(), AssetHandler.blackFont.getRegion(), AssetHandler.blackFont.usesIntegerPositions());
        blackFont.getData().setScale(0.7f, .7f);
        this.cancelCallback = callback;
        isPrompt = false;
        leftButton = new Button(callback, AssetHandler.promptButtonUp, AssetHandler.promptButtonDown, GameClass.GAME_WIDTH / 2 - 69 / 2, GameClass.MID_POINT + 25, 69, 45);

        message = "";
        leftLabel = "";
        messageWidth = messageHeight = leftLabelWidth = leftLabelHeight = 0;

        hide();
    }

    public void hide() {
        isHidden = true;
        leftButton.hide();
        if(isPrompt) {
            rightButton.hide();
        }
    }

    public void show() {
        isHidden = false;
        leftButton.show();
        if(isPrompt) {
            rightButton.show();
        }
    }

    public void render(SpriteBatch batch) {
        if(!isHidden) {
            batch.draw(AssetHandler.promptFrame, bounds.x, bounds.y, bounds.width, bounds.height);
            if(message2 == "") {
                blueFont.draw(batch, message, GameClass.GAME_WIDTH / 2 - messageWidth / 2, GameClass.MID_POINT - 35 - messageHeight/2);
            } else {
                blueFont.draw(batch, message, GameClass.GAME_WIDTH / 2 - messageWidth / 2, GameClass.MID_POINT - 45 - messageHeight/2);
                blueFont.draw(batch, message2, GameClass.GAME_WIDTH / 2 - message2Width / 2, GameClass.MID_POINT - 45 + messageHeight / 2 + message2Height);
            }


            leftButton.render(batch);


            if(isPrompt) {
                blackFont.draw(batch, leftLabel, 91 - leftLabelWidth / 2, GameClass.MID_POINT + 48 - leftLabelHeight / 2);
                rightButton.render(batch);
                blackFont.draw(batch, rightLabel, GameClass.GAME_WIDTH - 91 - rightLabelWidth + rightLabelWidth / 2, GameClass.MID_POINT + 48 - rightLabelHeight / 2);
            } else {
                blackFont.draw(batch, leftLabel, GameClass.GAME_WIDTH / 2 - leftLabelWidth / 2, GameClass.MID_POINT + 48 - leftLabelHeight / 2);
            }
        }
    }

    // If touched down on prompt square return true
    public boolean touchDown(int screenX, int screenY) {
        if(!isHidden) {
            if(leftButton.touchDown(screenX, screenY)) {
                return true;
            }
            if(isPrompt) {
                if(rightButton.touchDown(screenX, screenY)) {
                    return true;
                }
            }

            if(bounds.contains(screenX, screenY)) {
                return true;
            } else {
                cancelCallback.callback();
                return false;
            }
        }
        return false;
    }

    public boolean touchUp(int screenX, int screenY) {
        if(!isHidden) {
            if(isPrompt) {
                if(leftButton.touchUp(screenX, screenY)) {
                    return true;
                }
                if(isPrompt) {
                    if(rightButton.touchUp(screenX, screenY)) {
                        return true;
                    }
                }
            } else {
                if(leftButton.touchUp(screenX, screenY)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void updatePrompt(String message, String message2, String leftLabel, String rightLabel) {
        this.message = message;
        this.leftLabel = leftLabel;
        this.rightLabel = rightLabel;
        this.message2 = message2;

        GlyphLayout layout = new GlyphLayout();
        layout.setText(blackFont, message);
        messageWidth = layout.width;
        messageHeight = layout.height;
        layout.setText(blackFont, leftLabel);
        leftLabelWidth = layout.width;
        leftLabelHeight = layout.height;
        layout.setText(blackFont, rightLabel);
        rightLabelWidth = layout.width;
        rightLabelHeight = layout.height;
        layout.setText(blueFont, message2);
        message2Width = layout.width;
        message2Height = layout.height;
    }

    public void updateNotification(String message, String message2, String leftLabel) {
        this.message = message;
        this.leftLabel = leftLabel;
        this.message2 = message2;

        GlyphLayout layout = new GlyphLayout();
        layout.setText(blackFont, message);
        messageWidth = layout.width;
        messageHeight = layout.height;
        layout.setText(blackFont, leftLabel);
        leftLabelWidth = layout.width;
        leftLabelHeight = layout.height;
        layout.setText(blueFont, message2);
        message2Width = layout.width;
        message2Height = layout.height;
    }

    public void dispose() {
        blackFont.dispose();
        blueFont.dispose();
    }
}
