package net.yxiao233.botanypotconfigurationcard.client;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterClientTooltipComponentFactoriesEvent;
import net.yxiao233.botanypotconfigurationcard.BotanyPotConfigurationCard;

@SuppressWarnings({"removal","unused"})
@EventBusSubscriber(modid = BotanyPotConfigurationCard.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class TooltipComponentRegisterEvent {
    @SubscribeEvent
    public static void onRegistry(RegisterClientTooltipComponentFactoriesEvent event){
        event.register(ConfigurationCardTooltipComponent.class,ConfigurationCardTooltipComponent::getClientTooltipComponent);
    }
}
