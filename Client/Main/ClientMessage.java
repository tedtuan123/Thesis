package sample;

/*
 * Message sent to server.
 *
 * @Author Jay Sridhar
 */
public class ClientMessage
{
    private String from;
    private String name;

    public ClientMessage() {}

    public ClientMessage(String from,String text)
    {
	this.from = from;
	this.name = text;
    }

    public String getFrom()
    {
        return from;
    }

    public void setFrom(String from)
    {
        this.from = from;
    }

    public String getText()
    {
        return name;
    }

    public void setText(String text)
    {
        this.name = text;
    }
}
