package me.refluxo.cloudapi.bungeecord.events;

import me.refluxo.cloudapi.common.AuthHandler;
import net.md_5.bungee.api.event.*;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class JoinEvent implements Listener {

    @EventHandler
    public void onLogin(PostLoginEvent event) {
        new AuthHandler().setOnline(event.getPlayer().getUniqueId().toString(), true);
    }

    @EventHandler
    public void onLogout(PlayerDisconnectEvent event) {
        new AuthHandler().setOnline(event.getPlayer().getUniqueId().toString(), false);
    }

}
