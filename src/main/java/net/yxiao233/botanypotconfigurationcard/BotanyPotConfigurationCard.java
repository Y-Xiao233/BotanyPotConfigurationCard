package net.yxiao233.botanypotconfigurationcard;


import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.yxiao233.botanypotconfigurationcard.common.ConfigurationCardItem;
import net.yxiao233.botanypotconfigurationcard.common.PotInfo;

@Mod(BotanyPotConfigurationCard.MODID)
public class BotanyPotConfigurationCard {
    public static final String MODID = "botanypotconfigurationcard";
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, MODID);
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);
    public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENTS = DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, MODID);
    public static final DeferredHolder<Item,Item> CONFIGURATION_CARD = ITEMS.register("configuration_card", ConfigurationCardItem::new);
    public static final DeferredHolder<DataComponentType<?>,DataComponentType<PotInfo>> POT_INFO = DATA_COMPONENTS.register("pot_info",() -> DataComponentType.<PotInfo>builder().persistent(PotInfo.CODEC).build());
    public static final DeferredHolder<CreativeModeTab,CreativeModeTab> TAB = CREATIVE_MODE_TABS.register("botanypotconfigurationcard_tab", () -> CreativeModeTab.builder()
            .icon(() -> CONFIGURATION_CARD.get().getDefaultInstance())
            .title(Component.translatable("itemGroup.botanypotconfigurationcard"))
            .displayItems((parameters, output) -> {
                output.accept(CONFIGURATION_CARD.get());
            }).build());

    public BotanyPotConfigurationCard(IEventBus modEventBus, ModContainer modContainer) {
        ITEMS.register(modEventBus);
        DATA_COMPONENTS.register(modEventBus);
        CREATIVE_MODE_TABS.register(modEventBus);
    }
}
