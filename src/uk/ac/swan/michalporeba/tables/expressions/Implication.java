package uk.ac.swan.michalporeba.tables.expressions;

public class Implication extends BinaryExpression
{
    public Implication(int position) 
    {
        super(4, "Implication", position);
    }

    public Result evaluate() 
    {
        Result right = _right.evaluate();
        Result left = _left.evaluate();
        
        boolean a = left.getValue();
        boolean b = right.getValue();

        boolean value = (!a || a && b);

        return new Result(Position, value, right, left);
    }
}