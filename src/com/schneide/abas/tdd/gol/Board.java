package com.schneide.abas.tdd.gol;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

public class Board {

	public static final Board empty = new Board();

	private final Set<Point> living;

	public Board(Point... living) {
		this(new HashSet<>(Arrays.asList(living)));
	}

	private Board(Set<Point> survivors) {
		super();
		this.living = survivors;
	}

	public static Point at(int column, int row) {
		return new Point(column, row);
	}

	public int neighboursAround(Point position) {
		int result = 0;
		for (int xOffset = -1; xOffset <= 1; xOffset++) {
			for (int yOffset = -1; yOffset <= 1; yOffset++) {
				if ((0 != xOffset || 0 != yOffset) && this.living.contains(at(position.x + xOffset, position.y + yOffset))) {
					result++;
				}
			}
		}
		return result;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((living == null) ? 0 : living.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Board other = (Board) obj;
		if (living == null) {
			if (other.living != null)
				return false;
		} else if (!living.equals(other.living))
			return false;
		return true;
	}

	public Board nextGeneration() {
		Set<Point> survivors = new HashSet<>();
		for (Point each : this.living) {
			applyRulesToVincinity(each).forEach(
					around -> around.ifPresent(survivors::add));
		}
		return new Board(survivors);
	}

	private Stream<Optional<Point>> applyRulesToVincinity(Point position) {
		List<Optional<Point>> result = new ArrayList<>();
		for (int xOffset = -1; xOffset <= 1; xOffset++) {
			for (int yOffset = -1; yOffset <= 1; yOffset++) {
				Point cursor = at(position.x + xOffset, position.y + yOffset);
				int aliveAround = neighboursAround(cursor);
				if (new Rules().shouldBeAlive(
						this.living.contains(cursor),
						aliveAround)) {
					result.add(Optional.of(cursor));
				}
			}
		}
		return result.stream();
	}

	@Override
	public String toString() {
		return "Board [living=" + living + "]";
	}
}
