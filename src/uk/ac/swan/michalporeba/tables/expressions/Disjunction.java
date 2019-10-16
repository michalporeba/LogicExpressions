package uk.ac.swan.michalporeba.tables.expressions;

public class Disjunction extends BinaryExpression
{
    public Disjunction(int position) 
    {
        super(3, "Disjunction", position);
    }

    public Result evaluate() 
    {
        Result right = _right.evaluate();
        Result left = _left.evaluate();
        
        boolean a = left.getValue();
        boolean b = right.getValue();

        boolean value = (a || b);

        return new Result(Position, value, right, left);
    }
}