package com.outcast.rpgskill.facade;

import com.outcast.rpgcore.util.AbstractMessaging;
import javax.inject.Singleton;

@Singleton
public class SkillMessagingFacade extends AbstractMessaging {
    SkillMessagingFacade() {
        super("Skills");
    }
}
