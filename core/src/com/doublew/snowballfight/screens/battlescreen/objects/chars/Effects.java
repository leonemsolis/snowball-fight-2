package com.doublew.snowballfight.screens.battlescreen.objects.chars;

import java.util.ArrayList;

/**
 * Created by Leonemsolis on 14/02/2018.
 */

public class Effects {
    private ArrayList<Effect>effects;
    private ArrayList<Effect>toDelete;

    public Effects() {
        effects = new ArrayList<Effect>();
        toDelete = new ArrayList<Effect>();
    }

    public void addEffect(int id, float time) {
        // Basic Snowball OR Boulder
        if(id == 4 || id == 1) return;
        effects.add(new Effect(id, time));
    }

    public void update(float delta) {
        for (Effect e:effects) {
            if(e.timer - delta <= 0) {
                toDelete.add(e);
            } else {
                e.timer -= delta;
            }
        }
        proceedDeleting();
    }

    public boolean isEmpty() {
        return effects.isEmpty();
    }

    private void proceedDeleting() {
        if(!toDelete.isEmpty()) {
            for (Effect e:toDelete) {
                effects.remove(e);
            }
            toDelete.clear();
        }
    }

    public void clearAllEffects() {
        effects.clear();
    }

    public ArrayList<Effect> getEffects() {
        return effects;
    }

    public boolean containsFrozen() {
        for (Effect e: effects) {
            if(e.id == 2 || e.id == 3) {
                return true;
            }
        }
        return false;
    }
}
