package projetGL;

public aspect SecurityAspect {
    pointcut sensitiveActions(): 
        call(void RecetteApp.modifier*(..)) || 
        call(void RecetteApp.annuler*(..));

    after(): sensitiveActions() {
        System.out.println("Tentative de suppression ou mise à jour d’une recette");
    }
}
