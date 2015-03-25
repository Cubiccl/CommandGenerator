package commandGenerator.arguments.objects;

import javax.swing.ImageIcon;

import commandGenerator.main.Lang;

public class AttributeType extends ObjectBase
{

	/** New Attribute type
	 * 
	 * @param id
	 *            - <i>String</i> - The Attribute type ID */
	public AttributeType(String id)
	{
		super(id, ATTRIBUTE);
	}

	/** Returns this Attribute's name. */
	@Override
	public String getName()
	{
		return Lang.get("ATTRIBUTES:" + this.getId());
	}

	@Override
	public ImageIcon getTexture()
	{
		return null;
	}

}
