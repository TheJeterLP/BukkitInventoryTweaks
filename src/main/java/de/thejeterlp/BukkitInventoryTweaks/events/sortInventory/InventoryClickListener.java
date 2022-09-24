package de.thejeterlp.BukkitInventoryTweaks.events.sortInventory;

import de.thejeterlp.BukkitInventoryTweaks.utils.Utils;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventoryClickListener implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onMiddleClick(InventoryClickEvent e) {
        if (!e.getWhoClicked().hasPermission("bit.sortinventory") || !e.isRightClick() || e.getCurrentItem() != null) {
            return;
        }

        //Somewhere outside of the inventory has been clicked!
        Inventory inv = e.getInventory();
        InventoryType type = inv.getType();
        if (type != InventoryType.BARREL
                && type != InventoryType.CHEST
                && type != InventoryType.CRAFTING
                && type != InventoryType.ENDER_CHEST
                && type != InventoryType.PLAYER
                && type != InventoryType.SHULKER_BOX) {
            return;
        }

        if (type == InventoryType.CRAFTING) {
            //Actually use the players Inventory, not the CRAFTING inventory.
            inv = e.getWhoClicked().getInventory();
            type = e.getWhoClicked().getInventory().getType();
        }

        Utils.debug("Outside of the actual Inventory has been clicked! Inventory: " + inv.getType() + " has " + inv.getSize() + " slots.");

        int freeSlots = 0;
        for (ItemStack stack : inv.getContents()) {
            if (stack == null || stack.getType() == Material.AIR) freeSlots++;
        }

        Utils.debug("Inventory has " + freeSlots + " free slots and " + (inv.getSize() - freeSlots) + " used slots.");

        if (type == InventoryType.PLAYER) {
            //Sort players inventory

            //Example:
            // Slot 1 60 cobblestone
            // Slot 2 40 cobblestone

        } else {
            for (int i = 0; i < inv.getSize(); i++) {
                ItemStack stack = inv.getItem(i);
                if (stack == null) {
                    Utils.debug("Slot " + i + " is null! Skipping...");
                    continue;
                }
                if (stack.getAmount() == stack.getMaxStackSize()) {
                    Utils.debug("Slot " + i + ": " + stack + "has fullStackSize, skipping slot...");
                    continue;
                }
                Utils.debug(("Slot " + i + ": " + stack + " +  can be filled up! Starting search for fitting items..."));
                //Stack has actually more space
                //Now search if there are any other matching blocks
                for (int target = 0; target < inv.getSize(); target++) {
                    if (target == i) {
                        Utils.debug(("target Slot equals i! Skipping forward..."));
                        continue;
                    }
                    ItemStack targetStack = inv.getItem(target);
                    Utils.debug("Checking Slot " + target + " with ItemStack: " + targetStack);
                    if (targetStack == null) continue;
                    if (targetStack.getType() == stack.getType()) {
                        Utils.debug("Found matching Item in slot " + target);
                        int neededForFullStack = stack.getMaxStackSize() - stack.getAmount();
                        if (targetStack.getAmount() <= neededForFullStack) {
                            //Slot 1: 60 cobblestone
                            //Slot 2: 4 cobblestone
                            //= Slot 1 64 cobblestone
                            //= Slot 2 AIR
                            stack.setAmount(stack.getAmount() + targetStack.getAmount());
                            inv.setItem(target, new ItemStack(Material.AIR));
                            Utils.debug(("Target amount <= neededForFullStack! Clearing targetslot and adding amount to slot."));
                        } else {
                            //bigger than rest
                            //Slot 1: 60 cobblestone
                            //Slot 2: 10 cobblestone
                            //=Slot 1: 64 cobblestone
                            //=Slot 2: 6 cobblestone

                            //neededForFullStack = 4
                            targetStack.setAmount(targetStack.getAmount() + neededForFullStack);
                            stack.setAmount(stack.getAmount() - neededForFullStack);
                            Utils.debug("Target amount > neededForFullStack! Adding diff to slot and removing diff from targetSlot.");
                        }
                    }
                }
            }
        }


    }

}
