package com.doublew.snowballfight.support;

/**
 * Created by Leonemsolis on 22/02/2018.
 */

public class Sound {
    public static void playClickEmpty() {
        if(DataHandler.soundOn) {
            AssetHandler.clickEmpty.play();
        }
    }

    public static void playMapCharacterWalking() {
        if(DataHandler.soundOn) {
            AssetHandler.mapCharacterWalking.play();
        }
    }

    public static void playButtonDown() {
        if(DataHandler.soundOn) {
            AssetHandler.buttonDown.play();
        }
    }

    public static void playButtonUp() {
        if(DataHandler.soundOn) {
            AssetHandler.buttonUp.play();
        }
    }

    public static void playStoreSelect() {
        if(DataHandler.soundOn) {
            AssetHandler.storeSelect.play();
        }
    }

    public static void playFirework() {
        if(DataHandler.soundOn) {
            AssetHandler.firework.play();
        }
    }

    public static void playSpecialSlide() {
        if(DataHandler.soundOn) {
            AssetHandler.specialSlide.play();
        }
    }

    public static void playGreetings() {
        if(DataHandler.soundOn) {
            AssetHandler.greetings.play();
        }
    }

    public static void playHit() {
        if(DataHandler.soundOn) {
            AssetHandler.hit.play();
        }
    }

    public static void playHurt() {
        if(DataHandler.soundOn) {
            AssetHandler.hurt.play();
        }
    }

    public static void playLose() {
        if(DataHandler.soundOn) {
            AssetHandler.lose.play();
        }
    }

    public static void playExplosion() {
        if(DataHandler.soundOn) {
            AssetHandler.explosion.play();
        }
    }

    public static void playFire() {
        if(DataHandler.soundOn) {
            AssetHandler.fire.play();
        }
    }

    public static void playFire2() {
        if(DataHandler.soundOn) {
            AssetHandler.fire2.play();
        }
    }

    public static void playMusic() {
        if(DataHandler.soundOn) {
            AssetHandler.music.play();
        }
    }

    public static void playVictory() {
        if(DataHandler.soundOn) {
            AssetHandler.victory.play();
        }
    }
}
