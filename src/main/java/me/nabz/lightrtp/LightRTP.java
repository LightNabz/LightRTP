package me.nabz.lightrtp;

import org.bukkit.*;
import org.bukkit.command.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public class LightRTP extends JavaPlugin implements CommandExecutor {

    private int minRange, maxRange, centerX, centerZ, maxTries, cooldownSeconds;
    private final Map<UUID, Long> cooldownMap = new HashMap<>();

    @Override
    public void onEnable() {
        saveDefaultConfig();
        getCommand("ltp").setExecutor(this);
        getLogger().info("LightRTP loaded successfully~ OwO");
    }

    private void loadConfigValues(Player player) {
        FileConfiguration config = getConfig();
        
        // Load world-specific configurations based on player world
        minRange = config.getInt(player.getWorld().getName() + ".teleport.random.minRange", 100);
        maxRange = config.getInt(player.getWorld().getName() + ".teleport.random.maxRange", 1000);
        centerX = config.getInt(player.getWorld().getName() + ".teleport.random.centerX", 0);
        centerZ = config.getInt(player.getWorld().getName() + ".teleport.random.centerZ", 0);
        maxTries = config.getInt(player.getWorld().getName() + ".teleport.random.maxTries", 10);
        cooldownSeconds = config.getInt(player.getWorld().getName() + ".teleport.random.cooldownSeconds", 30);
    }

    private Location getSafeLocation(World world, Random random) {
        int tries = 0;
        while (tries < maxTries) {
            int range = random.nextInt(maxRange - minRange + 1) + minRange;
            double angle = random.nextDouble() * 2 * Math.PI;

            double x = centerX + Math.cos(angle) * range;
            double z = centerZ + Math.sin(angle) * range;

            Location loc = new Location(world, x, 0, z);
            loc.setY(world.getHighestBlockYAt(loc));

            Material block = loc.getBlock().getType();
            if (!block.isAir() && block.isSolid() && isSafe(block)) {
                loc.setY(loc.getY() + 1);
                return loc;
            }
            tries++;
        }
        return null;
    }

    private boolean isSafe(Material mat) {
        return mat != Material.LAVA &&
               mat != Material.WATER &&
               mat != Material.CACTUS &&
               mat != Material.MAGMA_BLOCK &&
               mat != Material.FIRE &&
               mat != Material.SWEET_BERRY_BUSH;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        boolean requireUsePerm = getConfig().getBoolean("permissions.require-use-permission", true);
    
        // Handle "reload" command
        if (args.length > 0 && args[0].equalsIgnoreCase("reload")) {
            if (sender.hasPermission("lightrtp.admin") || !requireUsePerm) {
                reloadConfig();
                if (sender instanceof Player player) {
                    loadConfigValues(player);
                    player.sendMessage("§a[LightRTP] Config reloaded~");
                } else {
                    sender.sendMessage("[LightRTP] Config reloaded~");
                }
            } else {
                sender.sendMessage("§cYou don't have permission to this command!");
            }
            return true;
        }
    
        // Only players can teleport!
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Only players can use this command!");
            return true;
        }
    
        if (requireUsePerm && !player.hasPermission("lightrtp.use")) {
            player.sendMessage("§cYou don't have permission to teleport!");
            return true;
        }
    
        loadConfigValues(player);
    
        long now = System.currentTimeMillis();
        long lastUsed = cooldownMap.getOrDefault(player.getUniqueId(), 0L);
        long waitTime = (cooldownSeconds * 1000L) - (now - lastUsed);
    
        if (waitTime > 0) {
            long secondsLeft = waitTime / 1000;
            player.sendMessage("§cYou have to wait for §e" + secondsLeft + " seconds §cbefore using this command again!");
            return true;
        }
    
        // Run teleport async
        Bukkit.getScheduler().runTaskAsynchronously(this, () -> {
            Location safeLoc = getSafeLocation(player.getWorld(), new Random());
            if (safeLoc != null) {
                Bukkit.getScheduler().runTask(this, () -> {
                    player.teleport(safeLoc);
                    player.sendMessage("§bYou have been teleported to a random location!");
                    cooldownMap.put(player.getUniqueId(), now);
                });
            } else {
                Bukkit.getScheduler().runTask(this, () -> {
                    player.sendMessage("§cFailed to find safe location after " + maxTries + " tries...");
                });
            }
        });
    
        return true;
    }
    
}
