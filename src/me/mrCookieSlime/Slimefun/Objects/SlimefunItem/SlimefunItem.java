package me.mrCookieSlime.Slimefun.Objects.SlimefunItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import me.mrCookieSlime.Slimefun.SlimefunStartup;
import me.mrCookieSlime.Slimefun.AncientAltar.AltarRecipe;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.Objects.Research;
import me.mrCookieSlime.Slimefun.Objects.SlimefunBlockHandler;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.handlers.BlockTicker;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.handlers.ItemHandler;
import me.mrCookieSlime.Slimefun.Setup.SlimefunManager;
import me.mrCookieSlime.Slimefun.URID.URID;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.Slimefun;
import me.mrCookieSlime.Slimefun.api.energy.ChargableBlock;
import me.mrCookieSlime.Slimefun.api.energy.EnergyNet;
import me.mrCookieSlime.Slimefun.api.energy.EnergyNet.NetworkComponent;
import me.mrCookieSlime.Slimefun.api.energy.EnergyTicker;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

/**
 * TODO
 * @since 4.0
 */
public class SlimefunItem {
	
	/**
	 * Lists all the enabled Slimefun items.
	 */
	public static List<SlimefunItem> items = new ArrayList<SlimefunItem>();
	
	/**
	 * Stores the URID corresponding to an item ID.
	 * 
	 * @deprecated As of 4.1.10, renamed to {@link SlimefunItem#map_id} for better name convenience
	 */
	@Deprecated
	public static Map<String, URID> map_name = new HashMap<String, URID>();
	
	/**
	 * Stores the URID corresponding to an item ID.
	 * 
	 * @since 4.1.10, rename of {@linkplain SlimefunItem#map_name}
	 */
	public static Map<String, URID> map_id = new HashMap<String, URID>();
	
	/**
	 * Lists radioactive ItemStacks.
	 */
	public static List<ItemStack> radioactive = new ArrayList<ItemStack>();
	
	/**
	 * Counts the enabled Slimefun items that are not added by addons.
	 */
	public static int vanilla = 0;
	
	/**
	 * TODO
	 */
	public static Set<String> tickers = new HashSet<String>();
	
	/**
	 * Lists all Slimefun items (even disabled one).
	 */
	public static List<SlimefunItem> all = new ArrayList<SlimefunItem>();
	
	/**
	 * TODO
	 */
	public static Map<String, Set<ItemHandler>> handlers = new HashMap<String, Set<ItemHandler>>();
	
	/**
	 * TODO
	 */
	public static Map<String, SlimefunBlockHandler> blockhandler = new HashMap<String, SlimefunBlockHandler>();
	
	private ItemStack item;
	private Category category;
	private ItemStack recipeOutput;
	private ItemStack[] recipe;
	private RecipeType recipeType;
	private String id;
	private String[] keys;
	private Object[] values;
	private Research research;
	private boolean ghost, replacing, enchantable, disenchantable;
	private Set<ItemHandler> itemhandlers;
	private URID urid;
	private boolean ticking = false;
	private boolean addon = false;
	private BlockTicker ticker;
	private EnergyTicker energy;
	public String hash;
	
	private State state;
	
	/**
	 * Defines whether a SlimefunItem is enabled, disabled or fall-backed on its vanilla behavior.
	 * 
	 * @since 4.1.10
	 */
	public enum State {
		/**
		 * This SlimefunItem is enabled.
		 */
	    ENABLED,
	    
	    /**
	     * This SlimefunItem is disabled and is not a {@link VanillaItem}.
	     */
	    DISABLED,
	    
	    /**
	     * This SlimefunItem is fall-backed on its vanilla behavior, because it is disabled and is a {@link VanillaItem}.
	     */
	    VANILLA;
	}
	
	/**
	 * Returns the ItemStack corresponding to this SlimefunItem.
	 * 
	 * @return the ItemStack corresponding to this SlimefunItem
	 */
	public ItemStack getItem() 			{		return item; 			}
	
	/**
	 * Returns the Category this SlimefunItem is bound to.
	 * 
	 * @return the Category this SlimefunItem is bound to
	 */
	public Category getCategory()		{		return category;		}
	
	/**
	 * Returns the custom recipe output of this SlimefunItem.
	 * 
	 * @return the custom recipe output of this SlimefunItem
	 */
	public ItemStack getCustomOutput()	{		return recipeOutput;	}
	
