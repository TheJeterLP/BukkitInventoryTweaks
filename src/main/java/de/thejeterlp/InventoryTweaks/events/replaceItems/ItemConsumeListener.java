package de.thejeterlp.InventoryTweaks.events.replaceItems;

import de.thejeterlp.InventoryTweaks.utils.Config;
import de.thejeterlp.InventoryTweaks.utils.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class ItemConsumeListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onItemConsume(PlayerItemConsumeEvent e) {
        if (!Config.REPLACE_ITEMS_ON_CONSUME.getBoolean()) return;
        Player p = e.getPlayer();
        ItemStack item = e.getItem();
        PlayerInventory inv = p.getInventory();

        Utils.debug(e.getClass().getName() + " was fired! " + p.getName() + " consumed " + item);

        if (item.getAmount() > 1) return;

        ItemStack target = Utils.getMatchingItemIfExisting(item, inv);
        if (target == null) return;
        inv.remove(target);
        inv.setItem(inv.getHeldItemSlot(), target);
        Utils.debug("Matching Item found! Replacing with " + target);
    }

}
