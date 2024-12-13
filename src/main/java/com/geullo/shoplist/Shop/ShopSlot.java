package com.geullo.shoplist.Shop;

import com.geullo.shoplist.Packet;
import com.geullo.shoplist.PacketList;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemMonsterPlacer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class ShopSlot {
    private String itemName;
    private ItemStack shopItem;
    private List<String> itemLore = new ArrayList<>();
    private int price,sellPrice;
    private double taxRate = 1.2;
    private SlotStatus status = SlotStatus.ALLOW;
    public ShopSlot(String name, ItemStack item, int subCode, HashMap<Enchantment,Integer> enchats, int sellPrice, int price, SlotStatus slotStatus, List<String> lore) {
        itemName = name;
        shopItem = item;
        item.setItemDamage(subCode);
        if (enchats!=null) enchats.forEach(item::addEnchantment);
        this.sellPrice= sellPrice;
        this.price = price;
        status = slotStatus;
        itemLore = lore;
    }
    public ShopSlot(String name, ItemStack item, String monster,int purchase,int price, SlotStatus slotStatus,List<String> lore) {
        itemName = name;
        if (item.getItem().equals(Items.SPAWN_EGG)) {
            ItemMonsterPlacer.applyEntityIdToItemStack(item,new ResourceLocation("minecraft:"+monster.toLowerCase()));
        }
        shopItem = item;
        sellPrice = purchase;
        this.price = price;
        status = slotStatus;
        itemLore = lore;
    }

    public ShopSlot(){
        itemName = "기본 상점 물품";
        shopItem = new ItemStack(Item.getItemById(1));
        sellPrice = 10000;
        price = (int) (sellPrice*taxRate);
    }

    public void buyItem(String shopName,boolean shiftClicked,int idx) {
        Packet.sendMessage(PacketList.BUY_ITEM.recogCode+"-!"+shopName+"-!"+idx+"-!"+(shiftClicked?"0":"1"));
    }
    public void sellItem(String shopName,boolean shiftClicked,int idx){
        Packet.sendMessage(PacketList.SELL_ITEM.recogCode+"-!"+shopName+"-!"+idx+"-!"+(shiftClicked?"0":"1"));
    }

    public List<String> getItemLore() {
        return itemLore;
    }

    public ItemStack getShopItem() {
        return shopItem;
    }

    public void setStatus(SlotStatus status) {
        this.status = status;
    }

    public String getStatusToString(){
        return status.name();
    }

    public SlotStatus getStatus(){
        return status;
    }

    public int getPrice() {
        return price;
    }

    public String getBuyPriceToString(){
        NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.KOREA);
        return nf.format(price).replaceAll("￦","") + "원";
    }

    public String getSellPriceToString(){
        NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.KOREA);
        return nf.format(sellPrice).replaceAll("￦","") + "원";
    }

    public int getSellPrice() {
        return sellPrice;
    }

    public String getItemName() {
        return itemName;
    }

    public void setPrice(int sellPrice) {
        this.sellPrice = sellPrice;
        price = (int) (sellPrice*taxRate);
    }

    public void setShopItem(ItemStack shopItem) {
        this.shopItem = shopItem;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    @Override
    public String toString()
    {
        return "ShopItem{" +
                "name='" + itemName +
                ", sellPrice=" + sellPrice +
                ", buyPrice=" + price +
                ", status=" + status.name() +
                '}';
    }
    public boolean slotStatus(SlotStatus checkStatus){
        return checkStatus.equals(SlotStatus.ONLY_SELL)?
                (status.equals(SlotStatus.ALLOW)||status.equals(SlotStatus.ONLY_SELL))
                :(status.equals(SlotStatus.ALLOW)||status.equals(SlotStatus.ONLY_BUY));
    }

    public static SlotStatus translate(String code){
        return code.equals(SlotStatus.ONLY_SELL.recogCode)?SlotStatus.ONLY_SELL:
                code.equals(SlotStatus.ONLY_BUY.recogCode)?SlotStatus.ONLY_BUY:
                        code.equals(SlotStatus.ALLOW.recogCode)?SlotStatus.ALLOW:
                                code.equals(SlotStatus.DENY.recogCode)?SlotStatus.DENY:SlotStatus.ALLOW;
    }
}