	/**
	 * TODO
	 * @return
	 */
	public ItemStack[] getRecipe()		{		return recipe;			}
	
	/**
	 * TODO
	 * @return
	 */
	public RecipeType getRecipeType()	{		return recipeType;		}
	
	/**
	 * Returns the ID of this SlimefunItem.
	 * 
	 * @return the ID of this SlimefunItem
	 * 
	 * @deprecated As of 4.1.10, renamed to {@link SlimefunItem#getID()} for better name convenience
	 */
	@Deprecated
	public String getName()				{		return id;				}
	
	/**
	 * Returns the ID of this SlimefunItem.
	 * 
	 * @return the ID of this SlimefunItem
	 * 
	 * @since 4.1.10, rename of {@link SlimefunItem#getName()}
	 */
	public String getID() 				{		return id;				}
	
	/**
	 * TODO
	 * @return
	 */
	public String[] listKeys()			{		return keys;			}
	
	/**
	 * TODO
	 * @return
	 */
	public Object[] listValues()		{		return values;			}
	
	/**
	 * Returns the Research this SlimefunItem is bound to.
	 * 
	 * @return the Research this SlimefunItem is bound to
	 */
	public Research getResearch()		{		return research;		}
	
	/**
	 * TODO
	 * @return
	 */
	public Set<ItemHandler> getHandlers() {
		return itemhandlers;
	}
	
	/**
	 * TODO
	 * 
	 * @param category
	 * @param item
	 * @param id
	 * @param recipeType
	 * @param recipe
	 */
	public SlimefunItem(Category category, ItemStack item, String id, RecipeType recipeType, ItemStack[] recipe) {
		this.item = item;
		this.category = category;
		this.id = id;
		this.recipeType = recipeType;
		this.recipe = recipe;
		this.recipeOutput = null;
		this.keys = null;
		this.values = null;
		this.ghost = false;
		this.replacing = false;
		this.enchantable = true;
		this.disenchantable = true;
		itemhandlers = new HashSet<ItemHandler>();
		
		urid = URID.nextURID(this, false);
	}
	
	/**
	 * TODO
	 * 
	 * @param category
	 * @param item
	 * @param id
	 * @param recipeType
	 * @param recipe
	 * @param recipeOutput
	 */
	public SlimefunItem(Category category, ItemStack item, String id, RecipeType recipeType, ItemStack[] recipe, ItemStack recipeOutput) {
		this.item = item;
		this.category = category;
		this.id = id;
		this.recipeType = recipeType;
		this.recipe = recipe;
		this.recipeOutput = recipeOutput;
		this.keys = null;
		this.values = null;
		this.ghost = false;
		this.replacing = false;
		this.enchantable = true;
		this.disenchantable = true;
		itemhandlers = new HashSet<ItemHandler>();
		
		urid = URID.nextURID(this, false);
	}
	
	/**
	 * TODO
	 * 
	 * @param category
	 * @param item
	 * @param id
	 * @param recipeType
	 * @param recipe
	 * @param recipeOutput
	 * @param keys
	 * @param values
	 */
	public SlimefunItem(Category category, ItemStack item, String id, RecipeType recipeType, ItemStack[] recipe, ItemStack recipeOutput, String[] keys, Object[] values) {
		this.item = item;
		this.category = category;
		this.id = id;
		this.recipeType = recipeType;
		this.recipe = recipe;
		this.recipeOutput = recipeOutput;
		this.keys = keys;
		this.values = values;
		this.ghost = false;
		this.replacing = false;
		this.enchantable = true;
		this.disenchantable = true;
		itemhandlers = new HashSet<ItemHandler>();
		
		urid = URID.nextURID(this, false);
	}
	
	/**
	 * TODO
	 * 
	 * @param category
	 * @param item
	 * @param id
	 * @param recipeType
	 * @param recipe
	 * @param keys
	 * @param values
	 */
	public SlimefunItem(Category category, ItemStack item, String id, RecipeType recipeType, ItemStack[] recipe, String[] keys, Object[] values) {
		this.item = item;
		this.category = category;
		this.id = id;
		this.recipeType = recipeType;
		this.recipe = recipe;
		this.recipeOutput = null;
		this.keys = keys;
		this.values = values;
		this.ghost = false;
		this.replacing = false;
		this.enchantable = true;
		this.disenchantable = true;
		itemhandlers = new HashSet<ItemHandler>();
		
		urid = URID.nextURID(this, false);
	}
	
