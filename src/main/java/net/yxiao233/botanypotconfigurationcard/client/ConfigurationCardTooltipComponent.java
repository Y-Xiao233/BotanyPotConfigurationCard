package net.yxiao233.botanypotconfigurationcard.client;

import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public record ConfigurationCardTooltipComponent(ItemStack soil, ItemStack seed) implements TooltipComponent {
    public ClientTooltipComponent getClientTooltipComponent() {
        return new ConfigurationCardTooltipRenderer(this);
    }
}
