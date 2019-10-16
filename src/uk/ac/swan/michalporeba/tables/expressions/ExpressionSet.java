package uk.ac.swan.michalporeba.tables.expressions;

import java.util.ArrayList;

public class ExpressionSet extends Expression
{
    private final ArrayList<Expression> _expressions = new ArrayList<Expression>();
    private final Expression _root = null;

    public ExpressionSet() 
    {
        super(0, "Group", -1);
    }

    public Expression add(Expression expression) 
    {
        _expressions.add(expression);
        return this;
    }

    public Result evaluate()
    {
        if (_expressions.size() > 1)
        {
            organize();
        }

        return _expressions.get(0).evaluate();
    }

    public Expression[] getChildren()
    {
        return _expressions.toArray(new Expression[0]);
    }

    public boolean isFull()
    {
        return false;
    }

    public void reset()
    {
        _expressions.clear();
    }

    public void organize()
    {
        for(Expression expression : _expressions)
        {
            if (expression instanceof ExpressionSet) 
            {
                ((ExpressionSet)expression).organize();
            }
        }

        for(int priority = 1; priority <= 5; priority++)
        {
            int i = 0;
            Expression current = null;
            while(i < _expressions.size())
            {
                current = _expressions.get(i);
                if (current.Priority == priority) 
                {
                    current.add(_expressions.get(i-1));
                    if (priority > 1) 
                    {
                        current.add(_expressions.get(i+1));
                        _expressions.remove(i+1);
                    }
                    _expressions.remove(i-1);
                }
                else
                {
                    i++;
                }
            }
        }
    }
}