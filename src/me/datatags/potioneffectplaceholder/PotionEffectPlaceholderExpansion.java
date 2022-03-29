package me.datatags.potioneffectplaceholder;

import java.util.Collection;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;

public class PotionEffectPlaceholderExpansion extends PlaceholderExpansion {
    private PotionEffectPlaceholder plugin;
    public PotionEffectPlaceholderExpansion(PotionEffectPlaceholder plugin) {
        this.plugin = plugin;
    }
    @Override
    public String getAuthor() {
        return plugin.getDescription().getAuthors().get(0);
    }

    @Override
    public String getIdentifier() {
        return "potioneffect";
    }

    @Override
    public String getVersion() {
        return plugin.getDescription().getVersion();
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public String onPlaceholderRequest(Player player, String param) {
        Collection<PotionEffect> effects = player.getActivePotionEffects();
        if (effects.size() == 0) {
            return "none";
        }
        if (effects.size() > 1) {
            return "You have " + effects.size() + " Potion Effects, use /effects to display them.";
        }
        return plugin.friendlyName(effects.iterator().next().getType());
    }
}
