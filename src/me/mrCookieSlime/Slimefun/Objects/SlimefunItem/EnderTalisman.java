package me.mrCookieSlime.Slimefun.Objects.SlimefunItem;

import me.mrCookieSlime.Slimefun.Lists.Categories;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.api.Slimefun;

import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

/**
 * 
 * @since 4.0
 */
public class EnderTalisman extends SlimefunItem {
	
	private boolean consumed;
	private boolean cancel;
	private PotionEffect[] effects;
	private String suffix;
	private int chance;

	public EnderTalisman(Talisman parent) {
		super(Categories.TALISMANS_2, parent.upgrade(), "ENDER_" + parent.getID(), RecipeType.MAGIC_WORKBENCH, new ItemStack[] {SlimefunItem.getItem("ENDER_LUMP_3"), null, SlimefunItem.getItem("ENDER_LUMP_3"), null, parent.getItem(), null, SlimefunItem.getItem("ENDER_LUMP_3"), null, SlimefunItem.getItem("ENDER_LUMP_3")}, parent.upgrade());
		this.consumed = parent.isConsumable();
		this.cancel = parent.isEventCancelled();
		this.suffix = parent.getSuffix();
		this.effects = parent.getEffects();
		this.chance = parent.getChance();
		Slimefun.addDescription("ENDER_" + parent.getID(), "&eEnder Talismans have the advantage", "&eof still working while they", "&eare in your Ender Chest");
	}
	
	public PotionEffect[] getEffects() {
		return effects;
	}
	
	public boolean isConsumable() {
		return consumed;
	}
	
	public boolean isEventCancelled() {
		return cancel;
	}
	
	public String getSuffix() {	
		return suffix;	
	}
	
	public int getChance() {
		return chance;
	}
}
