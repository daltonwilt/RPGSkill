package com.outcast.rpgskill.effect;

import com.outcast.rpgcore.db.Identifiable;
import com.outcast.rpgskill.api.effect.Applyable;
import com.outcast.rpgskill.api.effect.ApplyableCarrier;
import org.bukkit.entity.LivingEntity;

import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

//===========================================================================================================
// Class that caches effects that are currently on the living entity and the ability to
// add/remove an effect from the entity
//===========================================================================================================
public class EntityEffectCarrier implements Identifiable, ApplyableCarrier<LivingEntity> {

    private LivingEntity cachedLiving;
    private Set<Applyable> effects = new HashSet<>();

    public EntityEffectCarrier(LivingEntity living) {
        this.cachedLiving = living;
    }

    @Override
    public Optional<LivingEntity> getLiving() {
        if( cachedLiving == null )
            return Optional.empty();

        return Optional.of(cachedLiving);
    }

    @Override
    public void addEffect(Applyable applyable) {
        effects.add(applyable);
    }

    @Override
    public void removeEffect(Applyable applyable) {
        effects.remove(applyable);
    }

    @Override
    public boolean hasEffect(Applyable applyable) {
        return effects.contains(applyable);
    }

    @Override
    public boolean hasEffects() {
        return !effects.isEmpty();
    }

    @Override
    public Set<Applyable> getEffects() {
        return effects;
    }

    @Nonnull
    @Override
    public UUID getId() {
        return cachedLiving.getUniqueId();
    }

}
