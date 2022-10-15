package com.outcast.rpgskill.skill;

import com.outcast.rpgskill.api.exception.CastException;
import com.outcast.rpgskill.api.skill.CastError;
import com.outcast.rpgskill.api.skill.CastResult;
import com.outcast.rpgskill.api.skill.TargetedSkill;
import org.bukkit.entity.LivingEntity;

//===========================================================================================================
// Example for a base targeted skill
//
// Doesn't currently work because need to figure out raycasting
//===========================================================================================================

public class BaseDamageSkill extends TargetedSkill {

    private final double DEFAULT_DAMAGE = 5.0;

    public BaseDamageSkill() {
        super("base-damage", "Base Damage Skill");
    }

    @Override
    public CastResult cast(LivingEntity living, LivingEntity target, long timestamp, String... args) throws CastException {
        if(args.length == 0)
            throw CastError.invalidArguments();

        double damage;

        try {
            damage = Double.parseDouble(args[0]);
        } catch (NumberFormatException e) {
            damage = DEFAULT_DAMAGE;
        }

        living.sendMessage("Dealing " + damage + " damage to " + target.getType().getName());
        target.damage(damage, living);

        return CastResult.success();
    }

    @Override
    public long getCooldown(LivingEntity user) {
        return 6000;
    }

    @Override
    public double getResourceCost(LivingEntity user) {
        return 20.0;
    }

}
