package fr.poseidonj.cinematec_back.models;

import org.passay.data.CharacterData;

public enum CharacterDatas implements CharacterData {
    Non_Alphanumeric("NON_ALPHANUMERIC", "!@#$%^&*_+<>?/-");

    public final String errorCode;
    public final String charString;

    CharacterDatas(String errorCode, String charString) {
        this.errorCode = errorCode;
        this.charString = charString;
    }
    @Override
    public String getErrorCode() {
        return this.errorCode;
    }

    @Override
    public String getCharacters() {
        return this.charString;
    }
}
