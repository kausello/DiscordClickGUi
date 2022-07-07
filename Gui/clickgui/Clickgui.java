package me.kausel.clickgui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

import me.kausel.Client;
import me.kausel.clickgui.comp.CheckBox;
import me.kausel.clickgui.comp.Combo;
import me.kausel.clickgui.comp.Comp;
import me.kausel.clickgui.comp.Slider;
import me.kausel.fonts.impl.Fonts;
import me.kausel.modules.Category;
import me.kausel.modules.Module;
import me.kausel.settings.Setting;

public class Clickgui extends GuiScreen {

    public double posX, posY, width, height, dragX, dragY;
    public boolean dragging;
    public Category selectedCategory;

    private Module selectedModule;
    private Module module;
    public int modeIndex;

    public ArrayList<Comp> comps = new ArrayList<>();

    public Clickgui() {
        dragging = false;
        posX = getScaledRes().getScaledWidth() / 2 - 150;
        posY = getScaledRes().getScaledHeight() / 2 - 100;
        width = posX + 150 * 2;
        height = height + 200;
        selectedCategory = Category.COMBAT;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        if (dragging) {
            posX = mouseX - dragX;
            posY = mouseY - dragY;
        }
        
        String titlee = Character.toUpperCase(selectedCategory.name().toLowerCase().charAt(0)) + selectedCategory.name().toLowerCase().substring(1);
        
        width = posX + 150 * 2;
        height = posY + 200;
        Gui.drawRect(posX, posY - 30, width + 80, posY, new Color(0, 14, 28).getRGB());
        Gui.drawRect(posX, posY, width + 80, height + 80, new Color(0, 14, 28).getRGB());
        Gui.drawRect(posX + 40, posY, width, height, new Color(0, 14, 28).getRGB());
        Gui.drawRect(posX + 1, posY - 29, width - 261, height - 195, new Color(0, 28, 58).getRGB());
        Gui.drawRect(posX + 1, posY + 10, width - 261, height - 155, new Color(0, 247, 255).getRGB());
        Gui.drawRect(posX + 90, posY - 29, width - 259, height - 218, new Color(0, 28, 56).getRGB());
        Gui.drawRect(posX + 1, posY - -42, width - 300, height - 186, new Color(-1).getRGB());
        Fonts.DISCORD.REGULAR_20.REGULAR_20.drawString("Vanacord",(int)posX + 43, (int)(posY - 27), new Color(-1).getRGB());
        Fonts.DISCORD.REGULAR_50.REGULAR_50.drawString("V",(int)posX + 10, (int)(posY - 23), new Color(-1).getRGB());
        Fonts.DISCORD.REGULAR_30.REGULAR_30.drawString("Categories Server - " + titlee,(int)posX + 50, (int)(posY - 15), new Color(-1).getRGB());
        Fonts.DISCORD.REGULAR_20.REGULAR_20.drawString(">    CATEGORY    +",(int)posX + 50, (int)(posY + 15), new Color(-1).getRGB());
        Fonts.DISCORD.REGULAR_20.REGULAR_20.drawString(">    MODULES    +",(int)posX + 50, (int)(posY + 125), new Color(-1).getRGB());
        Fonts.DISCORD.REGULAR_50.REGULAR_50.drawString("C",(int)posX + 10, (int)(posY + 15), new Color(-1).getRGB());

        int offset = 0;
        for (Category category : Category.values()) {
        	String title = Character.toLowerCase(category.name().toLowerCase().charAt(0)) + category.name().toLowerCase().substring(1);
			
            Gui.drawRect(posX + 50,posY + 30 + offset,posX + 130,posY + 47 + offset,category.equals(selectedCategory) ? new Color(87, 87, 87).getRGB() : new Color(28,28,28).getRGB());
            Fonts.DISCORD.REGULAR_20.REGULAR_20.drawString("#" + title,(int)posX + 52, (int)(posY + 35) + offset, new Color(-1).getRGB());
            offset += 15;
        }
        offset = 0;
        for (Module m : Client.INSTANCE.moduleManager.getModules()) {
            if (!m.getCategory().equals(selectedCategory))continue;
            
            String title = Character.toLowerCase(m.getName().toLowerCase().charAt(0)) + m.getName().toLowerCase().substring(1);
            
            Gui.drawRect(posX + 50,posY + 141 + offset,posX + 130,posY + 156 + offset,m.isToggled() ? new Color(87, 87, 87).getRGB() : new Color(28,28,28).getRGB());
            Fonts.DISCORD.REGULAR_20.REGULAR_20.drawString("#" + title,(int)posX + 50, (int)(posY + 145) + offset, new Color(-1).getRGB());
            offset += 15;
        }

        for (Comp comp : comps) {
            comp.drawScreen(mouseX, mouseY);
        }

    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);
        for (Comp comp : comps) {
            comp.keyTyped(typedChar, keyCode);
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        if (isInside(mouseX, mouseY, posX, posY - 40, width, posY) && mouseButton == 0) {
            dragging = true;
            dragX = mouseX - posX;
            dragY = mouseY - posY;
        }
        int offset = 0;
        for (Category category : Category.values()) {
            if (isInside(mouseX, mouseY, posX + 50, posY + 30 + offset, posX + 130, posY + 47 + offset) && mouseButton == 0) {
                selectedCategory = category;
            }
            offset += 15;
        }
        offset = 0;
         for (Module m : Client.INSTANCE.moduleManager.getModules()) {
            if (!m.getCategory().equals(selectedCategory))continue;
            if (isInside(mouseX, mouseY,posX + 50,posY + 141 + offset,posX + 130,posY + 156 + offset)) {
                if (mouseButton == 0) {
                    m.toggle();
                }
                if (mouseButton == 1) {
                    int sOffset = 3;
                    comps.clear();
                    if (Client.INSTANCE.settingsManager.getSettingsByMod(m) != null)
                    for (Setting setting : Client.INSTANCE.settingsManager.getSettingsByMod(m)) {
                        selectedModule = m;
                        if (setting.isCombo()) {
                            comps.add(new Combo(275, sOffset, this, selectedModule, setting));
                            sOffset += 15;
                        }
                        if (setting.isCheck()) {
                            comps.add(new CheckBox(275, sOffset, this, selectedModule, setting));
                            sOffset += 15;
                        }
                        if (setting.isSlider()) {
                            comps.add(new Slider(275, sOffset, this, selectedModule, setting));
                            sOffset += 25;
                        }
                    }
                }
            }
            offset += 15;
        }
        for (Comp comp : comps) {
            comp.mouseClicked(mouseX, mouseY, mouseButton);
        }
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        super.mouseReleased(mouseX, mouseY, state);
        dragging = false;
        for (Comp comp : comps) {
            comp.mouseReleased(mouseX, mouseY, state);
        }
    }

    @Override
    public void initGui() {
        super.initGui();
        dragging = false;
    }

    public boolean isInside(int mouseX, int mouseY, double x, double y, double x2, double y2) {
        return (mouseX > x && mouseX < x2) && (mouseY > y && mouseY < y2);
    }

    public ScaledResolution getScaledRes() {
        return new ScaledResolution(Minecraft.getMinecraft());
    }

}
