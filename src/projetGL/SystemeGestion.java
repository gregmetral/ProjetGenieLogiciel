package projetGL;

import java.util.ArrayList;
import java.util.List;

public class SystemeGestion {
    private List<Recette> recettes; 

    public SystemeGestion() {
        this.recettes = new ArrayList<>();
    }

    public void ajouterRecette(Recette recette) {
        recettes.add(recette);
        System.out.println("Recette ajoutée par le commis : " + recette);
    }


    public void afficherRecettes() {
        System.out.println("Liste des recettes :");
        for (Recette recette : recettes) {
            System.out.println(recette);
        }
    }

}