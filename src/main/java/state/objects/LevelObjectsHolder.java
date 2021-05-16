package state.objects;

import character.Frames;
import character.XYPos;
import org.newdawn.slick.Animation;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import state.objects.logic.BasePlotTrigger;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class LevelObjectsHolder {

   private final List<LevelObject> objects;

   public LevelObjectsHolder(List<LevelObject> objects) {
      this.objects = objects;
   }

   public List<LevelObject> getObjects() {
      return objects;
   }

   private enum DescriptorLine {
      lvlCode, lo
   }

   private enum LinePairKey {
      nm, posX, posY, anim, animDur, collShW, collShH, plStCode, goToID, defTxt, reEnt
   }

   public static LevelObjectsHolder read(final String lvObjDescriptorFile) throws SlickException {
      InputStream is = LevelObjectsHolder.class.getClassLoader().getResourceAsStream("level/objects/" + lvObjDescriptorFile);
      List<LevelObject> loList;
      try {
         if (is != null) {
            loList = new ArrayList<>();
            final InputStreamReader isUTFCs = new InputStreamReader(is, StandardCharsets.UTF_8);
            try (BufferedReader br = new BufferedReader(isUTFCs)) {
               String line;
               while ((line = br.readLine()) != null) {
                  if (!line.startsWith("--")) { // -- comment
                     final String[] lineArr = line.split(":");
                     final String key = lineArr[0].intern();
                     final String val = lineArr[1].intern();
                     DescriptorLine dl = DescriptorLine.valueOf(key);
                     if (dl == DescriptorLine.lo) {
                        final LevelObject lo = parseLOLine(val);
                        loList.add(lo);
                     }
                  }
               }
            }
         } else {
            throw new SlickException("Level objects descriptor not found in " + lvObjDescriptorFile);
         }
      } catch (Exception e) {
         throw new SlickException("Parse level descriptor error " + e);
      }

      return new LevelObjectsHolder(loList.size() > 0 ? loList : null);
   }

   private static LevelObject parseLOLine(final String lineVal) throws SlickException {
      String objName = null;
      XYPos position = new XYPos();
      Animation objAnimation = null;
      Rectangle objCollShape = null;
      BasePlotTrigger objPlotTrigger = null;

      int frameDur = 100; // default level object duration
      float collShW = 0f;
      float collShH = 0f;
      int goToLvVId = -1;
      Boolean isRightReentrant = null;

      final String[] valPairs = lineVal.split(";");
      for (final String pair : valPairs) {
         final String[] pairArr = pair.split("=");
         final String key = pairArr[0];
         final String val = pairArr[1];
         if (LinePairKey.valueOf(key) == LinePairKey.nm) {
            objName = val;
         } else if (LinePairKey.valueOf(key) == LinePairKey.posX) {
            position.setX(Integer.parseInt(val));
         } else if (LinePairKey.valueOf(key) == LinePairKey.posY) {
            position.setY(Integer.parseInt(val));
         } else if (LinePairKey.valueOf(key) == LinePairKey.animDur) {
            frameDur = Integer.parseInt(val);
         } else if (LinePairKey.valueOf(key) == LinePairKey.anim) {
            objAnimation = new Animation(Frames.get(val), frameDur);
         } else if (LinePairKey.valueOf(key) == LinePairKey.collShW) {
            collShW = Float.parseFloat(val);
         } else if (LinePairKey.valueOf(key) == LinePairKey.collShH) {
            collShH = Float.parseFloat(val);
         }  else if (LinePairKey.valueOf(key) == LinePairKey.goToID) {
            goToLvVId = Integer.parseInt(val);
         } else if (LinePairKey.valueOf(key) == LinePairKey.plStCode) {
            objPlotTrigger = new BasePlotTrigger(val);
         } else if (LinePairKey.valueOf(key) == LinePairKey.defTxt) {
            if (objPlotTrigger != null) {
               objPlotTrigger.setNoActTxt(val);
            }
         } else if (LinePairKey.valueOf(key) == LinePairKey.reEnt) {
            isRightReentrant = val.equals("right") || val.equals("center");
         }
      }

      if (position.isZeroCoordinates())
         throw new SlickException("Object position not found");

      if (isRightReentrant == null && goToLvVId != -1)
         throw new SlickException("Object reentrant position for level exit not found");

      if (collShW != 0 && collShH != 0) {
         objCollShape = new Rectangle(position.getX(), position.getY(), collShW, collShH);
      }

      return new LevelObject(objName, goToLvVId, position, objAnimation, objCollShape, objPlotTrigger, isRightReentrant);
   }
}
