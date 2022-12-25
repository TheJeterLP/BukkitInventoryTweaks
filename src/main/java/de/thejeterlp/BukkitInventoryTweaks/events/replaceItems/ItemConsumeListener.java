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

        boolean mainHand = false;

        if (inv.getItemInMainHand().isSimilar(inv.getItemInOffHand())) {
            Utils.debug("Item in Main hand is the same as in off hand! Doing nothing...");
            return;
        }

        ItemStack consumed = null;
        if (inv.getItemInMainHand().isSimilar(item)) {
            consumed = inv.getItemInMainHand();
            mainHand = true;
            Utils.debug("Item in Mainhand is similar to item consumed!");
        } else if (inv.getItemInOffHand().isSimilar(item)) {
            consumed = inv.getItemInOffHand();
            Utils.debug("Item in Offhand is similar to item consumed!");
        }

        if (consumed == null || !consumed.getType().isEdible()) return;

        Utils.debug("Item consumed before actually removing: " + consumed);
        if (consumed.getAmount() == 1) {
            consumed = null;
            if (mainHand) {
                inv.setItemInMainHand(new ItemStack(Material.AIR));
                Utils.debug("Held after removing: " + inv.getItemInMainHand());
            } else {
                inv.setItemInOffHand(new ItemStack(Material.AIR));
                Utils.debug("Held after removing: " + inv.getItemInOffHand());
            }
        } else if(consumed.getAmount() == 0) {
            Utils.debug("Amount of held is already 0, no removing necessary.");
        } else {
            Utils.debug("Amount of held is bigger than 1, no replacing of ItemStack necessary.");
        }


        //Ensure that we dropped everything of that itemstack
        if (consumed == null || consumed.getType() == Material.AIR) {
            ItemStack target = Utils.getMatchingItemIfExisting(item, inv);
            if (target == null) {
                Utils.debug("No matching item found in Inventory.");
                return;
            }

            if (mainHand) {
                Utils.debug("Matching Item found! Replacing mainHand with " + target);
                inv.setItemInMainHand(target);
            } else {
                Utils.debug("Matching Item found! Replacing offHand with " + target);
                inv.setItemInOffHand(target);
            }

            Utils.debug("Setting target to AIR!");
            inv.setItem(inv.first(target), new ItemStack(Material.AIR));

            Utils.playSound(p);
        }
    }

}
