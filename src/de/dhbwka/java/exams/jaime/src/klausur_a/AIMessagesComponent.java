/*
 * File converted to course project at Tue Dec 09 08:16:18 CET 2025
 */

package de.dhbwka.java.exams.jaime.src.klausur_a; /* automatically updated */

import javax.swing.*;
import java.awt.*;

/**
 * UI element to display multiple ui elements scrollable
 *
 * Use like any other container:
 *
 * <pre>
 * AIMessagesComponent msgComp = new AIMessagesComponent();
 * msgComp.add(...);
 * </pre>
 */
@SuppressWarnings("serial")
public class AIMessagesComponent extends JScrollPane {

	/**
	 * Count of elements
	 */
	private int msgCount = 0;

	/**
	 * Panel for content
	 */
	private JPanel contentPanel = new JPanel(new GridBagLayout());

	/**
	 * Filler placeholder
	 */
	private JPanel fillerY = new JPanel();

	/**
	 * Create the AI Messages Component
	 */
	public AIMessagesComponent() {
		this.setViewportView(this.contentPanel);
	}

	/**
	 * Add a component
	 *
	 * @param component component to add
	 */
	public void add( JComponent component ){
		this.contentPanel.remove(this.fillerY);
		component.setBorder(BorderFactory.createEmptyBorder(1, 0, 1, 0));
		this.contentPanel.add(component, new GridBagConstraints(0, this.msgCount++, 1, 1, 1., 0., GridBagConstraints.BASELINE_LEADING, GridBagConstraints.HORIZONTAL, new Insets(2, 2, 2, 2), 0, 0));
		this.contentPanel.add(this.fillerY, new GridBagConstraints(0, this.msgCount, 1, 1, 1., 1., GridBagConstraints.BASELINE_LEADING, GridBagConstraints.BOTH, new Insets(2, 2, 2, 2), 0, 0));
		this.contentPanel.revalidate();

		SwingUtilities.invokeLater(() -> {
			JScrollBar vertScroll = this.getVerticalScrollBar();
			vertScroll.setValue(vertScroll.getMaximum());
		});
	}

	/**
	 * Add vertical gap
	 */
	public void addVerticalGap() {
		JPanel pan = new JPanel();
		pan.setMinimumSize(new Dimension(1, 6));
		this.add(pan);
	}
}
