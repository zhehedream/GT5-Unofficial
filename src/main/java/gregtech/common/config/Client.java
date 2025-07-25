package gregtech.common.config;

import static gregtech.api.recipe.RecipeCategorySetting.ENABLE;

import com.gtnewhorizon.gtnhlib.config.Config;

import gregtech.api.enums.Mods;
import gregtech.api.recipe.RecipeCategorySetting;

@Config(modid = Mods.ModIDs.GREG_TECH, category = "client", configSubDirectory = "GregTech", filename = "Client")
@Config.LangKey("GT5U.gui.config.client")
public class Client {

    @Config.Comment("Color Modulation section")
    public static final ColorModulation colorModulation = new ColorModulation();

    @Config.Comment("Interface section")
    public static final Interface iface = new Interface();

    @Config.Comment("Preference section")
    public static final Preference preference = new Preference();

    @Config.Comment("GT Tool Block Overlay section")
    public static final BlockOverlay blockoverlay = new BlockOverlay();

    @Config.Comment("Render section")
    public static final Render render = new Render();

    @Config.Comment("Waila section")
    public static final Waila waila = new Waila();

    @Config.Comment("NEI section")
    public static final NEI nei = new NEI();

    @Config.LangKey("GT5U.gui.config.client.color_modulation")
    public static class ColorModulation {

        @Config.Comment("RGB values for the cable insulation color modulation.")
        public CableInsulation cableInsulation = new CableInsulation();

        @Config.Comment("RGB values for the machine metal color modulation (default GUI color).")
        public MachineMetal machineMetal = new MachineMetal();

        @Config.LangKey("GT5U.gui.config.client.color_modulation.cable_insulation")
        public static class CableInsulation {

            @Config.DefaultInt(64)
            @Config.RangeInt(min = 0, max = 255)
            public int red;

            @Config.DefaultInt(64)
            @Config.RangeInt(min = 0, max = 255)
            public int green;

            @Config.DefaultInt(64)
            @Config.RangeInt(min = 0, max = 255)
            public int blue;
        }

        @Config.LangKey("GT5U.gui.config.client.color_modulation.machine_metal")
        public static class MachineMetal {

            @Config.DefaultInt(210)
            @Config.RangeInt(min = 0, max = 255)
            public int red;

            @Config.DefaultInt(220)
            @Config.RangeInt(min = 0, max = 255)
            public int green;

            @Config.DefaultInt(255)
            @Config.RangeInt(min = 0, max = 255)
            public int blue;
        }
    }

    @Config.LangKey("GT5U.gui.config.client.interface")
    public static class Interface {

        @Config.Comment("if true, makes cover tabs visible on GregTech machines.")
        @Config.DefaultBoolean(true)
        public boolean coverTabsVisible;

        @Config.Comment("if true, puts the cover tabs display on the right of the UI instead of the left.")
        @Config.DefaultBoolean(false)
        public boolean coverTabsFlipped;

        @Config.Comment("How verbose should tooltips be? 0: disabled, 1: one-line, 2: normal, 3+: extended.")
        @Config.DefaultInt(2)
        public int tooltipVerbosity;

        @Config.Comment("How verbose should tooltips be when LSHIFT is held? 0: disabled, 1: one-line, 2: normal, 3+: extended.")
        @Config.DefaultInt(3)
        public int tooltipShiftVerbosity;

        @Config.Comment("Which style to use for title tab on machine GUI? 0: text tab split-dark, 1: text tab unified, 2: item icon tab.")
        @Config.DefaultInt(0)
        public int titleTabStyle;

        @Config.Comment("Which style should tooltip separator lines have? 0: empty line, 1: dashed line, 2+: continuous line.")
        @Config.DefaultInt(2)
        public int separatorStyle;

        @Config.Comment("Which style should tooltip finisher separator lines have? 0: no line, 1: empty line, 2: dashed line, 3+: continuous line.")
        @Config.DefaultInt(3)
        public int tooltipFinisherStyle;
    }

