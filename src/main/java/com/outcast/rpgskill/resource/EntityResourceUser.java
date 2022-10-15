package com.outcast.rpgskill.resource;

import com.outcast.rpgcore.db.Identifiable;
import com.outcast.rpgskill.api.resource.ResourceEntity;
import org.bukkit.entity.LivingEntity;

import javax.annotation.Nonnull;
import java.util.UUID;

//===========================================================================================================
// Class that implements resource entity to house specific resource information for a living entity
//===========================================================================================================

public class EntityResourceUser implements Identifiable, ResourceEntity {

    private UUID uuid;
    private double current;
    private double max;

    public EntityResourceUser(LivingEntity living) {
        this.uuid = living.getUniqueId();
    }

    @Nonnull
    @Override
    public UUID getId() {
        return uuid;
    }

    @Override
    public void fill(double amount) {
        this.current += amount;
    }

    @Override
    public void fill() {
        this.current = max;
    }

    @Override
    public void drain(double amount) {
        this.current -= amount;
    }

    @Override
    public double getMax() {
        return max;
    }

    @Override
    public void setMax(double amount) {
        this.max = amount;
    }

    @Override
    public double getCurrent() {
        return current;
    }

}