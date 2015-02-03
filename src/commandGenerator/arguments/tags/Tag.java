package commandGenerator.arguments.tags;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import commandGenerator.arguments.objects.ObjectBase;
import commandGenerator.arguments.objects.Registerer;
import commandGenerator.main.CGConstants;
import commandGenerator.main.Lang;

public abstract class Tag extends ObjectBase
{

	public static final byte BOOLEAN = 0, STRING = 1, INT = 2, FLOAT = 3, COMPOUND = 4, LIST = 5;
	public static final int NONE = 0, MIN = 1, MAX = 2, BOTH = 3;

	private ObjectBase[] applicable;
	protected GridBagConstraints gbc;
	private boolean isEmpty;
	protected JPanel panel;
	private byte type;

	/** The base tag.
	 * 
	 * @param id
	 *            - The Tag ID.
	 * @param applicable
	 *            - The list of objects this tag is applicable to. */
	public Tag(String id, byte type, String... ids)
	{
		super(id, CGConstants.OBJECT_TAG);
		List<ObjectBase> objects = new ArrayList<ObjectBase>();
		for (String objectId : ids)
		{
			if (!objectId.startsWith("LIST="))
			{
				String[] objectsIds = objectId.split(":");
				for (String id1 : objectsIds)
					objects.add(Registerer.getObjectFromId(id1));

			} else
			{
				objectId = objectId.substring("LIST=".length());
				for (ObjectBase object : Registerer.getList(objectId))
					objects.add(object);

			}
		}
		applicable = objects.toArray(new ObjectBase[0]);
		panel = new JPanel(new GridBagLayout());
		isEmpty = true;
		this.type = type;

		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
	}

	/** Called when the user adds this tag. Asks the user for all parameters needed. Returns true if the user canceled */
	public abstract void askValue();

	/** Generates the NBT structure for the command. */
	public abstract String commandStructure();

	/** Displays the Tag to the user.
	 * 
	 * @param details */
	public abstract String display(int details, int lvls);

	public ObjectBase[] getApplicable()
	{
		return applicable;
	}

	public String getDescription()
	{
		if (getId().equals("")) return "";
		return Lang.get("TAGS:" + getId() + ".description");
	}

	public String getId()
	{
		String id = super.getId();
		if (id.startsWith("block.")) return id.substring("block.".length());
		if (id.startsWith("entity.")) return id.substring("entity.".length());
		if (id.startsWith("item.")) return id.substring("item.".length());
		return id;
	}

	public String getName()
	{
		if (getId().equals("")) return "";
		return Lang.get("TAGS:" + getId());
	}

	public byte getTagType()
	{
		return type;
	}

	public ImageIcon getTexture()
	{
		return null;
	}

	/** Is this tag empty? */
	public boolean isEmpty()
	{
		return isEmpty;
	}

	/** Displays the Tag panel. Returns true if the user canceled. */
	protected boolean showPanel()
	{
		isEmpty = true;
		boolean cancel = JOptionPane.showConfirmDialog(null, panel, Lang.get("GUI:tag.add") + getName(), JOptionPane.OK_CANCEL_OPTION) != JOptionPane.OK_OPTION;
		if (!cancel) isEmpty = false;
		return cancel;
	}

	public String toString()
	{
		return display(true);
	}
}
