package plot;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class QuestMenu {
    private static final ReentrantReadWriteLock rwL = new ReentrantReadWriteLock();
    private static final ReentrantReadWriteLock.ReadLock rL = rwL.readLock();
    private static final ReentrantReadWriteLock.WriteLock wL = rwL.writeLock();

    private static String currentQuest = "empty";

    public static void set(String txt) {
        wL.lock();
        try {
            currentQuest = txt;
        } finally {
            wL.unlock();
        }
    }

    public static String last() {
        rL.lock();
        try {
            return "Quest line > " + currentQuest;
        } finally {
            rL.unlock();
        }
    }
}
