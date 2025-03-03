package de.filippikus.worldgenateration;


import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.session.ClipboardHolder;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkPopulateEvent;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Listener implements org.bukkit.event.Listener {

    public static boolean Chunk = true;
    public static double chance = 5.5;
    public static String world = "Your World";

    @EventHandler
    public void onChunkLoad(ChunkPopulateEvent event) {
        if (Chunk) {
            if (event.getChunk().getWorld().getName().equals(world)) {
                if (new Random().nextDouble(100) > chance) {
                    return;
                }
                Clipboard clipboard;
                File dir = new File("plugins/WorldGeneration");
                if (!dir.isDirectory()) {
                    return;
                }

                if (dir.listFiles().length == 0) {
                    return;
                }

                File file = dir.listFiles()[new Random().nextInt(dir.listFiles().length)];
                if (!file.isFile()) {
                    return;
                }

                ClipboardFormat format = ClipboardFormats.findByFile(file);
                try (ClipboardReader reader = format.getReader(new FileInputStream(file))) {
                    clipboard = reader.read();
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                Location location = event.getChunk().getBlock(1, 100, 1).getLocation();
                try (EditSession editSession = WorldEdit.getInstance().getEditSessionFactory().getEditSession(BukkitAdapter.adapt(event.getWorld()), -1)) {
                    Operation operation = new ClipboardHolder(clipboard)
                            .createPaste(editSession)
                            .to(BlockVector3.at(location.x(), event.getWorld().getHighestBlockYAt(location), location.z()))
                            .ignoreAirBlocks(false)
                            .build();

                    Operations.complete(operation);
                } catch (WorldEditException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
