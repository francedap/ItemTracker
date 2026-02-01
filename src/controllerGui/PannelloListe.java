package controllerGui;

import javax.swing.*;
import java.awt.*;
import articolo.Articolo;
import gestioneListe.GestioneListe;
import listaDiArticoli.ListaDiArticoli;

public class PannelloListe extends JPanel {
	private static final long serialVersionUID = 1L;
	
    private JComboBox<String> comboListe;
    private JComboBox<Articolo> comboArticoliAnagrafica;
    private DefaultListModel<String> modelAttivi;
    private DefaultListModel<String> modelRimossi;
    private JLabel lblTotale;

    @SuppressWarnings("unchecked")
	public PannelloListe() {
        setLayout(new BorderLayout(10, 10));

        JPanel pnlTop = new JPanel();
        comboListe = new JComboBox<>();
        JButton btnNuovaLista = new JButton("Nuova Lista");
        
        pnlTop.add(new JLabel("Seleziona Lista:"));
        pnlTop.add(comboListe);
        pnlTop.add(btnNuovaLista);
        add(pnlTop, BorderLayout.NORTH);
        
        JPanel pnlSearch = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JTextField txtSearch = new JTextField(15);
        JButton btnSearch = new JButton("Cerca Prefisso");

        pnlSearch.add(new JLabel("Cerca Articolo:"));
        pnlSearch.add(txtSearch);
        pnlSearch.add(btnSearch);
        
        JPanel pnlNordCompleto = new JPanel(new GridLayout(2,1));
        pnlNordCompleto.add(pnlTop);
        pnlNordCompleto.add(pnlSearch);
        add(pnlNordCompleto, BorderLayout.NORTH);

        JPanel pnlCenter = new JPanel(new GridLayout(1, 2, 10, 10));

        JPanel pnlAttivi = new JPanel(new BorderLayout());
        modelAttivi = new DefaultListModel<>();
        pnlAttivi.add(new JLabel("Da Acquistare:"), BorderLayout.NORTH);
        pnlAttivi.add(new JScrollPane(new JList<>(modelAttivi)), BorderLayout.CENTER);
       
        JPanel pnlAggiunta = new JPanel(new BorderLayout(5, 0));
        comboArticoliAnagrafica = new JComboBox<>();
        comboArticoliAnagrafica.setPreferredSize(new Dimension(150, 25)); 

        JButton btnAdd = new JButton("+");
        JButton btnRemove = new JButton("-");

        pnlAggiunta.add(btnRemove, BorderLayout.WEST); 
        pnlAggiunta.add(comboArticoliAnagrafica, BorderLayout.CENTER);
        pnlAggiunta.add(btnAdd, BorderLayout.EAST);    

        pnlAttivi.add(pnlAggiunta, BorderLayout.SOUTH);

        JPanel pnlRimossi = new JPanel(new BorderLayout());
        modelRimossi = new DefaultListModel<>();
        pnlRimossi.add(new JLabel("Storico/Rimossi:"), BorderLayout.NORTH);
        pnlRimossi.add(new JScrollPane(new JList<>(modelRimossi)), BorderLayout.CENTER);

     
        JPanel pnlBottoniRimossi = new JPanel(new GridLayout(1, 2, 5, 5)); 
        JButton btnRipristina = new JButton("Ripristina");
        JButton btnSvuota = new JButton("Svuota Cestino");

        pnlBottoniRimossi.add(btnRipristina);
        pnlBottoniRimossi.add(btnSvuota);

        pnlRimossi.add(pnlBottoniRimossi, BorderLayout.SOUTH);
        
        pnlCenter.add(pnlAttivi);
        pnlCenter.add(pnlRimossi);
        add(pnlCenter, BorderLayout.CENTER);

        lblTotale = new JLabel("TOTALE: 0.00 €", SwingConstants.RIGHT);
        lblTotale.setFont(new Font("Arial", Font.BOLD, 18));
        add(lblTotale, BorderLayout.SOUTH);

        // --- EVENTI ---

        comboListe.addActionListener(e -> aggiornaDettagliLista());

        btnNuovaLista.addActionListener(e -> {
            String nome = JOptionPane.showInputDialog(this, "Nome della nuova lista:");
            if (nome != null && !nome.isEmpty()) {
                try {
                    GestioneListe.creaLista(nome);
                    aggiornaComboListe();
                    comboListe.setSelectedItem(nome);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage());
                }
            }
        });

