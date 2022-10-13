package com.outcast.rpgskill;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.outcast.rpgskill.facade.EffectFacade;
import com.outcast.rpgskill.facade.ResourceFacade;
import com.outcast.rpgskill.facade.SkillFacade;
import com.outcast.rpgskill.listener.EntityListener;
import com.outcast.rpgskill.listener.ResourceListener;
import com.outcast.rpgskill.service.CooldownService;
import com.outcast.rpgskill.service.EffectService;
import com.outcast.rpgskill.service.ResourceService;
import com.outcast.rpgskill.service.SkillService;

public class RPGSkillModule extends AbstractModule {
    @Override
    protected void configure() {
        // Configs
        bind(RPGSkillConfig.class);

        // Listeners
        bind(EntityListener.class).in(Scopes.SINGLETON);
        bind(ResourceListener.class).in(Scopes.SINGLETON);

        // Services
        bind(CooldownService.class).in(Scopes.SINGLETON);
        bind(EffectService.class).in(Scopes.SINGLETON);
        bind(ResourceService.class).in(Scopes.SINGLETON);
        bind(SkillService.class).in(Scopes.SINGLETON);

        // Facades
        bind(SkillFacade.class).in(Scopes.SINGLETON);
        bind(EffectFacade.class).in(Scopes.SINGLETON);
        bind(ResourceFacade.class).in(Scopes.SINGLETON);
    }
}
