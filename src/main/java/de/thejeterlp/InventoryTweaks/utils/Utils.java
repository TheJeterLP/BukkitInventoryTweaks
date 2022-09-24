package de.thejeterlp.InventoryTweaks.utils;

import de.thejeterlp.InventoryTweaks.BukkitInventoryTweaks;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class Utils {

    public static void debug(String msg) {
        if (Config.DEBUG.getBoolean()) {
            BukkitInventoryTweaks.getInstance().getLogger().info(msg);
        }
    }

    public static ItemStack getMatchingItemIfExisting(ItemStack target, Inventory inv) {
        Material m = target.getType();
        if (inv.contains(m, 1)) {
            for (ItemStack stack : inv.getContents()) {
                if (stack == null || stack.getType() != m) continue;
                //Ensure stack is the same material as the broken item here.
                return stack;
            }
        }
        return null;
    }

    public static void playSound(Player p) {
        if (Config.REPLACE_ITEMS_PLAY_SOUND.getBoolean()) {
            p.playSound(p.getLocation(), Sound.ITEM_ARMOR_EQUIP_GENERIC, 10, 10);
        }
    }

}
