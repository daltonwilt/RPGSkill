package com.outcast.rpgskill.api.skill;

import com.outcast.rpgskill.RPGSkill;
import com.outcast.rpgskill.api.exception.CastException;
import org.apache.commons.lang3.time.DurationFormatUtils;
import org.bukkit.ChatColor;

import java.util.Arrays;

//===========================================================================================================
// Utility class for Error results when casting skills
//===========================================================================================================

public final class CastError {

    public static CastException exceptionOf(Object... message) {
        return new CastException(Arrays.toString(message));
    }

    public static CastException failure(Castable castable) {
        return exceptionOf(ChatColor.RED + "Failed to use " + ChatColor.DARK_RED + castable.getName());
    }

    public static CastException cancelled(Castable castable) {
        return exceptionOf(ChatColor.RED + "Cancelled " + ChatColor.DARK_RED + castable.getName());
    }

    public static CastException onCooldown(long timestamp, Castable castable, long cooldownEnd) {
        return exceptionOf(ChatColor.DARK_RED + castable.getName() + ChatColor.RED + " is on cooldown for another " + ChatColor.GRAY + formatDuration(cooldownEnd - timestamp));
    }

    public static CastException onGlobalCooldown(long timestamp, long cooldownEnd) {
        return exceptionOf(ChatColor.RED + "You are on global cooldown for another " + ChatColor.GRAY + formatDuration(cooldownEnd - timestamp));
    }

    public static CastException insufficientResources(Castable castable) {
        return exceptionOf(
                    ChatColor.GRAY + "You don't have enough " +
                    RPGSkill.getInstance().getSkillConfig().RESOURCE_COLOR +
                    RPGSkill.getInstance().getSkillConfig().RESOURCE_NAME +
                    ChatColor.RED + " to cast " + castable.getName()
                );
    }

    public static CastException blocked(Castable castable) {
        return exceptionOf(ChatColor.DARK_RED + castable.getName() + ChatColor.RED + " has been blocked.");
    }

    public static CastException noTarget() {
        return exceptionOf(ChatColor.RED + "No target could be found.");
    }

    public static CastException invalidTarget() {
        return exceptionOf(ChatColor.RED, "Target is not valid.");
    }

    public static CastException obscuredTarget() {
        return exceptionOf(ChatColor.RED, "Target is obscured.");
    }

    public static CastException notImplemented() {
        return exceptionOf(ChatColor.RED, "This skill is not implemented yet.");
    }

    public static CastException noSuchSkill() {
        return exceptionOf(ChatColor.RED, "No such skill");
    }

    public static CastException internalError() {
        return exceptionOf(ChatColor.RED, "An internal error occurred while casting this skill. Please report this.");
    }

    public static CastException noPermission(Castable castable) {
        return exceptionOf(ChatColor.RED, "You lack the permission required to use the skill ", castable.getName());
    }

    public static CastException invalidArguments() {
        return exceptionOf(ChatColor.RED, "Invalid arguments were supplied to the skill.");
    }

    //===========================================================================================================
    // Utility method to format duration into readable string
    //===========================================================================================================

    private static String formatDuration(long duration) {
        String format = "H'h' m'm' s.S's'";

        if (duration < 60000) {
            format = "s.S's'";
        }

        if (duration >= 60000 && duration < 3600000) {
            format = "m'm' s.S's'";
        }

        if (duration >= 3600000) {
            format = "H'h' m'm' s.S's'";
        }

        return DurationFormatUtils.formatDuration(duration, format, false);
    }

}
