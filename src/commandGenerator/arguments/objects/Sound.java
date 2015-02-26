package commandGenerator.arguments.objects;

import javax.swing.ImageIcon;

import commandGenerator.main.CGConstants;

public class Sound extends ObjectBase
{

	/** Creates a new Sound.
	 * 
	 * @param id
	 *            - <i>String</i> - The Sound's ID. */
	public Sound(String id)
	{
		super(id, CGConstants.OBJECT_SOUND);
	}

	@Override
	public String getName()
	{
		return getId();
	}

	@Override
	public ImageIcon getTexture()
	{
		return null;
	}

}
