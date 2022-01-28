package net.rapust.qrcodemap.event;

import net.rapust.qrcodemap.Main;
import net.rapust.qrcodemap.util.ImageCreator;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.MapInitializeEvent;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;

import java.awt.image.BufferedImage;
import java.io.File;

public class MapInitialize implements Listener {

    @EventHandler
    public void onMapInitialize(MapInitializeEvent e) {
        MapView mapView = e.getMap();
        File mapsData = new File(Main.getInstance().getDataFolder()+File.separator+"data.yml");
        FileConfiguration maps = YamlConfiguration.loadConfiguration(mapsData);
        String data = maps.getString(String.valueOf(mapView.getId()));
        if (data == null) return;
        BufferedImage image = ImageCreator.generateQRcode(data);
        mapView.getRenderers().clear();
        mapView.addRenderer(new MapRenderer() {
            @Override
            public void render(MapView mapView, MapCanvas mapCanvas, Player player) {
                mapCanvas.drawImage(0, 0, image);
            }
        });
    }

}
