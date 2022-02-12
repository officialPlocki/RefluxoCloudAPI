package me.refluxo.cloudapi.spigot.events;

import me.refluxo.cloudapi.common.AuthHandler;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinEvent implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if(!new AuthHandler().canJoin(event.getPlayer().getUniqueId().toString())) {
            event.getPlayer().kickPlayer("§c§lBitte betrete den Server über Refluxo.me.");
        }
    }

}
