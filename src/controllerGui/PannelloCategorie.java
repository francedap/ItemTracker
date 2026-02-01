package controllerGui;

import javax.swing.*;
import java.awt.*;
import categoria.Categoria;
import gestioneListe.GestioneListe;

public class PannelloCategorie extends JPanel {
	private static final long serialVersionUID = 1L;
    
    private DefaultListModel<String> listModel;
    private JList<String> jList;
    private JTextField txtNome;

    public PannelloCategorie() {
        setLayout(new BorderLayout()); 

        
        listModel = new DefaultListModel<>();
        jList = new JList<>(listModel);
        aggiornaGrafica(); 
        add(new JScrollPane(jList), BorderLayout.CENTER);

       
        JPanel pnlInput = new JPanel();
        txtNome = new JTextField(15);
        JButton btnAggiungi = new JButton("Aggiungi");
        JButton btnElimina = new JButton("Elimina");

        pnlInput.add(new JLabel("Nuova Categoria:"));
        pnlInput.add(txtNome);
        pnlInput.add(btnAggiungi);
        pnlInput.add(btnElimina);
        add(pnlInput, BorderLayout.NORTH);

        // --- I BOTTONI ---

        btnAggiungi.addActionListener(e -> {
            try {
                String nome = txtNome.getText();
                GestioneListe.aggiungiCategoria(new Categoria(nome));
                txtNome.setText("");
                aggiornaGrafica();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
            }
        });

        
        btnElimina.addActionListener(e -> {
            String selezionata = jList.getSelectedValue();
            if (selezionata == null) {
                JOptionPane.showMessageDialog(this, "Seleziona una categoria!");
                return;
            }
            try {
                GestioneListe.eliminaCategoria(new Categoria(selezionata));
                aggiornaGrafica();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

  
    private void aggiornaGrafica() {
        listModel.clear();
        for (Categoria c : GestioneListe.getCategorie()) {
        	
            listModel.addElement(c.getNome());
        }
    }
}