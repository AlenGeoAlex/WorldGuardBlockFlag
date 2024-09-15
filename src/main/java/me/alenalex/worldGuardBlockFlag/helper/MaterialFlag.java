package me.alenalex.worldGuardBlockFlag.helper;

import com.sk89q.worldguard.protection.flags.Flag;
import com.sk89q.worldguard.protection.flags.FlagContext;
import org.bukkit.Material;

import javax.annotation.Nullable;

public class MaterialFlag extends Flag<Material> {

    public MaterialFlag(String name) {
        super(name);
    }

    @Override
    public Material parseInput(FlagContext context) {
        Material material = Material.matchMaterial(context.getUserInput());
        if (material != null)
            return material;
        else
            throw new RuntimeException("Unable to find the material! Please refer to https://hub.spigotmc.org/javadocs/spigot/org/bukkit/Material.html for valid ids");

    }

    @Override
    public Material unmarshal(@Nullable Object o) {
        if(o == null)
            return null;
        
        Material material = Material.matchMaterial(o.toString());
        if (material == null) //Fallback to legacy on unmarshal only
        {
            material = Material.matchMaterial(o.toString(), true);
        }

        return material;
    }

    @Override
    public Object marshal(Material o) {
        return o.name();
    }
}
