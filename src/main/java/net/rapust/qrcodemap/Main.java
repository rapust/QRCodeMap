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

public class Main extends JavaPlugin {

    @Getter @Setter
    private static Main instance;

    @SneakyThrows
    @Override
    public void onEnable() {
        setInstance(this);
        getCommand("qrcode").setExecutor(new QRCode());
        Bukkit.getPluginManager().registerEvents(new MapInitialize(), this);


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

}