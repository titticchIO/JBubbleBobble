package game.model;

import java.util.HashMap;
import java.util.Map;

public class EnemiesPath {

    // Mappa per associare ogni lettera a un percorso
    private static final Map<String, String> pathsMap = new HashMap<>();

    static {
        // Inizializzazione della mappa con percorsi di esempio
        pathsMap.put("Z", "enemies/zenchan/");
        pathsMap.put("M", "enemies/monsta/");
        pathsMap.put("N", "enemies/banebou/");
        pathsMap.put("U", "enemies/pulpul/");
        pathsMap.put("I", "enemies/invader/");
        pathsMap.put("S", "enemies/skelmonsta/");
        // Aggiungi altri percorsi per le altre lettere...
    }

    // Metodo per ottenere il percorso associato a una lettera
    public static String getPath(String letter) {
        return pathsMap.get(letter);
    }
}
