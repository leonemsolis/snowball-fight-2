package com.doublew.snowballfight.screens.mapscreen;

import com.doublew.snowballfight.screens.commonobjects.ux.Callback;
import com.doublew.snowballfight.screens.commonobjects.ux.Prompt;
import com.doublew.snowballfight.screens.mapscreen.objects.MAP_MODE;
import com.doublew.snowballfight.screens.mapscreen.objects.MapCharacter;
import com.doublew.snowballfight.support.AssetHandler;
import com.doublew.snowballfight.support.DataHandler;

/**
 * Created by Leonemsolis on 30/09/2017.
 *
 * ObjectHandler for MapScreen
 */

public class ObjectHandlerMap {
    public MapCharacter mapCharacter;
    public int battleID = 0;

    public MAP_MODE currentMode;
    public Prompt prompt, notification;
    public com.doublew.snowballfight.screens.commonobjects.ux.Button backButton;

    private ScreenMap screen;

    public ObjectHandlerMap(final ScreenMap screen, final MAP_MODE entryMode, final String message1, String message2) {
        mapCharacter = new MapCharacter();

        // Use message2 to set character near by stores
        if(message2 == "item") {
            mapCharacter.setPosition(true);
        } else if(message2 == "drug") {
            mapCharacter.setPosition(false);
        }

        this.screen = screen;

        prompt = new Prompt(new Callback() {
            @Override
            public void callback() {
                switch (getSelectedAreaCode()) {
                    case 1:
                        if(DataHandler.maxStage > 1) {
                            screen.gotoBattle(1, 3);
                        } else {
                            screen.gotoBattle(1, DataHandler.maxLevel);
                        }
                        break;
                    case 2:
                        if(DataHandler.maxStage >= 2) {
                            if(DataHandler.maxStage > 2) {
                                screen.gotoBattle(2, 3);
                            } else {
                                screen.gotoBattle(2, DataHandler.maxLevel);
                            }
                        } else {
                            switchCurrentMode(MAP_MODE.FREE);
                        }
                        break;
                    case 3:
                        if(DataHandler.maxStage >= 3) {
                            if(DataHandler.maxStage > 3) {
                                screen.gotoBattle(3, 3);
                            } else {
                                screen.gotoBattle(3, DataHandler.maxLevel);
                            }
                        } else {
                            switchCurrentMode(MAP_MODE.FREE);
                        }
                        break;
                    case 4:
                        if(DataHandler.maxStage >= 4) {
                            screen.gotoBattle(4, DataHandler.maxLevel);
                        } else {
                            switchCurrentMode(MAP_MODE.FREE);
                        }
                        break;
                    case 5:
                        screen.gotoShop(true);
                        break;
                    case 6:
                        screen.gotoShop(false);
                        break;
                }
                prompt.hide();
            }
        }, new Callback() {
            @Override
            public void callback() {
                switchCurrentMode(MAP_MODE.FREE);
                prompt.hide();
            }
        }, new Callback() {
            @Override
            public void callback() {
                switchCurrentMode(MAP_MODE.FREE);
                prompt.hide();
            }
        });

        notification = new Prompt(new Callback() {
            @Override
            public void callback() {
                if(!mapCharacter.notMoved()) {
                    switchCurrentMode(MAP_MODE.FREE);
                } else {
                    currentMode = MAP_MODE.FREE;
                }
                notification.hide();
            }
        });
        notification.updateNotification(message1, message2, "OK");

        backButton = new com.doublew.snowballfight.screens.commonobjects.ux.Button(new Callback() {
            @Override
            public void callback() {
                screen.goBack();
            }
        }, AssetHandler.backButtonUp, AssetHandler.backButtonDown, 0, 0, 80, 39);
//        backButton.show();

        if(entryMode == MAP_MODE.NOTIFICATION) {
            currentMode = entryMode;
            notification.show();
        } else {
            currentMode = entryMode;
        }

    }

    public void update(float delta) {
        mapCharacter.update(delta);
        switch (currentMode) {
            case FREE:
                if(getSelectedAreaCode() != 0) {
                    if(getSelectedAreaCode() == 5) {
                        screen.gotoShop(true);
                    } else if(getSelectedAreaCode() == 6) {
                        screen.gotoShop(false);
                    }
                    if(generateRightButtonText() == null) {
                        switchCurrentMode(MAP_MODE.NOTIFICATION);
                    } else {
                        switchCurrentMode(MAP_MODE.PROMPT);
                    }
                }
                break;
        }
    }

    public void switchCurrentMode(MAP_MODE newMode) {
        switch (newMode) {
            case FREE:
                mapCharacter.undoMove();
                break;
            case PROMPT:
                prompt.updatePrompt(generatePromptMessageText(),"", generateLeftButtonText(), generateRightButtonText());
                prompt.show();
                break;
            case NOTIFICATION:
                notification.updateNotification(generatePromptMessageText(), "", generateLeftButtonText());
                notification.show();
                break;
        }
        currentMode = newMode;
    }

    public int getSelectedAreaCode() {
       return mapCharacter.selectedAreaCode();
    }

    public String generatePromptMessageText() {
        switch (mapCharacter.selectedAreaCode()) {
            case 1:
                return "Go to stage 1?";
            case 2:
                if(DataHandler.maxStage >= 2) {
                    return "Go to stage 2?";
                } else {
                    return "Stage 2 is not available yet..";
                }
            case 3:
                if(DataHandler.maxStage >= 3) {
                    return "Go to stage 3?";
                } else {
                    return "Stage 3 is not available yet..";
                }
            case 4:
                if(DataHandler.maxStage >= 4) {
                    return "Go to stage 4?";
                } else {
                    return "Stage 4 is not available yet..";
                }
            case 5:
                return "Go to Drug Store?";
            case 6:
                return "Go to Item Store?";
        }
        return "";
    }

    public String generateLeftButtonText() {
        switch (mapCharacter.selectedAreaCode()) {
            case 1:
                return "Fight!";
            case 2:
                if(DataHandler.maxStage >= 2) {
                    return "Fight!";
                }
                return "Yeah..";
            case 3:
                if(DataHandler.maxStage >= 3) {
                    return "Fight!";
                }
                return "Okay..";
            case 4:
                if(DataHandler.maxStage >= 4) {
                    return "Fight!";
                }
                return "#*$@*!";
            case 5:
                return "Yeah";
            case 6:
                return "Yes!";
        }
        return "";
    }

    // If returns null, then generate notification instead of prompt
    public String generateRightButtonText() {
        switch (mapCharacter.selectedAreaCode()) {
            case 1:
                return "Back";
            case 2:
                if(DataHandler.maxStage >= 2) {
                    return "Back";
                }
                return null;
            case 3:
                if(DataHandler.maxStage >= 3) {
                    return "Back";
                }
                return null;
            case 4:
                if(DataHandler.maxStage >= 4) {
                    return "Back";
                }
                return null;
            case 5:
                return "Nope";
            case 6:
                return "No";
        }
        return "";
    }

    public void dispose() {
        prompt.dispose();
        notification.dispose();
    }

}
