package generator.registry;

import generator.CommandGenerator;
import generator.main.Utils;

/** An Object with a description. */
public class ObjectDescribed extends ObjectBase
{
	/** Its description. */
	private String description;

	/** Creates a new Object.
	 * 
	 * @param type - Its type.
	 * @param id - Its ID. */
	public ObjectDescribed(int type, String id)
	{
		super(type, id);
	}

	@Override
	public String getDescription()
	{
		return this.description;
	}

	@Override
	public void updateLang()
	{
		this.description = CommandGenerator.translate(Utils.getObjectTypeNameId(this.getType()).toUpperCase() + ":" + this.getId() + ".description");
		super.updateLang();
	}

}
