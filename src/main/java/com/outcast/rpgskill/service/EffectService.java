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

    public ApplyableCarrier<?> getOrCreateCarrier(LivingEntity living) {
        if (cache.containsKey(living.getUniqueId())) {
            return cache.get(living.getUniqueId());
        } else {
            EntityEffectCarrier newCarrier = new EntityEffectCarrier(living);
            cache.put(living.getUniqueId(), newCarrier);
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

    public void applyEffect(LivingEntity living, Applyable applyable) {
        getOrCreateCarrier(living).addEffect(applyable);
    }

    public void applyEffect(LivingEntity living, String effectId) {
        Applyable effect = effects.get(effectId);
        if (effect != null) {
            getOrCreateCarrier(living).addEffect(effect);
        }
    }

    public boolean hasEffect(LivingEntity living, Applyable applyable) {
        return getOrCreateCarrier(living).hasEffect(applyable);
    }

    public boolean hasEffect(LivingEntity living, String effectId) {
        return getOrCreateCarrier(living).getEffects().stream().anyMatch(effect -> effectId.equals(effect.getId()));
    }

    public void removeEffect(LivingEntity living, Applyable applyable) {
        getOrCreateCarrier(living).removeEffect(applyable);
    }

    public void removeEffect(LivingEntity living, String effectId) {
        Set<Applyable> effects = getOrCreateCarrier(living).getEffects();

        effects.removeIf(effect -> effect.getId().equals(effectId));
    }

    public void clearEffects(LivingEntity living) {
        ApplyableCarrier<?> carrier = getOrCreateCarrier(living);

        carrier.getEffects().forEach(Applyable::setRemoved);
    }

    public void clearNegativeEffects(LivingEntity living) {
        ApplyableCarrier<?> carrier = getOrCreateCarrier(living);

        carrier.getEffects().forEach(applyable -> {
            if (!applyable.isPositive()) {
                applyable.setRemoved();
            }
        });
    }

    public void clearPositiveEffects(LivingEntity living) {
        ApplyableCarrier<?> carrier = getOrCreateCarrier(living);

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