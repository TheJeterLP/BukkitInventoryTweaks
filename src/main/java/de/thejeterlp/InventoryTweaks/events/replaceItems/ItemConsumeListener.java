package de.thejeterlp.InventoryTweaks.events.replaceItems;

import de.thejeterlp.InventoryTweaks.utils.Config;
import de.thejeterlp.InventoryTweaks.utils.Utils;
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
        if(e.isCancelled()) return;
        if (!Config.REPLACE_ITEMS_ON_CONSUME.getBoolean()) return;
        Player p = e.getPlayer();
        if (p.getGameMode() == GameMode.CREATIVE && !Config.REPLACE_ITEMS_IN_CREATIVE.getBoolean()) return;
        ItemStack item = e.getItem();
        PlayerInventory inv = p.getInventory();

        Utils.debug(e.getClass().getName() + " was fired! " + p.getName() + " consumed " + item);

        if (item.getAmount() > 1) return;
        inv.setItem(inv.getHeldItemSlot(), new ItemStack(Material.AIR));
        ItemStack target = Utils.getMatchingItemIfExisting(item, inv);
        if (target == null) return;
        inv.setItem(inv.first(target), new ItemStack(Material.AIR));
        inv.setItem(inv.getHeldItemSlot(), target);
        Utils.playSound(p);
        Utils.debug("Matching Item found! Replacing with " + target);
    }

}
