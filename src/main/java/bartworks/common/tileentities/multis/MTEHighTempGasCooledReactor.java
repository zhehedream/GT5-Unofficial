/*
 * Copyright (C) 2022 kuba6000 This program is free software: you can redistribute it and/or modify it under the terms
 * of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version. This program is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details. You should have received a copy of the GNU General Public License along with
 * this program. If not, see <https://www.gnu.org/licenses/>.
 */

package bartworks.common.tileentities.multis;

import static bartworks.API.recipe.BartWorksRecipeMaps.htgrFakeRecipes;
import static com.gtnewhorizon.structurelib.structure.StructureUtility.ofBlock;
import static com.gtnewhorizon.structurelib.structure.StructureUtility.ofChain;
import static com.gtnewhorizon.structurelib.structure.StructureUtility.onElementPass;
import static com.gtnewhorizon.structurelib.structure.StructureUtility.transpose;
import static gregtech.api.enums.GTValues.AuthorKuba;
import static gregtech.api.util.GTRecipeBuilder.HOURS;
import static gregtech.api.util.GTStructureUtility.ofHatchAdder;
import static gregtech.api.util.GTUtility.validMTEList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import org.jetbrains.annotations.NotNull;

import com.gtnewhorizon.structurelib.alignment.IAlignmentLimits;
import com.gtnewhorizon.structurelib.structure.IStructureDefinition;
import com.gtnewhorizon.structurelib.structure.StructureDefinition;

import bartworks.common.items.SimpleSubItemClass;
import bartworks.system.material.WerkstoffLoader;
import bartworks.util.MathUtils;
import cpw.mods.fml.common.registry.GameRegistry;
import gregtech.api.GregTechAPI;
import gregtech.api.enums.GTValues;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.enums.Textures;
import gregtech.api.enums.TierEU;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.MTEEnhancedMultiBlockBase;
import gregtech.api.metatileentity.implementations.MTEHatchInput;
import gregtech.api.metatileentity.implementations.MTEHatchOutputBus;
import gregtech.api.recipe.RecipeMap;
import gregtech.api.recipe.check.CheckRecipeResult;
import gregtech.api.recipe.check.CheckRecipeResultRegistry;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.GTLanguageManager;
import gregtech.api.util.GTModHandler;
import gregtech.api.util.GTUtility;
import gregtech.api.util.MultiblockTooltipBuilder;
import gregtech.api.util.shutdown.ShutDownReasonRegistry;
import gregtech.api.util.shutdown.SimpleShutDownReason;

public class MTEHighTempGasCooledReactor extends MTEEnhancedMultiBlockBase<MTEHighTempGasCooledReactor> {

    private static final int BASECASINGINDEX = 181;

