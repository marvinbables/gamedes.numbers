package com.me.numbersgame;

import java.util.Random;
import com.me.numbersgame.Number;

public class NumberResources {
	public static final String zero = "img/zero2.png";
	public static final String one = "img/one2.png";
	public static final String two = "img/two2.png";
	public static final String three = "img/three2.png";
	public static final String four = "img/four2.png";
	public static final String five = "img/five2.png";
	public static final String six = "img/six2.png";
	public static final String seven = "img/seven2.png";
	public static final String eight = "img/eight2.png";
	public static final String nine = "img/nine2.png";
	
	public static final String zeroSelected = "img/zero2_selected.png";
	public static final String oneSelected = "img/one2_selected.png";
	public static final String twoSelected = "img/two2_selected.png";
	public static final String threeSelected = "img/three2_selected.png";
	public static final String fourSelected = "img/four2_selected.png";
	public static final String fiveSelected = "img/five2_selected.png";
	public static final String sixSelected = "img/six2_selected.png";
	public static final String sevenSelected = "img/seven2_selected.png";
	public static final String eightSelected = "img/eight2_selected.png";
	public static final String nineSelected = "img/nine2_selected.png";
	
	
	public static Number randomNumber() {
		Random rand = new Random();
		int i = rand.nextInt(10);
		switch(i) {
			case 0: return new Number(0, zero, zeroSelected);
			case 1: return new Number(1, one, oneSelected);
			case 2: return new Number(2, two, twoSelected);
			case 3: return new Number(3, three, threeSelected);
			case 4: return new Number(4, four, fourSelected);
			case 5: return new Number(5, five, fiveSelected);
			case 6: return new Number(6, six, sixSelected);
			case 7: return new Number(7, seven, sevenSelected);
			case 8: return new Number(8, eight, eightSelected);
			case 9: return new Number(9, nine, nineSelected);
		}
		return null;
	}
	
}
