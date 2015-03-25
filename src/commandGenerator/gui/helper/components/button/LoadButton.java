package commandGenerator.gui.helper.components.button;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import commandGenerator.arguments.objects.SavedObjects;
import commandGenerator.gui.helper.components.CLabel;
import commandGenerator.gui.helper.components.icomponent.ISave;
import commandGenerator.main.DisplayHelper;
import commandGenerator.main.Lang;

@SuppressWarnings("serial")
public class LoadButton extends CButton
{

	private ISave parent;
	private byte type;

	public LoadButton(byte type, ISave parent)
	{
		super("GENERAL:load");
		this.type = type;
		this.parent = parent;

		this.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				load();
			}
		});
	}

	private void load()
	{
		if (SavedObjects.getList(this.type).size() == 0)
		{
			DisplayHelper.showMessage(new CLabel("GENERAL:load_none"), Lang.get("GENERAL:load"));
			return;
		}
		Object object = SavedObjects.askObjectToLoad(this.type);
		if (object == null) return;
		this.parent.load(object);
	}

}
