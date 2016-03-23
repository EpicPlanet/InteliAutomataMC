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

package net.epicpla.inteliautomatamc;

import java.util.List;
import java.util.UUID;

public class InteliAutomataMC {
    public List<UUID> opt;
    public boolean isDefault;
    public boolean useTab;
    public List<String> exception;
    public String koMessage;
    public String unkoMessage;

    public boolean isInputtingKorean(UUID player) {
        boolean using = isDefault;
        if (opt.contains(player))
            using = !using;
        return using;
    }

    public boolean isException(String message) {
        for (String regex : exception)
            if (message.matches(regex))
                return true;
        return false;
    }

    public boolean setKoreanMode(UUID player, boolean mode) {
        if (mode == isDefault)
            return opt.remove(player);
        else
            return !opt.contains(player) && opt.add(player);
    }
}
