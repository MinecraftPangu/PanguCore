package cn.mccraft.pangu.core.client.gui;

import cn.mccraft.pangu.core.util.render.CustomFont;
import cn.mccraft.pangu.core.util.render.Rect;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

public class TextLabel extends GuiButton {
    private final CustomFont font;
    private boolean renderBox;

    public TextLabel(int buttonId, int x, int y, int height, String buttonText, CustomFont font) {
        super(buttonId, x, y, font.getStringWidth(buttonText), height,  buttonText);
        this.font = font;
    }

    /**
     * Draws this button to the screen.
     */
    public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
        if (!this.visible) return;

        this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;

        if (renderBox) Rect.draw(x, y, x + width, y + height, 0xFFBB0000);

        font.drawString(
                this.displayString,
                this.x,
                this.y + height / 2,
                0xFFFFFFFF,
                hovered);
    }

    public TextLabel setEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    public TextLabel setRenderBox(boolean renderBox) {
        this.renderBox = renderBox;
        return this;
    }


    public TextLabel setHeight(int height) {
        this.height = height;
        return this;
    }
}
