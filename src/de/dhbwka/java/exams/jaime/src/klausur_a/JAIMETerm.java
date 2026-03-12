/*
 * File converted to course project at Tue Dec 09 08:16:18 CET 2025
 */

package de.dhbwka.java.exams.jaime.src.klausur_a; /* automatically updated */

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Random;

public class JAIMETerm {
    private List<AIService> services;
    private BillingTerm billingTerm;
    private JFrame frame = new JFrame("JAIMe");
    private AIMessagesComponent messagesComponent = new AIMessagesComponent();
    private JPanel promptPanel = new JPanel(new GridLayout(1,2));
    private TextField promptField = new TextField();
    private JButton send = new JButton("Send");

    public void setDisable(boolean disable) {
        this.disable = disable;
    }

    private boolean disable = false;
    public JAIMETerm(List<AIService> services, BillingTerm billingTerm) {
        setServices(services);
        setBillingTerm(billingTerm);
        initGUI();
    }
    private void initGUI() {
        frame.setLayout(new BorderLayout());
        JComboBox<AIService> cbxServices = new JComboBox<>(services.toArray(new AIService[0]));
        send.addActionListener(e->{
            if(!promptField.getText().isBlank()) {
                AIService selectedService = (AIService) cbxServices.getSelectedItem();
                // Person selectedPerson = persons.get(cbxPersons.getSelectedIndex()); // Alternativ
                messagesComponent.add(new JLabel("["+selectedService.getName()+"] "+promptField.getText()));
                if(selectedService.getType()==AIType.TEXT) {
                    TextAnswer answer = AISimulator.getTextAnswer(selectedService, promptField.getText());
                    if(answer != null) {
                        try{
                            billingTerm.addCosts(selectedService,answer);
                            generateAnswer(answer);
                        } catch (BillingException ex) {
                            send.setEnabled(false);
                            promptField.setEditable(false);
                            setDisable(true);
                        }
                    }
                } else if (selectedService.getType()==AIType.IMG) {
                    ImageAnswer imageAnswer = AISimulator.getImageAnswer(selectedService, promptField.getText());
                    if(imageAnswer != null) {
                        JLabel answer = new JLabel();
                        answer.setIcon(imageAnswer.getImage());
                        try{
                            billingTerm.addCosts(selectedService,imageAnswer);
                            messagesComponent.add(answer);
                        } catch (BillingException ex) {
                            send.setEnabled(false);
                            promptField.setEditable(false);
                            setDisable(true);
                        }
                    }
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Please enter a prompt!", "Missing Prompt", JOptionPane.ERROR_MESSAGE);
            }

        });
        promptField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (!disable) {
                        if(!promptField.getText().isBlank()) {
                            AIService selectedService = (AIService) cbxServices.getSelectedItem();
                            // Person selectedPerson = persons.get(cbxPersons.getSelectedIndex()); // Alternativ
                            messagesComponent.add(new JLabel("["+selectedService.getName()+"] "+promptField.getText()));
                            if(selectedService.getType()==AIType.TEXT) {
                                TextAnswer answer = AISimulator.getTextAnswer(selectedService, promptField.getText());
                                if(answer != null) {
                                    try{
                                        billingTerm.addCosts(selectedService,answer);
                                        generateAnswer(answer);
                                    } catch (BillingException ex) {
                                        send.setEnabled(false);
                                        promptField.setEditable(false);
                                        setDisable(true);
                                    }
                                }
                            } else if (selectedService.getType()==AIType.IMG) {
                                ImageAnswer imageAnswer = AISimulator.getImageAnswer(selectedService, promptField.getText());
                                if(imageAnswer != null) {
                                    JLabel answer = new JLabel();
                                    answer.setIcon(imageAnswer.getImage());
                                    try{
                                        billingTerm.addCosts(selectedService,imageAnswer);
                                        messagesComponent.add(answer);
                                    } catch (BillingException ex) {
                                        send.setEnabled(false);
                                        promptField.setEditable(false);
                                        setDisable(true);
                                    }
                                }
                            }
                        } else {
                            JOptionPane.showMessageDialog(frame, "Please enter a prompt!", "Missing Prompt", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            }
        });
        frame.add(cbxServices, BorderLayout.NORTH);
        promptPanel.add(promptField);
        promptPanel.add(send);
        frame.add(messagesComponent, BorderLayout.CENTER);
        frame.add(promptPanel, BorderLayout.SOUTH);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        //frame.pack();
        frame.setVisible(true);
    }
    private void generateAnswer(TextAnswer answer) {
        Random rand = new Random();
        String[] parts = answer.getAnswer().split(" ");
        StringBuilder sb = new StringBuilder();
        JLabel answerLabel = new JLabel();
        messagesComponent.add(answerLabel);
        new Thread(()->{
            send.setEnabled(false);
            promptField.setEditable(false);
            setDisable(true);
            for(int i=0; i<parts.length; i++) {
                sb.append(parts[i]+" ");
                answerLabel.setText(sb.toString());
                try{
                    Thread.sleep(rand.nextInt(251)+50);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
            send.setEnabled(true);
            promptField.setEditable(true);
            setDisable(false);
        }).start();
    }

    private void performeSend(AIService service) {

    }

    public List<AIService> getServices() {
        return services;
    }

    public void setServices(List<AIService> services) {
        this.services = services;
    }

    public BillingTerm getBillingTerm() {
        return billingTerm;
    }

    public void setBillingTerm(BillingTerm billingTerm) {
        this.billingTerm = billingTerm;
    }

}
