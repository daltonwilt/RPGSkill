package com.outcast.rpgskill.api.exception;

import com.outcast.rpgskill.RPGSkill;
import org.bukkit.command.CommandException;

public class CastException extends CommandException {
    public CastException(String error) {
        super(RPGSkill.getInstance().getMessagingFacade().formatInfo(error).toString());
    }
}
