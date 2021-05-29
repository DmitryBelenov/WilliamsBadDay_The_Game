package plot;

import character.item.Item;
import org.newdawn.slick.Animation;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Image;
import params.GameParams;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ItemHolder {
    private static final Map<String, Item> itMap = new ConcurrentHashMap<>();

    public static void init() throws SlickException {
        cacheItems();
    }

    public static Item get(final String code) {
        return itMap.get(code);
    }

    private static void cacheItems() throws SlickException {
        InputStream is = ItemHolder.class.getClassLoader().getResourceAsStream("items/itemList.item");
        try {
            if (is != null) {
                final InputStreamReader isUTFCs = new InputStreamReader(is, StandardCharsets.UTF_8);
                try (BufferedReader br = new BufferedReader(isUTFCs)) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        if (!line.startsWith("--")) { // -- comment
                            final String[] lineArr = line.split(":");
                            final String code = lineArr[0].intern();
                            final int animFramesLen = Integer.parseInt(lineArr[2].intern());
                            if (animFramesLen == 0)
                                throw new SlickException("Zero length of item frames: " + code);

                            final Image[] frames;
                            if (animFramesLen == 1) {
                                frames = new Image[]{new Image(lineArr[1].intern() + "1.png")};
                            } else {
                                frames = new Image[animFramesLen];
                                int imgCnt = 1;
                                for (int i = 0; i < animFramesLen; i ++) {
                                    frames[i] = new Image(lineArr[1].intern() + imgCnt +".png");
                                    imgCnt ++;
                                }
                            }

                            final Animation itAnim = new Animation(frames,
                                    lineArr.length > 3 ? Integer.parseInt(lineArr[3].intern()) : GameParams.INV_ITEM_DEF_DUR);

                            itMap.put(code, new Item(code, itAnim));
                        }
                    }
                }
                if (itMap.size() == 0) {
                    throw new SlickException("No game items was initialized");
                }
            } else {
                throw new SlickException("Items descriptor not found in resource path");
            }
        } catch (Exception e) {
            throw new SlickException("Parse items descriptor error " + e);
        }
    }
}
