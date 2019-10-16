package uk.ac.swan.michalporeba.tables;

import uk.ac.swan.michalporeba.tables.expressions.Result;

public class Permutation 
{
    public boolean[] Values;
    public Result Result;

    public Permutation(int values)
    {
        Values = new boolean[values]; 
        Result = null;
    }


}