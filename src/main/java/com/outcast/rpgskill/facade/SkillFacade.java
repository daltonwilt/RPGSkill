package com.outcast.rpgskill.facade;

import com.outcast.rpgskill.api.exception.CastException;
import com.outcast.rpgskill.api.skill.CastError;
import com.outcast.rpgskill.api.skill.CastResult;
import com.outcast.rpgskill.api.skill.Castable;
import com.outcast.rpgskill.service.SkillService;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import javax.inject.Inject;
import javax.inject.Singleton;

//===========================================================================================================
// Facade class that implements the use of casting skills and returning the skill result
//===========================================================================================================
@Singleton
public class SkillFacade {

    @Inject
    SkillService skillService;

    SkillFacade(){}

    public void playerCastSkill(Player caster, Castable skill, String... args) throws CastException {
        livingCastSkill(caster, skill, args);
    }

    public void livingCastSkill(LivingEntity caster, Castable skill, String... args) throws CastException {
        if(skill == null)
            throw CastError.exceptionOf("Must provide valid skill");

        if(args == null)
            args = new String[0];

        CastResult castResult = skillService.castSkill(caster, skill, System.currentTimeMillis(), args);

        if(castResult == null) {
            throw CastError.internalError();
        } else {
            String message = castResult.getMessage();

            if(!message.isEmpty()) {
                caster.sendMessage(message);
            }
        }
    }

}
