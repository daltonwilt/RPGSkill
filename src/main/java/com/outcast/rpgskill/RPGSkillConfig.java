package com.outcast.rpgskill;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.outcast.rpgcore.config.ConfigService;
import org.bukkit.ChatColor;
import org.bukkit.Material;

import javax.inject.Singleton;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Singleton
public class RPGSkillConfig extends ConfigService {

    @JsonIgnore
    public String[] DEFAULT_VALUES = new String[] {
            Material.AIR.toString(), Material.COBWEB.toString(), Material.GRASS.toString(),
            Material.SNOW.toString(), Material.WATER.toString(), Material.WATER.toString(),
            Material.GRASS.toString(), Material.WHEAT.toString(), Material.SUGAR_CANE.toString(),
            Material.VINE.toString(), Material.SUNFLOWER.toString(), Material.FERN.toString(),
            Material.SEAGRASS.toString(), Material.SEA_PICKLE.toString(), Material.DANDELION.toString(),
            Material.POPPY.toString(), Material.BLUE_ORCHID.toString(), Material.ALLIUM.toString(),
            Material.AZURE_BLUET.toString(), Material.RED_TULIP.toString(), Material.ORANGE_TULIP.toString()
    };

    @JsonProperty("global-cooldown")
    public long GLOBAL_COOLDOWN = 500;

    @JsonProperty("resource-regen-interval-ticks")
    public int RESOURCE_REGEN_TICK_INTERVAL = 20;

    @JsonProperty("resource-regen-rate")
    public double RESOURCE_REGEN_RATE = 5;

    @JsonProperty("resource-limit")
    public double RESOURCE_LIMIT = 100.0d;

    @JsonProperty("resource-name")
    public String RESOURCE_NAME = "Energy";

    @JsonProperty("resource-colour")
    public ChatColor RESOURCE_COLOR = ChatColor.DARK_AQUA;

    @JsonProperty("passable-blocks")
    public Set<String> PASSABLE_BLOCKS = new HashSet<>(Arrays.asList(DEFAULT_VALUES));

    public RPGSkillConfig() throws IOException {
        super("config/rpgskill", "config.json", FileType.JSON);
    }

}
