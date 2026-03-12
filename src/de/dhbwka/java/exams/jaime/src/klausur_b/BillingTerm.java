/*
 * File converted to course project at Tue Dec 09 08:16:18 CET 2025
 */

package de.dhbwka.java.exams.jaime.src.klausur_b; // automatically inserted

import javax.swing.*;
import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Billing terminal window that tracks usage and costs of AI services
 */
@SuppressWarnings("serial")
public class BillingTerm extends JFrame {

	/**
	 * Budget limit in EUR
	 */
	private static final double BUDGET_LIMIT = 100.00;

	/**
	 * List of available AI services
	 */
	private List<AIService> services;

	/**
	 * Map to track costs for each service
	 */
	private Map<AIService, Double> serviceCosts;

	/**
	 * Map to track token usage for each service
	 */
	private Map<AIService, Integer> serviceTokens;

	/**
	 * Label to display remaining budget
	 */
	private JLabel budgetLabel;

	/**
	 * Map to store cost labels for each service
	 */
	private Map<AIService, JLabel> serviceCostLabels;

	/**
	 * Constructor for BillingTerm
	 *
	 * @param services list of available AI services
	 */
	public BillingTerm(List<AIService> services) {
		this.services = services;
		this.serviceCosts = new HashMap<>();
		this.serviceTokens = new HashMap<>();
		this.serviceCostLabels = new HashMap<>();

		// Initialize costs and tokens to 0 for all services
		for (AIService service : services) {
			serviceCosts.put(service, 0.0);
			serviceTokens.put(service, 0);
		}

		initializeUI();
	}

	/**
	 * Initialize the user interface
	 */
	private void initializeUI() {
		setTitle("JAIME Billing Term");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800, 600);
		setLocationRelativeTo(null);

		// Main panel with BorderLayout
		JPanel mainPanel = new JPanel(new BorderLayout());

		// Top panel for budget
		JPanel topPanel = new JPanel(new BorderLayout());
		budgetLabel = new JLabel("EUR 100,00", SwingConstants.RIGHT);
		budgetLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 48));
		budgetLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 40));
		topPanel.add(budgetLabel, BorderLayout.EAST);
		mainPanel.add(topPanel, BorderLayout.NORTH);

		// Center panel for services
		JPanel centerPanel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();

		int row = 0;
		for (AIService service : services) {
			// First row: Service name and slogan (left aligned)
			String serviceText = service.getName() + " - " + service.getSlogan();
			JLabel serviceLabel = new JLabel(serviceText);
			serviceLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));

			// Set color based on service type
			if (service.getType() == AIType.IMG) {
				serviceLabel.setForeground(Color.RED);
			} else {
				serviceLabel.setForeground(Color.BLUE);
			}

			gbc.gridx = 0;
			gbc.gridy = row;
			gbc.gridwidth = 2;
			gbc.anchor = GridBagConstraints.WEST;
			gbc.weightx = 1.0;
			gbc.fill = GridBagConstraints.HORIZONTAL;
			gbc.insets = new Insets(15, 30, 2, 30);
			centerPanel.add(serviceLabel, gbc);

			// Second row: Cost label (right aligned)
			JLabel costLabel = new JLabel("EUR 0,00 (0 tokens)", SwingConstants.RIGHT);
			costLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));
			serviceCostLabels.put(service, costLabel);

			gbc.gridx = 0;
			gbc.gridy = row + 1;
			gbc.gridwidth = 2;
			gbc.anchor = GridBagConstraints.EAST;
			gbc.weightx = 1.0;
			gbc.fill = GridBagConstraints.HORIZONTAL;
			gbc.insets = new Insets(0, 30, 10, 30);
			centerPanel.add(costLabel, gbc);

			row += 2; // Skip two rows for each service
		}

		// Add filler to push everything to top
		gbc.gridx = 0;
		gbc.gridy = row;
		gbc.gridwidth = 2;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.VERTICAL;
		centerPanel.add(new JPanel(), gbc);

		mainPanel.add(centerPanel, BorderLayout.CENTER);

		add(mainPanel);
		setVisible(true);
	}

	/**
	 * Add costs for a service usage with prompt and answer
	 *
	 * @param service the AI service that was used
	 * @param answer the answer that was generated
	 * @throws BillingException if budget is exceeded
	 */
	public void addCosts(AIService service, Answer answer) throws BillingException {
		// Get tokens from answer (includes both prompt and answer tokens)
		int totalTokens = answer.getTokens();

		// Calculate cost
		double cost = totalTokens * service.getPricePerUnit();

		// Update service costs and tokens
		double currentCost = serviceCosts.get(service);
		int currentTokens = serviceTokens.get(service);

		double newCost = currentCost + cost;
		int newTokens = currentTokens + totalTokens;

		serviceCosts.put(service, newCost);
		serviceTokens.put(service, newTokens);

		// Update UI
		updateServiceDisplay(service);
		updateBudgetDisplay();

		// Log the cost entry
		logCostEntry(service, cost, totalTokens);

		// Check if budget is exceeded
		if (getTotalCost() >= BUDGET_LIMIT) {
			throw new BillingException("Budget of EUR " + String.format("%.2f", BUDGET_LIMIT) + " exceeded!");
		}
	}

	/**
	 * Log cost entry to answerCosts.txt file
	 * Appends the current usage entry to the end of the file
	 *
	 * @param service the AI service that was used
	 * @param cost the cost in EUR for this specific usage
	 * @param tokens the number of tokens used for this specific usage
	 */
	private void logCostEntry(AIService service, double cost, int tokens) {
		try (PrintWriter writer = new PrintWriter(new FileWriter("answerCosts.txt", true))) {
			String logEntry = String.format("%s: EUR %.2f (%d tokens)",
					service.getName(), cost, tokens);
			writer.println(logEntry);
		} catch (IOException e) {
			// Fehler beim Schreiben werden abgefangen, aber nicht weiter behandelt
		}
	}

	/**
	 * Update the display for a specific service
	 *
	 * @param service the service to update
	 */
	private void updateServiceDisplay(AIService service) {
		JLabel costLabel = serviceCostLabels.get(service);
		if (costLabel != null) {
			double cost = serviceCosts.get(service);
			int tokens = serviceTokens.get(service);
			costLabel.setText(String.format("EUR %.2f (%d tokens)", cost, tokens));
		}
	}

	/**
	 * Update the budget display
	 */
	private void updateBudgetDisplay() {
		double remainingBudget = BUDGET_LIMIT - getTotalCost();
		budgetLabel.setText(String.format("EUR %.2f", remainingBudget));
	}

	/**
	 * Get the total cost of all services used
	 *
	 * @return total cost
	 */
	public double getTotalCost() {
		double total = 0.0;
		for (AIService service : services) {
			total += serviceCosts.get(service);
		}
		return total;
	}

	/**
	 * Get the remaining budget
	 *
	 * @return remaining budget
	 */
	public double getRemainingBudget() {
		return BUDGET_LIMIT - getTotalCost();
	}

	/**
	 * Check if budget is exceeded
	 *
	 * @return true if budget is exceeded
	 */
	public boolean isBudgetExceeded() {
		return getTotalCost() >= BUDGET_LIMIT;
	}
}
