package net.rapust.qrcodemap.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import lombok.SneakyThrows;
import net.rapust.qrcodemap.Main;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.awt.image.BufferedImage;
import java.util.HashMap;

public class ImageCreator {

    String data;
    String charset = "UTF-8";

    public ImageCreator(String data) {
        this.data = data;
    }

    public void generate(Player player) {
        BufferedImage image = generateQRcode();

        ItemStack map = MapCreator.generateMap(image, player, data);

        HashMap<ItemStack, String> info = new HashMap<>();
        info.put(map, data);
        Main.itemsToGive.put(player, info);

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
