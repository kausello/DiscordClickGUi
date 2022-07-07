package me.kausel.clickgui.comp;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

import java.awt.*;
import java.math.BigDecimal;
import java.math.RoundingMode;

import me.kausel.clickgui.Clickgui;
import me.kausel.fonts.impl.Fonts;
import me.kausel.modules.Module;
import me.kausel.settings.Setting;
import me.kausel.utils.color.ClickGuiColor;

public class Slider extends Comp {

    private boolean dragging = false;
    private double renderWidth;
    private double renderWidth2;

    public Slider(double x, double y, Clickgui parent, Module module, Setting setting) {
        this.x = x;
        this.y = y;
        this.parent = parent;
        this.module = module;
        this.setting = setting;
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        if (isInside(mouseX, mouseY, parent.posX + x - 70, parent.posY + y + 10,parent.posX + x - 70 + renderWidth2, parent.posY + y + 20) && mouseButton == 0) {
            dragging = true;
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state) {
        super.mouseReleased(mouseX, mouseY, state);
        dragging = false;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY) {
        super.drawScreen(mouseX, mouseY);

        double min = setting.getMin();
        double max = setting.getMax();
        double l = 90;

        renderWidth = (l) * (setting.getValDouble() - min) / (max - min);
        renderWidth2 = (l) * (setting.getMax() - min) / (max - min);

        double diff = Math.min(l, Math.max(0, mouseX - (parent.posX + x - 70)));
        if (dragging) {
            if (diff == 0) {
                setting.setValDouble(setting.getMin());
            }
            else {
                double newValue = roundToPlace(((diff / l) * (max - min) + min), 1);
                setting.setValDouble(newValue);
            }
        }
        
        Color t = ClickGuiColor.getClickGuiColor();
        
        Gui.drawRect(parent.posX + x - 70, parent.posY + y + 10,parent.posX + x - 70 + renderWidth2, parent.posY + y + 20, new Color(87, 87, 87).getRGB());
        Gui.drawRect(parent.posX + x - 70, parent.posY + y + 10, parent.posX + x - 70 + renderWidth, parent.posY + y + 20, new Color(t.getRed(), t.getGreen(), t.getBlue()).getRGB());
        Fonts.DISCORD.REGULAR_20.REGULAR_20.drawString(setting.getName() + ": " + setting.getValDouble(),(int)(parent.posX + x - 70),(int)(parent.posY + y), -1);
    }

    private double roundToPlace(double value, int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

}
