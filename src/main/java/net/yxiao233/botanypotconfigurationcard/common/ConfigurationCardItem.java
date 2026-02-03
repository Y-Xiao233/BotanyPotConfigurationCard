package net.yxiao233.botanypotconfigurationcard;

import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

public class ConfigurationCardItem extends Item {
    public ConfigurationCardItem() {
        super(new Properties().stacksTo(1));
    }

    @Override
    public @NotNull InteractionResult useOn(@NotNull UseOnContext context) {
        Level level = context.getLevel();
        BlockEntity blockEntity = level.getBlockEntity(context.getClickedPos());
        ItemStack card = context.getItemInHand();
        Player player = context.getPlayer();
        if(player != null && player.isShiftKeyDown()){
            PotConfigurationAction.create(card,blockEntity).action(player);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand usedHand) {
        HitResult hitResult = rayTraceSimple(level, player, 13, 0);
        ItemStack card = player.getItemInHand(usedHand);
        if(hitResult.getType() == HitResult.Type.BLOCK){
            return InteractionResultHolder.pass(card);
        }
        if(card.getItem() instanceof ConfigurationCardItem && player.isShiftKeyDown()){
            if(card.getTag() != null && card.getTag().contains("setting")){
                card.setTag(new CompoundTag());
            }
            player.displayClientMessage(PotConfigurationAction.reset.get(0),true);
            return InteractionResultHolder.success(card);
        }
        return InteractionResultHolder.pass(card);
    }

    @Override
    public @NotNull Optional<TooltipComponent> getTooltipImage(@NotNull ItemStack stack) {
        if(stack.getTag() != null && stack.getTag().contains("setting")){
            CompoundTag setting = stack.getTag().getCompound("setting");
            ItemStack seed = ItemStack.EMPTY;
            ItemStack soil = ItemStack.EMPTY;
            seed.deserializeNBT(setting.getCompound("seed"));
            soil.deserializeNBT(setting.getCompound("soil"));
            if(!seed.isEmpty() && !soil.isEmpty()){
                return Optional.of(new ConfigurationCardTooltipComponent(soil,seed));
            }
        }
        return super.getTooltipImage(stack);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, Level level, @NotNull List<Component> tooltipComponents, @NotNull TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.translatable("tooltip.botanypotconfigurationcard.tip0").withStyle(ChatFormatting.GRAY));
        tooltipComponents.add(Component.translatable("tooltip.botanypotconfigurationcard.tip1").withStyle(ChatFormatting.GRAY));
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        if(stack.getTag() != null && stack.getTag().contains("setting")){
            CompoundTag setting = stack.getTag().getCompound("setting");
            ItemStack seed = ItemStack.EMPTY;
            ItemStack soil = ItemStack.EMPTY;
            seed.deserializeNBT(setting.getCompound("seed"));
            soil.deserializeNBT(setting.getCompound("soil"));
            return !seed.isEmpty() && !soil.isEmpty();
        }
        return false;
    }

    public static HitResult rayTraceSimple(Level world, LivingEntity living, double blockReachDistance, float partialTicks) {
        Vec3 vec3d = living.getEyePosition(partialTicks);
        Vec3 vec3d1 = living.getViewVector(partialTicks);
        Vec3 vec3d2 = vec3d.add(vec3d1.x * blockReachDistance, vec3d1.y * blockReachDistance, vec3d1.z * blockReachDistance);
        return world.clip(new ClipContext(vec3d, vec3d2, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, living));
    }
}
