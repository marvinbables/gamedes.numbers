package com.me.numbersgame;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.me.numbersgame.Number;

public class NumberResources {
	public static final String zero = "img/zero.png";
	public static final String one = "img/one.png";
	public static final String two = "img/two.png";
	public static final String three = "img/three.png";
	public static final String four = "img/four.png";
	public static final String five = "img/five.png";
	public static final String six = "img/six.png";
	public static final String seven = "img/seven.png";
	public static final String eight = "img/eight.png";
	public static final String nine = "img/nine.png";
	
	public static final String zeroSelected = "img/zero_selected.png";
	public static final String oneSelected = "img/one_selected.png";
	public static final String twoSelected = "img/two_selected.png";
	public static final String threeSelected = "img/three_selected.png";
	public static final String fourSelected = "img/four_selected.png";
	public static final String fiveSelected = "img/five_selected.png";
	public static final String sixSelected = "img/six_selected.png";
	public static final String sevenSelected = "img/seven_selected.png";
	public static final String eightSelected = "img/eight_selected.png";
	public static final String nineSelected = "img/nine_selected.png";
	
	public static final String zeroWrong = "img/zero_wrong.png";
	public static final String oneWrong = "img/one_wrong.png";
	public static final String twoWrong = "img/two_wrong.png";
	public static final String threeWrong = "img/three_wrong.png";
	public static final String fourWrong = "img/four_wrong.png";
	public static final String fiveWrong = "img/five_wrong.png";
	public static final String sixWrong = "img/six_wrong.png";
	public static final String sevenWrong = "img/seven_wrong.png";
	public static final String eightWrong = "img/eight_wrong.png";
	public static final String nineWrong = "img/nine_wrong.png";
	
	public static final String oddSum = "img/oddSum.png";
	public static final String oddProduct = "img/oddProduct.png";
	public static final String evenSum = "img/evenSum.png";
	public static final String evenProduct = "img/evenProduct.png";
	
	public static final Sound wrong = Gdx.audio.newSound(Gdx.files.internal("sound/TNT.WAV"));
	public static final Sound correct = Gdx.audio.newSound(Gdx.files.internal("sound/Crystal.WAV"));
	
	public static Number randomNumber() {
		Random rand = new Random();
		int i = rand.nextInt(10);
		switch(i) {
			case 0: return new Number(0, zero, zeroSelected, zeroWrong);
			case 1: return new Number(1, one, oneSelected, oneWrong);
			case 2: return new Number(2, two, twoSelected, twoWrong);
			case 3: return new Number(3, three, threeSelected, threeWrong);
			case 4: return new Number(4, four, fourSelected, fourWrong);
			case 5: return new Number(5, five, fiveSelected, fiveWrong);
			case 6: return new Number(6, six, sixSelected, sixWrong);
			case 7: return new Number(7, seven, sevenSelected, sevenWrong);
			case 8: return new Number(8, eight, eightSelected, eightWrong);
			case 9: return new Number(9, nine, nineSelected, nineWrong);
		}
		return null;
	}
	
}
