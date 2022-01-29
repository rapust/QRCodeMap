package net.rapust.qrcodemap.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import lombok.SneakyThrows;
import net.rapust.qrcodemap.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.MapMeta;
import java.awt.image.BufferedImage;
import java.io.File;

public class ImageCreator {

    String data;
    String charset = "UTF-8";

    public ImageCreator(String data) {
        this.data = data;
    }

    @SneakyThrows
    public void generate(Player player) {
        BufferedImage image = generateQRcode();

        ItemStack map = MapCreator.generateMap(image, player, data);

        player.setItemInHand(map);
        String id = String.valueOf(((MapMeta) map.getItemMeta()).getMapId());

        File mapsData = new File(Main.getInstance().getDataFolder()+File.separator+"data.yml");
        FileConfiguration maps = YamlConfiguration.loadConfiguration(mapsData);
        maps.set(id, data);
        maps.save(mapsData);

    }

    @SneakyThrows
    public BufferedImage generateQRcode() {
        BitMatrix matrix = new MultiFormatWriter().encode(new String(data.getBytes(charset), charset), BarcodeFormat.QR_CODE, 128, 128);
        return MatrixToImageWriter.toBufferedImage(matrix);
    }

    @SneakyThrows
    public static BufferedImage generateQRcode(String data) {
        BitMatrix matrix = new MultiFormatWriter().encode(new String(data.getBytes("UTF-8"), "UTF-8"), BarcodeFormat.QR_CODE, 128, 128);
        return MatrixToImageWriter.toBufferedImage(matrix);
    }
}
