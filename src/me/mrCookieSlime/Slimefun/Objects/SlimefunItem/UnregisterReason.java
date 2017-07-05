package me.mrCookieSlime.Slimefun.Objects.SlimefunItem;

import me.mrCookieSlime.Slimefun.Objects.SlimefunBlockHandler;

/**
 * Defines how a block handled by Slimefun is being unregistered.
 * <p>
 * It is notably used by {@link SlimefunBlockHandler#onBreak(org.bukkit.entity.Player, org.bukkit.block.Block, SlimefunItem, UnregisterReason)}.
 * 
 * @since 4.0
 */
public enum UnregisterReason {

	/**
	 * An explosion destroys the block.
	 */
	EXPLODE,
	
	/**
	 * A player is breaking the block.
	 */
	PLAYER_BREAK;
	
}
