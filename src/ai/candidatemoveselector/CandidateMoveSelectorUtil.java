package ai.candidatemoveselector;

import com.google.common.collect.Sets;
import common.Constants;
import common.pattern.PatternType;
import common.PositionTransformer;
import common.StoneType;
import common.boardclass.BitBoard;
import common.boardclass.BoardClass;
import common.pattern.Pattern;

import java.util.*;

import model.Position;

import static java.util.Collections.emptyList;
import static java.util.Collections.singleton;

/**
 * Utility methods for candidate selector.
 */
public class CandidateMoveSelectorUtil {

  public static Collection<Position> centerIfEmptyBoard(BoardClass<?> boardClass, StoneType stoneType) {
    if (boardClass.isEmpty()) {
      return singleton(Position.of(Constants.BOARD_SIZE / 2, Constants.BOARD_SIZE / 2));
    }
    return emptyList();
  }

  public static Collection<Position> allDefendFour(BoardClass<?> boardClass, StoneType stoneType) {
    Set<Position> candidates = new HashSet<>();
    for (Pattern p : boardClass.getMatchingPatterns(stoneType.getOpponent(),
        PatternType.FOUR)) {
      candidates.addAll(p.getDefensiveMoves());
    }
    return candidates;
  }

  public static Collection<Position> minDefendFour(BoardClass<?> boardClass, StoneType stoneType) {
    return min(allDefendFour(boardClass, stoneType));
  }

  public static Collection<Position> allDefendThree(BoardClass<?> boardClass, StoneType stoneType) {
    Set<Position> candidates = new HashSet<>();
    for (Pattern p :
        boardClass.getMatchingPatterns(stoneType.getOpponent(), PatternType.THREE)) {
      candidates.addAll(p.getDefensiveMoves());
    }
    return candidates;
  }

  public static Collection<Position> defendThreeIntersections(BoardClass<?> boardClass, StoneType stoneType) {
    Set<Position> candidates = null;
    for (Pattern p :
        boardClass.getMatchingPatterns(stoneType.getOpponent(), PatternType.THREE)) {
      if (candidates == null) {
        candidates = p.getDefensiveMoves();
      } else {
        candidates = Sets.intersection(candidates, p.getDefensiveMoves());
      }
    }
    if (candidates == null) {
      // no pattern of three
      return emptyList();
    }
    return candidates;
  }

  public static Collection<Position> mostFrequentDefendThree(BoardClass<?> boardClass, StoneType stoneType) {
    List<Position> candidates = new ArrayList<>();
    for (Pattern p :
        boardClass.getMatchingPatterns(stoneType.getOpponent(), PatternType.THREE)) {
      candidates.addAll(p.getDefensiveMoves());
    }
    return mostFrequentElement(candidates);
  }

  public static Collection<Position> allOffendFour(BoardClass<?> boardClass, StoneType stoneType) {
    Set<Position> candidates = new HashSet<>();
    for (Pattern p : boardClass.getMatchingPatterns(stoneType, PatternType.FOUR)) {
      candidates.addAll(p.getDefensiveMoves());
    }
    return candidates;
  }

  public static Collection<Position> minOffendFour(BoardClass<?> boardClass, StoneType stoneType) {
    return min(allOffendFour(boardClass, stoneType));
  }

  public static Collection<Position> allOffendThree(BoardClass<?> boardClass, StoneType stoneType) {
    Set<Position> result = new HashSet<>();
    for (Pattern p : boardClass.getMatchingPatterns(stoneType, PatternType.THREE)) {
      result.addAll(p.getDefensiveMoves());
    }
    return result;
  }

  public static CandidateMovesSelectorBuilder.MoveSelector neighbour(int maxMoves) {
    return (boardClass, stoneType) -> randomSample(getNeighboringMoves(boardClass), maxMoves);
  }

  public static Collection<Position> getNeighboringMoves(BoardClass boardClass) {
    Set<Position> result = new HashSet<>();
    BitBoard board = boardClass.getBoard(PositionTransformer.IDENTITY);
    int[][] d = {{1, 0}, {1, 1}, {0, 1}, {-1, 1}, {-1, 0}, {-1, -1}, {0, -1}, {1, -1}};
    for (int i = 0; i < Constants.BOARD_SIZE; i++) {
      for (int j = 0; j < Constants.BOARD_SIZE; j++) {
        if (board.get(i, j) != StoneType.NOTHING) {
          for (int k = 0; k < d.length; k++) {
            int ti = i + d[k][0], tj = j + d[k][1];
            if (Position.isValid(ti, tj) && board.get(ti, tj) == StoneType.NOTHING) {
              Position pos = Position.of(ti, tj);
              result.add(pos);
            }
          }
        }
      }
    }
    return result;
  }

  private static Collection<Position> min(Collection<Position> candidates) {
    if (candidates.isEmpty()) {
      return emptyList();
    }
    return singleton(Collections.min(candidates));
  }

  private static Collection<Position> mostFrequentElement(List<Position> candidates) {
    if (candidates.isEmpty()) {
      return emptyList();
    }
    int maxCt = 0;
    Position result = null;
    Map<Position, Integer> counter = new HashMap<>();
    for (Position position : candidates) {
      int newCount;
      if (!counter.containsKey(position)) {
        newCount = 1;
      } else {
        newCount = counter.get(position) + 1;
      }
      if (newCount > maxCt) {
        maxCt = newCount;
        result = position;
      } else if (newCount == maxCt && position.compareTo(result) < 0) {
        result = position;
      }
      counter.put(position, newCount);
    }
    return singleton(result);
  }

  private static <T> Collection<T> randomSample(Collection<T> c, int n) {
    if (n > c.size()) {
      return c;
    }
    List<T> sample = new ArrayList<>(c);
    Collections.shuffle(sample);
    return sample.subList(0, n);
  }
}
