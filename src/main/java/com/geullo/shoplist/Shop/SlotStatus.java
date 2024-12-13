package com.geullo.shoplist.Shop;

public enum SlotStatus {
    ONLY_SELL("0"),
    ONLY_BUY("1"),
    ALLOW("2"),
    DENY("3");
    public String recogCode;
    SlotStatus(String code){
        recogCode = code;
    }
}
