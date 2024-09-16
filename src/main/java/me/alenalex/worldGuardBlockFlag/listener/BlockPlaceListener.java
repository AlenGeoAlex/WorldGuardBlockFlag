package me.alenalex.worldGuardBlockFlag.listener;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import me.alenalex.worldGuardBlockFlag.WorldGuardBlockFlag;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.Set;

public class BlockPlaceListener extends WorldListener {

    public BlockPlaceListener(WorldGuardBlockFlag plugin) {
        super(plugin);
    }

    @EventHandler(ignoreCancelled = false, priority = EventPriority.LOW)
    public void onBlockPlace(BlockPlaceEvent event) {
        Material blockMaterial = event.getBlock().getType();

        if(hasBuildPermission(event.getPlayer(), "block." + blockMaterial.name().toLowerCase() + ".place") || hasBuildPermission(event.getPlayer(), "block.place." + blockMaterial.name().toLowerCase()))
            return;

        RegionContainer regionContainer = plugin.worldGuardInstance().getPlatform().getRegionContainer();
        RegionQuery query = regionContainer.createQuery();
        ApplicableRegionSet set = query.getApplicableRegions(BukkitAdapter.adapt(event.getBlock().getLocation()));

        Set<Material> materials = set.queryValue(null, WorldGuardBlockFlag.getBlockPlaceExcludingFlag());

        if(materials == null || materials.isEmpty())
            return;

        if(!materials.contains(blockMaterial)){
            event.setCancelled(true);
            //tellErrorMessage(event.getPlayer(), event.getBlock().getWorld());
        }
    }
    
}
