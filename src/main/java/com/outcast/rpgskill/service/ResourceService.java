package com.outcast.rpgskill.service;

import com.outcast.rpgskill.RPGSkill;
import com.outcast.rpgskill.RPGSkillConfig;
import com.outcast.rpgskill.api.event.ResourceEvent;
import com.outcast.rpgskill.api.resource.ResourceEntity;
import com.outcast.rpgskill.resource.EntityResourceUser;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

//===========================================================================================================
// Utility service class that houses methods for getting/setting resource information
//===========================================================================================================
@Singleton
public class ResourceService {

    private RPGSkillConfig config;
    private Map<UUID, ResourceEntity> resourceEntities = new HashMap<>();

    @Inject
    ResourceService(RPGSkillConfig config) {
        this.config = config;
        // task
        new BukkitRunnable() {
            @Override
            public void run() { regenResource(); }
        }.runTaskTimer(RPGSkill.getInstance(), 0, config.RESOURCE_REGEN_TICK_INTERVAL);
    }

    public void regenResource() {
        resourceEntities.forEach((uuid, entity) -> {
            // If resource is maximum then don't regen
            if(entity.getCurrent() >= entity.getMax()) {
                return;
            }

            // Update this to be based off entities external attributes
            double regenAmount = config.RESOURCE_REGEN_RATE;

            if(entity.getMax() - entity.getCurrent() <= regenAmount) {
                regenAmount = entity.getMax() - entity.getCurrent();
            }

            Player player = Bukkit.getServer().getPlayer(uuid);

            ResourceEvent.Regen event;
            if(player != null) {
                event = new ResourceEvent.Regen(player, entity, regenAmount);
            } else {
                event = new ResourceEvent.Regen(entity, regenAmount);
            }

            // call event
            Bukkit.getPluginManager().callEvent(event);

            if(event.isCancelled()) {
                return;
            }

            entity.fill(event.getRegenAmount());
        });
    }

    public ResourceEntity getOrCreateEntity(LivingEntity living) {
        ResourceEntity resourceEntity = resourceEntities.get(living.getUniqueId());

        if (resourceEntity == null) {
            resourceEntity = new EntityResourceUser(living);
            resourceEntity.setMax(config.RESOURCE_LIMIT);
            resourceEntity.fill();
            resourceEntities.put(living.getUniqueId(), resourceEntity);

            // call event
            Bukkit.getPluginManager().callEvent(new ResourceEvent.Create(living, resourceEntity));
        }

        return resourceEntity;
    }

    public void withdrawResource(LivingEntity living, double amount) {
        getOrCreateEntity(living).drain(amount);
    }

}
