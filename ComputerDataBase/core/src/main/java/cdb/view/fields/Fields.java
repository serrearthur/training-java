package cdb.view.fields;

/**
 * Interface containing the name of all the fields used in the jsp.
 * @author aserre
 */
public class Fields implements ComputerFields, GeneralFields, PageFields {
    private Fields()
    {}
 
    private static class SingletonHolder
    {       
        private final static Fields instance = new Fields();
    }
 
    public static Fields getInstance()
    {
        return SingletonHolder.instance;
    }
}
