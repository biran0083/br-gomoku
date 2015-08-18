package common.boardclass.basic;

import static org.junit.Assert.assertEquals;

import common.Constants;
import common.StoneType;

import common.pattern.Pattern;
import org.junit.Test;

public class PatternsWithIndexTest {

  private Patterns patterns = new Patterns();

  @Test
  public void testGet() {
    for (int i = 0; i < Constants.BOARD_SIZE; i++) {
      for (int j = 0; j < Constants.BOARD_SIZE; j++) {
        for (StoneType stoneType : new StoneType[] {StoneType.BLACK, StoneType.WHITE}) {
          for (Pattern pattern : patterns.get(i, j, stoneType)) {
            assertEquals(stoneType.toString().charAt(0),
                pattern.toString().split("\n")[i].charAt(j));
          }
        }
      }
    }
  }
}