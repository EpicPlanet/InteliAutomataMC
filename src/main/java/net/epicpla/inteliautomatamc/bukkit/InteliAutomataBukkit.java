/*
 * InteliAutomataMC - Minecraft server mod to help users input Hangul.
 * Copyright (C) 2016 Final Child
 *
 * This file is part of InteliAutomataMC.
 *
 * InteliAutomataMC is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * InteliAutomataMC is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with InteliAutomataMC.  If not, see <http://www.gnu.org/licenses/>.
 */

package net.epicpla.inteliautomatamc.bukkit;

import net.epicpla.inteliautomatamc.InteliAutomataMC;
import net.epicpla.inteliautomatamc.bukkit.commands.KoCommand;
import net.epicpla.inteliautomatamc.bukkit.commands.UnkoCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Collections;
import java.util.UUID;
import java.util.stream.Collectors;

public class InteliAutomataBukkit extends JavaPlugin {

    private InteliAutomataMC core;
    private FileConfiguration configuration;

    @Override
    public void onEnable() {
        if (!getDataFolder().exists())
            if (!getDataFolder().mkdir())
                return;
        File file = new File(getDataFolder(), "config.yml");
        if (!file.exists()) {
            try (InputStream in = getResource("bukkitConfig.yml")) {
                Files.copy(in, file.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        configuration = getConfig();

        core = new InteliAutomataMC();
        core.isDefault = configuration.getBoolean("default");
        core.useTab = configuration.getBoolean("useTab");
        core.exception = configuration.getStringList("except");
        core.opt = Collections.synchronizedList(configuration.getStringList("opt").stream().map(UUID::fromString).collect(Collectors.toList()));
        core.koMessage = configuration.getString("lang.ko");
        core.unkoMessage = configuration.getString("lang.unko");

        getServer().getPluginManager().registerEvents(new ChatListener(this), this);
        getCommand("ko").setExecutor(new KoCommand(this));
        getCommand("unko").setExecutor(new UnkoCommand(this));
    }

    @Override
    public void onDisable() {
        configuration.set("default", core.isDefault);
        configuration.set("useTab", core.useTab);
        configuration.set("except", core.exception);
        configuration.set("opt", core.opt.stream().map(UUID::toString).collect(Collectors.toList()));
    }

    public InteliAutomataMC getCore() {
        return core;
    }
}
