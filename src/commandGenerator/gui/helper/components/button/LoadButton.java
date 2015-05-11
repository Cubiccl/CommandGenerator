package commandGenerator.gui.helper.components.button;

import java.awt.AWTEvent;

import commandGenerator.Generator;
import commandGenerator.arguments.objects.SavedObjects;
import commandGenerator.gui.helper.GuiHandler;
import commandGenerator.gui.helper.components.CLabel;
import commandGenerator.gui.helper.components.icomponent.ISave;
import commandGenerator.gui.helper.components.listeners.ClickListener;
import commandGenerator.gui.helper.components.listeners.IEvent;
import commandGenerator.main.DisplayHelper;

@SuppressWarnings("serial")
public class LoadButton extends CButton implements IEvent
{

	private ISave parent;
	private byte type;

	public LoadButton(byte type, ISave parent)
	{
		super("GENERAL:load");
		this.type = type;
		this.parent = parent;
		this.addActionListener(new ClickListener(this));
		this.setDrawType(GuiHandler.LEFT);
	}

	@Override
	public void handleEvent(AWTEvent e, int eventID)
	{
		super.handleEvent(e, eventID);
		if (eventID != IEvent.CLICK_EVENT) return;
		
		if (SavedObjects.getList(this.type).size() == 0)
		{
			DisplayHelper.showMessage(new CLabel("GENERAL:load_none"), Generator.translate("GENERAL:load"));
			return;
		}
		
		Object object = SavedObjects.askObjectToLoad(this.type);
		if (object == null) return;
		this.parent.load(object);
	}

}
