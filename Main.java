package Capolavoro;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.time.YearMonth;

public class Main implements ActionListener {
    private JFrame main;
    private JPanel mainPanel;
    private JTextField nomeField;
    private JTextField cognomeField;
    private JTextField comuneField;
    private JComboBox<String> sessoCombo;
    private JComboBox<Integer> giornoCombo;
    private JComboBox<String> meseCombo;
    private JComboBox<Integer> annoCombo;
    private JButton calcolaButton;
    private JTextField risultatoField;
    private JLabel statusLabel;

    private final String[] MESI = {"Gennaio", "Febbraio", "Marzo", "Aprile", "Maggio", "Giugno",
            "Luglio", "Agosto", "Settembre", "Ottobre", "Novembre", "Dicembre"};


    public Main() {
        main = new JFrame("Calcolatore codice fiscale");
        main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        main.setSize(600, 400);
        main.setLocationRelativeTo(null); // Centra la finestra
        main.setResizable(false);

        JPanel risultatoPanel = CreaPannelloRisultato();
        JPanel datiPanel = CreaPannelloDati();
        JPanel buttonePanel = CreaPannelloBottone();

        main.add(risultatoPanel, BorderLayout.NORTH);
        main.add(datiPanel, BorderLayout.CENTER);
        main.add(buttonePanel, BorderLayout.SOUTH);

        main.setVisible(true);
    }

    public JPanel CreaPannelloRisultato() {
        JPanel risultatoPanel = new JPanel(new BorderLayout(10, 10));
        risultatoPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 0, 20));
        risultatoPanel.setBackground(Color.GRAY);


        JLabel titleLabel = new JLabel("Codice Fiscale:");
        risultatoField = new JTextField(25);
        risultatoField.setEditable(false);
        risultatoField.setBackground(Color.LIGHT_GRAY);
        risultatoPanel.add(titleLabel);
        risultatoPanel.add(risultatoField);

        statusLabel = new JLabel(" ");
        statusLabel.setForeground(Color.RED);

        risultatoPanel.add(risultatoField, BorderLayout.CENTER);
        risultatoPanel.add(statusLabel, BorderLayout.SOUTH);
        risultatoPanel.add(titleLabel, BorderLayout.WEST);

        return risultatoPanel;
    }

    public JPanel CreaPannelloDati() {
        JPanel datiPanel = new JPanel(new GridLayout(7, 2,10,10));
        datiPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        datiPanel.setBackground(Color.GRAY);


        datiPanel.add(new JLabel("Nome:"));
        nomeField = new JTextField(20);
        nomeField.setBackground(Color.LIGHT_GRAY);
        datiPanel.add(nomeField);

        datiPanel.add(new JLabel("Cognome:"));
        cognomeField = new JTextField(20);
        cognomeField.setBackground(Color.lightGray);
        datiPanel.add(cognomeField);

        datiPanel.add(new JLabel("Sesso:"));
        sessoCombo = new JComboBox<>(new String[]{"M", "F"});
        sessoCombo.setBackground(Color.LIGHT_GRAY);
        datiPanel.add(sessoCombo);

        datiPanel.add(new JLabel("Comune o Stato di nascita:"));
        comuneField = new JTextField(20);
        comuneField.setBackground(Color.LIGHT_GRAY);
        datiPanel.add(comuneField);

        datiPanel.add(new JLabel("Giorno:"));
        giornoCombo = new JComboBox<>();
        giornoCombo.setBackground(Color.LIGHT_GRAY);
        datiPanel.add(giornoCombo);
        for (int i = 1; i <= 31; i++) {
            giornoCombo.addItem(i);
        }

        datiPanel.add(new JLabel("Mese:"));
        meseCombo = new JComboBox<>(MESI);
        meseCombo.setBackground(Color.LIGHT_GRAY);
        datiPanel.add(meseCombo);

        datiPanel.add(new JLabel("Anno:"));
        annoCombo = new JComboBox<>();
        annoCombo.setBackground(Color.LIGHT_GRAY);
        datiPanel.add(annoCombo);
        int annoCorrente = YearMonth.now().getYear();
        for (int i = annoCorrente; i >= 1900; i--) {
            annoCombo.addItem(i);
        }

        meseCombo.addActionListener(e -> aggiornaGiorni());
        annoCombo.addActionListener(e -> aggiornaGiorni());

        return datiPanel;
    }

    private void aggiornaGiorni(){
        int giorno = (int) giornoCombo.getSelectedItem();
        int mese = meseCombo.getSelectedIndex() + 1;
        int anno = (int) annoCombo.getSelectedItem();

        Data DataDiNascita = new Data(giorno,mese,anno);

        int giorniMax = DataDiNascita.calcGiorniMese();

        giornoCombo.removeAllItems();
        for (int i = 1; i <= giorniMax; i++) {
            giornoCombo.addItem(i);
        }

        if (giorno <= giorniMax) {
            giornoCombo.setSelectedItem(giorno);
        }

    }

    public JPanel CreaPannelloBottone() {
        JPanel buttonePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonePanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));
        buttonePanel.setBackground(Color.GRAY);

        calcolaButton = new JButton("Calcola Codice Fiscale");
        calcolaButton.setBackground(Color.lightGray);
        calcolaButton.addActionListener(this);
        buttonePanel.add(calcolaButton);

        return buttonePanel;
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> new Main());

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == calcolaButton) {

            if (nomeField.getText().trim().isEmpty() || cognomeField.getText().trim().isEmpty() || comuneField.getText().trim().isEmpty()) {
                statusLabel.setText("Tutti i campi sono obbligatori!");
                return;
            }

            statusLabel.setText(" ");
            int giorno = (int) giornoCombo.getSelectedItem();
            int mese = meseCombo.getSelectedIndex() + 1;
            int anno = (int) annoCombo.getSelectedItem();

            Persona io = new Persona();
            Data dataDiNascita = new Data(giorno,mese,anno);

            io.setNome(nomeField.getText().trim());
            io.setCognome(cognomeField.getText().trim());
            io.setComune(comuneField.getText().trim());
            io.setSesso((String) sessoCombo.getSelectedItem());
            io.setData_di_nascita(dataDiNascita);

            try {
                if(io.calcoloCodice_Comuni().equals("null")){
                    statusLabel.setText("Comune o Stato non travato");
                    return;
                }
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

            if(io.getSesso().equals("null")){
                statusLabel.setText("errore nella lettura dei dati");
                return;
            }

            try {
                String cf = io.calcoloCognome() + io.calcoloNome() + io.calcoloData() + io.calcoloCodice_Comuni();
                cf = cf + io.calcoloCarattereControllo(cf);
                risultatoField.setText(cf);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
