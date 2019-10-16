package uk.ac.swan.michalporeba.tables.expressions;

public abstract class BinaryExpression extends Expression
{
    public BinaryExpression(int priority, String label, int position) 
    {
        super(priority, label, position);
    }

    protected Expression _left;
    protected Expression _right;

    public Expression add(Expression expression) 
    {
        if (_left != null) 
        {
            throw new SyntaxException("The expression is already full and cannot accept more sub-expressions");
        }

        if (_right == null)
        {
            _right = expression;
        }
        else 
        {
            _left = expression;
        }

        return this;
    }

    public Expression[] getChildren()
    {
        return new Expression[] { _right, _left };
    }

    public boolean isFull()
    {
        return (_left != null);
    }
}