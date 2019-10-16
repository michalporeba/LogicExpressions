package uk.ac.swan.michalporeba.tables;

import java.util.ArrayList;
import java.util.TreeMap;
import java.util.logging.*;
import java.util.Stack;
import uk.ac.swan.michalporeba.tables.*;
import uk.ac.swan.michalporeba.tables.expressions.*;

public class Parser
{
	final Logger _logger = Logger.getGlobal();

	final ArrayList<Marker> _markers = new ArrayList<Marker>();
	final TreeMap<String, Variable> _variables = new TreeMap<String, Variable>();
	final Stack<Expression> _pointers = new Stack<Expression>();
	final ExpressionSet _solution = new ExpressionSet();

	public ArrayList<Marker> getMarkers() 
	{
		return _markers;
	}

    public Expression parse(String expression) throws Exception
	{
		_solution.reset();
		_pointers.clear();
		_pointers.push(_solution);

		String reversed = reverse(expression);
		int length = reversed.length();
		
		StringBuilder sb = new StringBuilder();
		
		int i = -1;
		char nextCharacter = ' ';
		Expression nextExpression = null;
		int position = 0;
		String name = "";
					
		while(i < length-1) 
		{
			nextCharacter = reversed.charAt(++i);
			_logger.info(String.format("Next character is: %s", nextCharacter));
			
			if (nextCharacter == ' ') 
			{
				continue;
			}

			if (Character.isLetter(nextCharacter)) 
			{
				sb.setLength(0); // reset string builder
				_logger.info("  this if the last character of a variable name");
				sb.append(nextCharacter);
				
				while (++i < length)
				{	
					nextCharacter = reversed.charAt(i);
					if (!Character.isLetter(nextCharacter))
					{
						break;
					}
					sb.append(nextCharacter);
				}
				
				sb.reverse();
				name = sb.toString();
				position = length - i;

				_logger.info(String.format("  Variable %s starts at position %d", name, position));

				nextExpression = getVariableExpression(name, position);

				i--;
			}
			else 
			{
				position = length - i - 2;

				if (nextCharacter == '~')
				{
					position++;
					_logger.info(String.format("  Negation at position %d", position));
					nextExpression = new Negation(position);
				}
				else if (nextCharacter == ')')
				{
					_pointers.push(new ExpressionSet());		
				}
				else if (nextCharacter == '(')
				{
					Expression closing = _pointers.pop();						
					_pointers.peek().add(closing);
				}
				else 
				{
					if (nextCharacter == '\\' && reversed.charAt(++i) == '/')
					{
						_logger.info(String.format("  Conjunction at position %d", position));
						nextExpression = new Conjunction(position);
					}
					else if (nextCharacter == '/' && reversed.charAt(++i) == '\\')
					{
						_logger.info(String.format("  Discjunction at position %d", position));
						nextExpression = new Disjunction(position);
					}
					else if (nextCharacter == '>' && reversed.charAt(++i) == '=')
					{
						nextCharacter = reversed.charAt(++i);
						if (nextCharacter == '<')
						{
							nextExpression = new Equivalence(position);
						}
						else 
						{
							nextExpression = new Implication(position);
						}		
					}
				}
			}

			if (nextExpression != null) 
			{
				_pointers.peek().add(nextExpression);
				_markers.add(new Marker(nextExpression.Position, String.format("%d: %s", nextExpression.Id, nextExpression.Label)));
				nextExpression = null;
			}

			_logger.info(String.format("CURRENT OUTPUT: %s", _solution));
		}

		_solution.organize();
		return _solution;
	}

	private VariableExpression getVariableExpression(String name, int position) 
	{
		if (!_variables.containsKey(name))
		{
			_variables.put(name, new Variable(name));
		}

		return new VariableExpression(name, position, _variables.get(name));
	}

	public Variable[] getVariables()
	{
		return _variables.values().toArray(new Variable[0]);
	}

	private static String reverse(String input) 
	{
		StringBuilder output = new StringBuilder();
		for(int i = input.length() - 1; i >= 0; i--) 
		{
			output.append(input.charAt(i));
		}
		return output.toString();
	}
}