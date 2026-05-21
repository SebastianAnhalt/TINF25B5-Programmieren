package de.dhbwka.java.exams.jaime.src.anis;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// TBD Clarify the limit btw. model and view

public class BillingTerm extends JFrame{
    // attributes
    private static final double BUDGET_LIMIT = 100.00;
    private List<AIService> services;
    private Map<AIService, Double> serviceCosts;
    private Map<AIService, Integer> serviceTokens;
    private Map<AIService, JLabel> serviceCostLabels;
    JLabel budgetLabel;

    // constructors
    public BillingTerm(List<AIService> services){
        this.services = services;
        this.serviceCosts = new HashMap<>();
        this.serviceTokens = new HashMap<>();
        this.serviceCostLabels = new HashMap<>();

        // initialise costs and token count
        for(AIService service : services){
            serviceCosts.put(service, 0.0);
            serviceTokens.put(service, 0);
        }

        // initialise ui
        initializeUI();
    }

    private void initializeUI() {
        setTitle("JAIME Billing Term (A001)");
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
            serviceLabel.setForeground(service.getType().getColor());
            // if (service.getType() == AIType.IMG) {
                // serviceLabel.setForeground(Color.RED);
            // } else {
                // serviceLabel.setForeground(Color.BLUE);
            // }

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
    void addCosts(AIService service, Answer answer) throws BillingException {
        // how many tokens were needed?
        int needed_tokens = answer.getTokens();

        // actually the most important action
        double needed_costs = service.getPrice_per_unit() * needed_tokens;

        // update model
        serviceCosts.put(service, serviceCosts.get(service) + needed_costs);
        serviceTokens.put(service, serviceTokens.get(service) + needed_tokens);

        // update ui
        updateServiceDisplay(service);
        updateBudgetDisplay();

        // log
        logCostEntry(service, needed_costs, needed_tokens);

        // check if budget is exceeded
        if(getTotalCost() >= BUDGET_LIMIT){
            throw new BillingException("Budget exceeded");
        }


    }

    private double getTotalCost() {
        return 0.0;
    }

    private void logCostEntry(AIService service, double neededCosts, int neededTokens) {
    }

    private void updateBudgetDisplay() {
    }

    private void updateServiceDisplay(AIService service) {
    }
}
