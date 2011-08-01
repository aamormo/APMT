package com.petproject.tools.apmt.common.utils;

import static org.junit.Assert.*;

import org.junit.Test;

import com.petproject.tools.apmt.common.utils.NumericUtils;

public class NumericUtilsTest {

	@Test
	public void is_a_number() {
		assertTrue(NumericUtils.isNumber("-1"));
		assertTrue(NumericUtils.isNumber("10000"));
		assertTrue(NumericUtils.isNumber("100.00"));
		assertTrue(NumericUtils.isNumber("+1"));
		assertTrue(NumericUtils.isNumber("00001"));
		assertTrue(NumericUtils.isNumber(" 100"));
		assertTrue(NumericUtils.isNumber(".5"));
	}

	@Test
	public void is_not_a_number() {
		assertFalse(NumericUtils.isNumber("A"));
		assertFalse(NumericUtils.isNumber("100A"));
	}

}
