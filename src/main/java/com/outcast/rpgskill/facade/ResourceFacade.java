package com.outcast.rpgskill.facade;

import com.google.common.collect.ImmutableMap;
import com.outcast.rpgskill.RPGSkillConfig;
import com.outcast.rpgskill.api.event.ResourceEvent;
import com.outcast.rpgskill.api.resource.ResourceEntity;
import com.outcast.rpgskill.resource.EntityResourceUser;
import com.outcast.rpgskill.service.ResourceService;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import javax.inject.Inject;

//===========================================================================================================
// Facade class that displays resources information on your action bar
//
// Change this class to use Kyori lib instead of md_5
//===========================================================================================================
public class ResourceFacade {

    private ResourceService resourceService;
    private RPGSkillConfig config;
    private final ImmutableMap<Integer, String> BARS;

    @Inject
    public ResourceFacade(ResourceService resourceService, RPGSkillConfig config) {
        this.resourceService = resourceService;
        this.config = config;

        BARS = ImmutableMap.<Integer, String>builder()
                .put(0, ChatColor.GRAY + "▒▒▒▒▒▒▒▒▒▒")
                .put(1, config.RESOURCE_COLOR + "▉" + ChatColor.GRAY + "▒▒▒▒▒▒▒▒▒")
                .put(2, config.RESOURCE_COLOR + "▉▉" + ChatColor.GRAY + "▒▒▒▒▒▒▒▒")
                .put(3, config.RESOURCE_COLOR + "▉▉▉" + ChatColor.GRAY + "▒▒▒▒▒▒▒")
                .put(4, config.RESOURCE_COLOR + "▉▉▉▉" + ChatColor.GRAY + "▒▒▒▒▒▒")
                .put(5, config.RESOURCE_COLOR + "▉▉▉▉▉" + ChatColor.GRAY + "▒▒▒▒▒")
                .put(6, config.RESOURCE_COLOR + "▉▉▉▉▉▉" + ChatColor.GRAY + "▒▒▒▒")
                .put(7, config.RESOURCE_COLOR + "▉▉▉▉▉▉▉" + ChatColor.GRAY + "▒▒▒")
                .put(8, config.RESOURCE_COLOR + "▉▉▉▉▉▉▉▉" + ChatColor.GRAY + "▒▒")
                .put(9, config.RESOURCE_COLOR + "▉▉▉▉▉▉▉▉▉" + ChatColor.GRAY + "▒")
                .put(10, config.RESOURCE_COLOR + "▉▉▉▉▉▉▉▉▉▉")
                .build();
    }

    public void onResourceRegen(ResourceEvent.Regen event) {
        if(event.getRegenAmount() > 0 && event.getResourceEntity() instanceof EntityResourceUser) {
            Player player = Bukkit.getServer().getPlayer(((EntityResourceUser) event.getResourceEntity()).getId());
            ResourceEntity user = event.getResourceEntity();

            if(player != null) {
                int amount = (int) (event.getRegenAmount() + user.getCurrent());
                amount = amount < user.getMax() ? amount : (int) user.getMax();

                // Maybe not use bars in the future
                int bars = (int) ((amount / user.getMax()) * 10);

                /**
                 * Maybe not use bars in the future probably will just change this to currResource / maxResource style
                 * Then append this to the already checked health bar action bar display
                 * E.X. (health) 180 | 240   (resource) 80 | 100
                 */
                String display = BARS.get(bars) + " " + config.RESOURCE_COLOR + amount + "/" + (int) user.getMax() + " " + config.RESOURCE_NAME;
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(display));
            }
        }
    }

    public void onPlayerJoin(Player player) {
        resourceService.getOrCreateEntity(player);
    }

}
