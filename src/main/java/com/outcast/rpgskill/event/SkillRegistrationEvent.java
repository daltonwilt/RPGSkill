package com.outcast.rpgskill.event;

import com.outcast.rpgskill.api.skill.Castable;
import com.outcast.rpgskill.service.SkillService;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

//===========================================================================================================
// An event that registers all skills found in the game
//===========================================================================================================

public class SkillRegistrationEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    private SkillService skillService;

    public SkillRegistrationEvent(SkillService skillService) {
        this.skillService = skillService;
    }

    public void registerSkills(Castable... castables) {
        skillService.registerSkills(castables);
    }

    public HandlerList getHandlers() {
        return handlers;
    }
    public static  HandlerList getHandlerList() {
        return handlers;
    }

}
