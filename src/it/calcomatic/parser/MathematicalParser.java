package it.calcomatic.parser;

import it.calcomatic.math.ClosingBracket;
import it.calcomatic.math.ExpressionTree;
import it.calcomatic.math.NumericSymbol;
import it.calcomatic.math.OpeningBracket;
import it.calcomatic.math.Operator;
import it.calcomatic.math.Symbol;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.ListIterator;

public class MathematicalParser {
	
	private ExpressionTree expression;
	
	private ArrayList<ExpressionTree> trees;
	
	private int enclosureLevel = 0;
	
	public ExpressionTree getTree() {
		return this.expression;
	}
	
	public void parse(String input) throws ParseException {
		MathematicalTokenizer tokenizer = new MathematicalTokenizer();
		tokenizer.tokenize(input);
		TokenList tokens = tokenizer.getTokens();
		
		Symbol currentSymbol;
		this.trees = new ArrayList<ExpressionTree>();
		TokenIterator it = tokens.tokenIterator();
		ExpressionTree currentTree;
		int tokenCount = 0;
		while (it.hasNext()) {
			tokenCount++;
			currentSymbol = it.next();
			currentTree = this.switchTree();
			
			if (currentSymbol instanceof Operator) {
				currentTree.addOperator((Operator) currentSymbol);
			} else if (currentSymbol instanceof NumericSymbol) {
				currentTree.addArgument((NumericSymbol) currentSymbol);
			} else if (currentSymbol instanceof OpeningBracket) {
				this.enclosureLevel++;
			} else if (currentSymbol instanceof ClosingBracket) {
				this.enclosureLevel--;
			} else {
				throw new ParseException("Unknown symbol: " + currentSymbol.getValue(), tokenCount);
			}
		}
		
		this.createExpression();
	}
	
	private ExpressionTree switchTree() {
		ExpressionTree tree;
		try {
			tree = this.trees.get(this.enclosureLevel);
		} catch (IndexOutOfBoundsException e) {
			this.trees.add(this.enclosureLevel, new ExpressionTree());
			tree = this.trees.get(this.enclosureLevel);
		}
		
		return tree;
	}
	
	private void createExpression() {
		ExpressionTree tree;
		ListIterator<ExpressionTree> it = this.trees.listIterator();
		while (it.hasNext()) {
			tree = it.next();
			if (this.expression == null) {
				this.expression = tree;
			} else {
				this.expression.addArgument(tree);
			}
		}
	}
}
