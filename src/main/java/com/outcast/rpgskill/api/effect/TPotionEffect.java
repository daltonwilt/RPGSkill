package com.outcast.rpgskill.api.effect;

import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

//===========================================================================================================
// Add a potion effect to an entity ( Needs to be finished )
//===========================================================================================================
public class TPotionEffect extends TemporaryEffect {

    private PotionEffect effect;

    public TPotionEffect(String id, String name, PotionEffect effect, boolean isPositive) {
        super(id, name, effect.getDuration() * 50L, isPositive);
        this.effect = effect;
    }

    public static TPotionEffect of(PotionEffectType effectType, int duration, int amplifier, boolean isPositive) {
        return new TPotionEffect(
                "rpg:" + effectType.getKey().toString() + "_effect",
                effectType.getName(),
                new PotionEffect(effectType, duration, amplifier),
                isPositive
        );
    }

    @Override
    protected boolean apply(ApplyableCarrier<?> character) {

        // apply potion effect from character as well as set to boolean
        return false;
    }

    @Override
    protected boolean remove(ApplyableCarrier<?> character) {
        // remove potion effect from character as well as set to boolean
        return false;
    }

}
