package de.thejeterlp.InventoryTweaks.events;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class ItemBreakListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onItemBreak(PlayerItemBreakEvent e) {
        Player p = e.getPlayer();
        ItemStack item = e.getBrokenItem();
        Material m = item.getType();

        PlayerInventory inv = p.getInventory();
        if (inv.contains(m, 2)) {
            for (ItemStack stack : inv.getContents()) {
                if (stack == null || stack.getType() != m) continue;
                //Ensure stack is the same material as the broken item here.

                if (stack.equals(item)) continue;
                inv.remove(stack);
                inv.setItem(inv.getHeldItemSlot(), stack);
                break;
            }
        }
    }

}