    private static final String STRUCTURE_PIECE_MAIN = "main";
    private static final IStructureDefinition<MTEHighTempGasCooledReactor> STRUCTURE_DEFINITION = StructureDefinition
        .<MTEHighTempGasCooledReactor>builder()
        .addShape(
            STRUCTURE_PIECE_MAIN,
            transpose(
                new String[][] {
                    { "  BBBBBBB  ", " BBBBBBBBB ", "BBBBBBBBBBB", "BBBBBBBBBBB", "BBBBBBBBBBB", "BBBBBBBBBBB",
                        "BBBBBBBBBBB", "BBBBBBBBBBB", "BBBBBBBBBBB", " BBBBBBBBB ", "  BBBBBBB  " },
                    { "  ccccccc  ", " c-------c ", "c---------c", "c---------c", "c---------c", "c---------c",
                        "c---------c", "c---------c", "c---------c", " c-------c ", "  ccccccc  " },
                    { "  ccccccc  ", " c-------c ", "c---------c", "c---------c", "c---------c", "c---------c",
                        "c---------c", "c---------c", "c---------c", " c-------c ", "  ccccccc  " },
                    { "  ccccccc  ", " c-------c ", "c---------c", "c---------c", "c---------c", "c---------c",
                        "c---------c", "c---------c", "c---------c", " c-------c ", "  ccccccc  " },
                    { "  ccccccc  ", " c-------c ", "c---------c", "c---------c", "c---------c", "c---------c",
                        "c---------c", "c---------c", "c---------c", " c-------c ", "  ccccccc  " },
                    { "  ccccccc  ", " c-------c ", "c---------c", "c---------c", "c---------c", "c---------c",
                        "c---------c", "c---------c", "c---------c", " c-------c ", "  ccccccc  " },
                    { "  ccccccc  ", " c-------c ", "c---------c", "c---------c", "c---------c", "c---------c",
                        "c---------c", "c---------c", "c---------c", " c-------c ", "  ccccccc  " },
                    { "  ccccccc  ", " c-------c ", "c---------c", "c---------c", "c---------c", "c---------c",
                        "c---------c", "c---------c", "c---------c", " c-------c ", "  ccccccc  " },
                    { "  ccccccc  ", " c-------c ", "c---------c", "c---------c", "c---------c", "c---------c",
                        "c---------c", "c---------c", "c---------c", " c-------c ", "  ccccccc  " },
                    { "  ccccccc  ", " c-------c ", "c---------c", "c---------c", "c---------c", "c---------c",
                        "c---------c", "c---------c", "c---------c", " c-------c ", "  ccccccc  " },
                    { "  ccccccc  ", " c-------c ", "c---------c", "c---------c", "c---------c", "c---------c",
                        "c---------c", "c---------c", "c---------c", " c-------c ", "  ccccccc  " },
                    { "  bbb~bbb  ", " bbbbbbbbb ", "bbbbbbbbbbb", "bbbbbbbbbbb", "bbbbbbbbbbb", "bbbbbbbbbbb",
                        "bbbbbbbbbbb", "bbbbbbbbbbb", "bbbbbbbbbbb", " bbbbbbbbb ", "  bbbbbbb  " }, }))
        .addElement('c', onElementPass(x -> x.mCasing++, ofBlock(GregTechAPI.sBlockCasings8, 5)))
        .addElement(
            'b',
            ofChain(
                ofHatchAdder(MTEHighTempGasCooledReactor::addOutputToMachineList, BASECASINGINDEX, 1),
                ofHatchAdder(MTEHighTempGasCooledReactor::addMaintenanceToMachineList, BASECASINGINDEX, 1),
                ofHatchAdder(MTEHighTempGasCooledReactor::addEnergyInputToMachineList, BASECASINGINDEX, 1),
                onElementPass(x -> x.mCasing++, ofBlock(GregTechAPI.sBlockCasings8, 5))))
        .addElement(
            'B',
            ofChain(
                ofHatchAdder(MTEHighTempGasCooledReactor::addInputToMachineList, BASECASINGINDEX, 2),
                onElementPass(x -> x.mCasing++, ofBlock(GregTechAPI.sBlockCasings8, 5))))
        // ofHatchAdderOptional(GT_TileEntity_HTGR::addInputToMachineList, BASECASINGINDEX, 2,
        // GregTechAPI.sBlockCasings8, 5))
        .build();

    private static final int HELIUM_NEEDED = 730000;
    public static final int powerUsage = (int) TierEU.RECIPE_LuV;
    private static final int maxcapacity = 720000;
    private static final int mincapacity = maxcapacity / 10;
    private int HeliumSupply;
    private int fueltype = -1, fuelsupply = 0;
    private boolean empty;
    private int emptyticksnodiff = 0;
    private int coolanttaking = 0;
    private int mCasing = 0;

    public MTEHighTempGasCooledReactor(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
    }

    private MTEHighTempGasCooledReactor(String aName) {
        super(aName);
    }

    @Override
    public IStructureDefinition<MTEHighTempGasCooledReactor> getStructureDefinition() {
        return STRUCTURE_DEFINITION;
    }

