package net.yxiao233.botanypotconfigurationcard.client;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterClientTooltipComponentFactoriesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.yxiao233.botanypotconfigurationcard.BotanyPotConfigurationCard;

@Mod.EventBusSubscriber(modid = BotanyPotConfigurationCard.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class TooltipComponentRegistryEvent {
    @SubscribeEvent
    public static void onRegistry(RegisterClientTooltipComponentFactoriesEvent event){
        event.register(ConfigurationCardTooltipComponent.class,ConfigurationCardTooltipComponent::getClientTooltipComponent);
    }
}
