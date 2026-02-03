package net.yxiao233.botanypotconfigurationcard.common;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

public class PotInfo{
    public static final PotInfo EMPTY = new PotInfo();
    private ItemStack seed;
    private ItemStack soil;
    private PotInfo(){
        seed = ItemStack.EMPTY;
        soil = ItemStack.EMPTY;
    }

    public static PotInfo create(ItemStack card){
        PotInfo info = new PotInfo();
        info.deserializeNBT(card);
        return info;
    }

    public static PotInfo of(ItemStack seed, ItemStack soil){
        PotInfo info = new PotInfo();
        info.seed = seed;
        info.soil = soil;
        return info;
    }

    public void serializeNBT(ItemStack card) {
        if(isEmpty()){
            card.setTag(new CompoundTag());
        }else{
            CompoundTag tag = new CompoundTag();
            CompoundTag setting = new CompoundTag();
            setting.put("seed",seed.serializeNBT());
            setting.put("soil",soil.serializeNBT());
            tag.put("setting",setting);
            card.setTag(tag);
        }
    }

    public void deserializeNBT(ItemStack card) {
        if(card.isEmpty() || !card.hasTag()){
            return;
        }
        CompoundTag compoundTag = card.getTag();
        if(compoundTag != null && compoundTag.contains("setting")){
            CompoundTag setting = compoundTag.getCompound("setting");
            this.seed = ItemStack.of(setting.getCompound("seed"));
            this.soil = ItemStack.of(setting.getCompound("soil"));
        }else{
            this.soil = ItemStack.EMPTY;
            this.seed = ItemStack.EMPTY;
        }
    }

    public boolean isEmpty(){
        return this.seed.isEmpty() || this.soil.isEmpty();
    }

    public ItemStack getSeed() {
        return seed;
    }

    public ItemStack getSoil() {
        return soil;
    }
}
