package uk.ac.swan.michalporeba.tables.expressions;

public class VariableExpression extends Expression
{
    private Variable _variable;
    
    public String Name;
    
    public VariableExpression(String name, int position, Variable variable) 
    {
        super(42, String.format("Variable(%s)", name), position);
        Name = name;
        _variable = variable;
        _stringFormat = "%d: %s%s";
    }
    
    public Expression add(Expression expression)
    {
        if (expression instanceof VariableExpression)
        {
            throw new SyntaxException("Cannot add an expression to a variable!");
        }

        return expression.add(this);
    }

    public Result evaluate()
    {
        return new Result(Position, _variable.getValue());
    }
    
    public Expression[] getChildren() 
    {
        return new Expression[0];
    }

    public boolean isFull() 
    {
        // variables are always full as they are leaf nodes
        return true;
    }
}