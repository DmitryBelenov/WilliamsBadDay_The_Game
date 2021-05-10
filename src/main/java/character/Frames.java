package character;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Frames {
    public static Image[] RIGHT_WALK;
    public static Image[] LEFT_WALK;
    public static Image[] RIGHT_STAY;
    public static Image[] LEFT_STAY;

    static {
        try {
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
        } catch (SlickException se) {
            System.out.println(se.getMessage());
        }
    }
}
