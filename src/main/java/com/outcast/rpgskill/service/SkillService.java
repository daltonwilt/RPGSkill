package com.outcast.rpgskill.service;

import com.outcast.rpgskill.api.event.SkillCastEvent;
import com.outcast.rpgskill.api.exception.CastException;
import com.outcast.rpgskill.api.resource.ResourceEntity;
import com.outcast.rpgskill.api.skill.CastError;
import com.outcast.rpgskill.api.skill.CastResult;
import com.outcast.rpgskill.api.skill.Castable;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

//===========================================================================================================
// Utility service class that houses methods for getting/setting/registering skills
//===========================================================================================================
@Singleton
public class SkillService {

    private Map<String, Castable> skills = new HashMap<>();

    @Inject
    ResourceService resourceService;

    @Inject
    CooldownService cooldownService;

    SkillService() {}

    public void registerSkill(Castable castable) {
        skills.put(castable.getId(), castable);
    }

    public void registerSkills(Castable... castables) {
        for (Castable castable : castables) {
            registerSkill(castable);
        }
    }

    public Optional<Castable> getSkillById(String id) {
        return Optional.ofNullable(skills.get(id));
    }

    /**
     * Casts a castable with the properties stored within this carrier
     *
     * @param living      The caster casting the skill
     * @param castable  the castable skill
     * @param timestamp When the skill is being cast
     * @param args      arguments
     * @return a {@link CastResult}
     */
    public CastResult castSkill(LivingEntity living, Castable castable, long timestamp, String... args) throws CastException {
        // Trigger the pre-cast event
        SkillCastEvent.Pre preCastEvent = new SkillCastEvent.Pre(living, castable, timestamp);
        // call event
        Bukkit.getPluginManager().callEvent(preCastEvent);

        // If the pre-cast event was cancelled, throw a cancelled exception
        if (preCastEvent.isCancelled()) {
            throw CastError.cancelled(castable);
        }

        // Set the entity, skill and skill properties to what was set in the pre-cast event
        living = preCastEvent.getLivingEntity();
        castable = preCastEvent.getSkill();

        // Validate
        if (validateSkillUse(living, castable, timestamp)) {

            // Cast the skill
            CastResult result = castable.cast(living, timestamp, args);

            // Set cooldown(s) and withdraw resources
            cooldownService.putOnGlobalCooldown(living, timestamp);
            cooldownService.setLastUsedTimestamp(living, castable, timestamp);
            resourceService.withdrawResource(living, castable.getResourceCost(living));

            // Trigger the post-cast event with the result
            SkillCastEvent.Post postCastEvent = new SkillCastEvent.Post(living, castable, timestamp, result);
            // call event
            Bukkit.getPluginManager().callEvent(postCastEvent);

            // Return the result
            return result;
        } else {
            throw CastError.internalError();
        }
    }

    private boolean validateSkillUse(LivingEntity living, Castable castable, long timestamp) throws CastException {
        boolean valid = validatePermission(living, castable);
        valid = valid && validateGlobalCooldown(living, timestamp);
        valid = valid && validateCooldown(living, castable, timestamp);
        valid = valid && validateResources(living, castable);

        return valid;
    }

    private boolean validatePermission(LivingEntity living, Castable skill) throws CastException {
        // If the entity is a player, check for permission.
        // If the entity is not a player, just return true ( is presumed to be non-player character )
        if (living instanceof Player) {
            String permission = skill.getPermission();

            // If no permission is set, just return true
            if (permission == null) {
                return true;
            }

            boolean permitted = living.hasPermission(permission);

            if (!permitted) {
                throw CastError.noPermission(skill);
            }
        }

        return true;
    }

    private boolean validateGlobalCooldown(LivingEntity living, Long timestamp) throws CastException {
        if (cooldownService.isOnGlobalCooldown(living, timestamp)) {
            long cooldownEnd = cooldownService.getLastGlobalCooldownEnd(living);
            throw CastError.onGlobalCooldown(timestamp, cooldownEnd);
        }

        return true;
    }

    private boolean validateCooldown(LivingEntity living, Castable castable, Long timestamp) throws CastException {
        long lastUsed = cooldownService.getLastUsedTimestamp(living, castable);
        long cooldownDuration = castable.getCooldown(living);

        if (cooldownService.isCooldownOngoing(timestamp, lastUsed, cooldownDuration)) {
            long cooldownEnd = cooldownService.getCooldownEnd(lastUsed, cooldownDuration);
            throw CastError.onCooldown(timestamp, castable, cooldownEnd);
        }

        return true;
    }

    private boolean validateResources(LivingEntity living, Castable castable) throws CastException {
        ResourceEntity resourceEntity = resourceService.getOrCreateEntity(living);

        if (resourceEntity.getCurrent() < castable.getResourceCost(living)) {
            throw CastError.insufficientResources(castable);
        }

        return true;
    }

    public Map<String, Castable> getAllSkills() {
        return skills;
    }

}
