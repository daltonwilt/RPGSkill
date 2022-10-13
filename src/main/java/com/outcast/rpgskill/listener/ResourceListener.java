package com.outcast.rpgskill.listener;

import com.outcast.rpgskill.api.event.ResourceEvent;
import com.outcast.rpgskill.facade.ResourceFacade;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import com.google.inject.Inject;
import com.google.inject.Singleton;

//===========================================================================================================
// Resource listener for updating entity resource information
//===========================================================================================================
@Singleton
public class ResourceListener implements Listener {

    @Inject
    private ResourceFacade resourceFacade;

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onResourceRegen(ResourceEvent.Regen event) {
        resourceFacade.onResourceRegen(event);
    }

}
