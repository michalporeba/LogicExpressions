package uk.ac.swan.michalporeba.tables;

import uk.ac.swan.michalporeba.tables.expressions.Expression;
import uk.ac.swan.michalporeba.tables.expressions.Variable;

public class TruthTable
{
    private final Variable[] _variables;
    private final Expression _expression;
    private Permutation[] _permutations;
    private final String _expressionText;

    public TruthTable(Variable[] variables, Expression expression, String expressionText)
    {
        _variables = variables;
        _expression = expression;
        _expressionText = expressionText;
        _permutations = createPermutations(variables);
    }

    public Permutation[] getPermutations()
    {
        return _permutations;
    }

    public Variable[] getVariables()
    {
        return _variables;
    }

    public String getExpressionText()
    {
        return _expressionText;
    }

    private Permutation[] createPermutations(Variable[] variables)
    {
        Permutation[] output = new Permutation[(int)Math.pow(2, variables.length)];

        for(int y = 0; y < output.length; y ++)
        {
            output[y] = new Permutation(variables.length);
            for(int x = 0; x < variables.length; x++)
            {   
                int bitMask = (int)Math.pow(2, variables.length-x-1);
                boolean flag = ((y & bitMask) == bitMask);
                output[y].Values[x] = flag;

                variables[x].setValue(flag);
            }

            output[y].Result = _expression.evaluate();
        }

        return output;
    }
} 