    @Config.LangKey("GT5U.gui.config.client.preference")
    public static class Preference {

        @Config.Comment("if true, input filter will initially be on when input buses are placed in the world.")
        @Config.DefaultBoolean(false)
        public boolean inputBusInitialFilter;

        @Config.Comment("if true, input filter will initially be on when input hatches are placed in the world.")
        @Config.DefaultBoolean(false)
        public boolean inputHatchInitialFilter;

        @Config.Comment("if true, allow multistacks on single blocks by default when they are first placed in the world.")
        @Config.DefaultBoolean(false)
        public boolean singleBlockInitialAllowMultiStack;

        @Config.Comment("if true, input filter will initially be on when machines are placed in the world.")
        @Config.DefaultBoolean(false)
        public boolean singleBlockInitialFilter;

        @Config.Comment("If true, scrolling up while hovering a ghost circuit in a machine UI will increment the circuit number.")
        @Config.DefaultBoolean(false)
        public boolean invertCircuitScrollDirection;

        @Config.Comment({
            "Overrides the MC total playable sounds limit. MC's default is 28, which causes problems with many machine sounds at once",
            "If sounds are causing large amounts of lag, try lowering this.",
            "If sounds are not working at all, try setting this to the lowest value (28).", "Default: 512" })
        @Config.RangeInt(min = 28, max = 2048)
        @Config.RequiresMcRestart
        public int maxNumSounds = 512;
    }

    @Config.LangKey("GT5U.gui.config.client.blockoverlay")
    public static class BlockOverlay {

        @Config.Comment("The line width of the block overlay")
        @Config.DefaultFloat(2.5f)
        @Config.RangeFloat(min = 0, max = 30f)
        public float lineWidth;

        @Config.Comment("The red color of the block overlay")
        @Config.DefaultInt(0)
        @Config.RangeInt(min = 0, max = 255)
        public int red;

        @Config.Comment("The green color of the block overlay")
        @Config.DefaultInt(0)
        @Config.RangeInt(min = 0, max = 255)
        public int green;

        @Config.Comment("The blue color of the block overlay")
        @Config.DefaultInt(0)
        @Config.RangeInt(min = 0, max = 255)
        public int blue;

        @Config.Comment("The alpha for the color of the block overlay")
        @Config.DefaultInt(127)
        @Config.RangeInt(min = 0, max = 255)
        public int alpha;
    }

    @Config.LangKey("GT5U.gui.config.client.render")
    public static class Render {

        @Config.Comment("if true, enables ambient-occlusion smooth lighting on tiles.")
        @Config.DefaultBoolean(true)
        public boolean renderTileAmbientOcclusion;

        @Config.Comment("if true, enables glowing of the machine controllers.")
        @Config.DefaultBoolean(true)
        public boolean renderGlowTextures;

        @Config.Comment("if true, render flipped machine with flipped textures.")
        @Config.DefaultBoolean(true)
        public boolean renderFlippedMachinesFlipped;

        @Config.Comment("if true, render indicators on hatches.")
        @Config.DefaultBoolean(true)
        public boolean renderIndicatorsOnHatch;

        @Config.Comment("if true, enables dirt particles when pollution reaches the threshold.")
        @Config.DefaultBoolean(true)
        public boolean renderDirtParticles;

        @Config.Comment("if true, enables pollution fog when pollution reaches the threshold.")
        @Config.DefaultBoolean(true)
        public boolean renderPollutionFog;

        @Config.Comment("if true, enables the green -> red durability for an item's damage value.")
        @Config.DefaultBoolean(true)
        public boolean renderItemDurabilityBar;

        @Config.Comment("if true, enables the blue charge bar for an electric item's charge.")
        @Config.DefaultBoolean(true)
        public boolean renderItemChargeBar;

        @Config.Comment("enables BaseMetaTileEntity block updates handled by BlockUpdateHandler.")
        @Config.DefaultBoolean(false)
        public boolean useBlockUpdateHandler;

