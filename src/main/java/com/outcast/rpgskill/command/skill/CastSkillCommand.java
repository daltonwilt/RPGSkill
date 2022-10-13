package com.outcast.rpgskill.command.skill;

import com.mongodb.CommandResult;
import com.outcast.rpgcore.command.CommandBuilder;
import com.outcast.rpgskill.RPGSkill;
import com.outcast.rpgskill.api.skill.Castable;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CastSkillCommand extends CommandBuilder {

    public CastSkillCommand() {
        super();
        this.addAlias("cast");
        this.setDescription("Casts a given skill. You must have permission to use the skill.");
        this.setName("cast");
        this.setPermission("rpgskill.skill.cast");
        this.setSyntax("/skill <args>");

//        List<String> skills = RPGSkill.getInstance().getSkillService().getAllSkills().entrySet().stream()
//                .filter(entry -> src.hasPermission(entry.getValue().getPermission()))
//                .filter(entry -> args.nextIfPresent().map(arg -> entry.getKey().startsWith(arg.toLowerCase())).orElse(true))
//                .map(Map.Entry::getKey)
//                .collect(Collectors.toList());

        this.setArguments(new ArrayList<>(Arrays.asList("skill")));
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) throws CommandException {
        try {
//            Castable skill = args.<Castable>getOne("skill-name").get();
//            String[] arguments = args.<String>getOne("arguments...").orElse("").split(" ");
//            RPGSkill.getInstance().getSkillFacade().playerCastSkill((Player) sender, skill, arguments);
            sender.sendMessage("Testing skill use!!!");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
