package com.outcast.rpgskill.api.util;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

//===========================================================================================================
// Utility methods for livingEnity
//===========================================================================================================

public class LivingUtil {

    // Apply potion effect
    // Remove potion effect

    /**
     * Needs to be worked on
     *
     * @param living the entity that will receive the damage
     * @param target the entity that will cause the damage
     * @param source the source cause of damage
     * @param amount damage amount
     * @return true/false if entity was damaged
     */
    public static boolean damageLiving(LivingEntity living, Entity target, DamageCause source, double amount) {
        living.damage(amount, target);
        return true;
    }

    /**
     * Needs to be worked on
     *
     * @param living the entity that will receive the healing
     * @param amount amount of healing the entity will receive if capable of healing
     * @return boolean value to see if entity was healed
     */
    public static boolean healLiving(LivingEntity living, double amount) {
        double health = living.getHealth();
        double maxHealth = living.getMaxHealth();

        double result = health + amount;

        result = Math.min(result, maxHealth);
        result = Math.max(result, 0.0);

        return true;
    }

}
