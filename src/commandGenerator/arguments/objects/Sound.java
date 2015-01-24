package commandGenerator.arguments.objects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;

import commandGenerator.main.CGConstants;

public class Sound extends ObjectBase
{

	private static Map<String, Sound> list = new HashMap<String, Sound>();
	private static List<String> ids = new ArrayList<String>();

	public Sound(String id)
	{
		super(id, CGConstants.OBJECT_SOUND);
		list.put(id, this);
		ids.add(id);
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

	public static Sound[] getList()
	{
		Sound[] sounds = new Sound[list.size()];
		for (int i = 0; i < sounds.length; i++)
			sounds[i] = list.get(ids.get(i));
		return sounds;
	}

	public static ObjectBase getSoundFromId(String id)
	{
		return list.get(id);
	}

}
