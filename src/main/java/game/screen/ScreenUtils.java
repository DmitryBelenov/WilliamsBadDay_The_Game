package game.screen;

import camera.Camera;
import game.MainGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ScreenUtils {

    private static final ReentrantReadWriteLock rwL = new ReentrantReadWriteLock();
    private static final ReentrantReadWriteLock.ReadLock rL = rwL.readLock();
    private static final ReentrantReadWriteLock.WriteLock wL = rwL.writeLock();

    public static int shownIdx = -1;

    public static void showTxt(String txt, Graphics gr, Camera camera, int idx) {
        showStart();
        float x = camera.getViewPort().getX() + 20;
        float y = camera.getViewPort().getY() + (MainGame.HEIGHT - 80);
        Rectangle txtArea = new Rectangle(x, y, MainGame.WIDTH - 40, 50);
//        long waitFor = System.currentTimeMillis() + 3000;
//        while (System.currentTimeMillis() < waitFor) {
        gr.setColor(Color.black);
        gr.fill(txtArea);
        gr.setColor(Color.white);
        gr.drawString(txt, x + 10, y + 10);
        gr.setColor(Color.transparent);
        setShown(idx);
    }

    public static boolean isShown(final int idx) {
        rL.lock();
        try {
            return shownIdx == idx;
        } finally {
            rL.unlock();
        }
    }

    public static void showStart() {
        wL.lock();
        try {
            shownIdx = -1;
        } finally {
            wL.unlock();
        }
    }

    public static void setShown(final int idx) {
        wL.lock();
        try {
            shownIdx = idx;
        } finally {
            wL.unlock();
        }
    }
}
