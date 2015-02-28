package commandGenerator.gui.helper.argumentSelection;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import commandGenerator.arguments.objects.Coordinates;
import commandGenerator.arguments.tags.Tag;
import commandGenerator.arguments.tags.TagFloat;
import commandGenerator.gui.helper.components.CLabel;
import commandGenerator.gui.helper.components.panel.HelperPanel;

@SuppressWarnings("serial")
public class RotationSelectionPanel extends HelperPanel
{

	private CLabel labelX, labelY;
	private JSpinner spinnerX, spinnerY;

	public RotationSelectionPanel(String id, String title)
	{
		super(id, title);
	}

	@Override
	protected void addComponents()
	{
		addLine(labelX, spinnerX);
		addLine(labelY, spinnerY);
	}

	@Override
	protected void createComponents()
	{
		labelX = new CLabel("GUI:rotation.horizontal");
		labelY = new CLabel("GUI:rotation.vertical");

		spinnerX = new JSpinner(new SpinnerNumberModel(0, 0, 360, 1));
		spinnerY = new JSpinner(new SpinnerNumberModel(0, -90, 90, 1));
	}

	@Override
	protected void createListeners()
	{}

	public List<Tag> getRotations()
	{
		List<Tag> rot = new ArrayList<Tag>();
		int x = (int) spinnerX.getValue(), y = (int) spinnerY.getValue();
		rot.add(new TagFloat().setValue(x));
		rot.add(new TagFloat().setValue(y));
		return rot;
	}

	public void setEnabledContent(boolean enable)
	{
		this.setEnabled(enable);
		labelX.setEnabled(enable);
		labelY.setEnabled(enable);
		spinnerX.setEnabled(enable);
		spinnerY.setEnabled(enable);
	}

	@Override
	public void setupFrom(Map<String, Object> data)
	{
		Coordinates coord = (Coordinates) data.get(getPanelId());
		float x = coord.getRot(Coordinates.X), y = coord.getRot(Coordinates.Y);
		spinnerX.setValue((int) x);
		spinnerY.setValue((int) y);
	}

}
