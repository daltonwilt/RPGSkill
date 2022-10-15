package com.outcast.rpgskill.api.skill;

//===========================================================================================================
// Class that houses message of cast result depending on custom message
//===========================================================================================================

public class CastResult {

    private String message;

    private CastResult(String message) {
        this.message = message;
    }

    public static CastResult empty() {
        return new CastResult("");
    }

    public static CastResult custom(String text) {
        return new CastResult(text);
    }

    public static CastResult success() {
        return new CastResult("");
    }

    public String getMessage() {
        return message;
    }

}
