/*
 * File converted to course project at Tue Dec 09 08:16:18 CET 2025
 */

package de.dhbwka.java.exams.jaime.src.klausur_b; // automatically inserted

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Random;

/**
 * Main terminal interface for JAIME AI Management Engine
 */
@SuppressWarnings("serial")
public class JAIMETerm extends JFrame {

	/**
	 * List of available AI services
	 */
	private List<AIService> services;

	/**
	 * Billing terminal reference
	 */
	private BillingTerm billingTerm;

	/**
	 * Dropdown for service selection
	 */
	private JComboBox<AIService> serviceComboBox;

	/**
	 * Input field for prompts
	 */
	private JTextField promptField;

	/**
	 * Messages component to display conversation
	 */
	private AIMessagesComponent messagesComponent;

	/**
	 * Generate button
	 */
	private JButton generateButton;

	/**
	 * Flag to track if animation is currently running
	 */
	private boolean isAnimationRunning = false;

	/**
	 * Constructor for JAIMETerm
	 *
	 * @param services list of available AI services
	 * @param billingTerm billing terminal reference
	 */
	public JAIMETerm(List<AIService> services, BillingTerm billingTerm) {
		this.services = services;
		this.billingTerm = billingTerm;

		initializeUI();
	}

	/**
	 * Initialize the user interface
	 */
	private void initializeUI() {
		setTitle("JAIME - AI Management Engine");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800, 600);
		setLocationRelativeTo(null);

		// Create main panel
		JPanel mainPanel = new JPanel(new BorderLayout());

		// Create header
		JPanel headerPanel = createHeaderPanel();
		mainPanel.add(headerPanel, BorderLayout.NORTH);

		// Create messages area
		messagesComponent = new AIMessagesComponent();
		messagesComponent.setBorder(BorderFactory.createTitledBorder("AI Conversation"));
		mainPanel.add(messagesComponent, BorderLayout.CENTER);

		// Create input panel
		JPanel inputPanel = createInputPanel();
		mainPanel.add(inputPanel, BorderLayout.SOUTH);

		add(mainPanel);
		setVisible(true);

		// Add welcome message
		addSystemMessage("Welcome to JAIME - Java AI Management Engine!");
		addSystemMessage("Select an AI service and enter your prompt to get started.");

