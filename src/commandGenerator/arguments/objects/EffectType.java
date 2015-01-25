package commandGenerator.arguments.objects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;

import commandGenerator.main.CGConstants;
import commandGenerator.main.Lang;
import commandGenerator.main.Resources;

public class EffectType extends ObjectBase
{

	private int idNum;
	private static Map<String, EffectType> list = new HashMap<String, EffectType>();
	private static List<String> ids = new ArrayList<String>();

	public EffectType(int idNum, String idString)
	{
		super(idString, CGConstants.OBJECT_EFFECT);
		this.idNum = idNum;
		list.put(idString, this);
		ids.add(idString);
	}

	public int getIdNum()
	{
		return idNum;
	}

	public String getName()
	{
		return Lang.get("EFFECTS:" + getId());
	}

	public static EffectType getEffectFromId(String id)
	{
		return list.get(id);
	}

	@Override
	public ImageIcon getTexture()
	{
		return new ImageIcon(Resources.folder + "textures/effects/" + getId() + ".png");
	}

	public static EffectType[] getList()
	{
		EffectType[] types = new EffectType[list.size()];
		for (int i = 0; i < ids.size(); i++)
			types[i] = list.get(ids.get(i));
		return types;
	}

	public static EffectType getEffectFromIdNum(int id)
	{
		for (int i = 0; i < ids.size(); i++)
		{
			if (list.get(ids.get(i)).getIdNum() == id) return getEffectFromId(ids.get(i));
		}
		return null;
	}

}
