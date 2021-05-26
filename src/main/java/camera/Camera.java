package camera;

import game.MainGame;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.tiled.TiledMap;
import character.Character;
import params.GameParams;

public class Camera {

    public int x, y;
    public int mapWidth, mapHeight;

    public Rectangle viewPort;

    public Camera(TiledMap map) {
        x = 0;
        y = 0;
        viewPort = new Rectangle(0, 0, GameParams.WIDTH, GameParams.HEIGHT);
        this.mapWidth = map.getWidth() * map.getTileWidth();
        this.mapHeight = map.getHeight() * map.getTileHeight();
    }

    public void translate(Graphics g, Character character) {

        if (character.getX() - GameParams.WIDTH / 2 + 16 < 0) {
            x = 0;
        } else if (character.getX() + GameParams.WIDTH / 2.0 + 16 > mapWidth) {
            x = - mapWidth + GameParams.WIDTH;
        } else {
            x = - character.getX() + GameParams.WIDTH / 2 - 16;
        }

        if (character.getY() - GameParams.HEIGHT / 2 + 16 < 0) {
            y = 0;
        } else if (character.getY() + GameParams.HEIGHT / 2.0 + 16 > mapHeight) {
            y = - mapHeight + GameParams.HEIGHT;
        } else {
            y = - character.getY() + GameParams.HEIGHT / 2 - 16;
        }
        g.translate(x, y);

        viewPort.setX(- x);
        viewPort.setY(- y);
    }

    public Rectangle getViewPort() {
        return viewPort;
    }
}
