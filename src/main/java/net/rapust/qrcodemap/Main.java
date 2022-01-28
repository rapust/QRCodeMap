package net.rapust.qrcodemap;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import net.rapust.qrcodemap.command.QRCode;
import net.rapust.qrcodemap.event.MapInitialize;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.util.HashMap;
import java.util.List;

public class Main extends JavaPlugin {

    @Getter @Setter
    private static Main instance;
    public static HashMap<Player, HashMap<ItemStack, String>> itemsToGive;

    @SneakyThrows
    @Override
    public void onEnable() {
        setInstance(this);
        itemsToGive = new HashMap<>();
        getCommand("qrcode").setExecutor(new QRCode());
        Bukkit.getPluginManager().registerEvents(new MapInitialize(), this);
        startItemsGiver();


        File configFile = new File(getDataFolder()+File.separator+"config.yml");
        if (!configFile.exists()) {
            getConfig().options().copyDefaults(true);
            saveDefaultConfig();
        }

        File mapsData = new File(getDataFolder()+File.separator+"data.yml");
        if (!mapsData.exists()) {
            mapsData.createNewFile();
        }

        getLogger().info("QRCodeMap is enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("QRCodeMap is disabled!");
    }

    public static String getMessage(String messageCode) {
        return getInstance().getConfig().getString(messageCode).replace("&", "§");
    }

    private void startItemsGiver() {
        (new BukkitRunnable() {
            @SneakyThrows
            @Override
            public void run() {
                File mapsData = new File(Main.getInstance().getDataFolder()+File.separator+"data.yml");
                FileConfiguration maps = YamlConfiguration.loadConfiguration(mapsData);
                for (Player player : itemsToGive.keySet()) {
                    ItemStack item = (ItemStack) itemsToGive.get(player).keySet().toArray()[0];
                    player.setItemInHand(item);
                    String id = String.valueOf(((MapMeta) item.getItemMeta()).getMapId());
                    String data = itemsToGive.get(player).get(item);
                    maps.set(id, data);

                    itemsToGive.remove(player);
                }
                maps.save(mapsData);
            }
        }).runTaskTimer(this, 0L, 1);
    }

}


