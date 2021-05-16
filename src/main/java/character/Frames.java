package character;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Frames {
    public static Image[] RIGHT_WALK;
    public static Image[] LEFT_WALK;
    public static Image[] RIGHT_STAY;
    public static Image[] LEFT_STAY;

    public static final Map<String, Image[]> OBJ_SPRITE_MAP = new ConcurrentHashMap<>();

    static {
        try {
            /* Player sprites */
            RIGHT_WALK = new Image[]{new Image("character/right/1.png"),
                    new Image("character/right/2.png"),
                    new Image("character/right/3.png"),
                    new Image("character/right/4.png"),
                    new Image("character/right/5.png"),
                    new Image("character/right/6.png"),
                    new Image("character/right/7.png"),
                    new Image("character/right/8.png")};

            LEFT_WALK = new Image[]{new Image("character/left/1.png"),
                    new Image("character/left/2.png"),
                    new Image("character/left/3.png"),
                    new Image("character/left/4.png"),
                    new Image("character/left/5.png"),
                    new Image("character/left/6.png"),
                    new Image("character/left/7.png"),
                    new Image("character/left/8.png")};

            RIGHT_STAY = new Image[]{new Image("character/right/stay/1.png")};

            LEFT_STAY = new Image[]{new Image("character/left/stay/1.png")};

            /* Level objects sprites */
            final Sprites SPRITES = (path -> {
                String[] pathLineArr = path.split("/");
                final Integer spNum = Integer.valueOf(pathLineArr[pathLineArr.length - 1]);
                final Image[] imgSet = new Image[spNum];

                int n = 1;
                boolean loaded = true;
                for (int i = 0; i < spNum; i ++) {
                    try {
                        imgSet[i] = new Image(path + "/" + n + ".png");
                    } catch (SlickException se) {
                        System.out.println("Error to load img: " + se.getMessage());
                        loaded = false;
                    }
                    n++;
                }

                return loaded ? imgSet : null;
            });

            OBJ_SPRITE_MAP.put("brdPgnSit8", SPRITES.get("sprites/bird/pigeon/siting/8"));
            OBJ_SPRITE_MAP.put("corgi24", SPRITES.get("sprites/dog/corgi/staying/30"));
            OBJ_SPRITE_MAP.put("firePlHome12", SPRITES.get("sprites/fireplace/home/11"));

        } catch (SlickException se) {
            System.out.println(se.getMessage());
        }
    }

    public static Image[] get(final String code) throws SlickException {
        final Image[] sprites = OBJ_SPRITE_MAP.get(code);
        if (sprites == null)
            throw new SlickException("No sprites found by code: " + code);

        return sprites;
    }

    @FunctionalInterface
    private interface Sprites {
        Image[] get(final String path);
    }
}