	/**
	 * TODO
	 * 
	 * @param category
	 * @param item
	 * @param id
	 * @param recipeType
	 * @param recipe
	 * @param ghost
	 */
	public SlimefunItem(Category category, ItemStack item, String id, RecipeType recipeType, ItemStack[] recipe, boolean ghost) {
		this.item = item;
		this.category = category;
		this.id = id;
		this.recipeType = recipeType;
		this.recipe = recipe;
		this.recipeOutput = null;
		this.keys = null;
		this.values = null;
		this.ghost = ghost;
		this.replacing = false;
		this.enchantable = true;
		this.disenchantable = true;
		itemhandlers = new HashSet<ItemHandler>();
		
		urid = URID.nextURID(this, false);
	}
	
	/**
	 * TODO
	 */
	public void register() {
		register(false);
	}
	
	/**
	 * TODO
	 * 
	 * @param slimefun
	 */
	public void register(boolean slimefun) {
		addon = !slimefun;
		try {
			if (recipe.length < 9) recipe = new ItemStack[] {null, null, null, null, null, null, null, null, null};
			all.add(this);
			
			SlimefunStartup.getItemCfg().setDefaultValue(id + ".enabled", true);
			SlimefunStartup.getItemCfg().setDefaultValue(id + ".can-be-used-in-workbenches", replacing);
			SlimefunStartup.getItemCfg().setDefaultValue(id + ".allow-enchanting", enchantable);
			SlimefunStartup.getItemCfg().setDefaultValue(id + ".allow-disenchanting", disenchantable);
			SlimefunStartup.getItemCfg().setDefaultValue(id + ".required-permission", "");
			if (keys != null && values != null) {
				for (int i = 0; i < keys.length; i++) {
					SlimefunStartup.getItemCfg().setDefaultValue(id + "." + keys[i], values[i]);
				}
			}
			
			for (World world: Bukkit.getWorlds()) {
				SlimefunStartup.getWhitelist().setDefaultValue(world.getName() + ".enabled", true);
				SlimefunStartup.getWhitelist().setDefaultValue(world.getName() + ".enabled-items." + id, true);
			}
			
			if (this.isTicking() && !SlimefunStartup.getCfg().getBoolean("URID.enable-tickers")) {
			    state = State.DISABLED;
			    return;
			}
			
			if (SlimefunStartup.getItemCfg().getBoolean(id + ".enabled")) {
				if (!Category.list().contains(category)) category.register();
				
				state = State.ENABLED;
				
				replacing = SlimefunStartup.getItemCfg().getBoolean(id + ".can-be-used-in-workbenches");
				enchantable = SlimefunStartup.getItemCfg().getBoolean(id + ".allow-enchanting");
				disenchantable = SlimefunStartup.getItemCfg().getBoolean(id + ".allow-disenchanting");
				
				items.add(this);
				if (slimefun) vanilla++;
				map_id.put(this.getID(), this.getURID());
				
				create();
				
				for (ItemHandler handler: itemhandlers) {
					Set<ItemHandler> handlerset = getHandlers(handler.toCodename());
					handlerset.add(handler);
					handlers.put(handler.toCodename(), handlerset);
				}
				
				if (SlimefunStartup.getCfg().getBoolean("options.print-out-loading")) System.out.println("[Slimefun] Loaded Item \"" + id + "\"");
			} else {
			    if (this instanceof VanillaItem) state = State.VANILLA;
			    else state = State.DISABLED;
			}
		} catch(Exception x) {
			System.err.println("[Slimefun] Item Registration failed: " + id);
		}
	}
	
	/**
	 * TODO
	 * Returns the list of enabled Slimefun items.
	 * 
	 * @return the list of enabled Slimefun items
	 */
	public static List<SlimefunItem> list() {
		return items;
	}
	
	/**
	 * TODO
	 * 
	 * @param research
	 */
	public void bindToResearch(Research research) {
		if (research != null) research.getEffectedItems().add(this);
		this.research = research;
	}
	
	/**
	 * TODO
	 * 
	 * @param recipe
	 */
	public void setRecipe(ItemStack[] recipe) {
		this.recipe = recipe;
	}
	
