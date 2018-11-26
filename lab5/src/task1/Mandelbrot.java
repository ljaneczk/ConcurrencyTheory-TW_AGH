package task1;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import javax.swing.JFrame;

public class Mandelbrot extends JFrame {

    private final int numberOfThreads, maxIteration;
    private final int strategy;         // 1: |tasks| ~ |threads|, 2: |tasks| ~ 10 * |threads|, 3: |tasks| = width * height
    private final int width, height;
    private final double zoom;
    private BufferedImage I;
    private double zx, zy, cX, cY, tmp;
    
    public Mandelbrot() throws Exception { this(1); }

    public Mandelbrot(int numberOfThreads) throws Exception { this(numberOfThreads, 1000); }

    public Mandelbrot(int numberOfThreads, int maxIteration) throws Exception { this(numberOfThreads, maxIteration, 1); }

    public Mandelbrot(int numberOfThreads, int maxIteration, int strategy) throws Exception { this(numberOfThreads, maxIteration, strategy, 800, 600, 150); }

    public Mandelbrot(int numberOfThreads, int maxIteration, int strategy, int width, int height, double zoom) throws Exception {
        super("Mandelbrot Set");
        this.numberOfThreads = numberOfThreads;
        this.maxIteration = maxIteration;
        this.strategy = strategy;
        this.width = width;
        this.height = height;
        this.zoom = zoom;
        setBounds(0, 0, width, height);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        calculate();
    }

    public void calculate() throws Exception {
        I = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        ExecutorService pool = Executors.newFixedThreadPool(numberOfThreads);
        List<Future<Integer>> codes = new ArrayList<>();
        if (strategy == 1) {
            int step = height / numberOfThreads, y1 = 0, y2;
            for (int i = 0; i+1 < numberOfThreads; i++) {
                y2 = y1 + step - 1;
                Future<Integer> value = pool.submit(new MandelbrotWorker(0, y1, width-1, y2, maxIteration, width, height, zoom, I));
                codes.add(value);
                y1 += step;
            }
            Future<Integer> value = pool.submit(new MandelbrotWorker(0, y1, width-1, height-1, maxIteration, width, height, zoom, I));
            value.get();
        }
        else
        if (strategy == 2) {
            int step = height / (10 * numberOfThreads), y1 = 0, y2;
            for (int i = 0; i+1 < 10 * numberOfThreads; i++) {
                y2 = y1 + step - 1;
                Future<Integer> value = pool.submit(new MandelbrotWorker(0, y1, width-1, y2, maxIteration, width, height, zoom, I));
                codes.add(value);
                y1 += step;
            }
            Future<Integer> value = pool.submit(new MandelbrotWorker(0, y1, width-1, height-1, maxIteration, width, height, zoom, I));
            value.get();
        }
        else
        if (strategy == 3) {
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    Future<Integer> value = pool.submit(new MandelbrotWorker(x, y, x, y, maxIteration, width, height, zoom, I));
                    codes.add(value);
                }
            }
        }
        for (Future<Integer> value : codes)
            value.get();
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(I, 0, 0, this);
    }
}