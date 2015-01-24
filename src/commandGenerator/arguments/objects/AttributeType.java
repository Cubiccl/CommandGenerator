package commandGenerator.arguments.objects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;

import commandGenerator.main.CGConstants;
import commandGenerator.main.Lang;

public class AttributeType extends ObjectBase
{

	private static Map<String, AttributeType> list = new HashMap<String, AttributeType>();
	private static List<String> ids = new ArrayList<String>();

	/** New Attribute type
	 * 
	 * @param id
	 *            - The Attribute type ID */
	public AttributeType(String id)
	{
		super(id, CGConstants.OBJECT_ATTRIBUTE);
		list.put(id, this);
		ids.add(id);
	}

	/** @return String - The Attribute Name */
	public String getName()
	{
		return Lang.get("ATTRIBUTES:" + getId());
	}

	public static AttributeType getAttributeFromId(String id)
	{
		return list.get(id);
	}

	public static AttributeType[] getList()
	{
		AttributeType[] attributes = new AttributeType[list.size()];
		for (int i = 0; i < attributes.length; i++)
		{
			attributes[i] = list.get(ids.get(i));
		}
		return attributes;
	}

	@Override
	public ImageIcon getTexture()
	{
		return null;
	}

}