    @Override
    protected MultiblockTooltipBuilder createTooltip() {
        MultiblockTooltipBuilder tt = new MultiblockTooltipBuilder();
        tt.addMachineType("Breeder Reactor, HTGR")
            .addInfo("You can clear internal buffer by changing the mode with a screwdriver")
            .addInfo("Needs a constant supply of coolant while running")
            .addInfo("Needs at least 72k Fuel pebbles to start operation (can hold up to 720k pebbles)")
            .addInfo("Consumes up to 2.5% of total Fuel Pellets per Operation depending on efficiency")
            .addInfo("Efficiency is calculated exponentially depending on the amount of pebbles in the internal buffer")
            .addInfo("and affects total recipe time (at 100% eff, -50% total recipe time")
            .addInfo(
                "Reactor will take 4 000L/s of coolant multiplied by efficiency and by fuel coolant value (check tooltips)")
            .addInfo("Uses " + GTUtility.formatNumbers(powerUsage) + " EU/t")
            .addInfo("One Operation takes 1 hour")
            .beginStructureBlock(11, 12, 11, true)
            .addController("Front bottom center")
            .addCasingInfoMin("Europium Reinforced Radiation Proof Casings", 500, false)
            .addStructureInfo("Corners and the 2 touching blocks are air (cylindric)")
            .addInputBus("Any top layer casing", 2)
            .addInputHatch("Any top layer casing", 2)
            .addOutputBus("Any bottom layer casing", 1)
            .addOutputHatch("Any bottom layer casing", 1)
            .addEnergyHatch("Any bottom layer casing", 1)
            .addMaintenanceHatch("Any bottom layer casing", 1)
            .toolTipFinisher(AuthorKuba);
        return tt;
    }

    @Override
    protected IAlignmentLimits getInitialAlignmentLimits() {
        return (d, r, f) -> d.offsetY == 0 && r.isNotRotated() && f.isNotFlipped();
    }

    @Override
    public void construct(ItemStack stackSize, boolean hintsOnly) {
        this.buildPiece("main", stackSize, hintsOnly, 5, 11, 0);
    }

    @Override
    public boolean checkMachine(IGregTechTileEntity aBaseMetaTileEntity, ItemStack itemStack) {
        this.mCasing = 0;
        return this.checkPiece("main", 5, 11, 0) && this.mCasing >= 500
            && this.mMaintenanceHatches.size() == 1
            && !this.mInputHatches.isEmpty()
            && !this.mOutputHatches.isEmpty()
            && !this.mInputBusses.isEmpty()
            && !this.mOutputBusses.isEmpty()
            && !this.mEnergyHatches.isEmpty();
    }

    @Override
    public void loadNBTData(NBTTagCompound aNBT) {
        super.loadNBTData(aNBT);
        this.HeliumSupply = aNBT.getInteger("HeliumSupply");
        this.fueltype = aNBT.getInteger("fueltype");
        this.fuelsupply = aNBT.getInteger("fuelsupply");
        this.empty = aNBT.getBoolean("EmptyMode");
        this.coolanttaking = aNBT.getInteger("coolanttaking");
    }

    @Override
    public void saveNBTData(NBTTagCompound aNBT) {
        super.saveNBTData(aNBT);
        aNBT.setInteger("HeliumSupply", this.HeliumSupply);
        aNBT.setInteger("fueltype", this.fueltype);
        aNBT.setInteger("fuelsupply", this.fuelsupply);
        aNBT.setBoolean("EmptyMode", this.empty);
        aNBT.setInteger("coolanttaking", this.coolanttaking);
    }

    @Override
    public void onPostTick(IGregTechTileEntity aBaseMetaTileEntity, long aTick) {
        super.onPostTick(aBaseMetaTileEntity, aTick);
        if (aBaseMetaTileEntity.isServerSide() && !this.empty) {
            boolean updateneeded = false;
            if (this.HeliumSupply < MTEHighTempGasCooledReactor.HELIUM_NEEDED) {
                for (FluidStack fluidStack : this.getStoredFluids()) {
                    if (fluidStack.isFluidEqual(Materials.Helium.getGas(1))) {
                        int toget = Math
                            .min(MTEHighTempGasCooledReactor.HELIUM_NEEDED - this.HeliumSupply, fluidStack.amount);
                        fluidStack.amount -= toget;
                        this.HeliumSupply += toget;
                        updateneeded = true;
                    }
                }
            }
            if (this.fuelsupply < maxcapacity) {
                this.startRecipeProcessing();
                for (ItemStack itemStack : this.getStoredInputs()) {
                    int type = -1;
                    if (itemStack == null || itemStack.getItem() != HTGRMaterials.aHTGR_Materials) continue;
                    int damage = HTGRMaterials.aHTGR_Materials.getDamage(itemStack);
                    if ((damage + 1) % HTGRMaterials.MATERIALS_PER_FUEL != HTGRMaterials.USABLE_FUEL_INDEX + 1)
                        continue; // is fuel
                    type = damage / HTGRMaterials.MATERIALS_PER_FUEL;
                    if (this.fueltype == -1) this.fueltype = type;
                    if (this.fueltype != type) continue;
                    int toget = Math.min(maxcapacity - this.fuelsupply, itemStack.stackSize);
                    this.fuelsupply += toget;
                    itemStack.stackSize -= toget;
                    updateneeded = true;
                }
                this.endRecipeProcessing();
            }
            if (updateneeded) this.updateSlots();
        }
    }

