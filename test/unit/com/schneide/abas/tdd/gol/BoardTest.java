package com.schneide.abas.tdd.gol;

import static com.schneide.abas.tdd.gol.Board.at;
import static org.assertj.core.api.Assertions.assertThat;

import java.awt.Point;

import org.assertj.core.api.Assertions;
import org.junit.Test;

public class BoardTest {

	@Test
	public void positionsHaveValueBasedEquality() {
		Assertions.assertThat(Board.at(0, 1)).isEqualTo(Board.at(0, 1));
	}

	@Test
	public void boardsAreEqualBasedOnLiveCells() {
		Board first = new Board(Board.at(2, 2), Board.at(3, 4));
		Board second = new Board(Board.at(2, 2), Board.at(3, 4));
		assertThat(first).isEqualTo(second);
	}

	@Test
	public void differentBoardsAreDifferent() {
		Board first = new Board(Board.at(5, 5));
		Board second = new Board(Board.at(5, 5), Board.at(6, 7));
		assertThat(first).isNotEqualTo(second);
	}

	@Test
	public void neighbourCountIsZeroWithoutLiveNeighbours() {
		Point targetPosition = at(3, 3);
		Board target = new Board(targetPosition);
		assertThat(target.neighboursAround(targetPosition)).isZero();
	}

	@Test
	public void neighbourCountOperatesVertically() {
		Point targetPosition = at(3, 3);
		Board target = new Board(targetPosition, at(3, 2), at(3, 4));
		assertThat(target.neighboursAround(targetPosition)).isEqualTo(2);
	}

	@Test
	public void neighbourCountOperatesHorizontally() {
		Point targetPosition = at(3, 3);
		Board target = new Board(targetPosition, at(2, 2), at(4, 4));
		assertThat(target.neighboursAround(targetPosition)).isEqualTo(2);
	}

	@Test
	public void fullNeighbourhood() {
		Point targetPosition = at(3, 3);
		Board target = new Board(
				targetPosition,
				at(2, 2),
				at(2, 3),
				at(2, 4),
				at(3, 2),
				at(3, 4),
				at(4, 2),
				at(4, 3),
				at(4, 4));
		assertThat(target.neighboursAround(targetPosition)).isEqualTo(8);
	}

	@Test
	public void starvation() {
		Board target = new Board(at(0, 0), at(0, 1));
		Board nextGeneration = target.nextGeneration();
		assertThat(nextGeneration).isEqualTo(Board.empty);
	}

	@Test
	public void glider() {
		Board startPosition = new Board(
				at(0, 0),
				at(1, 1),
				at(1, 2),
				at(0, 2),
				at(-1, 2));
		Board firstStep = startPosition.nextGeneration();
		assertThat(firstStep).isEqualTo(new Board(
				at(-1, 1),
				at(1, 1),
				at(0, 2),
				at(1, 2),
				at(0, 3)));
		Board secondStep = firstStep.nextGeneration();
		assertThat(secondStep).isEqualTo(new Board(
				at(-1, 2),
				at(0, 3),
				at(1, 1),
				at(1, 2),
				at(1, 3)));
		Board thirdStep = secondStep.nextGeneration();
		assertThat(thirdStep).isEqualTo(new Board(
				at(0, 1),
				at(0, 3),
				at(1, 2),
				at(1, 3),
				at(2, 2)));
		//Board cycleCompleted = thirdStep.nextGeneration();
		//assertThat(cycleCompleted).isEqualTo(startPosition);
	}

//	@Test
//	public void continuation() {
//		Board target = new Board(at(0, 0), at(0, 1), at(-1, -1));
//		Board nextGeneration = target.nextGeneration();
//		System.out.println(nextGeneration);
//		assertThat(nextGeneration).isEqualTo(new Board(at(0, 0)));
//	}
}
