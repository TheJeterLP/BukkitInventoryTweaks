package de.thejeterlp.InventoryTweaks.events.replaceItems;

import de.thejeterlp.InventoryTweaks.utils.Config;
import de.thejeterlp.InventoryTweaks.utils.Utils;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class BlockPlaceListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onBlockPlace(BlockPlaceEvent e) {
        if (e.isCancelled()) return;
        if (!Config.REPLACE_ITEMS_ON_BLOCK_PLACE.getBoolean()) return;
        Player p = e.getPlayer();
        if (p.getGameMode() == GameMode.CREATIVE && !Config.REPLACE_ITEMS_IN_CREATIVE.getBoolean()) return;
        Block placed = e.getBlockPlaced();
        ItemStack item = new ItemStack(placed.getType(), 1);
        PlayerInventory inv = p.getInventory();

        Utils.debug(e.getClass().getName() + " was fired! " + p.getName() + " Placed " + item);

        ItemStack held = inv.getItem(inv.getHeldItemSlot());
        Utils.debug("Item held before actually removing: " + held);
        if (held.getAmount() == 1) {
            held = null;
            Utils.debug("Setting held to null!");
        }


        //Ensure that we dropped everything of that itemstack
        if (held == null || held.getType() == Material.AIR) {
            ItemStack target = Utils.getMatchingItemIfExisting(item, inv);
            if (target == null) return;
            inv.setItem(inv.first(target), new ItemStack(Material.AIR));
            inv.setItem(inv.getHeldItemSlot(), target);
            Utils.playSound(p);
            Utils.debug("Matching Item found! Replacing with " + target);
        }
    }

}
