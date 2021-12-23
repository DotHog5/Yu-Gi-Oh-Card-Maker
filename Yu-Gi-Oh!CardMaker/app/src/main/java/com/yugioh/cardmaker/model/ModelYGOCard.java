package com.yugioh.cardmaker.model;

public class ModelYGOCard {
    String id, name, type, race, attribute, atk, def, desc;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String cardName) {
        this.name = cardName;
    }

    public String getType() {
        return type;
    }

    public void setType(String cardKind) {
        this.type = cardKind;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String cardType) {
        this.race = cardType;
    }

    public String getAttribute() {return attribute;}

    public void setAttribute(String cardAttr) {
        this.attribute = cardAttr;
    }

    public String getAtk() {
        return atk;
    }

    public void setAtk(String cardAtt) {
        this.atk = cardAtt;
    }

    public String getDef() {
        return def;
    }

    public void setDef(String cardDef) {
        this.def = cardDef;
    }

    public String getDesc() { return desc; }

    public void setDesc(String cardEff) {
        this.desc = cardEff;
    }

}