        @Config.Comment("Disables coil lighting. Requires world reload (f3 + a or relog).")
        @Config.DefaultBoolean(false)
        @Config.Name("Use Old Coil Textures")
        public boolean useOldCoils;

        @Config.Comment("Render lines to MagLev Pylons when tethering")
        @Config.DefaultBoolean(true)
        @Config.Name("Render MagLev Tethers")
        public boolean renderMagLevTethers;

        @Config.Comment("Enables or disables Trans Metal rendering, also impacts motors, pistons etc with same rendering. Accessibility option.")
        @Config.DefaultBoolean(true)
        public boolean renderTransMetalFancy;
    }

    @Config.LangKey("GT5U.gui.config.client.waila")
    public static class Waila {

        /**
         * This enables showing voltage tier of transformer for Waila, instead of raw voltage number
         */
        @Config.Comment("if true, enables showing voltage tier of transformer for Waila, instead of raw voltage number.")
        @Config.DefaultBoolean(true)
        public boolean wailaTransformerVoltageTier;

        @Config.Comment("if true, enables showing voltage tier of transformer for Waila, instead of raw voltage number.")
        @Config.DefaultBoolean(false)
        public boolean wailaAverageNS;
    }

    @Config.LangKey("GT5U.gui.config.client.nei")
    public static class NEI {

        @Config.Comment("Recipe category section")
        public final RecipeCategories recipeCategories = new RecipeCategories();

        @Config.Comment("if true, shows the recipes using seconds (as opposed to ticks).")
        @Config.DefaultBoolean(true)
        public boolean NEIRecipeSecondMode;

        @Config.Comment("if true, shows the mod which added the recipe.")
        @Config.DefaultBoolean(false)
        public boolean NEIRecipeOwner;

        @Config.Comment("if true, show the stacktrace related to the recipe addition.")
        @Config.DefaultBoolean(false)
        public boolean NEIRecipeOwnerStackTrace;

        @Config.Comment("if true, show original voltage when overclocked.")
        @Config.DefaultBoolean(false)
        public boolean NEIOriginalVoltage;

        @Config.LangKey("GT5U.gui.config.client.nei.recipe_categories")
        public static class RecipeCategories {

            @Config.LangKey("gt.recipe.category.arc_furnace_recycling")
            @Config.DefaultEnum("ENABLE")
            public RecipeCategorySetting arcFurnaceRecycling = ENABLE;

            @Config.LangKey("gt.recipe.category.macerator_recycling")
            @Config.DefaultEnum("ENABLE")
            public RecipeCategorySetting maceratorRecycling = ENABLE;

            @Config.LangKey("gt.recipe.category.fluid_extractor_recycling")
            @Config.DefaultEnum("ENABLE")
            public RecipeCategorySetting fluidExtractorRecycling = ENABLE;

            @Config.LangKey("gt.recipe.category.alloy_smelter_recycling")
            @Config.DefaultEnum("ENABLE")
            public RecipeCategorySetting alloySmelterRecycling = ENABLE;

            @Config.LangKey("gt.recipe.category.alloy_smelter_molding")
            @Config.DefaultEnum("ENABLE")
            public RecipeCategorySetting alloySmelterMolding = ENABLE;

            @Config.LangKey("gt.recipe.category.forge_hammer_recycling")
            @Config.DefaultEnum("ENABLE")
            public RecipeCategorySetting forgeHammerRecycling = ENABLE;

            @Config.LangKey("gt.recipe.category.tic_part_extruding")
            @Config.DefaultEnum("ENABLE")
            public RecipeCategorySetting ticPartExtruding = ENABLE;

            @Config.LangKey("gt.recipe.category.tic_bolt_molding")
            @Config.DefaultEnum("ENABLE")
            public RecipeCategorySetting ticBoltMolding = ENABLE;

            @Config.LangKey("gtpp.recipe.category.abs_non_alloy_recipes")
            @Config.DefaultEnum("ENABLE")
            public RecipeCategorySetting absNonAlloyRecipes = ENABLE;
        }
    }
}
