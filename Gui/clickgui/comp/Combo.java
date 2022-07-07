package me.kausel.clickgui.comp;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

import java.awt.*;

import me.kausel.clickgui.Clickgui;
import me.kausel.fonts.impl.Fonts;
import me.kausel.modules.Module;
import me.kausel.settings.Setting;
import me.kausel.utils.color.ClickGuiColor;
import me.kausel.utils.color.HudColor;

public class Combo extends Comp {

    public Combo(double x, double y, Clickgui parent, Module module, Setting setting) {
        this.x = x;
        this.y = y;
        this.parent = parent;
        this.module = module;
        this.setting = setting;
    }


    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        if (isInside(mouseX, mouseY, parent.posX + x - 70, parent.posY + y, parent.posX + x + 30, parent.posY + y + 10) && mouseButton == 0) {
            int max = setting.getOptions().size();
            if (parent.modeIndex + 1 >= max) {
                parent.modeIndex = 0;
            } else {
                parent.modeIndex++;
            }
            setting.setValString(setting.getOptions().get(parent.modeIndex));
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY) {
        super.drawScreen(mouseX, mouseY);
        
        Color temp = ClickGuiColor.getClickGuiColor();
        
        //Gui.drawRect(parent.posX + x - 70, parent.posY + y, parent.posX + x, parent.posY + y + 10,setting.getValBoolean() ? new Color(temp.getRed(), temp.getGreen(), temp.getBlue()).getRGB() : new Color(87, 87, 87).getRGB());
        Fonts.DISCORD.REGULAR_20.REGULAR_20.drawString(setting.getName() + ": " + setting.getValString(), (int)(parent.posX + x - 69), (int)(parent.posY + y + 1), new Color(-1).getRGB());
    }
}
