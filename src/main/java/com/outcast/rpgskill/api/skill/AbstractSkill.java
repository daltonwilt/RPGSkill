package com.outcast.rpgskill.api.skill;

import net.kyori.adventure.text.TextComponent;
import org.bukkit.entity.LivingEntity;

//===========================================================================================================
// Abstraction for castable skills that houses information for individual skill
//===========================================================================================================
public abstract class AbstractSkill implements Castable {

    private String id;
    private String name;

    protected AbstractSkill(String id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String getId() { return id; }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getPermission() {
        return "rpgskills.skills." + name.toLowerCase();
    }

    @Override
    public TextComponent getDescription(LivingEntity living) {
        return null;
    }

}
