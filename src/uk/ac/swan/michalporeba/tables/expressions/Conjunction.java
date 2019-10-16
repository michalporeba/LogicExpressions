package uk.ac.swan.michalporeba.tables.expressions;

public class Conjunction extends BinaryExpression
{
    public Conjunction(int position) 
    {
        super(2, "Conjunction", position);
    }

    public Result evaluate() 
    {
        Result right = _right.evaluate();
        Result left = _left.evaluate();
        
        boolean a = left.getValue();
        boolean b = right.getValue();

        boolean value = (a && b);

        return new Result(Position, value, right, left);
    }
}