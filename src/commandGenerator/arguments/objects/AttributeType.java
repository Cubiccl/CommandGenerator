package commandGenerator.arguments.objects;

import javax.swing.ImageIcon;

import commandGenerator.main.CGConstants;
import commandGenerator.main.Lang;

public class AttributeType extends ObjectBase
{

	/** New Attribute type
	 * 
	 * @param id
	 *            - <i>String</i> - The Attribute type ID */
	public AttributeType(String id)
	{
		super(id, CGConstants.OBJECT_ATTRIBUTE);
	}

	/** Returns this Attribute's name. */
	public String getName()
	{
		return Lang.get("ATTRIBUTES:" + getId());
	}

	@Override
	public ImageIcon getTexture()
	{
		return null;
	}

}
