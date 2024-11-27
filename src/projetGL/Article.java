package projetGL;

public class Article {
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
