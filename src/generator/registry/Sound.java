package generator.registry;

import generator.main.Utils;

public class Sound extends ObjectBase
{

	public Sound(String id)
	{
		this(Utils.SOUND, id);
	}
	
	protected Sound(int type, String id)
	{
		super(type, id);
	}

	@Override
	public String getDescription()
	{
		return super.getName();
	}

	@Override
	public String getName()
	{
		return this.getId();
	}

}
