package generator.registry;

public class ObjectWithNumId extends ObjectBase
{
	private final int idNum;

	public ObjectWithNumId(int type, int idNum, String idString)
	{
		super(type, idString);
		this.idNum = idNum;
	}

	public int getNumId()
	{
		return this.idNum;
	}

}
