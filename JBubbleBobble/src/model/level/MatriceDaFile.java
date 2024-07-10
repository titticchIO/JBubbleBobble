package model.level;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class MatriceDaFile {


    /**
     * Metodo per creare una matrice di stringhe da un file di testo.
     * Ogni elemento della matrice è un carattere preso dal file.
     * @param nomeFile Il nome del file di testo da leggere
     * @return Una matrice di stringhe dove ogni quadrato è un carattere
     * @throws IOException Se ci sono problemi nella lettura del file
     */
    public static String[][] creaMatriceDaFile(String nomeFile) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(nomeFile));
        String line;
        int numRows = 0;
        int numCols = 0;

        // Conta il numero di righe e determina il numero massimo di colonne
        while ((line = reader.readLine()) != null) {
            numRows++;
            if (line.length() > numCols) {
                numCols = line.length();
            }
        }

        reader.close();

        // Crea la matrice di stringhe
        String[][] matrice = new String[numRows][numCols];

        // Riempie la matrice con i caratteri dal file
        reader = new BufferedReader(new FileReader(nomeFile));
        int row = 0;
        while ((line = reader.readLine()) != null) {
            for (int col = 0; col < line.length(); col++) {
                matrice[row][col] = String.valueOf(line.charAt(col));
            }
            // Se la riga ha lunghezza minore del numero massimo di colonne, riempie con spazi vuoti
            for (int col = line.length(); col < numCols; col++) {
                matrice[row][col] = " ";
            }
            row++;
        }

        reader.close();
        return matrice;
    }

    /**
     * Metodo per stampare una matrice di stringhe.
     * @param matrice La matrice da stampare
     */
    public static void stampaMatrice(String[][] matrice) {
        for (String[] row : matrice) {
            for (String cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println();
        }
    }
    
    
    public static void main(String[] args) {
        try {
            String[][] matrice = creaMatriceDaFile("resources/levels/Livello1.txt");
            stampaMatrice(matrice);
        } catch (IOException e) {
            System.err.println("Errore nella lettura del file: " + e.getMessage());
        }
    }
}
