package common.boardclass;

import com.google.common.collect.Iterables;
import common.*;
import common.pattern.Pattern;

import model.GameBoard;

/**
 * Class of bit boards by PositionTransformer operation.
 */
class BoardClassImpl extends AbstractBoardClass {

  private static final Pattern.Factory PATTERNS = Pattern.DEFAULT_FACTORY;

  private static final BoardClassImpl EMPTY_BOARD = new BoardClassImpl();

  BoardClassImpl() {
    super();
  }

  BoardClassImpl(AbstractBoardClass boardClass, int i, int j, StoneType stoneType) {
    super(boardClass, i, j, stoneType);
  }

  BoardClassImpl(GameBoard gameBoard) {
    super(gameBoard);
  }

  @Override
  public BoardClass withPositionSet(int i, int j, StoneType stoneType) {
    return new BoardClassImpl(this, i, j, stoneType);
  }

  @Override
  public boolean matchesAny(StoneType stoneType, PatternType patternType) {
    return Iterables.any(PATTERNS.get(stoneType, patternType), (p) -> p.matches(this));
  }

  @Override
  public Iterable<Pattern> getMatchingPatterns(StoneType stoneType, PatternType patternType) {
    return Iterables.filter(PATTERNS.get(stoneType, patternType), (p) -> p.matches(this));
  }

  static class Factory implements BoardClass.Factory {

    @Override
    public BoardClass fromGameBoard(GameBoard gameBoard) {
      if (gameBoard instanceof BoardClassImpl) {
        return (BoardClassImpl) gameBoard;
      }
      return new BoardClassImpl(gameBoard);
    }

    @Override
    public BoardClass getEmptyBoard() {
      return EMPTY_BOARD;
    }
  }
}