package com.doublew.snowballfight.support;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Leonemsolis on 14/09/2017.
 *
 * AssetManager loads textures, sounds, etc..
 * and gives access to this data
 */

public class AssetHandler {

    public static Texture backgrounds, battle, buttons, chars, help, items, mapStuff, animation;

    public static TextureRegion specialButtonUp, specialButtonDown;
    public static TextureRegion playButtonUp, playButtonDown;
    public static TextureRegion settingsButtonUp, settingsButtonDown;
    public static TextureRegion helpButtonUp, helpButtonDown;
    public static TextureRegion likeButtonUp, likeButtonDown;
    public static TextureRegion backButtonUp, backButtonDown;

    public static TextureRegion map, promptFrame, promptButtonUp, promptButtonDown, unavailableLevel;
    public static TextureRegion drugStoreBackground, itemStoreBackground, itemSelectFrame;
    public static TextureRegion[] mapCharacter_still, mapCharacter_walking;
    public static TextureRegion[] battleStill, battleAiming, battleLeft, battleRight, battleThrowing, battleWinning;
    public static TextureRegion battleDying;

    public static TextureRegion battleUI, downTransition, battleBackgrounds[];
    public static TextureRegion leftControlUp, leftControlDown;
    public static TextureRegion rightControlUp, rightControlDown;
    public static TextureRegion centerControlUp, centerControlDown;
    public static TextureRegion pauseButtonUp, pauseButtonDown;
    public static TextureRegion soundToggleOn, soundToggleOff;
    public static TextureRegion colorToggleOn, colorToggleOff;
    public static TextureRegion infoButtonUp, infoButtonDown;

    public static TextureRegion snowball, snowballShadow, powerBar, pauseInventory;

    public static TextureRegion levelIndicator, redSquare, greenSquare, blackSquare, whiteSquare;

    public static TextureRegion enemySkin1_still[], enemySkin1_throwing, enemySkin1_dying;
    public static TextureRegion enemySkin2_still[], enemySkin2_throwing, enemySkin2_dying;

    public static TextureRegion bossSkin1_still[], bossSkin1_throwing, bossSkin1_dying;
    public static TextureRegion bossSkin2_still[], bossSkin2_throwing, bossSkin2_dying;
    public static TextureRegion bossSkin3_still[], bossSkin3_throwing, bossSkin3_dying;
    public static TextureRegion bossSkin4_still[], bossSkin4_throwing, bossSkin4_dying;
    public static TextureRegion[] special, gloves;
    public static TextureRegion specialCastBackground;

    public static TextureRegion itemsSmall[], itemsBig[];

    public static TextureRegion stunWhirligig, frozen;

    public static TextureRegion finishBackground, settingsBackground;
    public static TextureRegion finishFront;

    public static BitmapFont grayFont, blackFont, redFont, yellowFont, blueFont;


    public static Sound clickEmpty, mapCharacterWalking, storeSelect, firework;
    public static Sound specialSlide;
    public static Sound buttonDown, buttonUp;
    public static Sound greetings, hit, hurt, lose, explosion, fire, fire2, music, victory;

    public static void loadAssets() {
        loadSounds();
        loadFonts();
        loadTextures();
        loadSprites();
    }

    public static void loadSounds() {
        //OGG
        clickEmpty = Gdx.audio.newSound(Gdx.files.internal("sounds/clickEmpty.ogg"));
        mapCharacterWalking = Gdx.audio.newSound(Gdx.files.internal("sounds/mapCharWalking.ogg"));
        buttonDown = Gdx.audio.newSound(Gdx.files.internal("sounds/buttonDown.ogg"));
        buttonUp = Gdx.audio.newSound(Gdx.files.internal("sounds/buttonUp.ogg"));
        storeSelect = Gdx.audio.newSound(Gdx.files.internal("sounds/storeSelect.ogg"));
        firework = Gdx.audio.newSound(Gdx.files.internal("sounds/firework.ogg"));
        specialSlide = Gdx.audio.newSound(Gdx.files.internal("sounds/specialSlide.ogg"));
        greetings = Gdx.audio.newSound(Gdx.files.internal("sounds/greetings.ogg"));
        hit = Gdx.audio.newSound(Gdx.files.internal("sounds/hit.ogg"));
        hurt = Gdx.audio.newSound(Gdx.files.internal("sounds/hurt.ogg"));
        lose = Gdx.audio.newSound(Gdx.files.internal("sounds/lose.ogg"));
        explosion = Gdx.audio.newSound(Gdx.files.internal("sounds/explosion.ogg"));
        fire = Gdx.audio.newSound(Gdx.files.internal("sounds/fire.ogg"));
        fire2 = Gdx.audio.newSound(Gdx.files.internal("sounds/fire2.ogg"));
        music = Gdx.audio.newSound(Gdx.files.internal("sounds/music.ogg"));
        victory = Gdx.audio.newSound(Gdx.files.internal("sounds/victory.ogg"));
    }

