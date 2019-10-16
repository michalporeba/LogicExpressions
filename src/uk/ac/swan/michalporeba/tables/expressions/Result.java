package uk.ac.swan.michalporeba.tables.expressions;

public class Result
{
    private int _position;
    private boolean _value;
    private Result[] _children;

    public Result(int position, boolean value, Result... children)
    {
        _position = position;
        _value = value;
        _children = children;
    }

    public int getPosition() 
    {
        return _position;
    }

    public boolean getValue()
    {
        return _value;
    }

    public Result[] getChildren()
    {
        return _children;
    }
}