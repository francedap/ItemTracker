package controllerGui;

import javax.swing.*;
import java.awt.*;
import articolo.Articolo;
import categoria.Categoria;
import gestioneListe.GestioneListe;
import eccezioni.ElementoDupplicatoException;
import eccezioni.ElementoNonTrovatoException;

public class PannelloArticoli extends JPanel {
    private static final long serialVersionUID = 1L;
    
	private DefaultListModel<Articolo> listModel;
    private JList<Articolo> jList;
    
    
    private JTextField txtNome, txtPrezzo, txtNota;
    private JComboBox<String> comboCategorie; 

    public PannelloArticoli() {
        setLayout(new BorderLayout());

       
        JPanel pnlInput = new JPanel(new GridLayout(3, 4, 5, 8)); 
        pnlInput.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        txtNome = new JTextField();
        txtPrezzo = new JTextField();
        txtNota = new JTextField();
        comboCategorie = new JComboBox<>();
        JButton btnAggiungi = new JButton("Crea Articolo");
        JButton btnElimina = new JButton("Elimina");

        
        // RIGA 1: Nome e Prezzo
        pnlInput.add(new JLabel("Nome:"));
        pnlInput.add(txtNome);
        pnlInput.add(new JLabel("Prezzo (€):"));
        pnlInput.add(txtPrezzo);

        // RIGA 2: Nota e Categoria
        pnlInput.add(new JLabel("Nota:"));
        pnlInput.add(txtNota);
        pnlInput.add(new JLabel("Categoria:"));
        pnlInput.add(comboCategorie);
        
        
        pnlInput.add(btnAggiungi);
        pnlInput.add(new JLabel("")); 
        pnlInput.add(new JLabel(""));
        pnlInput.add(btnElimina);

        add(pnlInput, BorderLayout.NORTH);

        
        listModel = new DefaultListModel<>();
        jList = new JList<>(listModel);
        add(new JScrollPane(jList), BorderLayout.CENTER);

        aggiornaGrafica();

        // --- I BOTTONE ---
        btnAggiungi.addActionListener(e -> {
            try {
                String nome = txtNome.getText();
                double prezzo = Double.parseDouble(txtPrezzo.getText());
                String nota = txtNota.getText();
                String nomeCat = (String) comboCategorie.getSelectedItem();

                if (nomeCat == null) {
                    throw new ElementoNonTrovatoException("Devi selezionare una categoria!");
                }

                Categoria cat = GestioneListe.cercaCategoriaPerNome(nomeCat);
                
                GestioneListe.aggiungiArticoloGlobale(new Articolo(nome, prezzo, nota, cat));
                
                txtNome.setText("");
                txtPrezzo.setText("");
                txtNota.setText("");
                aggiornaGrafica();
                
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Il prezzo deve essere un numero (es: 1.50)", "Errore Formato", JOptionPane.ERROR_MESSAGE);
            } catch (ElementoDupplicatoException | ElementoNonTrovatoException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Errore Dati", JOptionPane.WARNING_MESSAGE);
            }
        });
        
        
        
        btnElimina.addActionListener(e -> {
            
            Articolo selezionato = jList.getSelectedValue(); 
            
            if (selezionato == null) {
                JOptionPane.showMessageDialog(this, "Seleziona un Articolo dalla lista!");
                return;
            }
            
            try {
            
                GestioneListe.eliminaArticolo(selezionato);
                aggiornaGrafica();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

   
    public void aggiornaGrafica() {
        listModel.clear();
        for (Articolo a : GestioneListe.getArticoliGlobali()) {
            listModel.addElement(a);
        }

        comboCategorie.removeAllItems();
        for (Categoria c : GestioneListe.getCategorie()) {
            comboCategorie.addItem(c.getNome());
        }
    }
}