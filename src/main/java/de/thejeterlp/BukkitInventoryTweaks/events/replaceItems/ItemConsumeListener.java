package de.thejeterlp.BukkitInventoryTweaks.events.replaceItems;

import de.thejeterlp.BukkitInventoryTweaks.utils.Config;
import de.thejeterlp.BukkitInventoryTweaks.utils.Utils;
import org.bukkit.GameMode;
import org.bukkit.Material;
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
        Player p = e.getPlayer();
        if (e.isCancelled() || !Config.REPLACE_ITEMS_ON_CONSUME.getBoolean() || !p.hasPermission("bit.replaceitems.itemconsumed"))
            return;
        if (p.getGameMode() == GameMode.CREATIVE && !Config.REPLACE_ITEMS_IN_CREATIVE.getBoolean()) return;
        ItemStack item = e.getItem();
        PlayerInventory inv = p.getInventory();

        Utils.debug(e.getClass().getName() + " was fired! " + p.getName() + " consumed " + item);

        if (item.getAmount() > 1) return;

        ItemStack consumed = inv.getItem(e.getHand());
        if (consumed == null) {
            return;
        }
        ItemStack cloneConsumed = consumed.clone();
        consumed.setType(Material.AIR);

        ItemStack target = Utils.getMatchingItemIfExisting(item, inv);
        if (target == null) return;
        ItemStack copy = target.clone();
        target.setType(Material.AIR);
        inv.setItem(e.getHand(), copy);
        Utils.playSound(p);
        Utils.debug("Matching Item found! Replacing " + cloneConsumed + " with " + copy);
    }

}
