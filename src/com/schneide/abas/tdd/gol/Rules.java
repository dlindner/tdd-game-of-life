package com.schneide.abas.tdd.gol;

public class Rules {

	public Rules() {
		super();
	}

	public boolean shouldBeAlive(boolean isCurrentlyAlive, int aliveNeighbours) {
		return ((3 == aliveNeighbours) || (isCurrentlyAlive && (2 == aliveNeighbours)));
	}
}
