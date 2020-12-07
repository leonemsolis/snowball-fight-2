package com.doublew.snowballfight.screens.store;

import com.badlogic.gdx.math.Rectangle;
import com.doublew.snowballfight.main.GameClass;
import com.doublew.snowballfight.screens.battlescreen.objects.particles.CrashParticleSystem;
import com.doublew.snowballfight.screens.commonobjects.ux.Item;
import com.doublew.snowballfight.screens.commonobjects.ux.Prompt;
import com.doublew.snowballfight.support.AssetHandler;
import com.doublew.snowballfight.support.DataHandler;
import com.doublew.snowballfight.support.Sound;

import java.util.ArrayList;

/**
 * Created by Leonemsolis on 09/01/2018.
 */

public class ObjectHandlerStore {
    public final int []drugPrices = new int[]{6, 12, 10, 12};
    public final int []itemPrices = new int[]{5, 8, 8, 14};


    public final boolean isDrugStore;
    public Rectangle items[];

    public Rectangle buyButton, exitButton;

    public int selectedItem;

    public Prompt promptBuy, promptExit, buyNotification;

    public com.doublew.snowballfight.screens.commonobjects.ux.Button info;

    public ArrayList<CrashParticleSystem> clickParticles;

    public ObjectHandlerStore(final boolean isDrugStore, final GameClass gameClass) {
        clickParticles = new ArrayList<CrashParticleSystem>();

        this.isDrugStore = isDrugStore;

        promptExit = new Prompt(
                new com.doublew.snowballfight.screens.commonobjects.ux.Callback() {
                    @Override
                    public void callback() {
                        if(isDrugStore) {
                            gameClass.gotoScreenMap("", "drug");
                        } else {
                            gameClass.gotoScreenMap("", "item");
                        }
                        promptExit.hide();
                    }
                },
                new com.doublew.snowballfight.screens.commonobjects.ux.Callback() {
                    @Override
                    public void callback() {
                        promptExit.hide();
                    }
                },
                new com.doublew.snowballfight.screens.commonobjects.ux.Callback() {
                    @Override
                    public void callback() {
                        promptExit.hide();
                    }
                }
        );

        buyNotification = new Prompt(
          new com.doublew.snowballfight.screens.commonobjects.ux.Callback() {
              @Override
              public void callback() {
                  buyNotification.hide();
              }
          }
        );

        info = new com.doublew.snowballfight.screens.commonobjects.ux.Button(new com.doublew.snowballfight.screens.commonobjects.ux.Callback() {
            @Override
            public void callback() {
                switch (selectedItem) {
                    case -1:
                        showNotification("Select the item", "");
                        break;
                    case 0:
                        if(isDrugStore) {
                            showNotification("Heals 30%", "");
                        } else {
                            showNotification("Next attack", "disarms enemy");
                        }
                        break;
                    case 1:
                        if(isDrugStore) {
                            showNotification("Restores 1 energy", "");
                        } else {
                            showNotification("Next attack deals", "doubled damage");
                        }
                        break;
                    case 2:
                        if(isDrugStore) {
                            showNotification("Heals 100%", "");
                        } else {
                            showNotification("Next attack", "freezes enemy");
                        }
                        break;
                    case 3:
                        if(isDrugStore) {
                            showNotification("Doubled damage", "for 1 round");
                        } else {
                            showNotification("Next attack deals doubled", "damage & freeze enemy");
                        }
                        break;
                }
            }
        }, AssetHandler.infoButtonUp, AssetHandler.infoButtonDown, 117, GameClass.MID_POINT + 133, 28, 28);

        String message = "Exit";
        if(isDrugStore) {
            message += " Drug Store?";
        } else {
            message += " Item Store?";
        }
        promptExit.updatePrompt(message, "", "Yes", "No");

        items = new Rectangle[]{new Rectangle(31, GameClass.MID_POINT+35, 64, 64),
                                new Rectangle(95, GameClass.MID_POINT+35, 64, 64),
                                new Rectangle(159, GameClass.MID_POINT+35, 64, 64),
                                new Rectangle(223, GameClass.MID_POINT+35, 64, 64)};
        buyButton = new Rectangle(17, GameClass.MID_POINT - 121, 111, 36);
        exitButton = new Rectangle(17, GameClass.MID_POINT - 70, 111, 36);
        selectedItem = -1;



        promptBuy = new Prompt(
                new com.doublew.snowballfight.screens.commonobjects.ux.Callback() {
                    @Override
                    public void callback() {
                        switch (buyItem()) {
                            case 0:
                                showNotification("You bought the item!", "");
                                break;
                            case 1:
                                showNotification("You're inventory is full!", "");
                                break;
                            case 2:
                                showNotification("Not enough gold!", "");
                                break;
                        }
                        promptBuy.hide();
                    }
                },
                new com.doublew.snowballfight.screens.commonobjects.ux.Callback() {
                    @Override
                    public void callback() {
                        promptBuy.hide();
                    }
                },
                new com.doublew.snowballfight.screens.commonobjects.ux.Callback() {
                    @Override
                    public void callback() {
                        promptBuy.hide();
                    }
                }
        );
    }