    public static void loadFonts() {
        grayFont = new BitmapFont(Gdx.files.internal("font/gray.fnt"), true);
        blackFont = new BitmapFont(Gdx.files.internal("font/black.fnt"), true);
        redFont = new BitmapFont(Gdx.files.internal("font/red.fnt"), true);
        yellowFont = new BitmapFont(Gdx.files.internal("font/yellow.fnt"), true);
        blueFont = new BitmapFont(Gdx.files.internal("font/blue.fnt"), true);
    }

    public static void loadTextures() {
        backgrounds = new Texture(Gdx.files.internal("graphics/backgrounds.png"));

        battle = new Texture(Gdx.files.internal("graphics/battle.png"));

        buttons = new Texture(Gdx.files.internal("graphics/buttons.png"));

        chars = new Texture(Gdx.files.internal("graphics/chars.png"));

        help = new Texture(Gdx.files.internal("graphics/help.png"));

        items = new Texture(Gdx.files.internal("graphics/items.png"));

        mapStuff = new Texture(Gdx.files.internal("graphics/map.png"));

        animation = new Texture(Gdx.files.internal("graphics/animation.png"));
    }

    public static void loadSprites() {
        battleBackgrounds = new TextureRegion[4];
        battleBackgrounds[0] = loadBackgrounds(0, 0, 320, 63);
        battleBackgrounds[1] = loadBackgrounds(0, 63, 320, 63);
        battleBackgrounds[2] = loadBackgrounds(0, 126, 320, 63);
        battleBackgrounds[3] = loadBackgrounds(0, 189, 320, 63);

        playButtonUp = loadButtons(10, 0, 201, 255);

        playButtonDown = loadButtons(210, 0, 198, 255);

        settingsButtonUp = loadButtons(14, 256, 256, 256);

        settingsButtonDown = loadButtons(270, 256, 256, 256);

        likeButtonUp = loadButtons(409, 0, 256, 236);

        likeButtonDown = loadButtons(665, 0, 256, 236);

        helpButtonUp = loadButtons(526, 256, 242, 256);

        helpButtonDown = loadButtons(768, 256, 242, 256);

        soundToggleOn = loadMap(960, 356, 142, 53);
        soundToggleOff = loadMap(1128, 356, 142, 53);

        colorToggleOn = loadMap(960, 430, 142, 53);
        colorToggleOff = loadMap(1128, 430, 142, 53);

        map = loadMap(0, 0, 320, 349);

        itemStoreBackground = loadMap(320, 0, 320, 333);

        drugStoreBackground = loadMap(640, 0, 320, 333);

        promptFrame = loadMap(280, 350, 280, 200);

        promptButtonUp = loadMap(36, 476, 69, 45);

        promptButtonDown = loadMap(176, 476, 69, 45);

        backButtonDown = loadMap(880, 511, 80, 39);
        backButtonUp = loadMap(880, 550, 80, 39);

        mapCharacter_still = new TextureRegion[3];
        mapCharacter_still[0] = loadAnimation(2, 0, 42, 60);
        mapCharacter_still[1] = loadAnimation(48, 0, 40, 60);
        mapCharacter_still[2] = loadAnimation(91, 0, 42, 60);

        mapCharacter_walking = new TextureRegion[3];
        mapCharacter_walking[0] = loadAnimation(0, 62, 43, 59);
        mapCharacter_walking[1] = loadAnimation(43, 62, 43, 59);
        mapCharacter_walking[2] = loadAnimation(86, 62, 43, 59);

        unavailableLevel = loadMap(881, 415, 70, 47);

        battleUI = loadBattle(0, 1440, 320, 51);

        downTransition = loadBattle(0, 1356, 320, 34);

        leftControlUp = loadBattle(0, 1285, 32, 70);

        leftControlDown = loadBattle(32, 1285, 32, 70);

        rightControlUp = loadBattle(0, 1285, 32, 70);
        rightControlUp.flip(true, false);

        rightControlDown = loadBattle(32, 1285, 32, 70);
        rightControlDown.flip(true, false);

        centerControlUp = loadBattle(64, 1285, 70, 70);

        centerControlDown = loadBattle(134, 1285, 70, 70);

        battleStill = new TextureRegion[3];
        battleStill[0] = loadAnimation(0, 122, 46, 59);
        battleStill[1] = loadAnimation(46, 122, 45, 57);
        battleStill[2] = loadAnimation(92, 122, 46, 59);

        battleAiming = new TextureRegion[2];
        battleAiming[0] = loadChars(231, 0, 52, 60);
        battleAiming[1] = loadChars(284, 0, 54, 60);

        battleThrowing = new TextureRegion[2];
        battleThrowing[0] = loadChars(284, 0, 55, 60);
        battleThrowing[1] = loadChars(339, 0, 38, 60);

        battleLeft = new TextureRegion[2];
        battleLeft[0] = loadAnimation(0, 122, 46, 59);
        battleLeft[1] = loadChars(135, 0, 45, 60);

        battleRight = new TextureRegion[2];
        battleRight[0] = loadAnimation(46, 122, 45, 59);
        battleRight[1] = loadChars(180, 0, 45, 60);

        battleDying = loadChars(30, 0, 62, 41);

        battleWinning = new TextureRegion[5];
        battleWinning[0] = loadChars(92, 0, 43, 60);
        battleWinning[1] = loadChars(347, 62, 50, 60);
        battleWinning[2] = loadChars(347, 62, 50, 60);
        battleWinning[3] = loadChars(347, 62, 50, 60);
        battleWinning[4] = loadChars(347, 62, 50, 60);

        snowball = loadBattle(338, 1287, 16, 16);
        snowballShadow = loadBattle(338, 1307, 20, 11);

        powerBar = loadBattle(339, 1339, 15, 60);

        //// TODO: 22/10/2017 TEST
        redSquare = loadBattle(19, 1459, 1, 1);
        greenSquare = loadBattle(103, 1118, 1, 1);
        blackSquare = loadBattle(38, 615, 1, 1);
        whiteSquare = loadBackgrounds(107, 57, 1, 1);
        levelIndicator = loadBattle(352, 1405, 31, 15);

        enemySkin1_still = new TextureRegion[2];
        enemySkin1_still[0] = loadChars(0, 174, 35, 40);
        enemySkin1_still[1] = loadChars(35, 174, 39, 40);

        enemySkin1_dying = loadChars(74, 174, 36, 30);

        enemySkin1_throwing = loadChars(110, 175, 35, 38);

        enemySkin2_still = new TextureRegion[2];
        enemySkin2_still[0] = loadChars(147, 174, 35, 40);
        enemySkin2_still[1] = loadChars(185, 175, 39, 40);

        enemySkin2_dying = loadChars(224, 175, 36, 30);

        enemySkin2_throwing = loadChars(261, 175, 40, 38);

        bossSkin1_still = new TextureRegion[2];
        bossSkin1_still[0] = loadChars(0, 60, 38, 55);
        bossSkin1_still[1] = loadChars(38, 60, 45, 55);
        bossSkin1_throwing = loadChars(123, 61, 47, 52);
        bossSkin1_dying = loadChars(83, 60, 39, 35);

        bossSkin2_still = new TextureRegion[2];
        bossSkin2_still[0] = loadChars(170, 61, 38, 55);
        bossSkin2_still[1] = loadChars(209, 61, 45, 55);
        bossSkin2_throwing = loadChars(294, 61, 47, 52);
        bossSkin2_dying = loadChars(253, 61, 40, 35);

        bossSkin3_still = new TextureRegion[2];
        bossSkin3_still[0] = loadChars(0, 115, 44, 58);
        bossSkin3_still[1] = loadChars(44, 115, 52, 58);
        bossSkin3_throwing = loadChars(151, 116, 47, 58);
        bossSkin3_dying = loadChars(96, 115, 55, 45);

        bossSkin4_still = new TextureRegion[2];
        bossSkin4_still[0] = loadChars(198, 116, 44, 58);
        bossSkin4_still[1] = loadChars(242, 116, 52, 58);
        bossSkin4_throwing = loadChars(348, 151, 48, 58);

        bossSkin4_dying = loadChars(294, 116, 55, 45);

        pauseButtonUp = loadBattle(320, 1440, 37, 40);

        pauseButtonDown = loadBattle(320, 1480, 37, 40);

        pauseInventory = loadBattle(0, 1488, 320, 142);

        itemSelectFrame = loadMap(880, 350, 64, 64);

        itemsBig = new TextureRegion[8];
        itemsBig[0] = loadItems(0, 0, 56, 52); // Boot
        itemsBig[1] = loadItems(56, 0, 56, 52); // Boulder
        itemsBig[2] = loadItems(0, 52, 56, 52); // Ice
        itemsBig[3] = loadItems(56, 52, 56, 52); // Icicle
        itemsBig[4] = loadItems(0, 104, 56, 56); // Bandage
        itemsBig[5] = loadItems(56, 104, 56, 56); // Pill
        itemsBig[6] = loadItems(0, 160, 56, 64); // Painkiller
        itemsBig[7] = loadItems(56, 160, 56, 64); // Potion

        itemsSmall = new TextureRegion[9];
        itemsSmall[0] = loadItems(20, 226, 20, 20); // Boot
        itemsSmall[1] = loadItems(40, 226, 20, 20); // Boulder
        itemsSmall[2] = loadItems(60, 226, 20, 20); // Ice
        itemsSmall[3] = loadItems(80, 226, 20, 20); // Icicle
        itemsSmall[4] = loadItems(0, 246, 20, 20); // Bandage
        itemsSmall[5] = loadItems(20, 246, 20, 20); // Pill
        itemsSmall[6] = loadItems(40, 246, 20, 20); // Painkiller
        itemsSmall[7] = loadItems(60, 246, 20, 20); // Potion
        itemsSmall[8] = loadItems(0, 226, 20, 20); // Snowball

        stunWhirligig = loadBattle(195, 1200, 35, 18);
        frozen = loadBattle(195, 1218, 45, 50);

        specialButtonUp = loadBattle(3, 1554, 314, 73);
        specialButtonDown = loadBattle(0, 1630, 314, 73);

        special = new TextureRegion[3];
        special[0] = loadBattle(0, 0, 386, 240);
        special[1] = loadBattle(0, 240, 386, 240);
        special[2] = loadBattle(0, 480, 386, 240);

        gloves = new TextureRegion[2];
        gloves[0] = loadBattle(0, 1200, 94, 86);
        gloves[1] = loadBattle(94, 1200, 100, 77);

        specialCastBackground = loadBattle(0, 720, 320, 479);

        finishBackground = loadMap(560, 350,320, 267);
        finishFront = loadMap(1280, 0,320, 267);

        settingsBackground = loadMap(960, 0,320, 350);

        infoButtonUp = loadMap(1006, 520, 28, 28);
        infoButtonDown = loadMap(1006, 551, 28, 28);
    }

