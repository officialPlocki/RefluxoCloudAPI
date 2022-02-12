package me.refluxo.cloudapi.spigot.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class CloudCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        sender.sendMessage("§b§lRefluxo§f§lCloud §8» §7Version §bv1.0-SNAPSHOT§7, by §cplocki§7.");
        return false;
    }

}
