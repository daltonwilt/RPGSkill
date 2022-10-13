package com.outcast.rpgskill.api.event;

import com.outcast.rpgskill.api.resource.ResourceEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.Optional;

//===========================================================================================================
// An event that manipulates and creates resource information for an entity
//===========================================================================================================
public class ResourceEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    private boolean cancelled;
    private LivingEntity living;
    private ResourceEntity resourceEntity;

    /**
     * @param living that has triggered resource event
     * @param resourceEntity interface that houses and allows manipulation of entity resources
     */
    public ResourceEvent(LivingEntity living, ResourceEntity resourceEntity) {
        this.living = living;
        this.resourceEntity = resourceEntity;
    }

    public ResourceEvent(ResourceEntity resourceEntity) {
        this.resourceEntity = resourceEntity;
    }

    public ResourceEntity getResourceEntity() {
        return resourceEntity;
    }

    public Optional<LivingEntity> getLivingEntity() {
        return Optional.ofNullable(living);
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }

    public static class Create extends ResourceEvent {
        public Create(LivingEntity living, ResourceEntity resourceEntity) {
            super(living, resourceEntity);
        }

        public Create(ResourceEntity resourceEntity) {
            super(resourceEntity);
        }
    }

    public static class Regen extends ResourceEvent implements Cancellable {
        private double regenAmount;

        public Regen(LivingEntity living, ResourceEntity resourceEntity, double regenAmount) {
            super(living, resourceEntity);
            this.regenAmount = regenAmount;
        }

        public Regen(ResourceEntity resourceEntity, double regenAmount) {
            super(resourceEntity);
            this.regenAmount = regenAmount;
        }

        public void setRegenAmount(double regenAmount) {
            this.regenAmount = regenAmount;
        }

        public double getRegenAmount() {
            return regenAmount;
        }
    }

    public HandlerList getHandlers() {
        return handlers;
    }
    public static  HandlerList getHandlerList() {
        return handlers;
    }

}
