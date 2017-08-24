package gregtech.api.items.metaitem;

import com.google.common.collect.Iterables;
import gregtech.api.items.metaitem.stats.IFoodStats;
import gregtech.api.util.GTUtility;
import gregtech.api.util.RandomPotionEffect;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Simple {@link IFoodStats} implementation
 *
 * @see gregtech.api.items.metaitem.MetaItem
 */
public class FoodStats implements IFoodStats {

    public final int foodLevel;
    public final float saturation;
    public final boolean isDrink;
    public final boolean alwaysEdible;
    public final RandomPotionEffect[] potionEffects;

    @Nullable
    public final ItemStack containerItem;

    public FoodStats(int foodLevel, float saturation, boolean isDrink, boolean alwaysEdible, ItemStack containerItem, RandomPotionEffect... potionEffects) {
        this.foodLevel = foodLevel;
        this.saturation = saturation;
        this.isDrink = isDrink;
        this.alwaysEdible = alwaysEdible;
        this.containerItem = containerItem.copy();
        this.potionEffects = potionEffects;
    }

    public FoodStats(int foodLevel, float saturation, boolean isDrink) {
        this(foodLevel, saturation, isDrink, false, null);
    }

    public FoodStats(int foodLevel, float saturation) {
        this(foodLevel, saturation, false);
    }

    @Override
    public int getFoodLevel(ItemStack itemStack, EntityPlayer player) {
        return foodLevel;
    }

    @Override
    public float getSaturation(ItemStack itemStack, EntityPlayer player) {
        return saturation;
    }

    @Override
    public boolean alwaysEdible(ItemStack itemStack, EntityPlayer player) {
        return alwaysEdible;
    }

    @Override
    public EnumAction getFoodAction(ItemStack itemStack) {
        return isDrink ? EnumAction.DRINK : EnumAction.EAT;
    }

    @Override
    public void onEaten(ItemStack itemStack, EntityPlayer player) {
        for(RandomPotionEffect potionEffect : potionEffects) {
            if (Math.random() * 100 > potionEffect.chance) {
                player.addPotionEffect(GTUtility.copyPotionEffect(potionEffect.effect));
            }
        }
    }

    @Override
    public void addInformation(ItemStack itemStack, List<String> lines) {
        if(potionEffects.length > 0) {
            PotionEffect[] effects = new PotionEffect[potionEffects.length];
            for (int i = 0; i < potionEffects.length; i++) {
                effects[i] = potionEffects[i].effect;
            }
            GTUtility.addPotionTooltip(Iterables.cycle(effects), lines);
        }
    }

}
