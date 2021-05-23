package game.screen;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class TextViewer {
    private static final ReentrantReadWriteLock rwL = new ReentrantReadWriteLock();
    private static final ReentrantReadWriteLock.ReadLock rL = rwL.readLock();
    private static final ReentrantReadWriteLock.WriteLock wL = rwL.writeLock();

    public static final long SHOW_TXT_DUR = 3000;
    public static boolean showingText;

    public static void setShowing() {
        wL.lock();
        try {
            showingText = true;
        } finally {
            wL.unlock();
        }
    }

    public static void setShown() {
        wL.lock();
        try {
            showingText = false;
        } finally {
            wL.unlock();
        }
    }

    public static boolean isShowing() {
        rL.lock();
        try {
            return showingText;
        } finally {
            rL.unlock();
        }
    }
}
