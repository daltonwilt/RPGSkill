package com.outcast.rpgskill.facade;

import com.outcast.rpgskill.api.effect.Applyable;
import com.outcast.rpgskill.service.EffectService;
import org.bukkit.command.CommandException;
import org.bukkit.entity.LivingEntity;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Optional;

//===========================================================================================================
// Facade class that allows the use of applying and removing effects for entities
//===========================================================================================================
@Singleton
public class EffectFacade {

    @Inject
    EffectService effectService;

    EffectFacade() {}

    public void applyEffect(LivingEntity living, String effectId) throws CommandException {
        Optional<Applyable> namedEffect = effectService.getNamedEffect(effectId);

        if(!namedEffect.isPresent())
            throw new CommandException("No effect with an id of \"" + effectId + "\" could be found.");

        namedEffect.ifPresent(e -> effectService.applyEffect(living, e));
    }

    public void removeEffect(LivingEntity living, String effectId) throws CommandException {
        effectService.removeEffect(living, effectId);
    }

    public void onEntityDeath(LivingEntity living) {
        effectService.clearEffects(living);
    }

}
