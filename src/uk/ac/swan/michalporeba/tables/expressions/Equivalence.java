package uk.ac.swan.michalporeba.tables.expressions;

public class Equivalence extends BinaryExpression
{
    public Equivalence(int position) 
    {
        super(5, "Equivalence", position);
    }

    public Result evaluate() 
    {
        Result right = _right.evaluate();
        Result left = _left.evaluate();
        
        boolean a = left.getValue();
        boolean b = right.getValue();

        boolean value = (a&&b) || (!a&&!b);

        return new Result(Position, value, right, left);
    }
}