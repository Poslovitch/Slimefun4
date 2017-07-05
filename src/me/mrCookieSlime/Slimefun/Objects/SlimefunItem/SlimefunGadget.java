package me.mrCookieSlime.Slimefun.Objects.SlimefunItem;

import java.util.ArrayList;
import java.util.List;

import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;

import org.bukkit.inventory.ItemStack;

/**
 * 
 * @since 4.0
 */
public class SlimefunGadget extends SlimefunItem {
	
	private List<ItemStack[]> recipes;
	private List<ItemStack> displayRecipes;

	public SlimefunGadget(Category category, ItemStack item, String id, RecipeType recipeType, ItemStack[] recipe, ItemStack[] machineRecipes) {
		super(category, item, id, recipeType, recipe);
		
		recipes = new ArrayList<ItemStack[]>();
		displayRecipes = new ArrayList<ItemStack>();
		
		for (ItemStack i: machineRecipes) {
			recipes.add(new ItemStack[] {i});
			displayRecipes.add(i);
		}
	}
	
	public SlimefunGadget(Category category, ItemStack item, String id, RecipeType recipeType, ItemStack[] recipe, ItemStack[] machineRecipes, String[] keys, Object[] values) {
		super(category, item, id, recipeType, recipe, keys, values);
		
		recipes = new ArrayList<ItemStack[]>();
		displayRecipes = new ArrayList<ItemStack>();
		
		for (ItemStack i: machineRecipes) {
			recipes.add(new ItemStack[] {i});
			displayRecipes.add(i);
		}
	}
	
	public List<ItemStack[]> getRecipes() {
		return recipes;
	}
	
	public List<ItemStack> getDisplayRecipes() {
		return displayRecipes;
	}
	
	public void addRecipe(ItemStack input, ItemStack output) {
		recipes.add(new ItemStack[] {input});
		recipes.add(new ItemStack[] {output});
		displayRecipes.add(input);
		displayRecipes.add(output);
	}
}