	/**
	 * TODO
	 * 
	 * @param type
	 */
	public void setRecipeType(RecipeType type) {
		this.recipeType = type;
	}
	
	/**
	 * TODO
	 * 
	 * @param category
	 */
	public void setCategory(Category category) {
		this.category = category;
	}
	
	/**
	 * TODO
	 * 
	 * @param output
	 */
	public void setRecipeOutput(ItemStack output) {
		this.recipeOutput = output;
	}
	
	/**
	 * TODO
	 * 
	 * @param name
	 * @return
	 * 
	 * @deprecated As of 4.1.10, renamed to {@link SlimefunItem#getByID(String)} for better name convenience
	 */
	@Deprecated
	public static SlimefunItem getByName(String name) {
		return (SlimefunItem) URID.decode(map_id.get(name));
	}
	
	/**
	 * TODO
	 * 
	 * @param id
	 * @return
	 * 
	 * @since 4.1.10, rename of {@link SlimefunItem#getByName(String)}
	 */
	public static SlimefunItem getByID(String id) {
		return (SlimefunItem) URID.decode(map_id.get(id));
	}
	
	/**
	 * TODO
	 * 
	 * @param item
	 * @return
	 */
	public static SlimefunItem getByItem(ItemStack item) {
		if (item == null) return null;
		for (SlimefunItem sfi: items) {
			if (sfi instanceof ChargableItem && SlimefunManager.isItemSimiliar(item, sfi.getItem(), false)) return sfi;
			else if (sfi instanceof DamagableChargableItem && SlimefunManager.isItemSimiliar(item, sfi.getItem(), false)) return sfi;
			else if (sfi instanceof ChargedItem && SlimefunManager.isItemSimiliar(item, sfi.getItem(), false)) return sfi;
			else if (sfi instanceof SlimefunBackpack && SlimefunManager.isItemSimiliar(item, sfi.getItem(), false)) return sfi;
			else if (SlimefunManager.isItemSimiliar(item, sfi.getItem(), true)) return sfi;
		}
		return null;
	}
	
	/**
	 * TODO
	 * 
	 * @param item
	 * @return
	 */
	public boolean isItem(ItemStack item) {
		if (item == null) return false;
		if (this instanceof ChargableItem && SlimefunManager.isItemSimiliar(item, this.getItem(), false)) return true;
		else if (this instanceof DamagableChargableItem && SlimefunManager.isItemSimiliar(item, this.getItem(), false)) return true;
		else if (this instanceof ChargedItem && SlimefunManager.isItemSimiliar(item, this.getItem(), false)) return true;
		else if (SlimefunManager.isItemSimiliar(item, this.getItem(), true)) return true;
		else return false;
	}
	
	/**
	 * TODO
	 */
	public void load() {
		try {
			if (!ghost) category.add(this);
			ItemStack output = item.clone();
			if (recipeOutput != null) output = recipeOutput.clone();
			
			if (recipeType.toItem().isSimilar(RecipeType.NULL.toItem()));
			else if (recipeType.toItem().isSimilar(RecipeType.MOB_DROP.toItem())) {
				try {
					EntityType entity = EntityType.valueOf(ChatColor.stripColor(recipe[4].getItemMeta().getDisplayName()).toUpperCase().replace(" ", "_"));
					List<ItemStack> dropping = new ArrayList<ItemStack>();
					if (SlimefunManager.drops.containsKey(entity)) dropping = SlimefunManager.drops.get(entity);
					dropping.add(output);
					SlimefunManager.drops.put(entity, dropping);
				} catch(Exception x) {
				}
			}
			else if (recipeType.toItem().isSimilar(RecipeType.ANCIENT_ALTAR.toItem())) {
				new AltarRecipe(Arrays.asList(recipe), output);
			}
			else if (recipeType.getMachine() != null && getByID(recipeType.getMachine().getID()) instanceof SlimefunMachine) {
				((SlimefunMachine) getByID(recipeType.getMachine().getID())).addRecipe(recipe, output);
			}
			install();
		} catch(Exception x) {
			System.err.println("[Slimefun] Item Initialization failed: " + id);
		}
	}
	
	/**
	 * TODO
	 * 
	 * @param item
	 * @return
	 * 
	 * @since 4.1.10
	 */
	public static State getState(ItemStack item) {
	    for (SlimefunItem i: all) {
            if (i.isItem(item)) {
                return i.getState();
            }
        }
        return State.ENABLED;
	}
	
