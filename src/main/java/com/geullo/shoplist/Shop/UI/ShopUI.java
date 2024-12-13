package com.geullo.shoplist.Shop.UI;

import com.geullo.shoplist.Message;
import com.geullo.shoplist.Shop.ShopSlot;
import com.geullo.shoplist.Shop.SlotStatus;
import com.geullo.shoplist.util.Reference;
import com.geullo.shoplist.Render;
import com.geullo.shoplist.util.Sound;
import com.geullo.shoplist.util.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.client.event.ColorHandlerEvent;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ShopUI extends GuiScreen {
    private List<ShopSlot> shopItemList = new ArrayList<>();
    private boolean mouseGrab = false, scrollClicked = false;
    private String shopName;
    private final double[] bgPos = new double[2], bgSize = new double[2], slotSize = new double[2], btnSize = new double[2],
            itemSlotSize = new double[2], itemSize = new double[2], showCaseSize = new double[2], gap = new double[2], slotAllSize = new double[2],
            scrollBarSizeHeight = new double[5], scrollBarBgSize = new double[2], scrollBarBgPos = new double[2], scrollBarSize = new double[2],
            scrollBarPos = new double[3], showCase = new double[4], helpPos = new double[2], helpSize = new double[2],nameSize = new double[2]
    ;
    private double[] charcterPos, charcterSize, slotPosX, slotPosY, btn1PosX, btn1PosY, btn2PosX, btn2PosY, itemSlotPosX, itemSlotPosY, itemPosX, itemPosY,namePosX,namePosY,priceX, priceY;
    private double scrollGapY = 0, slotFirstPos = 0, scrollCursor, slotFakeListPos;
    private int prevWidth, prevHeight;
    private String Character = "";

    public ShopUI(String storeName, List<ShopSlot> item) {
        if (item.isEmpty()) return;
        shopName = storeName;
        shopItemList = item;
        initInstance();
    }

    public ShopUI(String storeName, List<ShopSlot> item, String shopCharacter) {
        if (item.isEmpty()) return;
        shopName = storeName;
        shopItemList = item;
        Character = shopCharacter;
        charcterPos = new double[2];
        charcterSize = new double[2];
        initInstance();
    }

    private void initInstance() {
        slotPosX = new double[shopItemList.size() + 1];
        slotPosY = new double[shopItemList.size() + 1];
        btn1PosX = new double[shopItemList.size() + 1];
        btn1PosY = new double[shopItemList.size() + 1];
        btn2PosX = new double[shopItemList.size() + 1];
        btn2PosY = new double[shopItemList.size() + 1];
        priceX = new double[shopItemList.size() + 1];
        priceY = new double[shopItemList.size() + 1];
        itemSlotPosX = new double[shopItemList.size() + 1];
        itemSlotPosY = new double[shopItemList.size() + 1];
        itemPosX = new double[shopItemList.size() + 1];
        itemPosY = new double[shopItemList.size() + 1];
        namePosX = new double[shopItemList.size() + 1];
        namePosY = new double[shopItemList.size() + 1];
    }

    @Override
    public void initGui() {
        mc.mouseHelper.ungrabMouseCursor();
        initBg();
        initSlot(prevWidth != width || prevHeight != height);
        prevWidth = width;
        prevHeight = height;
        initHelp();
    }

    @Override
    public void updateScreen() {
    }

    public void initBg() {
        if (!"".equals(Character)) {
            bgSize[0] = (int) (width / 1.55);
            bgSize[1] = (int) (height / 1.5);
            bgPos[0] = ((width / 2) - (bgSize[0] / 1.45));
            bgPos[1] = (height / 2) - (bgSize[1] / 2.25);
        } else {
            bgSize[0] = (int) (width / 1.55);
            bgSize[1] = (int) (height / 1.5);
            bgPos[0] = ((width / 2) - (bgSize[0] / 2));
            bgPos[1] = (height / 2) - (bgSize[1] / 2.25);
        }
    }

    public void initHelp() {
        helpSize[0] = slotSize[0] * 1.15;
        helpSize[1] = slotSize[1] / 1.12;
        helpPos[0] = (bgPos[0]+bgSize[0])-helpSize[0];
        helpPos[1] = bgPos[1] - (gap[1]) - helpSize[1];
    }

    public void initSlots() {
        gap[0] = ((bgPos[1] / 4) / 2);
        gap[1] = gap[0] / 2;
        for (int i = 0; i < shopItemList.size(); i++) {
            if (i != 0) {
                slotPosX[i] = slotPosX[i - 1] + slotSize[0] + btnSize[0] + (gap[1] / 2);
                slotPosY[i] = slotPosY[i - 1];
                if (i % 2 == 0) {
                    slotPosX[i] = slotPosX[i - 2];
                    slotPosY[i] = (slotPosY[i - 1] + slotSize[1]) + gap[0];
                }
            }
            itemSlotPosX[i] = (slotPosX[i] + gap[0] / 1.6);
            itemSlotPosY[i] = (slotPosY[i] + gap[1] * 1.06);
            itemPosX[i] = itemSlotPosX[i]  +(gap[0]/3.55);
            itemPosY[i] = (itemSlotPosY[i] + ((itemSlotSize[1] / 2) - (itemSize[1] / 2))) + gap[1] / 2;
            btn2PosX[i] = (slotPosX[i] + slotSize[0] - (gap[0] / 1.7));
            btn2PosY[i] = slotPosY[i] + ((slotSize[1] / 1.85) - ((gap[1] / 3) / 3) / 2);
            btn1PosX[i] = btn2PosX[i];
            btn1PosY[i] = (btn2PosY[i] - (gap[1] / 2)) - btnSize[1];
            if (shopItemList.get(i).getStatus().equals(SlotStatus.ONLY_BUY)) {
                btn2PosX[i] = (slotPosX[i] + slotSize[0] - (gap[0] / 1.7));
                btn2PosY[i] = slotPosY[i] + ((slotSize[1] / 2) - (btnSize[1] / 2));
            }
            if (shopItemList.get(i).getStatus().equals(SlotStatus.ONLY_SELL)) {
                btn1PosX[i] = btn2PosX[i];
                btn1PosY[i] = slotPosY[i] + ((slotSize[1] / 2) - (btnSize[1] / 2));
            }
            priceX[i] = (slotPosX[i] + slotSize[0]) - (gap[0]*1.22);
            priceY[i] = (slotPosY[i] + slotSize[1]) - (gap[1] * 3.843);

            namePosX[i] = priceX[i];
            namePosY[i] = priceY[i]-slotSize[1]/2.15;
        }
    }

    public void initSlot(boolean first) {
        if (first) {
            slotSize[0] = (bgSize[0] - ((bgSize[0] / 4) / 3.8) / 3.7) / 3.3;
            slotSize[1] = (bgSize[1] / 3.35) / 1.65;
            if (Character.equals("")) {
                slotPosX[0] = bgPos[0] + (bgPos[0] / 3 / 1.87);
                slotPosY[0] = bgPos[1] + (bgSize[1] / 3 / 3 / 1.55);
            } else {
                slotPosX[0] = bgPos[0] + (bgSize[0] / 4 / 2 / 2.5);
                slotPosY[0] = bgPos[1] + (bgSize[1] / 3 / 3 / 1.55);
            }
            btnSize[0] = slotSize[0] / 2.15;
            btnSize[1] = slotSize[1] / 2.393;
            itemSlotSize[0] = Math.floor((slotSize[0] / 4.3) / 1.12);
            itemSlotSize[1] = Math.floor(slotSize[1] / 1.2);
            itemSize[0] = itemSlotSize[0]/1.005;
            itemSize[1] = itemSlotSize[1] / 1.35;
            nameSize[0] = (int) (slotSize[0] / 3 / 2.34);
            nameSize[1] = (int) (slotSize[1] / 2.32);
            if (!"".equals(Character)) {
                charcterPos[0] = 0;
                charcterPos[1] = 0;
                charcterSize[0] = width;
                charcterSize[1] = height;
            }
        }
        initSlots();
        slotAllSize[0] = slotPosX[0] - slotPosX[shopItemList.size() - 1] + slotSize[0];
        slotAllSize[1] = (slotPosY[shopItemList.size() - 1] + slotSize[1]) - slotPosY[0];
        slotFirstPos = slotPosY[0];
        initShowCase();
        initScrollBar(true);
    }

    public void initScrollBar(boolean first) {
        scrollBarBgPos[0] = (slotPosX[1] + slotSize[0] + btnSize[0]);
        scrollBarBgPos[1] = slotPosY[0];
        scrollBarBgSize[0] = gap[0];
        scrollBarBgSize[1] = showCase[3];

        scrollBarPos[0] = scrollBarBgPos[0] + (gap[1] / 2);
        scrollBarPos[1] = scrollBarBgPos[1] + (gap[1] / 2);
        scrollBarPos[2] = scrollBarPos[1];
        scrollBarSize[0] = scrollBarBgSize[0] / 1.85;
        initScrollBarHeight();
        if (first) {
            scrollBarSize[1] = Utils.percent(scrollBarSizeHeight[2], Utils.percentPartial(slotAllSize[1], slotAllSize[1] - (slotAllSize[1] - showCase[3])));
            if (scrollBarSize[1] >= scrollBarSizeHeight[2]) scrollBarSize[1] = scrollBarSizeHeight[2];
        }
    }

    public void initScrollBarHeight() {
        scrollBarSizeHeight[0] = (scrollBarPos[1] + scrollBarBgSize[1]) - (gap[1]);
        scrollBarSizeHeight[1] = scrollBarPos[1];
        scrollBarSizeHeight[2] = scrollBarBgSize[1] - gap[1];
        scrollBarSizeHeight[3] = ((slotSize[1] * (shopItemList.size() / 2)) + (gap[1] * (shopItemList.size() / 2))) - showCase[3];
        scrollBarSizeHeight[4] = (-(int) (scrollBarSizeHeight[3]));
    }

    public void initShowCase() {
        showCaseSize[0] = (slotSize[0] * 2) + (btnSize[0] * 2) + gap[0];
        showCaseSize[1] = (slotSize[1] * 4) + (gap[0] * 3);
        showCase[0] = slotPosX[0] - 0.001;
        showCase[1] = slotPosY[0] - 0.001;
        showCase[2] = showCaseSize[0] + 0.001;
        showCase[3] = showCaseSize[1] + 0.001;
    }


    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        if (mc == null) mc = Minecraft.getMinecraft();
        if (!mouseGrab) {
            if (mc.mouseHelper!=null) mc.mouseHelper.ungrabMouseCursor();
            mouseGrab = true;
        }
        Render.bindTexture(new ResourceLocation(Reference.MOD_ID, "shop/black_bg.png"));
        Render.setColor(0xffffffff);
        Render.drawTexturedRect(0, 0, width, height);
        Render.drawTexturedRect(0, 0, width, height);
        if (!"".equals(Character)) {
            Render.bindTexture(new ResourceLocation(Reference.MOD_ID, "shop/npc/" + Character + ".png"));
            Render.setColor(0xffffffff);
            Render.drawTexturedRect(charcterPos[0], charcterPos[1], charcterSize[0], charcterSize[1]);
        }
        Render.bindTexture(new ResourceLocation(Reference.MOD_ID, "shop/default_background.png"));
        Render.setColor(0xffffffff);
        Render.drawTexturedRect(bgPos[0], bgPos[1], bgSize[0], bgSize[1]);
        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        int scale = computeGuiScale();
        GL11.glScissor((int) ((showCase[0]) * scale),
                (int) (Minecraft.getMinecraft().displayHeight - (showCase[1] + showCase[3]) * scale),
                (int) (showCase[2] * scale),
                (int) (showCase[3] * scale));
        for (int i = 0; i < shopItemList.size(); i++) {
            ShopSlot slot = shopItemList.get(i);
            SlotStatus slotStatus = slot.getStatus();
            if (Utils.mouseBetweenIcon(slotPosX[i], slotPosY[i], showCase[0], showCase[1], showCase[2], showCase[3])
                    || Utils.mouseBetweenIcon(slotPosX[i], slotPosY[i] + slotSize[1], showCase[0], showCase[1], showCase[2], showCase[3])) {
                buttonDraw(i, mouseX, mouseY, slotStatus);
                Render.bindTexture(new ResourceLocation(Reference.MOD_ID, "shop/default_slot_background.png"));
                Render.setColor(0xffffffff);
                Render.drawTexturedRect(slotPosX[i], slotPosY[i], slotSize[0], slotSize[1]);
                Render.drawItemStack(slot.getShopItem(), (int) itemPosX[i], (int) itemPosY[i], (float) itemSize[0], (float) itemSize[1]);
                /*Render.bindTexture(new ResourceLocation(Reference.MOD_ID, "shop/black_bg.png"));
                Render.setColor(0xffffffff);
                Render.drawTexturedRect((int) itemPosX[i], (int) itemPosY[i], (float) itemSize[0], (float) itemSize[1]);*/
                Render.drawString(getPrice(slot), (float) priceX[i], (float) priceY[i], (int) (slotSize[0] / 3 / 2.5), (int) (slotSize[1] / 2.53), 2, 0Xffffff);
                Render.drawString(getItemName(i), (float) (namePosX[i]), (float) (namePosY[i]), (int) (nameSize[0]), (int) (nameSize[1]), 2, 0X000000);
            }
        }
        GL11.glDisable(GL11.GL_SCISSOR_TEST);
        if (shopItemList.size() > 8) scrollbarDraw(mouseX, mouseY);
        for (int i = 0; i < shopItemList.size(); i++) {
            loreDraw(i, mouseX, mouseY, shopItemList.get(i));
        }

        Render.bindTexture(new ResourceLocation(Reference.MOD_ID, "shop/help_message.png"));
        Render.setColor(0xffffffff);
        Render.drawTexturedRect(helpPos[0], helpPos[1], helpSize[0], helpSize[1]);
        GL11.glPopMatrix();
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    private String getPrice(ShopSlot slot) {
        return slot.getStatus().recogCode.equals(SlotStatus.ONLY_BUY.recogCode)?slot.getBuyPriceToString():slot.getSellPriceToString();
    }

    private String getItemName(int idx) {
        ShopSlot shopItem = shopItemList.get(idx);
        String name = "";
        if ("null".equals(shopItem.getItemName()) || "".equals(shopItem.getItemName()))
            if (shopItem.getShopItem().getItem().equals(Items.SPAWN_EGG)) name = "§f" + shopItem.getShopItem().getTooltip(this.mc.player, ITooltipFlag.TooltipFlags.NORMAL).get(0).replace("생성: ", "")+" 생성알";
            else if (shopItem.getShopItem().getItem().equals(Items.CHICKEN)||shopItem.getShopItem().getItem().equals(Items.BEEF)||shopItem.getShopItem().getItem().equals(Items.PORKCHOP)||shopItem.getShopItem().getItem().equals(Items.MUTTON)) name = "§f" + shopItem.getShopItem().getTooltip(this.mc.player, ITooltipFlag.TooltipFlags.NORMAL).get(0).replace("익히지 않은 ", "안 익힌 ");
            else name = "§f" + shopItem.getShopItem().getTooltip(this.mc.player, ITooltipFlag.TooltipFlags.NORMAL).get(0);
        else name =  shopItem.getItemName();
        return name.replace("§f" ,"§0");
    }

    public int computeGuiScale() {
        return new ScaledResolution(mc).getScaleFactor();
    }

    public void scrollbarDraw(int mouseX, int mouseY) {
        Render.bindTexture(new ResourceLocation(Reference.MOD_ID, "shop/default_scroll_background.png"));
        Render.setColor(0xffbbbbbb);
        Render.drawTexturedRect(scrollBarBgPos[0], scrollBarBgPos[1], scrollBarBgSize[0], scrollBarBgSize[1]);
        if (scrollClicked) {
            double ny = mouseY - scrollGapY;
            if (ny >= scrollBarSizeHeight[1] && ny + scrollBarSize[1] <= scrollBarSizeHeight[0])
                scrollBarPos[1] = ny;
            else if (ny <= scrollBarSizeHeight[1])
                scrollBarPos[1] = scrollBarSizeHeight[1];
            else if (ny + scrollBarSize[1] >= scrollBarSizeHeight[0])
                scrollBarPos[1] = scrollBarSizeHeight[0] - scrollBarSize[1];
            scrollCursor = scrollBarPos[1] - scrollBarPos[2];
            slotFakeListPos = ((showCase[1] + showCase[3]) - slotAllSize[1]) - slotFirstPos;
            slotPosY[0] = (Utils.percent(slotFakeListPos, Utils.percentPartial(scrollBarSizeHeight[2] - scrollBarSize[1], scrollCursor))) + slotFirstPos;
        }
        Render.setColor(0xffffffff);
        Render.drawTexturedRect(scrollBarPos[0], scrollBarPos[1], scrollBarSize[0], scrollBarSize[1]);
        if (scrollClicked || Utils.mouseBetweenIcon(mouseX, mouseY, scrollBarPos[0], scrollBarPos[1], scrollBarSize[0], scrollBarSize[1])) {
            Render.setColor(0x80000000);
            Render.drawTexturedRect(scrollBarPos[0], scrollBarPos[1], scrollBarSize[0], scrollBarSize[1]);
        }
    }

    public void buttonDraw(int idx, int mouseX, int mouseY, SlotStatus slotStatus) {
        if (slotStatus.equals(SlotStatus.ALLOW) || slotStatus.equals(SlotStatus.ONLY_SELL)) {
            Render.bindTexture(new ResourceLocation(Reference.MOD_ID, "shop/button/sell.png"));
            Render.setColor(0xffffffff);
            Render.drawTexturedRect(btn1PosX[idx], btn1PosY[idx], btnSize[0], btnSize[1]);
            if (Utils.mouseBetweenIcon(mouseX, mouseY, btn1PosX[idx], btn1PosY[idx], btnSize[0], btnSize[1]) && Utils.mouseBetweenIcon(mouseX, mouseY, showCase[0], showCase[1], showCase[2], showCase[3])) {
                Render.setColor(0x80000000);
                Render.drawTexturedRect(btn1PosX[idx], btn1PosY[idx], btnSize[0], btnSize[1]);
            }
        }
        if (slotStatus.equals(SlotStatus.ALLOW) || slotStatus.equals(SlotStatus.ONLY_BUY)) {
            Render.bindTexture(new ResourceLocation(Reference.MOD_ID, "shop/button/buy.png"));
            Render.setColor(0xffffffff);
            Render.drawTexturedRect(btn2PosX[idx], btn2PosY[idx], btnSize[0], btnSize[1]);
            if (Utils.mouseBetweenIcon(mouseX, mouseY, btn2PosX[idx], btn2PosY[idx], btnSize[0], btnSize[1]) && Utils.mouseBetweenIcon(mouseX, mouseY, showCase[0], showCase[1], showCase[2], showCase[3])) {
                Render.setColor(0x80000000);
                Render.drawTexturedRect(btn2PosX[idx], btn2PosY[idx], btnSize[0], btnSize[1]);
            }
        }
    }

    public void loreDraw(int idx, int mouseX, int mouseY, ShopSlot shopItem) {
        if ((Utils.mouseBetweenIcon(mouseX, mouseY, btn1PosX[idx], btn1PosY[idx], btnSize[0], btnSize[1]) || Utils.mouseBetweenIcon(mouseX, mouseY, btn2PosX[idx], btn2PosY[idx], btnSize[0], btnSize[1])) && Utils.mouseBetweenIcon(mouseX, mouseY, showCase[0], showCase[1], showCase[2], showCase[3])
        ) {
            List<String> lores = new ArrayList<>();
            if (Utils.mouseBetweenIcon(mouseX, mouseY, btn2PosX[idx], btn2PosY[idx], btnSize[0], btnSize[1]) && (shopItem.slotStatus(SlotStatus.ONLY_BUY))) {
                GlStateManager.disableDepth();
                lores.add("§a§l구매 가격 - §f" + shopItem.getBuyPriceToString());
                Render.drawTooltip(lores, mouseX, mouseY);
                GlStateManager.enableDepth();
                return;
            }
            if (Utils.mouseBetweenIcon(mouseX, mouseY, btn1PosX[idx], btn1PosY[idx], btnSize[0], btnSize[1]) && (shopItem.slotStatus(SlotStatus.ONLY_SELL))) {
                if (lores.size() != 0) lores.clear();
                GlStateManager.disableDepth();
                lores.add("§e§l판매 가격 - §f" + shopItem.getSellPriceToString());
                Render.drawTooltip(lores, mouseX, mouseY);
                GlStateManager.enableDepth();
                return;
            }
        }
        if (!(Utils.mouseBetweenIcon(mouseX, mouseY, (int) itemSlotPosX[idx], itemSlotPosY[idx], itemSlotSize[0], itemSlotSize[1]) && Utils.mouseBetweenIcon(mouseX, mouseY, showCase[0], showCase[1], showCase[2], showCase[3])))
            return;
        GlStateManager.disableDepth();
        List<String> lores = new ArrayList<>();
        List<String> tooltip = shopItem.getShopItem().getTooltip(this.mc.player, ITooltipFlag.TooltipFlags.NORMAL);
        if ("null".equals(shopItem.getItemName()) || "".equals(shopItem.getItemName())) {
            if (shopItem.getShopItem().getItem().equals(Items.SPAWN_EGG)) lores.add(0, "§f" + shopItem.getShopItem().getTooltip(this.mc.player, ITooltipFlag.TooltipFlags.NORMAL).get(0).replace("생성: ", "")+" 생성알");
            else lores.add(0, "§f" + tooltip.get(0));
        }
        else lores.add(0, shopItem.getItemName());
        if (shopItem.getShopItem().isItemEnchanted()) {
            NBTTagList tagList = shopItem.getShopItem().getEnchantmentTagList();
            lores.add(tooltip.get(tooltip.size()-tagList.tagCount()));
            for (int i=0;i<tagList.tagCount()-1;i++) lores.add("§8"+tooltip.get(tooltip.size()-i));
            shopItem.getShopItem().getEnchantmentTagList();
        }
        if (shopItem.getItemLore() != null)
            lores.addAll(new ArrayList<>(shopItem.getItemLore()));
        Render.drawTooltip(lores, mouseX, mouseY);
        GlStateManager.enableDepth();
    }

    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        if (!scrollClicked && Integer.signum(Mouse.getEventDWheel()) != 0 && scrollBarSize[1] != scrollBarSizeHeight[2]) {
            int amt = shiftClicked() ? 9 : 7;
            if (checkListFromEndToEnd(Integer.signum(Mouse.getEventDWheel()), amt) != 0) slotPosY[0] += checkListFromEndToEnd(Integer.signum(Mouse.getEventDWheel()), amt) * amt;
            else if (Integer.signum(Mouse.getEventDWheel()) == -1) slotPosY[0] = (showCase[1] + showCase[3]) - slotAllSize[1];
            else if (Integer.signum(Mouse.getEventDWheel()) == 1) slotPosY[0] = showCase[1];
            slotFakeListPos = ((showCase[1] + showCase[3]) - slotAllSize[1]) - slotFirstPos;
            scrollBarPos[1] = scrollBarPos[2] + (Utils.percent(scrollBarSizeHeight[2] - scrollBarSize[1], Utils.percentPartial(slotFakeListPos, slotPosY[0] - slotFirstPos)));
        }
        initSlots();
    }

    public double checkListFromEndToEnd(int n, int a) {
        if (n == -1) {if ((slotPosY[shopItemList.size() - 1] + slotSize[1]) + (n * a) <= showCase[1] + showCase[3]) return 0;}
        if (n > 0) {if ((slotPosY[0] + (n * a) >= showCase[1])) return 0;}
        return n;
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        if (scrollClicked) scrollClicked = false;
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (Utils.mouseBetweenIcon(mouseX, mouseY, scrollBarPos[0], scrollBarPos[1], scrollBarSize[0], scrollBarSize[1]) && shopItemList.size() > 6)
        {
            scrollGapY = mouseY - scrollBarPos[1];
            scrollClicked = true;
            Minecraft.getMinecraft().getSoundHandler().playSound(Sound.getSound(SoundEvents.UI_BUTTON_CLICK, SoundCategory.PLAYERS,1f,1f));
            return;
        }
        shopItemList.forEach(slot -> {
            int idx = shopItemList.indexOf(slot);
            if (Utils.mouseBetweenIcon(mouseX, mouseY, showCase[0], showCase[1], showCase[2], showCase[3])) {
                if (Utils.mouseBetweenIcon(mouseX, mouseY, btn1PosX[idx], btn1PosY[idx], btnSize[0], btnSize[1]) && slot.slotStatus(SlotStatus.ONLY_SELL)) {slot.sellItem(shopName, shiftClicked(), idx);Minecraft.getMinecraft().getSoundHandler().playSound(Sound.getSound(SoundEvents.UI_BUTTON_CLICK, SoundCategory.PLAYERS,1f,1f));}
                else if (Utils.mouseBetweenIcon(mouseX, mouseY, btn2PosX[idx], btn2PosY[idx], btnSize[0], btnSize[1]) && slot.slotStatus(SlotStatus.ONLY_BUY)) {slot.buyItem(shopName, shiftClicked(), idx);Minecraft.getMinecraft().getSoundHandler().playSound(Sound.getSound(SoundEvents.UI_BUTTON_CLICK, SoundCategory.PLAYERS,1f,1f));}
            }
        });
    }

    protected boolean shiftClicked() {
        return Keyboard.isKeyDown(Keyboard.KEY_RSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_LSHIFT);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (keyCode == Keyboard.KEY_ESCAPE) {
            mc.displayGuiScreen(null);
            Message.getInstance().shopSlots = null;
            Message.getInstance().shopSlots = new ArrayList<>();
        }
    }
}
