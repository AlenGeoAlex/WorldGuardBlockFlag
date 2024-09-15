package me.alenalex.worldGuardBlockFlag;

import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.flags.SetFlag;
import com.sk89q.worldguard.protection.flags.registry.FlagRegistry;
import me.alenalex.worldGuardBlockFlag.helper.MaterialFlag;
import me.alenalex.worldGuardBlockFlag.listener.BlockBreakListener;
import me.alenalex.worldGuardBlockFlag.listener.BlockPlaceListener;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;

public final class WorldGuardBlockFlag extends JavaPlugin {
    
    private static WorldGuardPlugin WORLD_GUARD_PLUGIN;
    private static WorldGuard WORLD_GUARD_INSTANCE;

    private static SetFlag<Material> BLOCK_BREAK_EXCLUDING;
    private static SetFlag<Material> BLOCK_PLACE_EXCLUDING;
    
    @Override
    public void onLoad() {
        WORLD_GUARD_PLUGIN = (WorldGuardPlugin) this.getServer().getPluginManager().getPlugin("WorldGuard");
        WORLD_GUARD_INSTANCE = WorldGuard.getInstance();

        FlagRegistry flagRegistry = WorldGuard.getInstance().getFlagRegistry();
        try {
            SetFlag<Material> flag = new SetFlag<>("block-break-excluding", new MaterialFlag(null));
            flagRegistry.register(flag);
            BLOCK_BREAK_EXCLUDING = flag;

        }catch (Exception e){
            getLogger().severe("Failed to register flag [block-break-excluding]: " + e.getMessage());
            BLOCK_BREAK_EXCLUDING = null;
        }

        try {
            SetFlag<Material> flag = new SetFlag<>("block-place-excluding", new MaterialFlag(null));
            flagRegistry.register(flag);
            BLOCK_PLACE_EXCLUDING = flag;

        }catch (Exception e){
            getLogger().severe("Failed to register flag [block-place-excluding]: " + e.getMessage());
            BLOCK_PLACE_EXCLUDING = null;
        }
        getLogger().info("WorldGuardBlockFlag Plugin loaded!");
    }

    @Override
    public void onEnable() {
        if(BLOCK_BREAK_EXCLUDING != null){
            getServer().getPluginManager().registerEvents(new BlockBreakListener(this), this);
            getLogger().info("Registered block-break-excluding");
        }
        
        if(BLOCK_PLACE_EXCLUDING != null){
            getServer().getPluginManager().registerEvents(new BlockPlaceListener(this), this);
            getLogger().info("Registered block-place-excluding");
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
    
    public static SetFlag<Material> getBlockBreakExcludingFlag(){
        if(BLOCK_BREAK_EXCLUDING == null){
            throw new RuntimeException("block-break-excluding flag not initialized");
        }
        
        return BLOCK_BREAK_EXCLUDING;
    }

    public static SetFlag<Material> getBlockPlaceExcludingFlag(){
        if(BLOCK_BREAK_EXCLUDING == null){
            throw new RuntimeException("block-place-excluding flag not initialized");
        }

        return BLOCK_PLACE_EXCLUDING;
    }
    
    public WorldGuardPlugin worldGuardPlugin(){
        return WORLD_GUARD_PLUGIN;
    }

    public WorldGuard worldGuardInstance() {
        return WORLD_GUARD_INSTANCE;
    }
}
