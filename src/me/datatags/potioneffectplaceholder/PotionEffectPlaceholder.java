package me.datatags.potioneffectplaceholder;

import java.util.Collection;
import java.util.StringJoiner;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import net.md_5.bungee.api.ChatColor;

public class PotionEffectPlaceholder extends JavaPlugin {
    @Override
    public void onEnable() {
        new PotionEffectPlaceholderExpansion(this).register();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "This command cannot be used from the console.");
            return true;
        }
        Player player = (Player) sender;
        Collection<PotionEffect> effects = player.getActivePotionEffects();
        if (effects.size() == 0) {
            player.sendMessage(ChatColor.RED + "You have no active effects.");
            return true;
        }
        player.sendMessage(ChatColor.YELLOW + "You have " + effects.size() + " active effect" + (effects.size() == 1 ? ":" : "s:"));
        for (PotionEffect effect : effects) {
            ChatColor color = ChatColor.of(new java.awt.Color(effect.getType().getColor().asRGB()));
            StringJoiner time = new StringJoiner(":");
            int seconds = effect.getDuration() / 20;
            if (seconds >= 60 * 60) {
                time.add(String.valueOf(seconds / 3600));
            }
            time.add(String.valueOf((seconds % 3600) / 60));
            time.add(String.valueOf(seconds % 60));
            player.sendMessage("- " + color + friendlyName(effect.getType()) + ": " + ChatColor.YELLOW + time.toString());
        }
        return true;
    }

    protected String friendlyName(PotionEffectType type) {
        String name = type.getName().replace("_", " ");
        return name.substring(0, 1) + name.substring(1, name.length()).toLowerCase();
    }
}
