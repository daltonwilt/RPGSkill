package com.outcast.rpgskill.command;

import com.outcast.rpgcore.command.CommandBuilder;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Arrays;

public final class SkillCommandArgs extends CommandBuilder {

    public SkillCommandArgs() {
        super();
        this.addAlias("skill");
        this.setDescription("Testing command service in RPGCore.");
        this.setName("skill");
        this.setPermission("rpgskill.skill");
        this.setSyntax("/skill <args>");
        this.setArguments(new ArrayList<>(Arrays.asList("skill")));
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) throws CommandException {
        try {
            sender.sendMessage("Testing command service!!!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

}
