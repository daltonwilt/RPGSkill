package com.outcast.rpgskill.api.effect;

import org.bukkit.entity.LivingEntity;

import java.util.Optional;
import java.util.Set;

//===========================================================================================================
// Methods to add and remove effects from carrier entity
//===========================================================================================================

public interface ApplyableCarrier<T extends LivingEntity> {

    Optional<T> getLivingEntity();

    void addEffect(Applyable applyable);

    void removeEffect(Applyable applyable);

    boolean hasEffect(Applyable applyable);

    boolean hasEffects();

    Set<Applyable> getEffects();

}
