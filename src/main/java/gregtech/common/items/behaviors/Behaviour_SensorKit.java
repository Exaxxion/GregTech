package gregtech.common.items.behaviors;

import gregtech.api.capability.IDescribable;
import gregtech.api.items.ItemList;
import gregtech.api.util.GTUtility;
import gregtech.api.util.GT_LanguageManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class Behaviour_SensorKit
        extends Behaviour_None {
    private final String mTooltip = GT_LanguageManager.addStringLocalization("gt.behaviour.sensorkit.tooltip", "Used to display Information using the Mod Nuclear Control");

    @Override
    public boolean onItemUseFirst(GT_MetaBase_Item aItem, ItemStack aStack, EntityPlayer aPlayer, World aWorld, BlockPos pos, EnumFacing aSide, float hitX, float hitY, float hitZ, EnumHand hand) {
        if ((aPlayer instanceof EntityPlayerMP)) {
            TileEntity tTileEntity = aWorld.getTileEntity(pos);
            if (((tTileEntity instanceof IInventory)) && (!((IInventory) tTileEntity).isUseableByPlayer(aPlayer))) {
                return false;
            }
            if (((tTileEntity instanceof IDescribable)) && (((IDescribable) tTileEntity).isGivingInformation())) {
                GTUtility.setStack(aStack, ItemList.NC_SensorCard.get(aStack.stackSize));
                NBTTagCompound tNBT = aStack.getTagCompound();
                if (tNBT == null) {
                    tNBT = new NBTTagCompound();
                }
                tNBT.setInteger("x", pos.getX());
                tNBT.setInteger("y", pos.getY());
                tNBT.setInteger("z", pos.getZ());
                aStack.setTagCompound(tNBT);
            }
            return true;
        }
        return false;
    }

    public List<String> getAdditionalToolTips(GT_MetaBase_Item aItem, List<String> aList, ItemStack aStack) {
        aList.add(this.mTooltip);
        return aList;
    }
}
