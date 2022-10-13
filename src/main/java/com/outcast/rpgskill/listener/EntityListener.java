package com.outcast.rpgskill.listener;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.outcast.rpgskill.facade.EffectFacade;
import com.outcast.rpgskill.facade.ResourceFacade;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;

//===========================================================================================================
// Entity listener that triggers different methods events
//===========================================================================================================
@Singleton
public class EntityListener implements Listener {

    @Inject
    EffectFacade effectFacade;

    @Inject
    ResourceFacade resourceFacade;

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntityDeath(EntityDeathEvent event) {
        effectFacade.onEntityDeath(event.getEntity());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntityDeath(PlayerJoinEvent event) {
        resourceFacade.onPlayerJoin(event.getPlayer());
    }

}
