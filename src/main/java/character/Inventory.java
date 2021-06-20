package character;

import character.item.Item;
import org.newdawn.slick.SlickException;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

public class Inventory {

    private static final ReentrantReadWriteLock rwL = new ReentrantReadWriteLock();
    private static final ReentrantReadWriteLock.ReadLock rL = rwL.readLock();
    private static final ReentrantReadWriteLock.WriteLock wL = rwL.writeLock();

    private static final Map<Item, Integer> invMap = new HashMap<>();
    private static final Set<String> invCodes = new HashSet<>();

    public static void init() throws SlickException {
        invCodes.addAll(invMap.keySet().stream().map(Item::getCode).collect(Collectors.toList()));
    }

    public static Map<Item, Integer> get() {
        rL.lock();
        try {
            return invMap;
        } finally {
            rL.unlock();
        }
    }

    public static Integer getCount(Item item) {
        rL.lock();
        try {
            return invMap.get(item);
        } finally {
            rL.unlock();
        }
    }

    public static boolean contains(final String code) {
        rL.lock();
        try {
            return invCodes.contains(code);
        } finally {
            rL.unlock();
        }
    }

    public static boolean contains(final Item item) {
        rL.lock();
        try {
            return invMap.containsKey(item) && invCodes.contains(item.getCode());
        } finally {
            rL.unlock();
        }
    }

    public static void putCnt(final Item item, final Integer putCnt) {
        wL.lock();
        try {
            if (invMap.containsKey(item)) {
                invMap.put(item, invMap.get(item) + putCnt);
            } else {
                invMap.put(item, putCnt);
            }
            invCodes.add(item.getCode());
        } finally {
            wL.unlock();
        }
    }

    public static void drop(final Item item) {
        wL.lock();
        try {
            if (invMap.containsKey(item)) {
                invMap.remove(item);
                invCodes.remove(item.getCode());
            }
        } finally {
            wL.unlock();
        }
    }

    public static void dropCnt(final Item item, final Integer cntToDrop) {
        wL.lock();
        try {
            if (invMap.containsKey(item)) {
                if (invMap.get(item) == 1) {
                    invMap.remove(item);
                    invCodes.remove(item.getCode());
                } else {
                    invMap.put(item, invMap.get(item) - cntToDrop);
                }
            }
        } finally {
            wL.unlock();
        }
    }
}
