package de.thejeterlp.BukkitInventoryTweaks.utils;

import de.thejeterlp.BukkitInventoryTweaks.BukkitInventoryTweaks;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public class ItemGroups {

    private static List<Material> SWORDS, PICKAXES, SHOVELS, AXES, HOES, FOOD;

    public static void init() {
        BukkitInventoryTweaks.getInstance().getLogger().info("Loading ItemGroups!");

        SWORDS = new ArrayList<>();
        PICKAXES = new ArrayList<>();
        SHOVELS = new ArrayList<>();
        AXES = new ArrayList<>();
        HOES = new ArrayList<>();
        FOOD = new ArrayList<>();

        for (String s : Config.SWORD_LIST.getStringList()) {
            Material mat = Material.valueOf(s);
            if (mat == null) {
                BukkitInventoryTweaks.getInstance().getLogger().severe(s + " is not a valid Material! Skipping...");
                continue;
            }
            if (!s.endsWith("SWORD")) {
                BukkitInventoryTweaks.getInstance().getLogger().severe(s + " is not a sword! Skipping...");
            }
            SWORDS.add(mat);
        }

        for (String s : Config.PICKAXE_LIST.getStringList()) {
            Material mat = Material.valueOf(s);
            if (mat == null) {
                BukkitInventoryTweaks.getInstance().getLogger().severe(s + " is not a valid Material! Skipping...");
                continue;
            }
            if (!s.endsWith("PICKAXE")) {
                BukkitInventoryTweaks.getInstance().getLogger().severe(s + " is not a pickaxe! Skipping...");
            }
            PICKAXES.add(mat);
        }

        for (String s : Config.SHOVEL_LIST.getStringList()) {
            Material mat = Material.valueOf(s);
            if (mat == null) {
                BukkitInventoryTweaks.getInstance().getLogger().severe(s + " is not a valid Material! Skipping...");
                continue;
            }
            if (!s.endsWith("SHOVEL")) {
                BukkitInventoryTweaks.getInstance().getLogger().severe(s + " is not a shovel! Skipping...");
            }
            SHOVELS.add(mat);
        }

        for (String s : Config.AXE_LIST.getStringList()) {
            Material mat = Material.valueOf(s);
            if (mat == null) {
                BukkitInventoryTweaks.getInstance().getLogger().severe(s + " is not a valid Material! Skipping...");
                continue;
            }
            if (!s.endsWith("AXE")) {
                BukkitInventoryTweaks.getInstance().getLogger().severe(s + " is not an axe! Skipping...");
            }
            AXES.add(mat);
        }

        for (String s : Config.HOE_LIST.getStringList()) {
            Material mat = Material.valueOf(s);
            if (mat == null) {
                BukkitInventoryTweaks.getInstance().getLogger().severe(s + " is not a valid Material! Skipping...");
                continue;
            }
            if (!s.endsWith("HOE")) {
                BukkitInventoryTweaks.getInstance().getLogger().severe(s + " is not a hoe! Skipping...");
            }
            HOES.add(mat);
        }

        for (String s : Config.FOOD_LIST.getStringList()) {
            Material mat = Material.valueOf(s);
            if (mat == null) {
                BukkitInventoryTweaks.getInstance().getLogger().severe(s + " is not a valid Material! Skipping...");
                continue;
            }
            if (!getEdibles().contains(mat.name())) {
                BukkitInventoryTweaks.getInstance().getLogger().severe(s + " is not consumable! Skipping...");
                continue;
            }
            FOOD.add(mat);
        }
    }

    public static boolean isFood(Material mat) {
        return FOOD.contains(mat);
    }

    public static boolean isSword(Material mat) {
        return SWORDS.contains(mat);
    }

    public static boolean isPickaxe(Material mat) {
        return PICKAXES.contains(mat);
    }

    public static boolean isShovel(Material mat) {
        return SHOVELS.contains(mat);
    }

    public static boolean isAxe(Material mat) {
        return AXES.contains(mat);
    }

    public static boolean isHoe(Material mat) {
        return HOES.contains(mat);
    }

    protected static List<String> getEdibles() {
        List<String> ret = new ArrayList<>();
        for (Material mat : Material.values()) {
            if (mat.isEdible()) ret.add(mat.name());
        }
        return ret;
    }

}
