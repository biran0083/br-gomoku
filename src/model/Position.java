package model;

import common.Constants;
import player.minmax.PositionTransformer;

public class Position {

	private final int i;
	private final int j;

  private static final Position[][] cached;

  static {
    // row index can be up to 2 * board size - 1 because we sometimes rotate board by 45 degree.
    cached = new Position[2 * Constants.BOARD_SIZE - 1][Constants.BOARD_SIZE];
    for (int i = 0; i < 2 * Constants.BOARD_SIZE - 1; i++)
      for (int j = 0; j < Constants.BOARD_SIZE; j++) {
        cached[i][j] = new Position(i, j);
      }
  }

	private Position(int i, int j) {
		this.i = i;
		this.j = j;
	}

	public static Position create(int i, int j) {
		return cached[i][j];
	}

  public Position transform(PositionTransformer transformer) {
    if (transformer == PositionTransformer.IDENTITY) {
      return this;
    }
    return create(transformer.getI(i, j), transformer.getJ(i, j));
  }

	public int getRowIndex() {
		return i;
	}

	public int getColumnIndex() {
		return j;
	}

  @Override
  public String toString() {
    return "[" + i + ", " + j + "]";
  }

  @Override
  public int hashCode() {
    return i * Constants.BOARD_SIZE + j;
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof Position)) {
      return false;
    }
    Position p = (Position) o;
    return p.i == i && p.j == j;
  }
}