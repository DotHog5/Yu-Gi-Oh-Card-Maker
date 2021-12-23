package com.yugioh.cardmaker.model;

public class ModelCustomCard {
    String _id, cardCode, cardName, cardKind, cardType, cardAttr, cardAtt, cardDef, cardEff;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getCardCode() { return cardCode;}

    public void setCardCode(String cardCode) {
        this.cardCode = cardCode;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getCardKind() {
        return cardKind;
    }

    public void setCardKind(String cardKind) {
        this.cardKind = cardKind;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getCardAttr() {return cardAttr;}

    public void setCardAttr(String cardAttr) {
        this.cardAttr = cardAttr;
    }

    public String getCardAtt() {
        return cardAtt;
    }

    public void setCardAtt(String cardAtt) {
        this.cardAtt = cardAtt;
    }

    public String getCardDef() {
        return cardDef;
    }

    public void setCardDef(String cardDef) {
        this.cardDef = cardDef;
    }

    public String getCardEff() { return cardEff; }

    public void setCardEff(String cardEff) {
        this.cardEff = cardEff;
    }

}
