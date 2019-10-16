/**
 * Propositional logic parser and truth table printer
 */

package uk.ac.swan.michalporeba.tables;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.*;
import java.io.File;
import uk.ac.swan.michalporeba.tables.*;
import uk.ac.swan.michalporeba.tables.expressions.*;
import uk.ac.swan.michalporeba.tables.expressions.Variable;

public class Tables 
{
	final static Logger _logger = Logger.getGlobal();
	public static void main(String[] args) throws Exception
	{
		// System.setProperty("java.util.logging.SimpleFormatter.format", "[%1$tF %1$tT] [%4$-7s] %5$s %n");
		System.setProperty("java.util.logging.SimpleFormatter.format", "%5$s %n");
		_logger.setLevel(Level.OFF);

		String input = "";
		ArrayList<Marker> markers = new ArrayList<Marker>();
		
		try(Scanner in = new Scanner(new File("input.data")))
		{
			while(true)
			{			
				Expression.resetIds();
				System.out.println("Give me a propositional expression");
				input = in.nextLine();
				if (input.length() == 0) 
				{
					System.out.println("No? OK. Bye bye!\n");
					break;
				}
				
				System.out.println(input);

				Parser parser = new Parser();
				Expression expression = parser.parse(input);
				printMarkers(80, parser.getMarkers());
				
				System.out.println();
				System.out.printf("Expression Tree for (%s)\n", input);
				printTree(expression);
				System.out.println();

				TruthTable table = new TruthTable(parser.getVariables(), expression, input);

				System.out.printf("Truth Table\n");
				printTable(table);
				System.out.println();
			}
		}
	}

	private static void printTable(TruthTable table) 
	{
		Variable[] variables = table.getVariables();
		int[] variablePositions = new int[variables.length];
		int variablesLength = 0;
		int expressionLength = table.getExpressionText().length();

		for(int i = 0; i < variables.length; i++) 
		{
			variablePositions[i] = variablesLength+1;
			System.out.print(" "+variables[i].getLabel());
			variablesLength += variables[i].getLabel().length()+1;
		}

		System.out.print(" | " + table.getExpressionText());

		System.out.println();

		for(int i = 0; i < 3 + variablesLength + expressionLength; i++) 
		{
			if (i == variablesLength+1)
			{
				System.out.print("+");
			}
			else 
			{
				System.out.print("-");
			}
		}

		System.out.println();

		int positiveResults = 0;

		Permutation[] permutations = table.getPermutations();
		for(int p = 0; p < permutations.length; p++)
		{
			System.out.print(" ");

			for(int v = 0; v < variables.length; v++) 
			{
				System.out.print(permutations[p].Values[v] ? "T" : "F");

				for(int i = variables[v].getLabel().length(); i > 0; i--)
				{
					System.out.print(" ");
				}
			}

			System.out.print("| ");

			StringBuilder sb = new StringBuilder();
			for(int i = 0; i < expressionLength; i++) 
			{
				sb.append(" ");
			}

			if (permutations[p].Result.getValue()) 
			{
				positiveResults++;
			}
			plotResults(sb, permutations[p].Result);

			System.out.println(sb);
		}

		for(int i = 0; i < 3 + variablesLength + expressionLength; i++)
		{
			if (i == 3 + variablesLength + permutations[0].Result.getPosition()) 
			{
				System.out.print("^");
			}
			else
			{
				System.out.print("-");
			}
		}

		System.out.println();
		if (positiveResults == permutations.length) 
		{
			System.out.println("TAUTOLOGY");
		} 
		else if (positiveResults == 0) 
		{
			System.out.println("CONTRADITION");
		}
		else 
		{
			System.out.println("SATISFIABLE");
		}
	}

	private static void plotResults(StringBuilder sb, Result result)
	{
		if (result == null) { return; }

		char value = (result.getValue() ? 'T' : 'F');
		sb.setCharAt(result.getPosition(), value);

		for(Result r : result.getChildren())
		{
			plotResults(sb, r);
		}
	}

	private static void printMarkers(int maxLength, ArrayList<Marker> markers) 
	{
		int c = 0;
		int j = 0;
		for(int i = 0; i < markers.size(); i++)
		{
			j = markers.size() - 1;
			c = 0;
			while(j >= i)
			{
				while(c++ < markers.get(j).Position)
				{
					System.out.print(" ");
				}
				
				if (j == i) 
				{
					System.out.print("*");
				} else {
					System.out.print("|");
				}
				j--;
			}

			System.out.printf(" %s\n", markers.get(i).Label);
		}
	}

	private static void markColumn(int position, String text)
	{
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < position - 1; i++) { sb.append(" "); }
		sb.append("^ ");
		sb.append(text);
		System.out.println(sb);
	}
	
	
	private static void printTree(Expression... expressions) 
	{
		printTree(0, expressions);
	}

	private static void printTree(int level, Expression... expressions)
	{
		for(Expression expression : expressions)
		{
			if (expression != null)
			{
				for(int i = 0; i < level; i++)
				{
					System.out.print("   ");
				}

				System.out.println(expression);
				printTree(level+1, expression.getChildren());
			}
		}
	}
	
	

	
	
	
}