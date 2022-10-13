package com.outcast.rpgskill.service;

import com.outcast.rpgskill.RPGSkill;
import com.outcast.rpgskill.api.effect.Applyable;
import com.outcast.rpgskill.api.effect.ApplyableCarrier;
import com.outcast.rpgskill.effect.EntityEffectCarrier;
import org.bukkit.entity.LivingEntity;
import org.bukkit.scheduler.BukkitRunnable;

import javax.inject.Singleton;
import java.util.*;

//===========================================================================================================
// Utility service class that houses methods for getting/setting/registering effects
//===========================================================================================================
@Singleton
public class EffectService {

    private Map<String, Applyable> effects = new HashMap<>();
    private Map<UUID, ApplyableCarrier> cache = new HashMap<>();

    public void registerEffect(Applyable applyable) {
        effects.put(applyable.getId(), applyable);
    }

    public void registerEffects(Applyable... applyables) {
        for (Applyable applyable : applyables) {
            registerEffect(applyable);
        }
    }

    public Optional<Applyable> getEffectById(String id) {
        return Optional.ofNullable(effects.get(id));
    }

    public EffectService() {
        // constructor run service
        new BukkitRunnable() {
            @Override
            public void run() {
                tick();
            }
        }.runTaskTimer(RPGSkill.getInstance(), 0, 1);
    }

    private void tick() {
        long timestamp = System.currentTimeMillis();

        cache.entrySet().removeIf(entry -> {
            if (entry.getValue().hasEffects()) {
                tickAllEffects(timestamp, entry.getValue());
                return false;
            } else {
                return true;
            }
        });
    }

    public ApplyableCarrier<?> getOrCreateCarrier(LivingEntity entity) {
        if (cache.containsKey(entity.getUniqueId())) {
            return cache.get(entity.getUniqueId());
        } else {
            EntityEffectCarrier newCarrier = new EntityEffectCarrier(entity);
            cache.put(entity.getUniqueId(), newCarrier);
            return newCarrier;
        }
    }

    private void tickAllEffects(long timestamp, ApplyableCarrier<?> carrier) {
        carrier.getEffects().removeIf(effect -> tickEffect(timestamp, carrier, effect));
    }

    private boolean tickEffect(long timestamp, ApplyableCarrier<?> carrier, Applyable effect) {
        if (effect.canApply(timestamp, carrier)) {
            effect.apply(timestamp, carrier);
        }

        if (effect.canRemove(timestamp, carrier)) {
            effect.remove(timestamp, carrier);
            return true;
        }

        return false;
    }

    public void applyEffect(LivingEntity entity, Applyable applyable) {
        getOrCreateCarrier(entity).addEffect(applyable);
    }

    public void applyEffect(LivingEntity entity, String effectId) {
        Applyable effect = effects.get(effectId);
        if (effect != null) {
            getOrCreateCarrier(entity).addEffect(effect);
        }
    }

    public boolean hasEffect(LivingEntity entity, Applyable applyable) {
        return getOrCreateCarrier(entity).hasEffect(applyable);
    }

    public boolean hasEffect(LivingEntity entity, String effectId) {
        return getOrCreateCarrier(entity).getEffects().stream().anyMatch(effect -> effectId.equals(effect.getId()));
    }

    public void removeEffect(LivingEntity entity, Applyable applyable) {
        getOrCreateCarrier(entity).removeEffect(applyable);
    }

    public void removeEffect(LivingEntity entity, String effectId) {
        Set<Applyable> effects = getOrCreateCarrier(entity).getEffects();

        effects.removeIf(effect -> effect.getId().equals(effectId));
    }

    public void clearEffects(LivingEntity entity) {
        ApplyableCarrier<?> carrier = getOrCreateCarrier(entity);

        carrier.getEffects().forEach(Applyable::setRemoved);
    }

    public void clearNegativeEffects(LivingEntity entity) {
        ApplyableCarrier<?> carrier = getOrCreateCarrier(entity);

        carrier.getEffects().forEach(applyable -> {
            if (!applyable.isPositive()) {
                applyable.setRemoved();
            }
        });
    }

    public void clearPositiveEffects(LivingEntity entity) {
        ApplyableCarrier<?> carrier = getOrCreateCarrier(entity);

        carrier.getEffects().forEach(applyable -> {
            if (applyable.isPositive()) {
                applyable.setRemoved();
            }
        });
    }

    public Optional<Applyable> getNamedEffect(String id) {
        return Optional.ofNullable(effects.get(id));
    }

}