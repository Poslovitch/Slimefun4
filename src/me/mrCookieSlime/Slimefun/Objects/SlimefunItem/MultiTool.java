package me.mrCookieSlime.Slimefun.Objects.SlimefunItem;

import java.util.ArrayList;
import java.util.List;

import me.mrCookieSlime.Slimefun.Lists.Categories;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.api.Slimefun;

import org.bukkit.inventory.ItemStack;

/**
 * 
 * @author TheBusyBiscuit
 * @since 4.0
 */
public class MultiTool extends DamagableChargableItem {
	
	private List<Integer> modes;

	public MultiTool(ItemStack item, String id, RecipeType recipeType, ItemStack[] recipe, String[] keys, Object[] values) {
		super(Categories.TECH, item, id, recipeType, recipe, "Multi Tool", keys, values);
	}
	
	@Override
	public void create() {
		List<Integer> list = new ArrayList<Integer>();
		for (int i = 0; i < 50; i++) {
			if (Slimefun.getItemValue(getID(), "mode." + i + ".enabled") != null) {
				if ((Boolean) Slimefun.getItemValue(getID(), "mode." + i + ".enabled")) list.add(i);
			}
		}
		this.modes = list;
	}
	
	public List<Integer> getModes() {
		return modes;
	}

}
