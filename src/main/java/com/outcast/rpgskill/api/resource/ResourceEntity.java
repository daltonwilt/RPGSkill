package com.outcast.rpgskill.api.resource;

public interface ResourceEntity {

    /**
     * Fill this ResourceEntity's resource by the specified amount
     *
     * @param amount
     */
    void fill(double amount);

    void fill();

    /**
     * Drain this ResourceEntity's resource by the specified amount
     *
     * @param amount
     */
    void drain(double amount);

    double getMax();

    /**
     * Set this ResourceEntity's resource by the specified amount
     *
     * @param amount
     */
    void setMax(double amount);

    double getCurrent();

}
