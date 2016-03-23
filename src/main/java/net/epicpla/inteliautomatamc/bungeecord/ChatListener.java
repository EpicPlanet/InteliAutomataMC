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

import me.neder.inteliautomata.InteliAutomata;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.event.TabCompleteEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;

public class ChatListener implements Listener {

    InteliAutomataBungeecord plugin;

    public ChatListener(InteliAutomataBungeecord plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onChat(ChatEvent event) {
        if (!event.isCancelled() && !event.isCommand() && event.getSender() instanceof ProxiedPlayer)
            if (plugin.getCore().isInputtingKorean(((ProxiedPlayer) event.getSender()).getUniqueId()) && ((ProxiedPlayer) event.getSender()).hasPermission("inteliautomatabungeecord.chat") && !plugin.getCore().isException(event.getMessage()))
                event.setMessage(InteliAutomata.convert(event.getMessage()));
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onTab(TabCompleteEvent event) {
        if (!event.isCancelled() && event.getSender() instanceof ProxiedPlayer)
            if (!plugin.getCore().isInputtingKorean(((ProxiedPlayer) event.getSender()).getUniqueId()) && plugin.getCore().useTab)  {
                String[] cursor = event.getCursor().split(" ");
                String lastToken = cursor[cursor.length - 1];
                event.getSuggestions().add(InteliAutomata.convert(lastToken));
            }
    }
}
