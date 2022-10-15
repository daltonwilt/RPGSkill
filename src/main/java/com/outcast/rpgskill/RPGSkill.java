package com.outcast.rpgskill;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.outcast.rpgcore.RPGCore;
import com.outcast.rpgcore.command.CommandService;
import com.outcast.rpgskill.command.SkillCommandArgs;
import com.outcast.rpgskill.event.EffectRegistrationEvent;
import com.outcast.rpgskill.event.SkillRegistrationEvent;
import com.outcast.rpgskill.facade.EffectFacade;
import com.outcast.rpgskill.facade.ResourceFacade;
import com.outcast.rpgskill.facade.SkillFacade;
import com.outcast.rpgskill.facade.SkillMessagingFacade;
import com.outcast.rpgskill.listener.EntityListener;
import com.outcast.rpgskill.listener.ResourceListener;
import com.outcast.rpgskill.service.CooldownService;
import com.outcast.rpgskill.service.EffectService;
import com.outcast.rpgskill.service.ResourceService;
import com.outcast.rpgskill.service.SkillService;
import com.outcast.rpgskill.skill.BaseDamageSkill;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class RPGSkill extends JavaPlugin {

    private static RPGSkill instance;
    private Components components;
    private Injector injector;

    //===========================================================================================================
    // Getter methods.
    //===========================================================================================================

    public static RPGSkill getInstance() {
        return instance;
    }

    public RPGSkillConfig getSkillConfig() {
        return components.config;
    }

    public EffectService getEffectService() {
        return components.effectService;
    }

    public SkillService getSkillService() {
        return components.skillService;
    }

    public CooldownService getCooldownService() {
        return components.cooldownService;
    }

    public ResourceService getResourceService() {
        return components.resourceService;
    }

    public EffectFacade getEffectFacade() {
        return components.effectFacade;
    }

    public SkillFacade getSkillFacade() {
        return components.skillFacade;
    }

    public SkillMessagingFacade getMessagingFacade() {
        return components.skillMessagingFacade;
    }

    //===========================================================================================================
    // Utility methods and components.
    //===========================================================================================================

    private static class Components {

        @Inject
        RPGSkillConfig config;

        @Inject
        EffectService effectService;

        @Inject
        SkillService skillService;

        @Inject
        CooldownService cooldownService;

        @Inject
        ResourceService resourceService;

        @Inject
        EffectFacade effectFacade;

        @Inject
        SkillFacade skillFacade;

        @Inject
        SkillMessagingFacade skillMessagingFacade;

        @Inject
        ResourceFacade resourceFacade;

        @Inject
        EntityListener entityListener;

        @Inject
        ResourceListener resourceListener;
    }

    private static void printBlank() {
        RPGCore.info(instance, "");
    }

    private static void printDivider() {
        RPGCore.info(instance, "================================================================================================");
    }

    //===========================================================================================================
    // onEnable onDisable methods.
    //===========================================================================================================

    @Override
    public void onEnable() {
        instance = this;

        printDivider();
        printBlank();
        RPGCore.info(instance, "  RPGSkill v%s", getDescription().getVersion());
        RPGCore.info(instance, "  You're running on %s.", getServer().getVersion());
        printDivider();

        // Register Command Manager
        try {
            CommandService.createCommand(this, "skill", "Command prefix for RPGSkill.", "/skill", SkillCommandArgs.class);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        // Injecting Module
        components = new Components();
        injector = Guice.createInjector(new RPGSkillModule());
        injector.injectMembers(components);

        // Initialize Config
        getSkillConfig().init();

        // Register Events ( Listeners )
        getServer().getPluginManager().registerEvents(components.entityListener, this);
        getServer().getPluginManager().registerEvents(components.resourceListener, this);

        // Call events for initialization ( Custom Events )
        if(getServer().getPluginManager().isPluginEnabled("RPGSkills")) {
            RPGCore.info("RPGSkills enabled...");
            Bukkit.getPluginManager().callEvent(new EffectRegistrationEvent(components.effectService));
            Bukkit.getPluginManager().callEvent(new SkillRegistrationEvent(components.skillService));
        }

        // Register skills ( SkillService )
        components.skillService.registerSkill(new BaseDamageSkill());
    }

    @Override
    public void onDisable() {
        RPGCore.info(instance, "RPGSkills v%s is being disabled.", getDescription().getVersion());
    }

}
