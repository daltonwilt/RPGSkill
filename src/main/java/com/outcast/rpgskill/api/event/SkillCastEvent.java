package com.outcast.rpgskill.api.event;

import com.outcast.rpgskill.api.skill.CastResult;
import com.outcast.rpgskill.api.skill.Castable;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

//===========================================================================================================
// An event that tracks skill information while be casted by an entity
//===========================================================================================================

public abstract class SkillCastEvent extends Event {

    protected LivingEntity living;
    protected Castable skill;
    private long timestamp;

    /**
     * @param living that is casting the skill
     * @param skill the skill cast that has triggered this event
     * @param timestamp the point in which the skill was cast ( event )
     */
    public SkillCastEvent(LivingEntity living, Castable skill, long timestamp) {
        this.living = living;
        this.skill = skill;
        this.timestamp = timestamp;
    }

    public LivingEntity getLivingEntity() {
        return living;
    }

    public Castable getSkill() {
        return skill;
    }

    public long getTimestamp() {
        return timestamp;
    }

    //===========================================================================================================
    // Implement cancellable so skills can be canceled before casting
    //===========================================================================================================

    public static class Pre extends SkillCastEvent implements Cancellable {

        private static final HandlerList handlers = new HandlerList();

        private boolean cancelled;

        public Pre(LivingEntity living, Castable skill, long timestamp) {
            super(living, skill, timestamp);
        }

        public void setEntity(LivingEntity living) {
            this.living = living;
        }

        public void setSkill(Castable skill) {
            this.skill = skill;
        }

        @Override
        public boolean isCancelled() {
            return cancelled;
        }

        @Override
        public void setCancelled(boolean cancel) {
            this.cancelled = cancel;
        }

        public HandlerList getHandlers() {
            return handlers;
        }
        public static  HandlerList getHandlerList() {
            return handlers;
        }
    }

    //===========================================================================================================
    // Again implement cancellable for those skills that continue channeling after casted
    //===========================================================================================================

    public static class Post extends SkillCastEvent implements Cancellable {

        private static final HandlerList handlers = new HandlerList();

        private boolean cancelled;

        @Override
        public boolean isCancelled() {
            return cancelled;
        }

        @Override
        public void setCancelled(boolean cancel) {
            this.cancelled = cancel;
        }

        private CastResult result;

        public Post(LivingEntity living, Castable skill, long timestamp, CastResult result) {
            super(living, skill, timestamp);
            this.result = result;
        }

        public CastResult getResult() {
            return result;
        }

        public HandlerList getHandlers() {
            return handlers;
        }
        public static  HandlerList getHandlerList() {
            return handlers;
        }
    }

}
