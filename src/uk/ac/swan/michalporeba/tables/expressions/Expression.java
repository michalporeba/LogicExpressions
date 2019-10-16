package uk.ac.swan.michalporeba.tables.expressions;

public abstract class Expression 
{
    private static int BaseId = 0;
    
    private Expression _parent = null;
    protected String _stringFormat = "%d: %s(%s)";
    public int Id = 0;
    public String Label = "";
    public int Priority = Integer.MAX_VALUE;

    public Expression(int priority, String label, int position) 
    {
        Priority = priority;
        Label = label;
        Position = position;
        Id = BaseId++;
    }

    public int Position;
    public abstract Expression add(Expression expression) throws SyntaxException;
    public abstract Result evaluate();
    public abstract Expression[] getChildren();
    public abstract boolean isFull();

    public boolean hasParent()
    {
        return (_parent != null);
    }
    
    public void setParent(Expression expression) 
    {
        _parent = expression;
    }

    public Expression getParent() 
    {
        return _parent;
    }

    public String toString() 
    {
        String children = "";
        for(Expression expression : getChildren())
        {
            if (children.length() > 0) { children += ", "; }
            children += (expression == null) ? "<NULL>" : expression.Id;
        }

        return String.format(_stringFormat, Id, Label, children);
    }

    public static void resetIds()
    {
        BaseId = 0;
    }
}