package com.outcast.rpgskill.event;

import com.outcast.rpgskill.api.effect.Applyable;
import com.outcast.rpgskill.service.EffectService;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

//===========================================================================================================
// Event that registers all effects found in the game
//===========================================================================================================
public class EffectRegistrationEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    private EffectService effectService;

    public EffectRegistrationEvent(EffectService effectService) {
        this.effectService = effectService;
    }

    public void registerEffects(Applyable... applyables) {
        effectService.registerEffects(applyables);
    }

    public HandlerList getHandlers() {
        return handlers;
    }
    public static  HandlerList getHandlerList() {
        return handlers;
    }

}
