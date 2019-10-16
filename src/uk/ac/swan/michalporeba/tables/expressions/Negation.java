package uk.ac.swan.michalporeba.tables.expressions;

public class Negation extends Expression
{
    private Expression _expression;

    public Negation(int position) 
    {
        super(1, "Negation", position);
    }

    public Expression add(Expression expression) 
    {
        if (_expression != null) 
        {
            throw new SyntaxException();
        }
        
        _expression = expression;

        return this;
    }
    
    public Result evaluate()
    {
        Result result = _expression.evaluate();
        boolean value = !result.getValue();
        return new Result(Position, value, result);
    }
    
    public Expression[] getChildren() 
    {
        return new Expression[] { _expression };
    }

    public boolean isFull() 
    {
        return (_expression != null);
    }
}