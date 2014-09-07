package com.mnishiguchi.java;


/**
 *  Provides static methods, which  evaluates the type of a string.
 *  isEmpty(String s), isInt(String s), isFloat(String s), isPhoneNumber(String s)
 *  
 *  E.g.,
 *        : 	            EMPTY
 * 123:   	        INTEGER 
 * -5123: 	        INTEGER
 * 1234.41:   	FLOAT
 * -1234.41: 	FLOAT
 * -1234..41: 	STRING
 * a12312: 	    STRING
 * 523sdf234sdf.123: 	STRING
 * 
 * http://stackoverflow.com/questions/16394491/validating-int-and-float-value-all-together
 */
public class StringChecker
{
    private static enum VarType  { INTEGER, FLOAT, STRING, EMPTY }
    
    private static  VarType getVarType(String s)
    {
        boolean onlyDigits = true;
        int dotCount = 0;
        if (s == null)
        {
            return VarType.EMPTY;
        }
        
        String trimmed = s.trim();
        
        if ( trimmed.length() == 0 )
        {
            return VarType.EMPTY;
        }
        
        int a=0;
        if (trimmed.charAt(0) == '-') 
        {
            a++;
        }
        
        for (int max = trimmed.length();  a < max; a++)
        {
            if ( trimmed.charAt(a) == '.' ) 
            {
                dotCount++;
                if (dotCount>1) break;
            } 
            else if ( !Character.isDigit( trimmed.charAt(a) ) )
            {
                onlyDigits = false;
                break;
            }  
        }

        if (onlyDigits) 
        {
            if (dotCount ==0) 
            {
                return VarType.INTEGER;
            }
            else if (dotCount ==1)
            {
                return VarType.FLOAT;
            } 
            else
            {
                return VarType.STRING;
            }
        }
        return VarType.STRING;
    }
    
    /**
     * @param s a string to evaluate
     * @return true if the string is empty
     */
    public static boolean isEmpty(String s)
    {
    	return getVarType(s) == VarType.EMPTY;
    }
    
    /**
     * @param s a string to evaluate
     * @return true if the string is an integer
     */
    public static boolean isInt(String s)
    {
    	return getVarType(s) == VarType.INTEGER;
    }
    
    /**
     * @param s a string to evaluate
     * @return true if the string is floating point number
     */
    public static boolean isFloat(String s)
    {
    	return getVarType(s) == VarType.FLOAT;
    }	
}