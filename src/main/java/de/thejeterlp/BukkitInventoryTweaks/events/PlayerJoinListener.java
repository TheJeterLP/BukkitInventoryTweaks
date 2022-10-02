package de.thejeterlp.BukkitInventoryTweaks.events;

import de.jeter.updatechecker.Result;
import de.thejeterlp.BukkitInventoryTweaks.BukkitInventoryTweaks;
import de.thejeterlp.BukkitInventoryTweaks.utils.Config;
import de.thejeterlp.BukkitInventoryTweaks.utils.Locales;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        if (Config.CHECK_UPDATE.getBoolean() && e.getPlayer().hasPermission("bit.notifyupdate") && BukkitInventoryTweaks.getInstance().getUpdateChecker() != null) {
            if (BukkitInventoryTweaks.getInstance().getUpdateChecker().getResult() == Result.UPDATE_FOUND) {
                e.getPlayer().sendMessage(Locales.UPDATE_FOUND.getString().replaceAll("%oldversion", BukkitInventoryTweaks.getInstance().getDescription().getVersion()).replaceAll("%newversion", BukkitInventoryTweaks.getInstance().getUpdateChecker().getLatestRemoteVersion()));
            }
        }
    }
}