    @Override
    public RecipeMap<?> getRecipeMap() {
        // Only for visual
        return htgrFakeRecipes;
    }

    @Override
    protected boolean filtersFluid() {
        return false;
    }

    @Override
    public @NotNull CheckRecipeResult checkProcessing() {

        if (this.empty) {
            if (this.HeliumSupply > 0 || this.fuelsupply > 0) {
                this.mEfficiency = 10000;
                this.mMaxProgresstime = 100;
                return CheckRecipeResultRegistry.SUCCESSFUL;
            }
            return CheckRecipeResultRegistry.NO_RECIPE;
        }
        if (this.HeliumSupply < MTEHighTempGasCooledReactor.HELIUM_NEEDED || this.fuelsupply < mincapacity)
            return CheckRecipeResultRegistry.NO_RECIPE;

        double ratio = (double) this.fuelsupply / (double) mincapacity;
        double eff = Math.min(ratio * ratio, 100D) / 100D - (this.getIdealStatus() - this.getRepairStatus()) / 10D;

        if (eff <= 0) return CheckRecipeResultRegistry.NO_RECIPE;

        int toReduce = MathUtils.floorInt(this.fuelsupply * 0.025D * eff);

        final int originalToReduce = toReduce;
        int burnedballs = toReduce / 64;
        if (burnedballs > 0) toReduce -= burnedballs * 64;

        int meta = this.fueltype * HTGRMaterials.MATERIALS_PER_FUEL + HTGRMaterials.BURNED_OUT_FUEL_INDEX;

        ItemStack[] toOutput = { new ItemStack(HTGRMaterials.aHTGR_Materials, burnedballs, meta),
            new ItemStack(HTGRMaterials.aHTGR_Materials, toReduce, meta + 1) };
        if (!this.canOutputAll(toOutput)) return CheckRecipeResultRegistry.NO_RECIPE;

        this.fuelsupply -= originalToReduce;
        this.mOutputItems = toOutput;

        // this.updateSlots(); // not needed ?

        this.coolanttaking = (int) (4000D * (this.fueltype * 0.5D + 1) * eff);

        this.mEfficiency = (int) (eff * 10000D);
        this.mEfficiencyIncrease = 0;
        this.mEUt = -powerUsage;
        this.mMaxProgresstime = (int) (72000 * (1d - eff / 2d));
        return CheckRecipeResultRegistry.SUCCESSFUL;
    }

    private int runningtick = 0;

