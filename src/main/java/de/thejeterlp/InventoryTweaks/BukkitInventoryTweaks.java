package de.thejeterlp.InventoryTweaks;

import de.jeter.updatechecker.UpdateChecker;
import de.thejeterlp.InventoryTweaks.events.replaceItems.BlockPlaceListener;
import de.thejeterlp.InventoryTweaks.events.replaceItems.DropItemListener;
import de.thejeterlp.InventoryTweaks.events.replaceItems.ItemBreakListener;
import de.thejeterlp.InventoryTweaks.events.replaceItems.ItemConsumeListener;
import de.thejeterlp.InventoryTweaks.utils.Config;
import org.bstats.bukkit.Metrics;
import org.bukkit.plugin.java.JavaPlugin;

public class BukkitInventoryTweaks extends JavaPlugin {

    private static BukkitInventoryTweaks INSTANCE;
    private UpdateChecker updatechecker = null;

    @Override
    public void onEnable() {
        INSTANCE = this;
        Config.load();

        if (Config.CHECK_UPDATE.getBoolean()) {
            updatechecker = new UpdateChecker(this, 105437);
        }

        if (Config.B_STATS.getBoolean()) {
            Metrics metrics = new Metrics(this, 16495);
            getLogger().info("Thanks for using bstats, it was enabled!");
        }

        getServer().getPluginManager().registerEvents(new ItemBreakListener(), this);
        getServer().getPluginManager().registerEvents(new ItemConsumeListener(), this);
        getServer().getPluginManager().registerEvents(new BlockPlaceListener(), this);
        getServer().getPluginManager().registerEvents(new DropItemListener(), this);

        getLogger().info("Plugin is now enabled!");
    }

    @Override
    public void onDisable() {
        getServer().getScheduler().cancelTasks(this);
        getLogger().info("Plugin is now disabled!");
    }

    public static BukkitInventoryTweaks getInstance() {
        return INSTANCE;
    }

}
