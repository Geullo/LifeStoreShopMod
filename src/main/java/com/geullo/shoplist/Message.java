package com.geullo.shoplist;

import com.geullo.shoplist.Shop.ShopSlot;
import com.geullo.shoplist.Shop.UI.ShopUI;
import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

import java.util.*;

public class Message {
	
	private static Message instance;
	public List<ShopSlot> shopSlots = new ArrayList<>();
	private Minecraft mc = Minecraft.getMinecraft();
	public static Message getInstance() {
		if(instance == null) {
			instance = new Message();
		}
		return instance;
	}
	
	private Message() {
	}

	public void handle(Packet message) {
		String code = message.data.substring(0,2);
		if (code.equals(PacketList.ITEM_ADD.recogCode)){
			String[] split = message.data.split("-!");
			if (split.length<7) return;
			if (mc.currentScreen instanceof ShopUI) return;
			String l = split[7].replace("[", "").replace("]", "");
			List<String> lore = l.equals("")?null:new ArrayList<>(Arrays.asList(l.split(", ")));
			String[] item = split[2].split("=");
			ItemStack itemStack = new ItemStack(Item.getItemById(Integer.parseInt(item[0])));
			HashMap<Enchantment,Integer> en = new HashMap<>();
			if (!split[6].equals("[]")) {
				String[] itemEn = split[6].split("}}");
				for (int i = 0; i < itemEn.length; i+=2) {
					en.put(Enchantment.getEnchantmentByID(Integer.parseInt(itemEn[i])),Integer.parseInt(itemEn[i+1]));
					if (lore!=null) lore.add("§7"+Objects.requireNonNull(Enchantment.getEnchantmentByID(Integer.parseInt(itemEn[i]))).getTranslatedName(Integer.parseInt(itemEn[i+1])));
				}
			}
			if (itemStack.getItem().equals(Items.SPAWN_EGG)) shopSlots.add(new ShopSlot(split[1],itemStack,item[1],Integer.parseInt(split[4]),Integer.parseInt(split[3]),ShopSlot.translate(split[5]),lore));
			else shopSlots.add(new ShopSlot(split[1],itemStack,Integer.parseInt(item[1]),en,Integer.parseInt(split[4]), Integer.parseInt(split[3]),ShopSlot.translate(split[5]),lore));
		}
		else if (code.equals(PacketList.OPEN_SHOP_UI.recogCode)){
			if (shopSlots.isEmpty()) {
				ITextComponent iTextComponent = new TextComponentString("§4[ §c§l! §4]§f 상점에 오류가 생겼습니다. 관리자에게 문의 바랍니다.");
				mc.ingameGUI.getChatGUI().printChatMessage(iTextComponent);
				return;
			}
			String[] s = message.data.split("-!");
			if (s.length<3) return;
			if (shopSlots.size()==Integer.parseInt(s[2])) {
				if (isCharacter(s[1])) mc.displayGuiScreen(new ShopUI(s[1], shopSlots, getNPC(s[1])));
				else mc.displayGuiScreen(new ShopUI(s[1], shopSlots));
			}
		}
	}

	public String getNPC(String shopName){
		System.out.println(shopName);
		if (shopName.contains("예술가")){
			return "artist";
		}
		else if (shopName.contains("보석상")){
			return "bosuksang";
		}
		else if (shopName.contains("옷")||shopName.contains("디자이너")||shopName.contains("의복사")){
			return "designer";
		}
		else if (shopName.contains("조주사")||shopName.contains("주점")){
			return "jojusa";
		}
		else if (shopName.contains("목수")){
			return "moksu";
		}
		else if (shopName.contains("무당")){
			return "mudang";
		}
		else if (shopName.contains("슈퍼")){
			return "super";
		}
		else if (shopName.contains("식당")||shopName.contains("요리사")){
			return "chef";
		}
		else if (shopName.contains("포포")){
			return "special_product_2";
		}
		else if (shopName.contains("할데스")){
			return "special_product_4";
		}
		else if (shopName.contains("베렌티")){
			return "special_product_0";
		}
		else if (shopName.contains("에클레")){
			return "special_product_1";
		}
		else if (shopName.contains("피오사")){
			return "special_product_3";
		}
		else if (shopName.contains("광부")){
			return "miner";
		}
		else if (shopName.contains("농부")){
			return "farmer";
		}
		else if (shopName.contains("나무꾼")){
			return "lumberjack";
		}
		else if (shopName.contains("사냥꾼")){
			return "hunter";
		}
		return shopName;
	}
	public boolean isCharacter(String shopName){
		return shopName.contains("예술가")|| shopName.contains("보석상")|| shopName.contains("옷")||shopName.contains("디자이너")||shopName.contains("의복사")|| shopName.contains("조주사")||shopName.contains("주점")|| shopName.contains("식당")||shopName.contains("요리사")|| shopName.contains("목수")|| shopName.contains("무당")|| shopName.contains("슈퍼")|| shopName.contains("포포")|| shopName.contains("할데스")|| shopName.contains("베렌티")|| shopName.contains("에클레")|| shopName.contains("피오사")|| shopName.contains("광부")|| shopName.contains("농부")|| shopName.contains("나무꾼")|| shopName.contains("사냥꾼");
	}
}
