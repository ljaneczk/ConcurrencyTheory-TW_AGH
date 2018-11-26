package task1;

import java.awt.image.BufferedImage;
import java.util.concurrent.Callable;

public class MandelbrotWorker implements Callable<Integer> {
    private final int x1, y1, x2, y2, max, width, height;
    private double zoom;
    private BufferedImage image;

    public MandelbrotWorker(int x1, int y1, int x2, int y2, int max, int width, int height, double zoom, BufferedImage image) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.max = max;
        this.width = width;
        this.height = height;
        this.zoom = zoom;
        this.image = image;
    }

    @Override
    public Integer call() {
        final int w_2 = width / 2, h_2 = height / 2;
        for (int x = x1; x <= x2; x++) {
            for (int y = y1; y <= y2; y++) {
                int iteration = max;
                double zx, zy, cX, cY, tmp;
                zx = zy = 0;
                cX = (x - w_2) / zoom;
                cY = (y - h_2) / zoom;
                while (zx * zx + zy * zy < 4 && iteration > 0) {
                    tmp = zx * zx - zy * zy + cX;
                    zy = 2.0 * zx * zy + cY;
                    zx = tmp;
                    iteration--;
                }
                image.setRGB(x, y, iteration | (iteration << 8));
            }
        }
        return 0;
    }
}
