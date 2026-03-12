/*
 * File converted to course project at Tue Dec 09 08:16:18 CET 2025
 */

package de.dhbwka.java.exams.jaime.src.klausur_a; /* automatically updated */

import javax.swing.*;
import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class BillingTerm {
    private List<AIService> services;
    private float budget = 100.00f;
    private JFrame frame = new JFrame("JAIME Billing Term");
    private JPanel servicePanel = new JPanel();
    private JLabel budgetLabel = new JLabel("EUR "+budget);
    private JButton button = new JButton();
    private JLabel[] serviceLabels;
    private JLabel[] budgetLabels;
    public BillingTerm(List<AIService> services) {
        setServices(services);
        initGUI();
    }
    private void initGUI() {
        frame.setLayout(new BorderLayout());
        budgetLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        frame.add(budgetLabel, BorderLayout.NORTH);
        serviceLabels = new JLabel[services.size()];
        budgetLabels = new JLabel[services.size()];
        servicePanel.setLayout(new GridLayout(services.size(), 1));
        for (int i = 0; i < services.size(); i++) {
            serviceLabels[i] = new JLabel(services.get(i).getName()+" - "+services.get(i).getSlogan());
            serviceLabels[i].setHorizontalAlignment(SwingConstants.LEFT);
            budgetLabels[i] = new JLabel("EUR 0,00 (0 tokens)");
            budgetLabels[i].setHorizontalAlignment(SwingConstants.RIGHT);
            servicePanel.add(serviceLabels[i]);
            servicePanel.add(budgetLabels[i]);
        }
        frame.add(servicePanel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setVisible(true);
    }

    public void addCosts(AIService service,Answer answer) throws BillingException{
        int index = -1;
        for(int i = 0; i < services.size(); i++) {
            if(services.get(i).getName().equals(service.getName())) {
                index = i;
                break;
            }
        }
        float cost = service.getCost()* answer.getTokens();
        if(budget < 0){
            throw new BillingException("Sie haben nicht genug EUR");
        }
        budget -= cost;
        /*float usedCost = service.getUsedCost();
        int usedTokens = service.getUsedTokens();
        usedCost += cost;
        usedTokens += answer.getTokens();
        service.setUsedCost(usedCost);
        service.setUsedTokens(usedTokens);*/
        service.setUsedCost(cost);
        service.setUsedTokens(answer.getTokens());
        budgetLabel.setText("EUR "+budget);
        budgetLabels[index].setText("EUR" + service.getUsedCost() + " (" + service.getUsedTokens() + " tokens)");
        //budgetLabels[index].setText("EUR" + cost + " (" + answer.getTokens() + " tokens)");
        //Diese Zeile für den Aktuellen Verbrauch die darüber für gesamten
        try(PrintWriter out = new PrintWriter(new FileWriter("src/klausur/answersCosts.txt",true))){
            String line = service.getName()+": EUR " + service.getUsedCost() + " (" + service.getUsedTokens() + " tokens)";
            //String line = service.getName()+": EUR " + cost + " (" + answer.getTokens() + " tokens)";
            //Diese Zeile für den Aktuellen Verbrauch die darüber für gesamten
            out.println(line);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public List<AIService> getServices() {
        return services;
    }

    public void setServices(List<AIService> services) {
        this.services = services;
    }
}
