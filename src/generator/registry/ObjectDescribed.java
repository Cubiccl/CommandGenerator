package generator.registry;

import generator.main.Text;
import generator.main.Utils;

/** An Object with a description. */
public class ObjectDescribed extends ObjectBase
{
	/** Its description. */
	private Text description;

	/** Creates a new Object.
	 * 
	 * @param type - Its type.
	 * @param id - Its ID. */
	public ObjectDescribed(int type, String id)
	{
		super(type, id);
	}

	@Override
	public void complete()
	{
		this.description = new Text(Utils.getObjectTypeNameId(this.getType()).toUpperCase(), this.getId() + ".description");
		super.complete();
	}

	@Override
	public String getDescription()
	{
		return this.description.getValue();
	}

}
