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

package net.epicpla.inteliautomatamc.bungeecord;

import net.epicpla.inteliautomatamc.InteliAutomataMC;
import net.epicpla.inteliautomatamc.bungeecord.commands.KoCommand;
import net.epicpla.inteliautomatamc.bungeecord.commands.UnkoCommand;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Collections;
import java.util.UUID;
import java.util.stream.Collectors;

public class InteliAutomataBungeecord extends Plugin {

    private InteliAutomataMC core;
    private Configuration configuration;

    @Override
    public void onEnable() {
        if (!getDataFolder().exists())
            if (!getDataFolder().mkdir())
                return;
        File file = new File(getDataFolder(), "config.yml");
        if (!file.exists()) {
            try (InputStream in = getResourceAsStream("bungeecordConfig.yml")) {
                Files.copy(in, file.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        core = new InteliAutomataMC();
        core.isDefault = configuration.getBoolean("default");
        core.useTab = configuration.getBoolean("useTab");
        core.exception = configuration.getStringList("except");
        core.opt = Collections.synchronizedList(configuration.getStringList("opt").stream().map(UUID::fromString).collect(Collectors.toList()));
        core.koMessage = configuration.getString("lang.ko");
        core.unkoMessage = configuration.getString("lang.unko");

        getProxy().getPluginManager().registerListener(this, new ChatListener(this));
        getProxy().getPluginManager().registerCommand(this, new KoCommand(this, "ko", "inteliautomatabungeecord.ko"));
        getProxy().getPluginManager().registerCommand(this, new UnkoCommand(this, "unko", "inteliautomatabungeecord.unko"));
    }

    @Override
    public void onDisable() {
        configuration.set("default", core.isDefault);
        configuration.set("useTab", core.useTab);
        configuration.set("except", core.exception);
        configuration.set("opt", core.opt.stream().map(UUID::toString).collect(Collectors.toList()));
        try {
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(configuration, new File(getDataFolder(), "config.yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public InteliAutomataMC getCore() {
        return core;
    }
}
