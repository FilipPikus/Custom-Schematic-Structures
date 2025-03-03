package de.filippikus.worldgenateration;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class WorldGenateration extends JavaPlugin {
    public static WorldGenateration instance;

    @Override
    public void onEnable() {
        instance = this;
        getCommand("wg").setExecutor(new WorldGenatieron());
        Bukkit.getPluginManager().registerEvents(new Listener(),this);
        Config config = ConfigUtil.getConfig("config");
        config.setDefault("world",Listener.world);
        Listener.world = config.getString("world");
        config.setDefault("spawn-chance",Listener.chance);
        Listener.chance = config.getDouble("spawn-chance");
        Dir();
    }


    public static void Dir(){
        new File("plugins/WorldGeneration").mkdirs();
    }
    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
