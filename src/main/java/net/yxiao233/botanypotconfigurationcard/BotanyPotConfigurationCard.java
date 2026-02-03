package net.yxiao233.botanypotconfigurationcard;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.yxiao233.botanypotconfigurationcard.common.ConfigurationCardItem;

@Mod(BotanyPotConfigurationCard.MODID)
public class BotanyPotConfigurationCard {
    public static final String MODID = "botanypotconfigurationcard";
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);
    public static final RegistryObject<Item> CONFIGURATION_CARD = ITEMS.register("configuration_card", ConfigurationCardItem::new);
    public static final RegistryObject<CreativeModeTab> TAB = CREATIVE_MODE_TABS.register("botanypotconfigurationcard_tab", () -> CreativeModeTab.builder()
            .icon(() -> CONFIGURATION_CARD.get().getDefaultInstance())
            .title(Component.translatable("itemGroup.botanypotconfigurationcard"))
            .displayItems((parameters, output) -> {
                output.accept(CONFIGURATION_CARD.get());
            }).build());

    @SuppressWarnings("removal")
    public BotanyPotConfigurationCard() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ITEMS.register(modEventBus);
        CREATIVE_MODE_TABS.register(modEventBus);
    }
}
