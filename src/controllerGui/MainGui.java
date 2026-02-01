package controllerGui;

import javax.swing.*;
import java.awt.*;
import gestioneListe.GestioneListe;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainGui extends JFrame {
	private static final long serialVersionUID = 1L;
    
	
	/**
     * Costruisce la finestra principale, inizializza i componenti e configura i listener globali.
     * Flusso di inizializzazione:
     * Icona: Carica il logo e lo imposta sia per la finestra che per la Taskbar/Dock di sistema.
     * Frame: Imposta dimensioni, titolo e comportamento di chiusura.
     * Dati: Tenta di caricare i dati persistenti tramite {@link GestioneListe}. Se fallisce, avvisa l'utente.
     * Salvataggio: salva i dati automaticamente alla chiusura.
     * Tabs: Crea le tre schede principali e aggiunge un listener per aggiornare i pannelli (es. le combobox) quando vengono selezionati.
     */
    public MainGui() {
    	
    	//parte per il logo
    	java.net.URL url = getClass().getResource("/controllerGui/logo.jpeg");

    	if (url != null) {
    	    Image imgOriginale = new ImageIcon(url).getImage();
    	    
    	    // --- PARTE PER WINDOWS / LINUX (Icona della finestra) ---
    	    java.util.List<Image> icone = new java.util.ArrayList<>();
    	    icone.add(imgOriginale);
    	    icone.add(imgOriginale.getScaledInstance(32, 32, Image.SCALE_SMOOTH));
    	    this.setIconImages(icone);

    	    // --- PARTE PER MAC (Icona nel Dock) ---
    	    try {
    	        if (Taskbar.isTaskbarSupported()) {
    	            Taskbar taskbar = Taskbar.getTaskbar();
    	            if (taskbar.isSupported(Taskbar.Feature.ICON_IMAGE)) {
    	                taskbar.setIconImage(imgOriginale);
    	            }
    	        }
    	    } catch (Exception e) {
    	        // Se il sistema non supporta la taskbar , ignoriamo l'errore
    	        System.err.println("Taskbar non supportata su questo sistema.");
    	    }
    	}
    	//fine parte per il logo
        
        setTitle("ItemTracker");
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

  
        try {
            GestioneListe.caricaDati();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Impossibile caricare il database dei dati. Verrà creato un nuovo archivio.", 
                "Avviso Caricamento", 
                JOptionPane.WARNING_MESSAGE);
        }

  
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    GestioneListe.salvaDati();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

     
        JTabbedPane tabbedPane = new JTabbedPane();
        
      
        tabbedPane.addTab("Categorie", new PannelloCategorie()); 
        tabbedPane.addTab("Articoli", new PannelloArticoli());
        tabbedPane.addTab("Liste", new PannelloListe());

        add(tabbedPane);
        
        
        
        tabbedPane.addChangeListener(e -> {
            Component selected = tabbedPane.getSelectedComponent();
            if (selected instanceof PannelloArticoli) {
                ((PannelloArticoli) selected).aggiornaGrafica();
            } else if (selected instanceof PannelloListe) {
                ((PannelloListe) selected).aggiornaComboListe();
            }
        });
        
    }
    
    /**
     * Punto di ingresso (Entry Point) pe r l'applicazione in modalità grafica.
     * @param args argomenti da riga di comando (non utilizzati).
     */
    public static void main(String[] args) {
    	
        SwingUtilities.invokeLater(() -> {
            new MainGui().setVisible(true);
        });
    }
    
    
    
}
    