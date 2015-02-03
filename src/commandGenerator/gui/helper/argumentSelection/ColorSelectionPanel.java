package commandGenerator.gui.helper.argumentSelection;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import commandGenerator.gui.helper.components.CLabel;
import commandGenerator.gui.helper.components.HelperPanel;
import commandGenerator.main.CGConstants;

@SuppressWarnings("serial")
public class ColorSelectionPanel extends HelperPanel
{

	private GridBagConstraints gbc = new GridBagConstraints();
	private JLabel labelColor;
	private CLabel labelRed, labelGreen, labelBlue;
	private JPanel panelColor;
	private JSlider sliderRed, sliderGreen, sliderBlue;

	public ColorSelectionPanel(String title)
	{
		super(CGConstants.NBTID_COLOR, title);
	}

	@Override
	protected void addComponents()
	{
		gbc.gridx = 0;
		gbc.gridy = 0;
		add(labelRed);
		gbc.gridy++;
		add(labelGreen);
		gbc.gridy++;
		add(labelBlue);
		gbc.gridy++;
		add(Box.createGlue());

		gbc.gridx++;
		gbc.gridy = 0;
		add(sliderRed);
		gbc.gridy++;
		add(sliderGreen);
		gbc.gridy++;
		add(sliderBlue);

		gbc.gridx++;
		gbc.gridy = 0;
		gbc.gridheight = 4;
		add(panelColor);
		gbc.gridheight = 1;
	}

	@Override
	protected void createComponents()
	{
		labelColor = new JLabel("");
		labelRed = new CLabel("GUI:color.red");
		labelGreen = new CLabel("GUI:color.green");
		labelBlue = new CLabel("GUI:color.blue");

		sliderRed = new JSlider(0, 255);
		sliderGreen = new JSlider(0, 255);
		sliderBlue = new JSlider(0, 255);

		panelColor = new JPanel();
		panelColor.setPreferredSize(new Dimension(150, 150));
		panelColor.setBackground(new Color(sliderRed.getValue(), sliderGreen.getValue(), sliderBlue.getValue()));
		panelColor.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		panelColor.add(labelColor);
	}

	@Override
	protected void createListeners()
	{
		sliderRed.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent arg0)
			{
				setLabelColor();
			}
		});
		sliderGreen.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent arg0)
			{
				setLabelColor();
			}
		});
		sliderBlue.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent arg0)
			{
				setLabelColor();
			}
		});
	}

	public int getColor()
	{
		return (sliderRed.getValue() << 16) + (sliderGreen.getValue() << 8) + (sliderBlue.getValue());
	}

	public void setEnabledContent(boolean enable)
	{
		setEnabled(enable);
		panelColor.setEnabled(enable);
		labelRed.setEnabled(enable);
		labelGreen.setEnabled(enable);
		labelBlue.setEnabled(enable);
		sliderRed.setEnabled(enable);
		sliderGreen.setEnabled(enable);
		sliderBlue.setEnabled(enable);
	}

	public void setLabelColor()
	{
		panelColor.setBackground(new Color(sliderRed.getValue(), sliderGreen.getValue(), sliderBlue.getValue()));
	}

}
