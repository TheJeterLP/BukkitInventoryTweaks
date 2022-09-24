package de.thejeterlp.InventoryTweaks.events.replaceItems;

import de.thejeterlp.InventoryTweaks.utils.Config;
import de.thejeterlp.InventoryTweaks.utils.Utils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class ItemBreakListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onItemBreak(PlayerItemBreakEvent e) {
        if (!Config.REPLACE_ITEMS_ON_BREAK.getBoolean()) return;
        Player p = e.getPlayer();
        ItemStack item = e.getBrokenItem();
        PlayerInventory inv = p.getInventory();
        inv.setItem(inv.getHeldItemSlot(), new ItemStack(Material.AIR));

        Utils.debug(e.getClass().getName() + " was fired! " + p.getName() + " broke " + item);

        if (item.getAmount() > 1) return;

        ItemStack target = Utils.getMatchingItemIfExisting(item, inv);
        if (target == null) return;
        inv.setItem(inv.first(target), new ItemStack(Material.AIR));
        inv.setItem(inv.getHeldItemSlot(), target);
        Utils.playSound(p);
        Utils.debug("Matching Item found! Replacing with " + target);
    }

}
