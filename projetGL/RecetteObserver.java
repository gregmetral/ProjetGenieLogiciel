package projetGL;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JOptionPane;

@SuppressWarnings("deprecation")
public class RecetteObserver implements Observer {
	private List<String> recettesValidées = new ArrayList<>();  

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof Article) {
        	Article article = (Article) o;

            recettesValidées.add("La " + article.getNom() + " est désormais disponible");
        }
        if (!recettesValidées.isEmpty()) {
            String message = String.join("\n", recettesValidées);
            JOptionPane.showMessageDialog(null, message, "ALERTE", JOptionPane.WARNING_MESSAGE);
            recettesValidées.clear();
        }
    }
}
