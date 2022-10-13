package com.outcast.rpgskill.api.skill;

import com.outcast.rpgskill.RPGSkill;
import com.outcast.rpgskill.api.exception.CastException;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;

import java.util.function.Predicate;

//===========================================================================================================
// Interface allows entity to use skill on another while checking the path between source and target
// this will check if there are any obstructions that will not allow the skill to hit its target
//
// Need to figure out raycasting
//===========================================================================================================
public interface TargetedCastable extends Castable {

    // Need to figure out how to get the blocks on a target path Ray Casting
    Predicate<Block> blockFilter = hit -> {
        Material type = hit.getType();
        return !RPGSkill.getInstance().getSkillConfig().PASSABLE_BLOCKS.contains(type);
    };

    @Override
    default CastResult cast(LivingEntity user, long timestamp, String... args) throws CastException {
        double range = getRange(user);

        // Check Ray Casting to see if entity can be hit

        throw CastError.noTarget();
    }

    CastResult cast(LivingEntity user, LivingEntity target, long timestamp, String... args) throws CastException;

    default double getRange(LivingEntity entity) {
        return 100.0;
    }
}
