package net.yxiao233.botanypotconfigurationcard.common;

import net.darkhax.botanypots.block.BlockEntityBotanyPot;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.ItemHandlerHelper;

import java.util.HashMap;

public class PotConfigurationAction {
    public static final PotConfigurationAction EMPTY = new PotConfigurationAction(null,null);
    public static HashMap<Integer, Component> applyOrSave;
    public static HashMap<Integer, Component> reset;
    private final ItemStack card;
    private final BlockEntityBotanyPot entity;
    private PotConfigurationAction(ItemStack stack, BlockEntityBotanyPot entity){
        this.card = stack;
        this.entity = entity;
    }

    public static PotConfigurationAction create(ItemStack card, BlockEntity blockEntity){
        if(card.getItem() instanceof ConfigurationCardItem && blockEntity instanceof BlockEntityBotanyPot potBlockEntity){
            return new PotConfigurationAction(card,potBlockEntity);
        }
        return EMPTY;
    }

    public void action(Player player){
        if(this == EMPTY || this.entity == null || this.card == null || this.card.isEmpty()){
            return;
        }
        int messageValue;
        PotInfo info = PotInfo.create(card);
        if(!info.isEmpty()){
            messageValue = applySetting(player,info);
        }else{
            messageValue = saveSetting();
        }
        Component message = PotConfigurationAction.applyOrSave.get(messageValue);
        player.displayClientMessage(message,true);
    }

    private int applySetting(Player player, PotInfo info){
        if(info == null || info.isEmpty()){
            return -1;
        }

        ItemStack potSeed = getSeedItem();
        ItemStack potSoil = getSoilItem();
        ItemStack seed = info.getSeed().copy();
        ItemStack soil = info.getSoil().copy();
        if(potSeed.isEmpty() || !ItemStack.isSameItemSameTags(seed,potSeed)){
            int seedSlot = player.getInventory().findSlotMatchingItem(seed);
            if (seedSlot != -1 || player.isCreative()) {
                setSeedItem(seed);
                if(!player.isCreative()){
                    player.getInventory().getItem(seedSlot).shrink(1);
                }
            }else{
                return 1;
            }
            if(!potSeed.isEmpty()){
                ItemHandlerHelper.giveItemToPlayer(player,potSeed);
            }
        }
        if(potSoil.isEmpty() || !ItemStack.isSameItemSameTags(potSoil,soil)){
            int soilSlot = player.getInventory().findSlotMatchingItem(soil);
            if (soilSlot != -1 || player.isCreative()) {
                setSoilItem(soil);
                if(!player.isCreative()){
                    player.getInventory().getItem(soilSlot).shrink(1);
                }
            }else{
                return 2;
            }
            if(!potSoil.isEmpty()){
                ItemHandlerHelper.giveItemToPlayer(player,potSoil);
            }
        }
        return 0;
    }

    private int saveSetting(){
        ItemStack potSeed = getSeedItem();
        ItemStack potSoil = getSoilItem();
        if(potSeed.isEmpty()){
            return 11;
        }
        if(potSoil.isEmpty()){
            return 12;
        }

        PotInfo.of(potSeed,potSoil).serializeNBT(card);
        return 10;
    }

    private ItemStack getSeedItem(){
        return entity.getInventory().getCropStack().copy();
    }

    private ItemStack getSoilItem(){
        return entity.getInventory().getSoilStack().copy();
    }

    private void setSeedItem(ItemStack seed){
        entity.setItem(1,seed.copy());
    }

    private void setSoilItem(ItemStack soil){
        entity.setItem(0,soil.copy());
    }


    static{
        applyOrSave = new HashMap<>();
        applyOrSave.put(-1,Component.translatable("message.botanypotconfigurationcard.error").withStyle(ChatFormatting.RED));
        applyOrSave.put(0,Component.translatable("message.botanypotconfigurationcard.success").withStyle(ChatFormatting.GREEN));
        applyOrSave.put(1,Component.translatable("message.botanypotconfigurationcard.miss_seed").withStyle(ChatFormatting.YELLOW));
        applyOrSave.put(2,Component.translatable("message.botanypotconfigurationcard.miss_soil").withStyle(ChatFormatting.YELLOW));
        applyOrSave.put(10,Component.translatable("message.botanypotconfigurationcard.save").withStyle(ChatFormatting.GREEN));
        applyOrSave.put(11,Component.translatable("message.botanypotconfigurationcard.null_seed").withStyle(ChatFormatting.RED));
        applyOrSave.put(12,Component.translatable("message.botanypotconfigurationcard.null_soil").withStyle(ChatFormatting.RED));

        reset = new HashMap<>();
        reset.put(0,Component.translatable("message.botanypotconfigurationcard.reset").withStyle(ChatFormatting.GREEN));
    }
}