	/**
	 * TODO
	 * 
	 * @param item
	 * @return
	 * 
	 * @since 4.1.10
	 */
	public static boolean isDisabled(ItemStack item) {
	    for (SlimefunItem i: all) {
			if (i.isItem(item)) {
				return i.isDisabled();
			}
		}
	    return false;
	}
	
	/**
	 * TODO
	 * 
	 * @return
	 * 
	 * @since 4.1.10
	 */
	public State getState(){
	    return state;
	}
	
	/**
	 * TODO
	 * 
	 * @return
	 * 
	 * @since 4.1.10
	 */
	public boolean isDisabled(){
	    return state != State.ENABLED;
	}
	
	/**
	 * TODO
	 */
	public void install() {}
	
	/**
	 * TODO
	 */
	public void create()  {}
	
	/**
	 * TODO
	 * 
	 * @return
	 */
	public boolean isReplacing() {
		return replacing;
	}
	
	/**
	 * TODO
	 * 
	 * @return
	 */
	public boolean isEnchantable() {
	    return enchantable;
	}
	
	/**
	 * TODO
	 * 
	 * @return
	 * 
	 * @since 4.1.10
	 */
	public boolean isDisenchantable() {
		return disenchantable;
	}
	
	/**
	 * TODO
	 * 
	 * @param replacing
	 */
	public void setReplacing(boolean replacing) {
		this.replacing = replacing;
	}
	
	/**
	 * TODO
	 * 
	 * @param handler
	 */
	public void addItemHandler(ItemHandler... handler) {
		itemhandlers.addAll(Arrays.asList(handler));
		
		for (ItemHandler h: handler) {
			if (h instanceof BlockTicker) {
				ticking = true;
				tickers.add(getID());
				ticker = (BlockTicker) h;
			}
			else if (h instanceof EnergyTicker) {
				energy = (EnergyTicker) h;
				EnergyNet.registerComponent(getID(), NetworkComponent.SOURCE);
			}
		}
	}
	
	/**
	 * TODO
	 * 
	 * @param vanilla
	 * @param handlers
	 */
	public void register(boolean vanilla, ItemHandler... handlers) {
		addItemHandler(handlers);
		register(vanilla);
	}
	
	/**
	 * TODO
	 * 
	 * @param handlers
	 */
	public void register(ItemHandler... handlers) {
		addItemHandler(handlers);
		register(false);
	}
	
	/**
	 * TODO
	 * 
	 * @param vanilla
	 * @param handler
	 */
	public void register(boolean vanilla, SlimefunBlockHandler handler) {
		blockhandler.put(getID(), handler);
		register(vanilla);
	}
	
	/**
	 * TODO
	 * 
	 * @param handler
	 */
	public void register(SlimefunBlockHandler handler) {
		blockhandler.put(getID(), handler);
		register(false);
	}
	
	/**
	 * TODO
	 * 
	 * @param codename
	 * @return
	 */
	public static Set<ItemHandler> getHandlers(String codename) {
		if (handlers.containsKey(codename)) return handlers.get(codename);
		else return new HashSet<ItemHandler>();
	}

	/**
	 * TODO
	 * 
	 * @param item
	 */
	public static void setRadioactive(ItemStack item) {
		radioactive.add(item);
	}
	
	/**
	 * TODO
	 * 
	 * @param id
	 * @return
	 */
	public static ItemStack getItem(String id) {
		SlimefunItem item = getByID(id);
		return item != null ? item.getItem(): null;
	}
	
	/**
	 * TODO
	 * 
	 * @param id
	 * @param stack
	 */
	public static void patchExistingItem(String id, ItemStack stack) {
		SlimefunItem item = getByID(id);
		if (item != null) {
			System.out.println("[Slimefun] WARNING - Patching existing Item - " + id);
			System.out.println("[Slimefun] This might take a while");
			
			final ItemStack old = item.getItem();
			item.setItem(stack);
			for (SlimefunItem sfi: list()) {
				ItemStack[] recipe = sfi.getRecipe();
				for (int i = 0; i < 9; i++) {
					if (SlimefunManager.isItemSimiliar(recipe[i], old, true)) recipe[i] = stack;
				}
				sfi.setRecipe(recipe);
			}
		}
	}
	
