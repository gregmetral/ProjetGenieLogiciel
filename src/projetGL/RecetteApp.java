package projetGL;

import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

class Article {
    private String code;
    private String nom;
    private double prix;
    private String source;

    public Article(String code, String nom, double prix, String source) {
        this.code = code;
        this.nom = nom;
        this.prix = prix;
        this.source = source;
    }

    public String getCode() {
        return code;
    }

    public String getNom() {
        return nom;
    }

    public double getPrix() {
        return prix;
    }

    public String getSource() {
        return source;
    }

    @Override
    public String toString() {
        return nom + " (" + code + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Article article = (Article) obj;
        return code.equals(article.code);
    }

    @Override
    public int hashCode() {
        return code.hashCode();
    }
}

public class RecetteApp extends JFrame {
    private JComboBox<String> categorieComboBox;
    private JPanel articlesPanel;
    private JTextField montantGlobalField;
    private JTextArea recettesTextArea;
    private JButton addButton;

    private List<Article> articles = new ArrayList<>();
    private Map<String, List<Article>> selectionsParCategorie = new HashMap<>();
    private Map<String, Double> montantGlobalParCategorie = new HashMap<>();
    private List<String> recettes = new ArrayList<>();

    public RecetteApp() {
        articles.add(new Article("S1A1", "Chambre Île-du-Prince-Édouard", 50, "Hôtel"));
        articles.add(new Article("S1A2", "Chambre à Québec", 60, "Hôtel"));
        articles.add(new Article("S1A3", "Chambre Ottawa", 80, "Hôtel"));
        articles.add(new Article("S1A4", "Chambre St John", 70, "Hôtel"));
        articles.add(new Article("S1A5", "Chambre New Brankweek", 100, "Hôtel"));
        articles.add(new Article("S2A1", "Magasin Île-du-Prince-Édouard", 100, "Magasin"));
        articles.add(new Article("S2A2", "Magasin Québec city", 90, "Magasin"));
        articles.add(new Article("S2A3", "Magasin Ottawa", 80, "Magasin"));
        articles.add(new Article("S2A4", "Magasin St John", 70, "Magasin"));
        articles.add(new Article("S2A5", "Magasin New Brankweek", 100, "Magasin"));
        articles.add(new Article("S3A1", "Espace Île-du-Prince-Édouard", 100, "Espace"));
        articles.add(new Article("S3A2", "Espace Québec City", 80, "Espace"));
        articles.add(new Article("S3A3", "Espace Ottawa", 80, "Espace"));

        setTitle("Gestion des Recettes");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        categorieComboBox = new JComboBox<>(new String[]{"Hôtel", "Magasin", "Espace", "WC", "Douche"});
        categorieComboBox.addActionListener(e -> afficherArticles());
        add(categorieComboBox, BorderLayout.NORTH);

        articlesPanel = new JPanel();
        articlesPanel.setLayout(new BoxLayout(articlesPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(articlesPanel);
        add(scrollPane, BorderLayout.CENTER);

        recettesTextArea = new JTextArea(5, 30);
        recettesTextArea.setEditable(false);
        add(new JScrollPane(recettesTextArea), BorderLayout.EAST);

        addButton = new JButton("Ajouter Recette");
        addButton.addActionListener(e -> ajouterRecette());
        add(addButton, BorderLayout.SOUTH);

        afficherArticles();
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

        List<Article> articlesSelectionnes = selectionsParCategorie.getOrDefault(categorie, new ArrayList<>());

        for (Article article : articles) {
            if (article.getSource().equalsIgnoreCase(categorie)) {
                JCheckBox checkBox = new JCheckBox(article.toString());
                checkBox.setSelected(articlesSelectionnes.contains(article));
                checkBox.putClientProperty("article", article);

                checkBox.addActionListener(e -> {
                    if (checkBox.isSelected()) {
                        articlesSelectionnes.add(article);
                    } else {
                        articlesSelectionnes.remove(article);
                    }
                    selectionsParCategorie.put(categorie, articlesSelectionnes);
                });

                articlesPanel.add(checkBox);
            }
        }

        articlesPanel.revalidate();
        articlesPanel.repaint();
    }

    private void ajouterRecette() {
        String categorie = (String) categorieComboBox.getSelectedItem();
        List<Article> articlesSelectionnes = selectionsParCategorie.getOrDefault(categorie, new ArrayList<>());

        StringBuilder recetteDetails = new StringBuilder("Recette: " + categorie + "\n");

        for (Article article : articlesSelectionnes) {
            recetteDetails.append("- ").append(article.getNom()).append(" : ").append(article.getPrix()).append("€\n");
        }

        if (categorie.equals("WC") || categorie.equals("Douche")) {
            try {
                double montantGlobal = Double.parseDouble(montantGlobalField.getText());
                recetteDetails.append("Montant Global : ").append(montantGlobal).append("€\n");
                montantGlobalParCategorie.put(categorie, montantGlobal);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Veuillez entrer un montant valide.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        recettes.add(recetteDetails.toString());
        recettesTextArea.setText(String.join("\n", recettes));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(RecetteApp::new);
    }
}