		// Check initial budget status
		if (isBudgetExceeded()) {
			setInputControlsEnabled(false);
			addSystemMessage("Budget exceeded - input controls are disabled!");
		}
	}

	/**
	 * Create the header panel
	 *
	 * @return header panel
	 */
	private JPanel createHeaderPanel() {
		JPanel headerPanel = new JPanel(new BorderLayout());

		JLabel titleLabel = new JLabel("JAIME - AI Management Engine", JLabel.CENTER);
		titleLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
		titleLabel.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));
		titleLabel.setOpaque(true);
		titleLabel.setBackground(new Color(70, 130, 180));
		titleLabel.setForeground(Color.WHITE);

		headerPanel.add(titleLabel, BorderLayout.CENTER);

		// Create service selection panel
		JPanel servicePanel = new JPanel(new GridBagLayout());
		servicePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		GridBagConstraints gbc = new GridBagConstraints();

		// Service selection
		JLabel serviceLabel = new JLabel("AI Service:");
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(5, 5, 5, 5);
		servicePanel.add(serviceLabel, gbc);

		serviceComboBox = new JComboBox<>();
		for (AIService service : services) {
			serviceComboBox.addItem(service);
		}
		serviceComboBox.setPreferredSize(new Dimension(300, 25));
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 1.0;
		servicePanel.add(serviceComboBox, gbc);

		headerPanel.add(servicePanel, BorderLayout.SOUTH);

		return headerPanel;
	}

	/**
	 * Create the input panel
	 *
	 * @return input panel
	 */
	private JPanel createInputPanel() {
		JPanel inputPanel = new JPanel(new GridBagLayout());
		inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		GridBagConstraints gbc = new GridBagConstraints();

		// Prompt input
		JLabel promptLabel = new JLabel("Prompt:");
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(5, 5, 5, 5);
		inputPanel.add(promptLabel, gbc);

		promptField = new JTextField();
		promptField.setPreferredSize(new Dimension(300, 25));
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 1.0;
		inputPanel.add(promptField, gbc);

		// Generate button
		generateButton = new JButton("Generate");
		generateButton.setPreferredSize(new Dimension(100, 25));
		generateButton.addActionListener(new GenerateActionListener());
		gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.weightx = 0.0;
		gbc.insets = new Insets(5, 10, 5, 5);
		inputPanel.add(generateButton, gbc);

		// Add Enter key listener to prompt field
		promptField.addActionListener(new GenerateActionListener());

		return inputPanel;
	}

	/**
	 * Add a system message to the conversation
	 *
	 * @param message system message
	 */
	private void addSystemMessage(String message) {
		JLabel messageLabel = new JLabel("<html><div style='padding: 5px; color: #666666;'><strong>System:</strong> " + message + "</div></html>");
		messageLabel.setOpaque(true);
		messageLabel.setBackground(new Color(245, 245, 245));
		messageLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
		messagesComponent.add(messageLabel);
	}

	/**
	 * Add a user message to the conversation
	 *
	 * @param service AI service used
	 * @param prompt user prompt
	 */
	private void addUserMessage(AIService service, String prompt) {
		JLabel messageLabel = new JLabel("<html><div style='padding: 5px; color: #000080;'><strong>You (" + service.getName() + "):</strong> " + prompt + "</div></html>");
		messageLabel.setOpaque(true);
		messageLabel.setBackground(new Color(230, 240, 255));
		messageLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
		messagesComponent.add(messageLabel);
	}

	/**
	 * Add an AI response to the conversation
	 *
	 * @param service AI service that responded
	 * @param response AI response (text or image)
	 */
	private void addAIResponse(AIService service, Object response) {
		JPanel responsePanel = new JPanel(new BorderLayout());
		responsePanel.setOpaque(true);
		responsePanel.setBackground(new Color(240, 255, 240));
		responsePanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

		JLabel headerLabel = new JLabel("<html><strong>" + service.getName() + ":</strong></html>");
		headerLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
		responsePanel.add(headerLabel, BorderLayout.NORTH);

		if (service.getType() == AIType.TEXT && response instanceof String) {
			JTextArea textArea = new JTextArea();
			textArea.setEditable(false);
			textArea.setOpaque(false);
			textArea.setWrapStyleWord(true);
			textArea.setLineWrap(true);
			textArea.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
			responsePanel.add(textArea, BorderLayout.CENTER);

			// Add the response panel first, then animate the text
			messagesComponent.add(responsePanel);

			// Start text animation
			animateTextResponse(textArea, (String) response);
		} else if (service.getType() == AIType.IMG && response instanceof javax.swing.ImageIcon) {
			JLabel imageLabel = new JLabel((javax.swing.ImageIcon) response);
			imageLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
			responsePanel.add(imageLabel, BorderLayout.CENTER);
			messagesComponent.add(responsePanel);
		}
	}

	/**
	 * Animate text response word by word
	 *
	 * @param textArea the text area to animate
	 * @param fullText the complete text to display
	 */
	private void animateTextResponse(JTextArea textArea, String fullText) {
		// Disable input controls during animation
		setInputControlsEnabled(false);
		isAnimationRunning = true;

		// Split text into words
		String[] words = fullText.split(" ");

		// Start animation in a background thread
		new Thread(() -> {
			Random random = new Random();
			StringBuilder currentText = new StringBuilder();

			for (int i = 0; i < words.length; i++) {
				final String wordToAdd = words[i];

				// Add word to current text
				if (currentText.length() > 0) {
					currentText.append(" ");
				}
				currentText.append(wordToAdd);

				final String displayText = currentText.toString();

				// Update UI on EDT
				SwingUtilities.invokeLater(() -> {
					textArea.setText(displayText);
					textArea.repaint();
				});

				// Wait random time between 50-300ms (except for last word)
				if (i < words.length - 1) {
					try {
						int delay = 50 + random.nextInt(251); // 50 to 300 milliseconds
						Thread.sleep(delay);
					} catch (InterruptedException e) {
						Thread.currentThread().interrupt();
						break;
					}
				}
			}

			// Animation finished - re-enable controls if budget allows
			SwingUtilities.invokeLater(() -> {
				isAnimationRunning = false;
				if (!isBudgetExceeded()) {
					setInputControlsEnabled(true);
				}
			});
		}).start();
	}

	/**
	 * Enable or disable input controls (prompt field and generate button)
	 *
	 * @param enabled true to enable, false to disable
	 */
	private void setInputControlsEnabled(boolean enabled) {
		promptField.setEnabled(enabled);
		generateButton.setEnabled(enabled);
		serviceComboBox.setEnabled(enabled);
	}

	/**
	 * Check if budget is exceeded based on total cost
	 *
	 * @return true if budget is exceeded
	 */
	private boolean isBudgetExceeded() {
		return billingTerm.isBudgetExceeded();
	}

	/**
	 * Show a simple missing prompt dialog
	 */
	private void showMissingPromptDialog() {
		JFrame errorDialog = new JFrame("Missing Prompt");
		errorDialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		errorDialog.setResizable(false);

		JPanel panel = new JPanel(new BorderLayout());
		panel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

		JLabel message = new JLabel("Please enter a prompt!");

		message.setHorizontalAlignment(JLabel.CENTER);
		panel.add(message, BorderLayout.CENTER);

		JButton okButton = new JButton("OK");
		okButton.addActionListener(event -> errorDialog.dispose());

		JPanel buttonPanel = new JPanel();
		buttonPanel.add(okButton);
		panel.add(buttonPanel, BorderLayout.SOUTH);

		errorDialog.add(panel);
		errorDialog.setSize(300, 120);
		errorDialog.setLocationRelativeTo(this);
		errorDialog.setVisible(true);
	}

	/**
	 * Action listener for the generate button
	 */
	private class GenerateActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// Prevent multiple concurrent requests
			if (isAnimationRunning) {
				return;
			}

			// Check if budget is exceeded
			if (isBudgetExceeded()) {
				setInputControlsEnabled(false);
				return;
			}

			AIService selectedService = (AIService) serviceComboBox.getSelectedItem();
			String prompt = promptField.getText().trim();

			if (selectedService != null && !prompt.isEmpty()) {
				// Add user message
				addUserMessage(selectedService, prompt);

				// Generate AI response
				Object response;
				Answer answerObj = null;

				if (selectedService.getType() == AIType.TEXT) {
					TextAnswer textAnswer = AISimulator.getTextAnswer(selectedService, prompt);
					response = textAnswer != null ? textAnswer.getText() : "No response available";
					answerObj = textAnswer;
				} else {
					ImageAnswer imageAnswer = AISimulator.getImageAnswer(selectedService, prompt);
					response = imageAnswer != null ? imageAnswer.getImage() : null;
					answerObj = imageAnswer;
				}

				// Record usage for billing BEFORE adding response
				try {
					if (answerObj != null) {
						billingTerm.addCosts(selectedService, answerObj);
					}
				} catch (BillingException be) {
					// Budget exceeded - disable controls permanently
					setInputControlsEnabled(false);
					addSystemMessage("Budget exceeded: " + be.getMessage());
				}

				// Add AI response (will animate for text, show immediately for images)
				addAIResponse(selectedService, response);

				// Clear prompt field
				promptField.setText("");

				// Add vertical gap
				messagesComponent.addVerticalGap();

				// Check budget again after usage
				if (isBudgetExceeded()) {
					setInputControlsEnabled(false);
				}
			} else {
				// Create a beautiful error dialog
				showMissingPromptDialog();
			}
		}
	}
}