	/**
	 * TODO
	 * 
	 * @param capacity
	 */
	public void registerChargeableBlock(int capacity) {
		registerChargeableBlock(false, capacity);
	}
	
	/**
	 * TODO
	 * 
	 * @param slimefun
	 * @param capacity
	 */
	public void registerChargeableBlock(boolean slimefun, int capacity) {
		register(slimefun);
		ChargableBlock.registerChargableBlock(id, capacity, true);
		EnergyNet.registerComponent(id, NetworkComponent.CONSUMER);
	}
	
	/**
	 * TODO
	 * 
	 * @param slimefun
	 * @param capacity
	 */
	public void registerUnrechargeableBlock(boolean slimefun, int capacity) {
		register(slimefun);
		ChargableBlock.registerChargableBlock(id, capacity, false);
	}
	
	/**
	 * TODO
	 * 
	 * @param slimefun
	 * @param capacity
	 */
	public void registerBlockCapacitor(boolean slimefun, int capacity) {
		register(slimefun);
		ChargableBlock.registerCapacitor(id, capacity);
	}
	
	/**
	 * TODO
	 * 
	 * @param slimefun
	 */
	public void registerEnergyDistributor(boolean slimefun) {
		register(slimefun);
		EnergyNet.registerComponent(id, NetworkComponent.DISTRIBUTOR);
	}
	
	/**
	 * TODO
	 * 
	 * @param slimefun
	 * @param capacity
	 * 
	 * @deprecated As of 4.1.10, renamed to {@link SlimefunItem#registerDistributingCapacitor(boolean, int)} due to a typing mistake
	 */
	@Deprecated
	public void registerDistibutingCapacitor(boolean slimefun, final int capacity) {
		register(slimefun);
		EnergyNet.registerComponent(id, NetworkComponent.DISTRIBUTOR);
		ChargableBlock.registerCapacitor(id, capacity);
	}
	
	/**
	 * TODO
	 * 
	 * @param slimefun
	 * @param capacity
	 * 
	 * @since 4.1.10, rename of {@link SlimefunItem#registerDistibutingCapacitor(boolean, int)}
	 */
	public void registerDistributingCapacitor(boolean slimefun, final int capacity) {
		register(slimefun);
		EnergyNet.registerComponent(id, NetworkComponent.DISTRIBUTOR);
		ChargableBlock.registerCapacitor(id, capacity);
	}
	
	/**
	 * TODO
	 * 
	 * @param stack
	 */
	protected void setItem(ItemStack stack) {
		this.item = stack;
	}
	
	/**
	 * TODO
	 * 
	 * @return
	 */
	public URID getURID() {
		return urid;
	}
	
	/**
	 * TODO
	 * 
	 * @return
	 */
	public boolean isTicking() {
		return ticking;
	}
	
	/**
	 * TODO
	 * 
	 * @param item
	 * @return
	 */
	public static boolean isTicking(String item) {
		return tickers.contains(item);
	}
	
	/**
	 * TODO
	 * 
	 * @return
	 */
	public BlockTicker getTicker() {
		return ticker;
	}
	
	/**
	 * TODO
	 * 
	 * @param id
	 * @param handler
	 */
	public static void registerBlockHandler(String id, SlimefunBlockHandler handler) {
		blockhandler.put(id, handler);
	}

	/**
	 * TODO
	 * 
	 * @param vanilla
	 * @param capacity
	 * @param handlers
	 */
	public void registerChargeableBlock(boolean vanilla, int capacity, ItemHandler... handlers) {
		addItemHandler(handlers);
		registerChargeableBlock(vanilla, capacity);
	}

	/**
	 * TODO
	 */
	public EnergyTicker getEnergyTicker() {
		return energy;
	}
	
	/**
	 * TODO
	 * 
	 * @param b
	 * @return
	 */
	public BlockMenu getBlockMenu(Block b) {
		return BlockStorage.getInventory(b);
	}
	
	/**
	 * TODO
	 * 
	 * @param page
	 */
	public void addWikipage(String page) {
		Slimefun.addWikiPage(id, "https://github.com/mrCookieSlime/Slimefun4/wiki/" + page);
	}
	
	/**
	 * Returns true if this SlimefunItem has been registered by an addon.
	 * 
	 * @return true if this SlimefunItem has been registered by an addon;
	 *         false otherwise
	 */
	public boolean isAddonItem() {
		return addon;
	}
}