    public void update(float delta) {
        for (CrashParticleSystem s:clickParticles) {
            s.update(delta);
        }
        proceedCompleteParticles();
    }

    public boolean checkInput(int screenX, int screenY) {
        for(int i = 0; i < 4; ++i) {
            if(items[i].contains(screenX, screenY)) {
                Sound.playStoreSelect();
                if(selectedItem == i) {
                    selectedItem = -1;
                } else {
                    selectedItem = i;
                }
                return true;
            }
        }

        if(buyButton.contains(screenX, screenY)) {
            Sound.playStoreSelect();
            if(selectedItem != -1) {
                updateBuyPrompt();
                promptBuy.show();
            }
            return true;
        }

        if(exitButton.contains(screenX, screenY)) {
            Sound.playStoreSelect();
            promptExit.show();
            return true;
        }

        return false;
    }


    public void updateBuyPrompt() {
        String message = "";
        if(isDrugStore) {
            switch(selectedItem) {
                case 0:
                    message += " the Bandage?";
                    break;
                case 1:
                    message += " the Pill?";
                    break;
                case 2:
                    message += " the Painkiller?";
                    break;
                case 3:
                    message += " the Potion?";
                    break;
            }
        } else {
            switch(selectedItem) {
                case 0:
                    message += " the Boot?";
                    break;
                case 1:
                    message += " the Boulder?";
                    break;
                case 2:
                    message += " the Frozen Snowball?";
                    break;
                case 3:
                    message += " the Icicle?";
                    break;
            }
        }
        promptBuy.updatePrompt("Do you want to buy", message, "Yes", "No");
    }

    public int buyItem() {
        if(DataHandler.items.size() >= 5) {
            return 1;
        }

        if(isDrugStore) {
            if(drugPrices[selectedItem] > DataHandler.gold) {
                return 2;
            }
        } else {
            if(itemPrices[selectedItem] > DataHandler.gold) {
                return 2;
            }
        }


        // Items first, drugs second
        if(!isDrugStore) {
                DataHandler.items.add(new Item(selectedItem));
            DataHandler.gold -= itemPrices[selectedItem];
        } else {
            DataHandler.items.add(new Item(4 + selectedItem));
            DataHandler.gold -= drugPrices[selectedItem];
        }

        return 0;
    }

    public void showNotification(String message, String message2) {
        buyNotification.updateNotification(message,message2, "OK");
        buyNotification.show();
    }

    public void dispose() {
        promptBuy.dispose();
        promptExit.dispose();
    }

    private void proceedCompleteParticles() {
        ArrayList<com.doublew.snowballfight.screens.battlescreen.objects.particles.ParticleSystem> sToRemove = new ArrayList<com.doublew.snowballfight.screens.battlescreen.objects.particles.ParticleSystem>();
        for (com.doublew.snowballfight.screens.battlescreen.objects.particles.ParticleSystem s : clickParticles) {
            if(s.isComplete()) {
                sToRemove.add(s);
            }
        }

        if(!sToRemove.isEmpty()) {
            for (com.doublew.snowballfight.screens.battlescreen.objects.particles.ParticleSystem s:sToRemove) {
                s.dispose();
                clickParticles.remove(s);
            }
        }
    }
}
