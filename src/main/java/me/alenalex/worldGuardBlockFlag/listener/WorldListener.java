package me.alenalex.worldGuardBlockFlag.listener;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.bukkit.BukkitWorldConfiguration;
import me.alenalex.worldGuardBlockFlag.WorldGuardBlockFlag;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Listener;

public abstract class WorldListener implements Listener {

    protected final WorldGuardBlockFlag plugin;
    
    public WorldListener(WorldGuardBlockFlag plugin) {
        this.plugin = plugin;
    }
    
    
    protected BukkitWorldConfiguration getWorldConfig(World world){
        return plugin.worldGuardPlugin().getConfigManager().get(BukkitAdapter.adapt(world));
    }

    protected boolean hasBuildPermission(CommandSender sender, String perm) {
        return plugin.worldGuardPlugin().hasPermission(sender, "worldguard.build." + perm);
    }

    protected void tellErrorMessage(CommandSender sender, World world) {
        String message = getWorldConfig(world).buildPermissionDenyMessage;
        if (!message.isEmpty()) {
            sender.sendMessage(message);
        }
    }

}
