package jsp.builders;

/**
 * class build html select
 */
public class HtmlSelectBuilder
{
    private String nameAttribute = "";
    private String innerPart = "";

    /**
     * set name of select
     */
    public void setNameAttribute(final String nameAttribute)
    {
        this.nameAttribute = nameAttribute;
        innerPart += getStart();
    }

    private String getStart()
    {
        return "<select name='" + nameAttribute + "'>";
    }

    /**
     * add element to select
     */
    public void addElement(String nameElement)
    {
        final String elementStart = "<option>";
        final String elementEnd = "</option>";
        innerPart += elementStart + nameElement + elementEnd;
    }

    /**
     * return result html
     */
    public String built()
    {
        innerPart += "</select>";
        return innerPart;
    }
}
