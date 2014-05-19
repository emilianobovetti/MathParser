package it.calcomatic;

import java.text.ParseException;
import java.util.Scanner;

import it.calcomatic.math.ExpressionTree;
import it.calcomatic.parser.MathematicalParser;

public class MainTest {

	public static void main(String[] args) throws ParseException {
		Scanner s = new Scanner(System.in);
		
		String input = s.nextLine();
		MathematicalParser parser = new MathematicalParser();
		parser.parse(input);
		
		ExpressionTree tree = parser.getTree();
		
		System.out.println(tree.toString());
		System.out.println(tree.solve());
		
		s.close();
	}
}
