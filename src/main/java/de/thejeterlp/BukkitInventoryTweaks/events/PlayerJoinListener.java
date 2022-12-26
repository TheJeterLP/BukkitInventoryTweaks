package de.thejeterlp.BukkitInventoryTweaks.events;

import de.jeter.updatechecker.Result;
import de.jeter.updatechecker.UpdateChecker;
import de.thejeterlp.BukkitInventoryTweaks.BukkitInventoryTweaks;
import de.thejeterlp.BukkitInventoryTweaks.utils.Config;
import de.thejeterlp.BukkitInventoryTweaks.utils.Locales;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        UpdateChecker checker = BukkitInventoryTweaks.getInstance().getUpdateChecker();
        if (Config.CHECK_UPDATE.getBoolean() && e.getPlayer().hasPermission("bit.notifyupdate") && checker != null) {
            if (checker.getResult() == Result.UPDATE_FOUND) {
                TextComponent msg = new TextComponent(Locales.UPDATE_FOUND.getString().replaceAll("%oldversion", BukkitInventoryTweaks.getInstance().getDescription().getVersion()).replaceAll("%newversion", BukkitInventoryTweaks.getInstance().getUpdateChecker().getLatestRemoteVersion()));
                msg.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("§aClick to download")));
                msg.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, checker.getDownloadLink()));
                e.getPlayer().spigot().sendMessage(msg);}
        }
    }
}