    public static TextureRegion loadBackgrounds(int x, int y, int width, int height) {
        TextureRegion result = new TextureRegion(backgrounds, x, y, width, height);
        result.flip(false, true);
        return result;
    }

    public static TextureRegion loadBattle(int x, int y, int width, int height) {
        TextureRegion result = new TextureRegion(battle, x, y, width, height);
        result.flip(false, true);
        return result;
    }

    public static TextureRegion loadButtons(int x, int y, int width, int height) {
        TextureRegion result = new TextureRegion(buttons, x, y, width, height);
        result.flip(false, true);
        return result;
    }

    public static TextureRegion loadChars(int x, int y, int width, int height) {
        TextureRegion result = new TextureRegion(chars, x, y, width, height);
        result.flip(false, true);
        return result;
    }

    public static TextureRegion loadHelp(int x, int y, int width, int height) {
        TextureRegion result = new TextureRegion(help, x, y, width, height);
        result.flip(false, true);
        return result;
    }

    public static TextureRegion loadItems(int x, int y, int width, int height) {
        TextureRegion result = new TextureRegion(items, x, y, width, height);
        result.flip(false, true);
        return result;
    }

    public static TextureRegion loadMap(int x, int y, int width, int height) {
        TextureRegion result = new TextureRegion(mapStuff, x, y, width, height);
        result.flip(false, true);
        return result;
    }

    public static TextureRegion loadAnimation(int x, int y, int width, int height) {
        TextureRegion result = new TextureRegion(animation, x, y, width, height);
        result.flip(false, true);
        return result;
    }

    public static void dispose() {
        // TEXTURES
        backgrounds.dispose();
        battle.dispose();
        buttons.dispose();
        chars.dispose();
        help.dispose();
        items.dispose();
        mapStuff.dispose();
        animation.dispose();


        // FONTS
        grayFont.dispose();
        blackFont.dispose();
        yellowFont.dispose();
        blueFont.dispose();
        redFont.dispose();


        // SOUNDS
        clickEmpty.dispose();
        mapCharacterWalking.dispose();
        storeSelect.dispose();
        firework.dispose();
        specialSlide.dispose();
        buttonDown.dispose();
        buttonUp.dispose();
        greetings.dispose();
        hit.dispose();
        hurt.dispose();
        lose.dispose();
        explosion.dispose();
        fire.dispose();
        fire2.dispose();
        music.dispose();
        victory.dispose();
    }
}
