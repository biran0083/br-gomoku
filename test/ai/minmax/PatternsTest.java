package ai.minmax;

import static common.BoardClassUtil.B;
import static common.BoardClassUtil.E;
import static common.BoardClassUtil.W;
import static common.BoardClassUtil.createBoard;
import static org.junit.Assert.assertEquals;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;

import common.BoardClass;
import common.Pattern;
import common.Patterns;
import common.PositionTransformer;
import common.StoneType;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import model.Position;

/**
 * Unit tests for Patterns class.
 */
public class PatternsTest {

  @Test
  public void testGoalNumber() {
    assertEquals(572, Patterns.BLACK_GOALS.size());
    assertEquals(572, Patterns.WHITE_GOALS.size());
  }

  @Test
  public void testHorizontalGoalMatch() {
    testSinglePattern(
        new StoneType[][]{
            {B, B, B, B, B},
        },
        PositionTransformer.IDENTITY,
        StoneType.BLACK,
        Patterns.BLACK_GOALS);

    testSinglePattern(
        new StoneType[][]{
            {B, B, W, B, B},
            {E, E, W, W, W, W, W},
        },
        PositionTransformer.IDENTITY,
        StoneType.WHITE,
        Patterns.WHITE_GOALS);
  }

  @Test
  public void testVerticalGoalMatch() {
    testSinglePattern(
        new StoneType[][]{
            {B},
            {B},
            {B},
            {B},
            {B},
        },
        PositionTransformer.CLOCK_90,
        StoneType.BLACK,
        Patterns.BLACK_GOALS);

    testSinglePattern(
        new StoneType[][]{
            {},
            {},
            {E, W},
            {E, W},
            {E, W},
            {E, W},
            {E, W},
        },
        PositionTransformer.CLOCK_90,
        StoneType.WHITE,
        Patterns.WHITE_GOALS);
  }

  @Test
  public void testDiagonalGoalMatch() {
    testSinglePattern(
        new StoneType[][]{
            {B},
            {E, B},
            {E, E, B},
            {E, E, E, B},
            {E, E, E, E, B},
        },
        PositionTransformer.LEFT_DIAGONAL,
        StoneType.BLACK,
        Patterns.BLACK_GOALS);

    testSinglePattern(
        new StoneType[][]{
            {E, E, E, E, W},
            {E, E, E, W},
            {E, E, W},
            {E, W},
            {W},
        },
        PositionTransformer.RIGHT_DIAGONAL,
        StoneType.WHITE,
        Patterns.WHITE_GOALS);
  }

  @Test
  public void testHorizontalStraitFourMatch() {
    testSinglePattern(
        new StoneType[][]{
            {B, B, B, B, E},
        },
        PositionTransformer.IDENTITY,
        StoneType.BLACK,
        Patterns.BLACK_STRAIT_FOUR);

    testSinglePattern(
        new StoneType[][]{
            {B, B, W, B, B},
            {E, E, E, W, W, W, W, B},
        },
        PositionTransformer.IDENTITY,
        StoneType.WHITE,
        Patterns.WHITE_STRAIT_FOUR);
  }

  @Test
  public void testVerticalStraitFourMatch() {
    testSinglePattern(
        new StoneType[][]{
            {B},
            {B},
            {B},
            {B},
        },
        PositionTransformer.CLOCK_90,
        StoneType.BLACK,
        Patterns.BLACK_STRAIT_FOUR);

    testSinglePattern(
        new StoneType[][]{
            {E, W},
            {E, W},
            {E, W},
            {E, W},
        },
        PositionTransformer.CLOCK_90,
        StoneType.WHITE,
        Patterns.WHITE_STRAIT_FOUR);
  }

  @Test
  public void testDiagonalStraitFourMatch() {
    testSinglePattern(
        new StoneType[][]{
            {B},
            {E, B},
            {E, E, B},
            {E, E, E, B},
        },
        PositionTransformer.LEFT_DIAGONAL,
        StoneType.BLACK,
        Patterns.BLACK_STRAIT_FOUR);

    testSinglePattern(
        new StoneType[][]{
            {E, E, E, E, W},
            {E, E, E, W},
            {E, E, W},
            {E, W},
            {E},
        },
        PositionTransformer.RIGHT_DIAGONAL,
        StoneType.WHITE,
        Patterns.WHITE_STRAIT_FOUR);
  }

  @Test
  public void testHorizontalThreeMatch() {
    testSinglePattern(
        new StoneType[][]{
            {E, B, B, B, E, E},
        },
        PositionTransformer.IDENTITY,
        StoneType.BLACK,
        Patterns.BLACK_THREE);

    testSinglePattern(
        new StoneType[][]{
            {B, B, W, B, B},
            {E, E, E, W, W, W, E, B},
        },
        PositionTransformer.IDENTITY,
        StoneType.WHITE,
        Patterns.WHITE_THREE);
  }

  @Test
  public void testVerticalThreeMatch() {
    testSinglePattern(
        new StoneType[][]{
            {E},
            {B},
            {B},
            {B},
        },
        PositionTransformer.CLOCK_90,
        StoneType.BLACK,
        Patterns.BLACK_THREE);

    testSinglePattern(
        new StoneType[][]{
            {},
            {E, W},
            {E, W},
            {E, W},
        },
        PositionTransformer.CLOCK_90,
        StoneType.WHITE,
        Patterns.WHITE_THREE);
  }

  @Test
  public void testDiagonalThreeMatch() {
    testSinglePattern(
        new StoneType[][]{
            {E},
            {E, B},
            {E, E, B},
            {E, E, E, B},
        },
        PositionTransformer.LEFT_DIAGONAL,
        StoneType.BLACK,
        Patterns.BLACK_THREE);

    testSinglePattern(
        new StoneType[][]{
            {},
            {},
            {E, E, E, W},
            {E, E, W},
            {E, W},
            {E},
        },
        PositionTransformer.RIGHT_DIAGONAL,
        StoneType.WHITE,
        Patterns.WHITE_THREE);
  }

  @Test
  public void testDefensiveMove() {
    List<Position> temp = new ArrayList<>();
    for (Pattern p :
        Iterables.concat(
            Patterns.BLACK_THREE,
            Patterns.WHITE_THREE,
            Patterns.BLACK_STRAIT_FOUR,
            Patterns.WHITE_STRAIT_FOUR,
            Patterns.BLACK_OPEN_FOUR,
            Patterns.WHITE_OPEN_FOUR)) {
      // make sure all defensive moves are valid positions.
      temp.addAll(p.getDefensiveMoves());
    }
  }

  private void testSinglePattern(StoneType[][] board,
                                 PositionTransformer orientation,
                                 StoneType stoneType,
                                 ImmutableList<Pattern> candidates) {
    BoardClass boardClass = createBoard(board);

    final List<Pattern> patterns = new ArrayList<>();
    boardClass.filterMatchedPatterns(candidates)
        .forEach(p -> {
          patterns.add(p);
          assertEquals(orientation, p.getTransformer());
          assertEquals(stoneType, p.getStoneType());
        });
    assertEquals(1L, patterns.size());
  }
}