package plot;

import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.*;

public class Plot {
    private static final ReentrantReadWriteLock rwL = new ReentrantReadWriteLock();
    private static final ReadLock rL = rwL.readLock();
    private static final WriteLock wL = rwL.writeLock();

    public static volatile String curCode;

    private static String[] plot;
    private static int idx;

    public static synchronized void init(final String[] plotArr) {
        plot = plotArr;
        curCode = plot[idx];
    }

    public static void shiftFw() {
        wL.lock();
        try {
            if (idx == plot.length - 1) {
                return;
            }
            idx++;
            curCode = plot[idx];
        } finally {
            wL.unlock();
        }
    }

    public static void shiftBw() {
        wL.lock();
        try {
            if (idx == 0) {
                return;
            }
            idx --;
            curCode = plot[idx];
        } finally {
            wL.unlock();
        }
    }

    public static String curCode() {
        rL.lock();
        try {
            return curCode;
        } finally {
            rL.unlock();
        }
    }
}
