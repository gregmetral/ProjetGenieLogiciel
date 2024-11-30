package projetGL;

import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class RecetteApp extends JFrame {
    private JComboBox<String> categorieComboBox;
    private JPanel articlesPanel;
    private JTextField montantGlobalField;
    private JTextArea resumeTextArea;
    private JButton addButton;


    private List<Article> articles = new ArrayList<>();
    private Map<String, List<Article>> selectionsParCategorie = new HashMap<>();
    private Map<String, Double> montantTotalParCategorie = new HashMap<>();
    private Map<String, Set<Article>> articlesUtilisesParCategorie = new HashMap<>();

    public RecetteApp() {
        articles.add(new Article("S1A1", "Chambre �le-du-Prince-�douard", 50, "H�tel"));
        articles.add(new Article("S1A2", "Chambre � Qu�bec", 60, "H�tel"));
        articles.add(new Article("S1A3", "Chambre Ottawa", 80, "H�tel"));
        articles.add(new Article("S1A4", "Chambre St John", 70, "H�tel"));
        articles.add(new Article("S1A5", "Chambre New Brankweek", 100, "H�tel"));
        articles.add(new Article("S2A1", "Magasin �le-du-Prince-�douard", 100, "Magasin"));
        articles.add(new Article("S2A2", "Magasin Qu�bec city", 90, "Magasin"));
        articles.add(new Article("S2A3", "Magasin Ottawa", 80, "Magasin"));
        articles.add(new Article("S2A4", "Magasin St John", 70, "Magasin"));
        articles.add(new Article("S2A5", "Magasin New Brankweek", 100, "Magasin"));
        articles.add(new Article("S3A1", "Espace �le-du-Prince-�douard", 100, "Espace"));
        articles.add(new Article("S3A2", "Espace Qu�bec City", 80, "Espace"));
        articles.add(new Article("S3A3", "Espace Ottawa", 80, "Espace"));

        for (String categorie : new String[]{"H�tel", "Magasin", "Espace", "WC", "Douche"}) {
            selectionsParCategorie.put(categorie, new ArrayList<>());
            montantTotalParCategorie.put(categorie, 0.0);
            articlesUtilisesParCategorie.put(categorie, new HashSet<>());
        }

        setTitle("Gestion des Recettes");
        setSize(900, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        categorieComboBox = new JComboBox<>(new String[]{"H�tel", "Magasin", "Espace", "WC", "Douche"});
        categorieComboBox.addActionListener(e -> afficherArticles());
        add(categorieComboBox, BorderLayout.NORTH);

        articlesPanel = new JPanel();
        articlesPanel.setLayout(new BoxLayout(articlesPanel, BoxLayout.Y_AXIS));
        JScrollPane articlesScrollPane = new JScrollPane(articlesPanel);
        add(articlesScrollPane, BorderLayout.CENTER);

        resumeTextArea = new JTextArea(10, 20);
        resumeTextArea.setEditable(false);
        add(new JScrollPane(resumeTextArea), BorderLayout.EAST);

        addButton = new JButton("Ajouter Recette");
        addButton.addActionListener(e -> ajouterRecette());
        add(addButton, BorderLayout.SOUTH);

        afficherArticles();
        mettreAJourResume();
        setVisible(true);
    }

    private void afficherArticles() {
        articlesPanel.removeAll();
        String categorie = (String) categorieComboBox.getSelectedItem();

        if (categorie.equals("WC") || categorie.equals("Douche")) {
            JLabel montantLabel = new JLabel("Montant Global :");
            montantGlobalField = new JTextField(10);
            montantGlobalField.setPreferredSize(new Dimension(100, 20));
            JPanel montantPanel = new JPanel();
            montantPanel.add(montantLabel);
            montantPanel.add(montantGlobalField);
            articlesPanel.add(montantPanel);
        }

        List<Article> articlesSelectionnes = selectionsParCategorie.get(categorie);
        Set<Article> articlesUtilises = articlesUtilisesParCategorie.get(categorie);

        for (Article article : articles) {
            if (article.getSource().equalsIgnoreCase(categorie)) {
                JPanel articlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

                JCheckBox checkBox = new JCheckBox(article.toString());
                checkBox.putClientProperty("article", article);

                if (articlesUtilises.contains(article)) {
                    checkBox.setSelected(true);
                    checkBox.setEnabled(false);

                    JButton modifierButton = new JButton("Modifier");
                    modifierButton.addActionListener(e -> modifierPrix(categorie, article));

                    JButton annulerButton = new JButton("Annuler");
                    annulerButton.addActionListener(e -> annulerRecette());

                    articlePanel.add(checkBox);
                    articlePanel.add(modifierButton);
                    articlePanel.add(annulerButton);
                } else {
                    checkBox.setSelected(articlesSelectionnes.contains(article));
                    checkBox.addActionListener(e -> {
                        if (checkBox.isSelected()) {
                            articlesSelectionnes.add(article);
                        } else {
                            articlesSelectionnes.remove(article);
                        }
                        selectionsParCategorie.put(categorie, articlesSelectionnes);
                    });
                    articlePanel.add(checkBox);
                }

                articlesPanel.add(articlePanel);
            }
        }

        articlesPanel.revalidate();
        articlesPanel.repaint();
    }

    private void modifierPrix(String categorie, Article article) {
        String nouveauPrix = JOptionPane.showInputDialog(this, 
                "Entrez le nouveau prix pour : " + article.getNom(), 
                article.getPrix());
        if (nouveauPrix != null) {
            try {
                //
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Prix invalide! Veuillez entrer un nombre.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void annulerRecette() {
    	//
    }

    private void ajouterRecette() {
        String categorie = (String) categorieComboBox.getSelectedItem();
        List<Article> articlesSelectionnes = selectionsParCategorie.get(categorie);
        Set<Article> articlesUtilises = articlesUtilisesParCategorie.get(categorie);

        double montantTotal = 0.0;

        List<Article> recettesValid�es = new ArrayList<>();

        for (Article article : articlesSelectionnes) {
            montantTotal += article.getPrix();
            articlesUtilises.add(article); 

            Article articleAlerte = new Article(article.getCode(), article.getNom(), article.getPrix(), article.getSource());
            recettesValid�es.add(articleAlerte); 
        }
        if (categorie.equals("WC") || categorie.equals("Douche")) {
            try {
                double montantGlobal = Double.parseDouble(montantGlobalField.getText());
                montantTotal += montantGlobal;
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Veuillez entrer un montant valide.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        montantTotalParCategorie.put(categorie, montantTotalParCategorie.get(categorie) + montantTotal);

        RecetteObserver observer = new RecetteObserver();
        for (Article article : recettesValid�es) {
        	article.addObserver(observer); 
        	article.valider();  
        }
        mettreAJourResume();

        selectionsParCategorie.put(categorie, new ArrayList<>());
        afficherArticles();
    }
    
    
    private void mettreAJourResume() {
        StringBuilder resume = new StringBuilder("R�sum� des recettes :\n");
        for (Map.Entry<String, Double> entry : montantTotalParCategorie.entrySet()) {
            resume.append(entry.getKey()).append(" : ").append(entry.getValue()).append("�\n");
        }
        resumeTextArea.setText(resume.toString());
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(RecetteApp::new);
    }
}
