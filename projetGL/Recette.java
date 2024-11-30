package projetGL;

public class Recette {
    private String source;
    private double montant;
    private String date;


    public Recette(String source, double montant, String date) {
        this.source = source;
        this.montant = montant;
        this.date = date;
    }

    // Getters et Setters
    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public String getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "Recette [Source: " + source + ", Montant: " + montant + ", Date: " + date + "]";
    }
}