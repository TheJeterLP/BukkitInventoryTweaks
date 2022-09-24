package de.thejeterlp.BukkitInventoryTweaks;

import de.jeter.updatechecker.UpdateChecker;
import de.thejeterlp.BukkitInventoryTweaks.events.PlayerJoinListener;
import de.thejeterlp.BukkitInventoryTweaks.events.replaceItems.BlockPlaceListener;
import de.thejeterlp.BukkitInventoryTweaks.events.replaceItems.DropItemListener;
import de.thejeterlp.BukkitInventoryTweaks.events.replaceItems.ItemBreakListener;
import de.thejeterlp.BukkitInventoryTweaks.events.replaceItems.ItemConsumeListener;
import de.thejeterlp.BukkitInventoryTweaks.events.sortInventory.InventoryClickListener;
import de.thejeterlp.BukkitInventoryTweaks.utils.Config;
import de.thejeterlp.BukkitInventoryTweaks.utils.ItemGroups;
import de.thejeterlp.BukkitInventoryTweaks.utils.Locales;
import org.bstats.bukkit.Metrics;
import org.bukkit.plugin.java.JavaPlugin;

public class BukkitInventoryTweaks extends JavaPlugin {

    private static BukkitInventoryTweaks INSTANCE;
    private UpdateChecker updatechecker = null;

    @Override
    public void onEnable() {
        INSTANCE = this;
        Config.load();
        Locales.load();

        if (Config.CHECK_UPDATE.getBoolean()) {
            updatechecker = new UpdateChecker(this, 105437);
        }

        if (Config.B_STATS.getBoolean()) {
            Metrics metrics = new Metrics(this, 16495);
            getLogger().info("Thanks for using bstats, it was enabled!");
        }

        ItemGroups.init();

        getServer().getPluginManager().registerEvents(new ItemBreakListener(), this);
        getServer().getPluginManager().registerEvents(new ItemConsumeListener(), this);
        getServer().getPluginManager().registerEvents(new BlockPlaceListener(), this);
        getServer().getPluginManager().registerEvents(new DropItemListener(), this);

        getServer().getPluginManager().registerEvents(new InventoryClickListener(), this);

        getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);

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

    public UpdateChecker getUpdateChecker() {
        return this.updatechecker;
    }

}
