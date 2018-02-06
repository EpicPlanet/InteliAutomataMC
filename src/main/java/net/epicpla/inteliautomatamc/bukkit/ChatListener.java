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

import me.neder.inteliautomata.InteliAutomata;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerChatTabCompleteEvent;

public class ChatListener implements Listener {

    InteliAutomataBukkit plugin;

    public ChatListener(InteliAutomataBukkit plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onChat(AsyncPlayerChatEvent event) {
        if (plugin.getCore().isInputtingKorean(event.getPlayer().getUniqueId()) && event.getPlayer().hasPermission("inteliautomatabukkit.chat") && !plugin.getCore().isException(event.getMessage()))
            event.setMessage(InteliAutomata.convert(event.getMessage()));
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onTab(PlayerChatTabCompleteEvent event) {
        if (!plugin.getCore().isInputtingKorean(event.getPlayer().getUniqueId()) && plugin.getCore().useTab && event.getPlayer().hasPermission("inteliautomatabukkit.tab")) {
            event.getTabCompletions().add(InteliAutomata.convert(event.getLastToken()));
        }
    }
}