    @Override
    public boolean onRunningTick(ItemStack aStack) {
        this.runningtick++;

        if (this.empty) {
            if (this.emptyticksnodiff > 20 && this.emptyticksnodiff % 20 != 0) {
                this.emptyticksnodiff++;
                return true;
            }
            if (this.HeliumSupply > 0) {
                this.addOutput(Materials.Helium.getGas(this.HeliumSupply));
                this.HeliumSupply = 0;
            }
            if (this.fuelsupply > 0) {
                ItemStack iStack = new ItemStack(
                    HTGRMaterials.aHTGR_Materials,
                    this.fuelsupply,
                    HTGRMaterials.MATERIALS_PER_FUEL * this.fueltype + HTGRMaterials.USABLE_FUEL_INDEX);
                boolean storedAll = false;
                for (MTEHatchOutputBus tHatch : validMTEList(mOutputBusses)) {
                    if (tHatch.storePartial(iStack)) {
                        storedAll = true;
                        break;
                    }
                }
                if (!storedAll) {
                    if (this.fuelsupply == iStack.stackSize) this.emptyticksnodiff++;
                    else {
                        this.fuelsupply = iStack.stackSize;
                        this.emptyticksnodiff = 0;
                    }
                } else {
                    this.fuelsupply = 0;
                    this.fueltype = -1;
                    this.coolanttaking = 0;
                }
            }
            return true;
        }
        // USE DA POWAH
        if (!this.drainEnergyInput(-this.mEUt)) {
            this.stopMachine(ShutDownReasonRegistry.POWER_LOSS);
            return false;
        }

        if (this.runningtick % 20 == 0) {
            int takecoolant = this.coolanttaking;
            int drainedamount = 0;

            for (MTEHatchInput tHatch : validMTEList(mInputHatches)) {
                FluidStack tLiquid = tHatch.getFluid();
                if (tLiquid != null && tLiquid.isFluidEqual(GTModHandler.getIC2Coolant(1))) {
                    FluidStack drained = tHatch.drain(takecoolant, true);
                    takecoolant -= drained.amount;
                    drainedamount += drained.amount;
                    if (takecoolant <= 0) break;
                }
            }

            if (drainedamount > 0) this.addOutput(FluidRegistry.getFluidStack("ic2hotcoolant", drainedamount));

            this.updateSlots();

            if (takecoolant > 0) this.stopMachine(SimpleShutDownReason.ofNormal("no_coolant"));
        }

        return true;
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity iGregTechTileEntity) {
        return new MTEHighTempGasCooledReactor(this.mName);
    }

    @Override
    public String[] getInfoData() {
        return new String[] {
            StatCollector.translateToLocalFormatted(
                "BW.infoData.htgr.mode",
                this.empty ? StatCollector.translateToLocal("BW.infoData.htgr.mode.emptying")
                    : StatCollector.translateToLocal("BW.infoData.htgr.mode.normal")),
            StatCollector.translateToLocalFormatted(
                "BW.infoData.htgr.progress",
                GTUtility.formatNumbers(this.mProgresstime / 20),
                GTUtility.formatNumbers(this.mMaxProgresstime / 20)),
            StatCollector.translateToLocalFormatted(
                "BW.infoData.htgr.fuel_type",
                this.fueltype == -1 ? StatCollector.translateToLocal("BW.infoData.htgr.fuel_type.none")
                    : StatCollector.translateToLocalFormatted(
                        "BW.infoData.htgr.fuel_type.triso",
                        // TODO: check how to get fuel type localized name
                        HTGRMaterials.sHTGR_Fuel[this.fueltype].sEnglish)),
            StatCollector
                .translateToLocalFormatted("BW.infoData.htgr.fuel_amount", GTUtility.formatNumbers(this.fuelsupply)),
            StatCollector.translateToLocalFormatted(
                "BW.infoData.htr.helium_level",
                GTUtility.formatNumbers(this.HeliumSupply),
                GTUtility.formatNumbers(MTEHighTempGasCooledReactor.HELIUM_NEEDED)),
            StatCollector
                .translateToLocalFormatted("BW.infoData.htgr.coolant", GTUtility.formatNumbers(this.coolanttaking)),
            StatCollector.translateToLocalFormatted(
                "BW.infoData.htr.problems",
                String.valueOf(this.getIdealStatus() - this.getRepairStatus())) };
    }

