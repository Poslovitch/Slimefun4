package me.mrCookieSlime.Slimefun.Objects.SlimefunItem;

import me.mrCookieSlime.Slimefun.Lists.Categories;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;

import org.bukkit.inventory.ItemStack;

/**
 * Subclass of SlimefunItem. 
 * <br>
 * Represents an alloy that is only craftable in the {@link RecipeType#SMELTERY}.
 * <p>
 * See {@link SlimefunItem} for the complete documentation.
 * 
 * @author TheBusyBiscuit
 * @since 4.0
 * @see SlimefunItem
 */
public class Alloy extends SlimefunItem {

	/**
	 * Constructor for an Alloy.
	 * <p>
	 * It automatically defines {@link Category} to {@link Categories#RESOURCES} and {@link RecipeType} to {@link RecipeType#SMELTERY}.
	 * <p>
	 * See {@link SlimefunItem} for more information about creating an item.
	 * 
	 * @param item ItemStack for the Alloy
	 * @param id
	 * @param recipe
	 * 
	 * @since 4.0
	 * @see #Alloy(Category, ItemStack, String, ItemStack[])
	 */
	public Alloy(ItemStack item, String id, ItemStack[] recipe) {
		super(Categories.RESOURCES, item, id, RecipeType.SMELTERY, recipe);
	}
	
	/**
	 * Constructor for an Alloy.
	 * <p>
	 * It automatically defines {@link RecipeType} to {@link RecipeType#SMELTERY}.
	 * <p>
	 * See {@link SlimefunItem} for more information about creating an item.
	 * 
	 * @param category
	 * @param item
	 * @param id
	 * @param recipe
	 * 
	 * @since 4.0
	 * @see #Alloy(ItemStack, String, ItemStack[])
	 */
	public Alloy(Category category, ItemStack item, String id, ItemStack[] recipe) {
		super(category, item, id, RecipeType.SMELTERY, recipe);
	}

}
