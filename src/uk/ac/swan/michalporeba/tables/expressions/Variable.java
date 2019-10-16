package uk.ac.swan.michalporeba.tables.expressions;

public class Variable
{
    private final String _label;
    private boolean _value;

    public Variable(String label)
    {
        _label = label;
    }

    public String getLabel()
    {
        return _label;
    }

    public void setValue(boolean value) 
    {
        _value = value;
    }

    public boolean getValue()
    {
        return _value;
    }
}