        btnAdd.addActionListener(e -> {
            try {
                String nomeL = (String) comboListe.getSelectedItem();
                Articolo art = (Articolo) comboArticoliAnagrafica.getSelectedItem();
                if (nomeL != null && art != null) {
                    ListaDiArticoli lista = (ListaDiArticoli) GestioneListe.getLista(nomeL);
                    lista.aggiungiArticolo(art);
                    aggiornaDettagliLista();
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        });

        aggiornaComboListe();
        
        btnRemove.addActionListener(e -> {
            try {
             
                String nomeL = (String) comboListe.getSelectedItem();
                JList<String> listaGraficaAttivi = (JList<String>) ((JScrollPane)pnlAttivi.getComponent(1)).getViewport().getView();
                String nomeArticoloSelezionato = listaGraficaAttivi.getSelectedValue();

                if (nomeL != null && nomeArticoloSelezionato != null) {
                    ListaDiArticoli lista = (ListaDiArticoli) GestioneListe.getLista(nomeL);
            
                    Articolo daSpostare = null;
                    for (Articolo a : lista.getArticoliAttivi()) {
                        if (a.getNomeArticolo().equalsIgnoreCase(nomeArticoloSelezionato)) {
                            daSpostare = a;
                            break;
                        }
                    }

                    if (daSpostare != null) {
                       
                        lista.rimuoviArticolo(daSpostare);
                        aggiornaDettagliLista(); 
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Seleziona un articolo dalla lista 'Da Acquistare'");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Errore: " + ex.getMessage());
            }
        });

   
        btnRipristina.addActionListener(e -> {
            try {
                String nomeL = (String) comboListe.getSelectedItem();
                
                JList<String> listaGraficaRimossi = (JList<String>) ((JScrollPane)pnlRimossi.getComponent(1)).getViewport().getView();
                String nomeArticoloDaRipristinare = listaGraficaRimossi.getSelectedValue();

                if (nomeL != null && nomeArticoloDaRipristinare != null) {
                    ListaDiArticoli lista = (ListaDiArticoli) GestioneListe.getLista(nomeL);

                    Articolo daRecuperare = null;
                    for (Articolo a : lista.getArticoliRimossi()) {
                        if (a.getNomeArticolo().equalsIgnoreCase(nomeArticoloDaRipristinare)) {
                            daRecuperare = a;
                            break;
                        }
                    }

                    if (daRecuperare != null) {
                        lista.ripristinaArticolo(daRecuperare);
                        aggiornaDettagliLista();
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Seleziona un articolo dallo 'Storico'");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Errore: " + ex.getMessage());
            }
        });
        
        
        btnSearch.addActionListener(e -> {
            String prefisso = txtSearch.getText();
            String nomeL = (String) comboListe.getSelectedItem();
            
            if (nomeL != null && !prefisso.isEmpty()) {
                try {
                    ListaDiArticoli lista = (ListaDiArticoli) GestioneListe.getLista(nomeL);
                  
                    java.util.List<Articolo> risultati = lista.cercaPerPrefisso(prefisso);
                    
                    if (risultati.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Nessun articolo trovato con: " + prefisso);
                    } else {
                       
                        StringBuilder sb = new StringBuilder("Articoli trovati:\n");
                        for (Articolo a : risultati) {
                            sb.append("- ").append(a.getNomeArticolo()).append("\n");
                        }
                        JOptionPane.showMessageDialog(this, sb.toString(), "Risultati Ricerca", JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Errore nella ricerca: " + ex.getMessage());
                }
            }
        });
        
        btnSvuota.addActionListener(e -> {
            String nomeL = (String) comboListe.getSelectedItem();
            if (nomeL != null) {
                int conferma = JOptionPane.showConfirmDialog(this, "Vuoi eliminare definitivamente lo storico?");
                if (conferma == JOptionPane.YES_OPTION) {
                    try {
                        ListaDiArticoli lista = (ListaDiArticoli) GestioneListe.getLista(nomeL);
                        lista.svuotaCancellati();
                        aggiornaDettagliLista();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
    }

    public void aggiornaComboListe() {
        comboListe.removeAllItems();
        for (ListaDiArticoli l : GestioneListe.getAllListe()) {
            comboListe.addItem(l.getNomeLista());
        }
        
        comboArticoliAnagrafica.removeAllItems();
        for (Articolo a : GestioneListe.getArticoliGlobali()) {
            comboArticoliAnagrafica.addItem(a);
        }
    }

    private void aggiornaDettagliLista() {
        String nomeL = (String) comboListe.getSelectedItem();
        modelAttivi.clear();
        modelRimossi.clear();
        
        if (nomeL != null) {
            try {
                ListaDiArticoli lista = (ListaDiArticoli) GestioneListe.getLista(nomeL);
                for (Articolo a : lista.getArticoliAttivi()) modelAttivi.addElement(a.getNomeArticolo());
                for (Articolo a : lista.getArticoliRimossi()) modelRimossi.addElement(a.getNomeArticolo());
                lblTotale.setText(String.format("TOTALE: %.2f €", lista.getPrezzoTotale()));
            } catch (Exception e) {
                lblTotale.setText("Errore caricamento");
            }
        }
    }
    
    
    
}

