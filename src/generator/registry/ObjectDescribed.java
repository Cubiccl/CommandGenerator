package generator.registry;

import generator.CommandGenerator;
import generator.main.Constants;

public class ObjectDescribed extends ObjectBase
{
	private String description;

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
		this.description = CommandGenerator.translate(Constants.OBJECT_NAMES[this.getType()].toUpperCase() + ":" + this.getId() + ".description");
		super.updateLang();
	}

}
