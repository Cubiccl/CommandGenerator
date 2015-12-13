package generator.registry;

/** An Object with Numerical ID. */
public class ObjectWithNumId extends ObjectBase
{
	/** The numerical ID. */
	private final int idNum;

	/** @param type - The type of the Object.
	 * @param idNum - Its numerical ID.
	 * @param idString - Its String ID. */
	public ObjectWithNumId(int type, int idNum, String idString)
	{
		super(type, idString);
		this.idNum = idNum;
	}

	/** @return This Object's numerical ID. */
	public int getNumId()
	{
		return this.idNum;
	}

}
