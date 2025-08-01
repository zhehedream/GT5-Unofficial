package gtPlusPlus.core.handler.events;

import java.util.ArrayList;

import net.minecraft.entity.boss.EntityDragon;
import net.minecraftforge.event.entity.living.LivingDropsEvent;

import org.jetbrains.annotations.NotNull;

import com.kuba6000.mobsinfo.api.IMobExtraInfoProvider;
import com.kuba6000.mobsinfo.api.MobDrop;
import com.kuba6000.mobsinfo.api.MobRecipe;

import cpw.mods.fml.common.Optional;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import gregtech.api.enums.Mods;
import gregtech.api.util.GTUtility;
import gregtech.api.util.ReflectionUtil;
import gtPlusPlus.core.material.MaterialsElements;
import gtPlusPlus.core.util.math.MathUtils;

@Optional.Interface(iface = "com.kuba6000.mobsinfo.api.IMobExtraInfoProvider", modid = Mods.ModIDs.MOBS_INFO)
public class EnderDragonDeathHandler implements IMobExtraInfoProvider {

    private static final Class<?> mHardcoreDragonClass = ReflectionUtil
        .getClass("chylex.hee.entity.boss.EntityBossDragon");
    private static final Class<?> mChaoseDragonClass = ReflectionUtil
        .getClass("com.brandon3055.draconicevolution.common.entity.EntityCustomDragon");

    @SubscribeEvent
    public void onEntityDrop(LivingDropsEvent event) {
        int aCountTotal = 0;
        if (mHardcoreDragonClass != null && mHardcoreDragonClass.isInstance(event.entityLiving)) {
            for (int y = 0; y < MathUtils.randInt(100, 250); y++) {
                int aAmount = MathUtils.randInt(5, 25);
                event.entityLiving.entityDropItem(
                    MaterialsElements.STANDALONE.DRAGON_METAL.getNugget(aAmount),
                    MathUtils.randFloat(0, 1));
                aCountTotal = aAmount;
            }
        } else if (mChaoseDragonClass != null && mChaoseDragonClass.isInstance(event.entityLiving)) {
            for (int y = 0; y < MathUtils.randInt(100, 200); y++) {
                int aAmount = MathUtils.randInt(1, 5);
                event.entityLiving.entityDropItem(
                    MaterialsElements.STANDALONE.DRAGON_METAL.getIngot(aAmount),
                    MathUtils.randFloat(0, 1));
                aCountTotal = aAmount;
            }
        } else if (event.entityLiving instanceof EntityDragon) {
            for (int y = 0; y < MathUtils.randInt(25, 50); y++) {
                int aAmount = MathUtils.randInt(1, 10);
                event.entityLiving.entityDropItem(
                    MaterialsElements.STANDALONE.DRAGON_METAL.getNugget(aAmount),
                    MathUtils.randFloat(0, 1));
                aCountTotal = aAmount;
            }
        }
        if (aCountTotal > 0) {
            GTUtility
                .sendServerMessage(aCountTotal + " Shards of Dragons Blood have crystallized into a metallic form.");
        }
    }

    @Optional.Method(modid = Mods.ModIDs.MOBS_INFO)
    @Override
    public void provideExtraDropsInformation(@NotNull String entityString, @NotNull ArrayList<MobDrop> drops,
        @NotNull MobRecipe recipe) {
        if (mHardcoreDragonClass != null && mHardcoreDragonClass.isInstance(recipe.entity)) {
            MobDrop drop = new MobDrop(
                MaterialsElements.STANDALONE.DRAGON_METAL.getNugget(1),
                MobDrop.DropType.Normal,
                (int) (MobDrop.getChanceBasedOnFromTo(100, 250) * MobDrop.getChanceBasedOnFromTo(5, 25) * 10000d),
                null,
                null,
                false,
                false);

            drop.clampChance();

            drops.add(drop);
        } else if (mChaoseDragonClass != null && mChaoseDragonClass.isInstance(recipe.entity)) {
            MobDrop drop = new MobDrop(
                MaterialsElements.STANDALONE.DRAGON_METAL.getIngot(1),
                MobDrop.DropType.Normal,
                (int) (MobDrop.getChanceBasedOnFromTo(100, 200) * MobDrop.getChanceBasedOnFromTo(1, 5) * 10000d),
                null,
                null,
                false,
                false);

            drop.clampChance();

            drops.add(drop);
        } else if (recipe.entity instanceof EntityDragon) {
            MobDrop drop = new MobDrop(
                MaterialsElements.STANDALONE.DRAGON_METAL.getNugget(1),
                MobDrop.DropType.Normal,
                (int) (MobDrop.getChanceBasedOnFromTo(25, 50) * MobDrop.getChanceBasedOnFromTo(1, 10) * 10000d),
                null,
                null,
                false,
                false);

            drop.clampChance();

            drops.add(drop);
        }
    }
}
