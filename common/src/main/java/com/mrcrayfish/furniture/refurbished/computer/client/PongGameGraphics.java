package com.mrcrayfish.furniture.refurbished.computer.client;

import com.mrcrayfish.furniture.refurbished.computer.app.PongGame;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;

/**
 * Author: MrCrayfish
 */
public class PongGameGraphics extends DisplayableProgram<PongGame>
{
    public PongGameGraphics(PongGame program)
    {
        super(program, 180, 100);
        this.setWindowOutlineColour(0xFF47403E);
        this.setWindowTitleBarColour(0xFF5B5450);
        this.setScene(new TestScene());
    }

    private class TestScene extends Scene
    {
        private final Button testButton;

        public TestScene()
        {
            this.testButton = this.addWidget(Button.builder(Component.literal("Test"), var1 -> {
                PongGameGraphics.this.setScene(new GameScene());
            }).size(50, 20).build());
        }

        @Override
        public void updateWidgets(int contentStart, int contentTop)
        {
            this.testButton.setX(contentStart - 5);
            this.testButton.setY(contentTop + 5);
        }

        @Override
        public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick)
        {

        }
    }

    private class GameScene extends Scene
    {
        private final Button testButton;

        public GameScene()
        {
            this.testButton = this.addWidget(Button.builder(Component.literal("Test 2"), var1 -> {
                PongGameGraphics.this.setScene(new TestScene());
            }).size(50, 20).build());
        }

        @Override
        public void updateWidgets(int contentStart, int contentTop)
        {
            this.testButton.setX(contentStart + 100);
            this.testButton.setY(contentTop + 5);
        }

        @Override
        public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick)
        {

        }
    }
}
