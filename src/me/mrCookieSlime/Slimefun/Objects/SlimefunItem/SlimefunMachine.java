package me.mrCookieSlime.Slimefun.Objects.SlimefunItem;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.Objects.MultiBlock;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * 
 * @author TheBusyBiscuit
 * @since 4.0
 */
public class SlimefunMachine extends SlimefunItem {
	
	private List<ItemStack[]> recipes;
	private List<ItemStack> shownRecipes;
	private Material trigger;

	public SlimefunMachine(Category category, ItemStack item, String id, ItemStack[] recipe, ItemStack[] machineRecipes, Material trigger) {
		super(category, item, id, RecipeType.MULTIBLOCK, recipe);
		
		recipes = new ArrayList<ItemStack[]>();
		shownRecipes = new ArrayList<ItemStack>();
		
		for (ItemStack i: machineRecipes) {
			shownRecipes.add(i);
		}
		
		this.trigger = trigger;
	}
	
	public SlimefunMachine(Category category, ItemStack item, String id, ItemStack[] recipe, ItemStack[] machineRecipes, Material trigger, boolean ghost) {
		super(category, item, id, RecipeType.MULTIBLOCK, recipe, ghost);
		
		recipes = new ArrayList<ItemStack[]>();
		shownRecipes = new ArrayList<ItemStack>();
		
		for (ItemStack i: machineRecipes) {
			shownRecipes.add(i);
		}
		
		this.trigger = trigger;
	}
	
	public SlimefunMachine(Category category, ItemStack item, String id, ItemStack[] recipe, ItemStack[] machineRecipes, Material trigger, String[] keys, Object[] values) {
		super(category, item, id, RecipeType.MULTIBLOCK, recipe, keys, values);
		
		recipes = new ArrayList<ItemStack[]>();
		shownRecipes = new ArrayList<ItemStack>();
		
		for (ItemStack i: machineRecipes) {
			shownRecipes.add(i);
		}
		
		this.trigger = trigger;
	}
	
	public List<ItemStack[]> getRecipes() {
		return recipes;
	}
	
	public List<ItemStack> getDisplayRecipes() {
		return shownRecipes;
	}
	
	public void addRecipe(ItemStack[] input, ItemStack output) {
		recipes.add(input);
		recipes.add(new ItemStack[] {output});
	}
	
	@Override
	public void create() {
		toMultiBlock().register();
	}
	
	@Override
	public void install() {
		for (ItemStack i: getDisplayRecipes()) {
			SlimefunItem item = SlimefunItem.getByItem(i);
			if (item == null) recipes.add(new ItemStack[] {i});
			else if (!SlimefunItem.isDisabled(i)) recipes.add(new ItemStack[] {i});
		}
	}
	
	public MultiBlock toMultiBlock() {
		List<Material> mats = new ArrayList<Material>();
		for (ItemStack i: getRecipe()) {
			if (i == null) mats.add(null);
			else if (i.getType() == Material.CAULDRON_ITEM) mats.add(Material.CAULDRON);
			else if (i.getType() == Material.FLINT_AND_STEEL) mats.add(Material.FIRE);
			else mats.add(i.getType());
		}
		Material[] build = mats.toArray(new Material[mats.size()]);
		return new MultiBlock(build, trigger);
	}
	
	public Iterator<ItemStack[]> recipeIterator() {
		return recipes.iterator();
	}

}
