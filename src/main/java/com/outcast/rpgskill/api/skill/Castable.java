package com.outcast.rpgskill.api.skill;

import com.outcast.rpgskill.api.exception.CastException;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.entity.LivingEntity;

//===========================================================================================================
// Interface that grabs skill information to casting result
//===========================================================================================================
public interface Castable {

    String getId();

    String getName();

    String getPermission();

    TextComponent getDescription(LivingEntity living);

    long getCooldown(LivingEntity living);

    double getResourceCost(LivingEntity living);

    /**
     * Triggers the use of this Castable by the provided Living
     *
     * @param living    The user of the skill
     * @param timestamp The timestamp of when the skill is being triggered
     * @param args      Arguments
     * @return A {@link CastResult}
     * @throws CastException If a skill-related error occurs
     */
    CastResult cast(LivingEntity living, long timestamp, String... args) throws CastException;

}
