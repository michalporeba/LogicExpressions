package uk.ac.swan.michalporeba.tables.expressions;

public class SyntaxException extends RuntimeException 
{
    public SyntaxException() 
    {
        super("Invalid Syntax");
    }

    public SyntaxException(String message)
    {
        super("Invalid Syntax: " + message);
    }
}