package com.schneide.abas.tdd.gol;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class RulesTest {

	@Test
	public void starvation() {
		assertThat(new Rules().shouldBeAlive(true, 0)).isFalse();
		assertThat(new Rules().shouldBeAlive(true, 1)).isFalse();
	}

	@Test
	public void continuation() {
		assertThat(new Rules().shouldBeAlive(true, 2)).isTrue();
		assertThat(new Rules().shouldBeAlive(false, 2)).isFalse();
	}

	@Test
	public void birth() {
		assertThat(new Rules().shouldBeAlive(true, 3)).isTrue();
		assertThat(new Rules().shouldBeAlive(false, 3)).isTrue();
	}

	@Test
	public void overcrowding() {
		assertThat(new Rules().shouldBeAlive(true, 4)).isFalse();
		assertThat(new Rules().shouldBeAlive(true, 5)).isFalse();
		assertThat(new Rules().shouldBeAlive(true, 6)).isFalse();
		assertThat(new Rules().shouldBeAlive(true, 7)).isFalse();
		assertThat(new Rules().shouldBeAlive(true, 8)).isFalse();
		assertThat(new Rules().shouldBeAlive(false, 4)).isFalse();
	}
}
