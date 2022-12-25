package de.thejeterlp.BukkitInventoryTweaks.events.replaceItems;

import de.thejeterlp.BukkitInventoryTweaks.utils.Config;
import de.thejeterlp.BukkitInventoryTweaks.utils.Utils;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.inventory.ItemStack;

public class ItemBreakListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onItemBreak(PlayerItemBreakEvent e) {
        Player p = e.getPlayer();
        if (!Config.REPLACE_ITEMS_ON_BREAK.getBoolean() || !p.hasPermission("bit.replaceitems.itembreak")) return;
        if (p.getGameMode() == GameMode.CREATIVE && !Config.REPLACE_ITEMS_IN_CREATIVE.getBoolean()) return;
        ItemStack item = e.getBrokenItem();
        Utils.debug(e.getClass().getName() + " was fired! " + p.getName() + " broke " + item);
        Utils.replaceWithAnotherItem(p, item);
    }

}
