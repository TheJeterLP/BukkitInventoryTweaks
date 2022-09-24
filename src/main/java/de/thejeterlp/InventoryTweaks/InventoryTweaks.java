package de.thejeterlp.InventoryTweaks;

import de.jeter.updatechecker.UpdateChecker;
import de.thejeterlp.InventoryTweaks.events.ItemBreakListener;
import de.thejeterlp.InventoryTweaks.utils.Config;
import org.bstats.bukkit.Metrics;
import org.bukkit.plugin.java.JavaPlugin;

public class InventoryTweaks extends JavaPlugin {

    private static InventoryTweaks INSTANCE;
    private UpdateChecker updatechecker = null;

    @Override
    public void onEnable() {
        INSTANCE = this;
        Config.load();

        if (Config.CHECK_UPDATE.getBoolean()) {
            //Disabled for now as there is no ID yet.
            updatechecker = new UpdateChecker(this, 00000);
        }

        if (Config.B_STATS.getBoolean()) {
            //Disabled for now, no ID yet
            Metrics metrics = new Metrics(this, 0000);
            getLogger().info("Thanks for using bstats, it was enabled!");
        }

        getServer().getPluginManager().registerEvents(new ItemBreakListener(), this);

        getLogger().info("Plugin is now enabled!");
    }

    @Override
    public void onDisable() {
        getServer().getScheduler().cancelTasks(this);
        getLogger().info("Plugin is now disabled!");
    }

    public static InventoryTweaks getInstance() {
        return INSTANCE;
    }

}
