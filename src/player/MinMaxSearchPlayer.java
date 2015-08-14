package player;

import ai.minmax.MinMaxSearch;
import ai.minmax.transitiontable.SmartTransitionTable;
import common.StoneType;
import common.boardclass.BoardFactories;
import model.GameBoard;
import model.Position;

public class MinMaxSearchPlayer implements Player {

  private final String name;
  private final StoneType stoneType;
  private final MinMaxSearch minMaxSearch = MinMaxSearch.newBuilder()
      .withBoardClassFactory(BoardFactories.PRE_COMPUTE_MATCHING_FACTORY)
      .withTransitionTableFactory(() -> new SmartTransitionTable())
      .withMaxDepth(6)
      .withRandomSampleBranchCandidates(15)
      .build();

  public MinMaxSearchPlayer(String s, StoneType stoneType) {
    this.name = s;
    this.stoneType = stoneType;
  }

  @Override
  public String toString() {
    return name;
  }

  @Override
  public Position makeMove(GameBoard gameBoard) {
    return minMaxSearch.nextMove(gameBoard, stoneType);
  }

  @Override
  public StoneType getStoneType() {
    return stoneType;
  }

  @Override
  public boolean isHuman() {
    return false;
  }
}