    @Override
    public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, ForgeDirection side,
        ForgeDirection forgeDirection, int aColorIndex, boolean aActive, boolean aRedstone) {
        if (side == forgeDirection) {
            if (aActive) return new ITexture[] {
                Textures.BlockIcons.getCasingTextureForId(MTEHighTempGasCooledReactor.BASECASINGINDEX),
                TextureFactory.builder()
                    .addIcon(Textures.BlockIcons.OVERLAY_FRONT_HEAT_EXCHANGER_ACTIVE)
                    .extFacing()
                    .build(),
                TextureFactory.builder()
                    .addIcon(Textures.BlockIcons.OVERLAY_FRONT_HEAT_EXCHANGER_ACTIVE_GLOW)
                    .extFacing()
                    .glow()
                    .build() };
            return new ITexture[] {
                Textures.BlockIcons.getCasingTextureForId(MTEHighTempGasCooledReactor.BASECASINGINDEX),
                TextureFactory.builder()
                    .addIcon(Textures.BlockIcons.OVERLAY_FRONT_HEAT_EXCHANGER)
                    .extFacing()
                    .build(),
                TextureFactory.builder()
                    .addIcon(Textures.BlockIcons.OVERLAY_FRONT_HEAT_EXCHANGER_GLOW)
                    .extFacing()
                    .glow()
                    .build() };
        }
        return new ITexture[] {
            Textures.BlockIcons.getCasingTextureForId(MTEHighTempGasCooledReactor.BASECASINGINDEX) };
    }

    @Override
    public void onScrewdriverRightClick(ForgeDirection side, EntityPlayer aPlayer, float aX, float aY, float aZ,
        ItemStack aTool) {
        if (this.mMaxProgresstime > 0) {
            GTUtility.sendChatToPlayer(aPlayer, "HTGR mode cannot be changed while the machine is running.");
            return;
        }
        this.empty = !this.empty;
        GTUtility.sendChatToPlayer(
            aPlayer,
            "HTGR is now running in " + (this.empty ? "emptying mode." : "normal Operation"));
    }

    @Override
    public boolean supportsVoidProtection() {
        return true;
    }

    public static class HTGRMaterials {

        private static class CustomHTGRSimpleSubItemClass extends SimpleSubItemClass {

            HashMap<Integer, String> tooltip = null;

            public CustomHTGRSimpleSubItemClass(HashMap<Integer, String> tooltip, String... tex) {
                super(tex);
                this.tooltip = tooltip;
            }

            @Override
            @SuppressWarnings({ "unchecked", "rawtypes" })
            public void addInformation(ItemStack p_77624_1_, EntityPlayer p_77624_2_, List aList, boolean p_77624_4_) {
                if (this.tooltip.containsKey(this.getDamage(p_77624_1_)))
                    aList.add(this.tooltip.get(this.getDamage(p_77624_1_)));
                aList.add(StatCollector.translateToLocal("tooltip.bw.high_temp_gas_cooled_reactor.material"));
                super.addInformation(p_77624_1_, p_77624_2_, aList, p_77624_4_);
            }
        }

        private static class Base_ {

            public String sName;
            public String sEnglish;

            public Base_(String a, String b) {
                this.sName = a;
                this.sEnglish = b;
            }
        }

        public static class Fuel_ {

            public String sName;
            public String sEnglish;
            public ItemStack mainItem;
            public ItemStack secondaryItem;
            public ItemStack[] recycledItems = { GTValues.NI, GTValues.NI, GTValues.NI, GTValues.NI, GTValues.NI,
                GTValues.NI };
            public FluidStack recycledFluid;
            public int[] recycleChances;
            public String tooltip;

            public Fuel_(String sName, String sEnglish, ItemStack mainItem, ItemStack secondaryItem,
                FluidStack recycledFluid, ItemStack[] recycledItems, int[] recycleChances, String tooltip) {
                this.sName = sName;
                this.sEnglish = sEnglish;
                this.mainItem = mainItem;
                this.secondaryItem = secondaryItem;
                this.recycledFluid = recycledFluid;
                System.arraycopy(recycledItems, 0, this.recycledItems, 0, recycledItems.length);
                this.recycleChances = recycleChances;
                this.tooltip = tooltip;
            }
        }

        private static class LangEntry_ {

            public String sName;
            public String sEnglish;

            public LangEntry_(String a, String b) {
                this.sName = a;
                this.sEnglish = b;
            }
        }

        public static final Base_[] sHTGR_Bases = { new Base_("HTGRFuelMixture", "HTGR fuel mixture"),
            new Base_("BISOPebbleCompound", "BISO pebble compound"),
            new Base_("TRISOPebbleCompound", "TRISO pebble compound"), new Base_("TRISOBall", "TRISO ball"),
            new Base_("TRISOPebble", "TRISO pebble"), new Base_("BurnedOutTRISOBall", "Burned out TRISO Ball"),
            new Base_("BurnedOutTRISOPebble", "Burned out TRISO Pebble"), };
        public static final int MATERIALS_PER_FUEL = sHTGR_Bases.length;
        static final int USABLE_FUEL_INDEX = 4;
        static final int BURNED_OUT_FUEL_INDEX = 5;
        public static final Fuel_[] sHTGR_Fuel = { new Fuel_(
            "Thorium",
            "Thorium",
            WerkstoffLoader.Thorium232.get(OrePrefixes.dust, 64),
            Materials.Uranium235.getDust(4),
            GTValues.NF,
            new ItemStack[] { Materials.Silicon.getDust(1), Materials.Graphite.getDust(1), Materials.Carbon.getDust(1),
                Materials.Lutetium.getDust(1), WerkstoffLoader.Thorium232.get(OrePrefixes.dust, 1) },
            new int[] { 9900 / 4, 9900 / 4, 9900 / 4, 9900 / 4, 162 / 4 },
            "Multiplies coolant by 1"),
            new Fuel_(
                "Uranium",
                "Uranium",
                Materials.Uranium.getDust(64),
                Materials.Uranium235.getDust(8),
                FluidRegistry.getFluidStack("krypton", 4),
                new ItemStack[] { Materials.Silicon.getDust(1), Materials.Graphite.getDust(1),
                    Materials.Carbon.getDust(1), Materials.Lead.getDust(1), Materials.Uranium.getDust(1) },
                new int[] { 9900 / 4, 9900 / 4, 9900 / 4, 5000 / 4, 5000 / 4 },
                "Multiplies coolant by 1.5"),
            new Fuel_(
                "Plutonium",
                "Plutonium",
                Materials.Plutonium.getDust(64),
                Materials.Plutonium241.getDust(4),
                FluidRegistry.getFluidStack("xenon", 4),
                new ItemStack[] { Materials.Silicon.getDust(1), Materials.Graphite.getDust(1),
                    Materials.Carbon.getDust(1), Materials.Lead.getDust(1), Materials.Plutonium.getDust(1) },
                new int[] { 9900 / 4, 9900 / 4, 9900 / 4, 5000 / 4, 5000 / 4 },
                "Multiplies coolant by 2"), };
        public static final CustomHTGRSimpleSubItemClass aHTGR_Materials;
        static final ArrayList<LangEntry_> aHTGR_Localizations = new ArrayList<>();

        static {
            String[] sHTGR_Materials = new String[sHTGR_Bases.length * sHTGR_Fuel.length];
            HashMap<Integer, String> tooltip = new HashMap<>();
            int i = 0;
            for (Fuel_ fuel : sHTGR_Fuel) for (Base_ base : sHTGR_Bases) {
                sHTGR_Materials[i] = "HTGR" + base.sName + fuel.sName;
                aHTGR_Localizations.add(
                    new LangEntry_("item." + sHTGR_Materials[i] + ".name", base.sEnglish + " (" + fuel.sEnglish + ")"));
                if ((i + 1) % MATERIALS_PER_FUEL == USABLE_FUEL_INDEX + 1 && fuel.tooltip != null
                    && !fuel.tooltip.isEmpty()) tooltip.put(i, fuel.tooltip);
                i++;
            }
            aHTGR_Materials = new CustomHTGRSimpleSubItemClass(tooltip, sHTGR_Materials);
        }

        public static void registeraTHR_Materials() {
            for (LangEntry_ iName : aHTGR_Localizations)
                GTLanguageManager.addStringLocalization(iName.sName, iName.sEnglish);
            GameRegistry.registerItem(MTEHighTempGasCooledReactor.HTGRMaterials.aHTGR_Materials, "bw.HTGRMaterials");
        }

        public static void register_fake_THR_Recipes() {

            int i = 0;
            for (@SuppressWarnings("unused")
            Fuel_ fuel : sHTGR_Fuel) {

                GTValues.RA.stdBuilder()
                    .itemInputs(new ItemStack(MTEHighTempGasCooledReactor.HTGRMaterials.aHTGR_Materials, 64, i + 4))
                    .itemOutputs(new ItemStack(MTEHighTempGasCooledReactor.HTGRMaterials.aHTGR_Materials, 1, i + 5))
                    .duration(1 * HOURS)
                    .eut(powerUsage)
                    .ignoreCollision()
                    .fake()
                    .addTo(htgrFakeRecipes);

                i += MATERIALS_PER_FUEL;
            }
        }
    }
}
