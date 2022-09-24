/*
 * This file is part of ChatEx
 * Copyright (C) 2022 ChatEx Team
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 */
package de.thejeterlp.InventoryTweaks.utils;

import de.thejeterlp.InventoryTweaks.BukkitInventoryTweaks;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public enum Config {

    CHECK_UPDATE("check-for-updates", true, "Should the plugin check for updates by itself?"),
    B_STATS("enable-bstats", true, "Do you want to use bstats?"),
    DEBUG("debug", false, "Should the debug log be enabled?"),
    //LOCALE("locale", "en-EN", "Which language do you want?"),
    REPLACE_ITEMS_IN_CREATIVE("ReplaceItems.replaceInCreative", false, "Should the items be replaced in creative mode too?"),
    REPLACE_ITEMS_PLAY_SOUND("ReplaceItems.playSound", true, "Should a sound be played to the player after the item was replaced?"),
    //REPLACE_ITEMS_EXACT("ReplaceItems.needsToMatchExactly", false, "Does the item to replace need to match exactly? (For ex. should a woodenpickaxe be replaced by a diamond picakxe)"),
    REPLACE_ITEMS_ON_BREAK("ReplaceItems.onBreak", true, "Should the item be replaced after it breaks?"),
    REPLACE_ITEMS_ON_CONSUME("ReplaceItems.onConsume", true, "Should the item be replaced when its consumed?"),
    REPLACE_ITEMS_ON_DROP("ReplaceItems.onDrop", false, "Should the item be replaced after it has been dropped by the player?"),
    REPLACE_ITEMS_ON_BLOCK_PLACE("ReplaceItems.onBlockPlace", true, "Should the item be replaced after a Block was placed?"),
    ;

    private final Object value;
    private final String path;
    private final String description;
    private static YamlConfiguration cfg;
    private static final File f = new File(BukkitInventoryTweaks.getInstance().getDataFolder(), "config.yml");

    Config(String path, Object val, String description) {
        this.path = path;
        this.value = val;
        this.description = description;
    }

    public String getPath() {
        return path;
    }

    public String getDescription() {
        return description;
    }

    public Object getDefaultValue() {
        return value;
    }

    public boolean getBoolean() {
        return cfg.getBoolean(path);
    }

    public double getDouble() {
        return cfg.getDouble(path);
    }

    public int getInt() {
        return cfg.getInt(path);
    }

    public String getString() {
        return cfg.getString(path);
    }

    public static void load() {
        BukkitInventoryTweaks.getInstance().getDataFolder().mkdirs();
        reload(false);
        List<String> header = new ArrayList<>();
        for (Config c : values()) {
            header.add(c.getPath() + ": " + c.getDescription());
            if (!cfg.contains(c.getPath())) {
                c.set(c.getDefaultValue(), false);
            }
        }
        cfg.options().setHeader(header);
        try {
            cfg.save(f);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void set(Object value, boolean save) {
        cfg.set(path, value);
        if (save) {
            try {
                cfg.save(f);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            reload(false);
        }
    }

    public static void reload(boolean complete) {
        if (!complete) {
            cfg = YamlConfiguration.loadConfiguration(f);
            return;
        }
        load();
    }
}
