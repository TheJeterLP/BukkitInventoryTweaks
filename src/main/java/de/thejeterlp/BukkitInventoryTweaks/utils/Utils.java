package de.thejeterlp.BukkitInventoryTweaks.utils;

import de.thejeterlp.BukkitInventoryTweaks.BukkitInventoryTweaks;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class Utils {

    public static void debug(String msg) {
        if (Config.DEBUG.getBoolean()) {
            BukkitInventoryTweaks.getInstance().getLogger().info(msg);
        }
    }

    public static void replaceWithAnotherItem(Player p, ItemStack item) {
        PlayerInventory inv = p.getInventory();
        boolean mainHand = false;

        if (inv.getItemInMainHand().isSimilar(inv.getItemInOffHand())) {
            Utils.debug("Item in Main hand is the same as in off hand! Doing nothing...");
            return;
        }

        ItemStack consumed = null;
        if (inv.getItemInMainHand().isSimilar(item)) {
            consumed = inv.getItemInMainHand();
            mainHand = true;
            Utils.debug("Item in Mainhand is similar to item used!");
        } else if (inv.getItemInOffHand().isSimilar(item)) {
            consumed = inv.getItemInOffHand();
            Utils.debug("Item in Offhand is similar to item used!");
        }

        if (consumed == null) return;

        Utils.debug("Item used before actually removing: " + consumed);
        if (consumed.getAmount() == 1) {
            consumed = null;
            if (mainHand) {
                inv.setItemInMainHand(new ItemStack(Material.AIR));
                Utils.debug("Held after removing: " + inv.getItemInMainHand());
            } else {
                inv.setItemInOffHand(new ItemStack(Material.AIR));
                Utils.debug("Held after removing: " + inv.getItemInOffHand());
            }
        } else if (consumed.getAmount() == 0) {
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

            Utils.debug("Setting targetslot to AIR!");
            inv.setItem(inv.first(target), new ItemStack(Material.AIR));

            final boolean hand = mainHand;

            BukkitInventoryTweaks.getInstance().getServer().getScheduler().scheduleSyncDelayedTask(BukkitInventoryTweaks.getInstance(), new Runnable() {
                @Override
                public void run() {
                    if (hand) {
                        Utils.debug("Matching Item found! Replacing mainHand with " + target);
                        inv.setItemInMainHand(target);
                    } else {
                        Utils.debug("Matching Item found! Replacing offHand with " + target);
                        inv.setItemInOffHand(target);
                    }
                    Utils.playSound(p);
                }

            }, 5);
        }
    }

    public static ItemStack getMatchingItemIfExisting(ItemStack target, Inventory inv) {
        Material m = target.getType();

        //First check if exact same item is existing in INV
        if (inv.contains(m, 1)) {
            for (ItemStack stack : inv.getContents()) {
                if (stack == null || stack.getType() != m) continue;
                //Ensure stack is the same material as the broken item here
                return stack;
            }
        }

        //Now check if similar item is existing
        if (!Config.REPLACE_ITEMS_EXACT.getBoolean()) {
            //Exact matches are disabled, we can also switch other items.

            for (ItemStack stack : inv.getContents()) {
                if (stack == null || stack.getType() == Material.AIR) continue;
                Material sMat = stack.getType();
                if (ItemGroups.isFood(m) && ItemGroups.isFood(sMat)) {
                    return stack;
                } else if (ItemGroups.isSword(m) && ItemGroups.isSword(sMat)) {
                    return stack;
                } else if (ItemGroups.isPickaxe(m) && ItemGroups.isPickaxe(sMat)) {
                    return stack;
                } else if (ItemGroups.isShovel(m) && ItemGroups.isShovel(sMat)) {
                    return stack;
                } else if (ItemGroups.isAxe(m) && ItemGroups.isAxe(sMat)) {
                    return stack;
                } else if (ItemGroups.isHoe(m) && ItemGroups.isHoe(sMat)) {
                    return stack;
                } else {
                    continue;
                }

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
