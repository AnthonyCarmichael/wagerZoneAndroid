package com.example.wagerzone;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Scanner;
/**
 * Classe de gestion des fonds utilisant un fichier pour stocker les informations.
 */
public class GestionFonds{
    private File fichier;
    static private String path = "/data/data/com.example.wagerzone/files/fonds.txt";
    private BigDecimal fonds;
    /**
     * Constructeur de la classe GestionFonds.
     * Crée ou ouvre le fichier contenant les fonds.
     */
    public GestionFonds(){
        fichier = new File(path);
        if (!fichier.exists()) {
            try {
                fichier.createNewFile();
                fonds = BigDecimal.ZERO;
                ecrireFonds();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }else {
            lireFonds();
        }
    }
    /**
     * Lit les fonds à partir du fichier.
     */
    private void lireFonds(){
        try {
            Scanner scanner = new Scanner(fichier);
            if (scanner.hasNext()) {
                fonds = new BigDecimal(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Écrit les fonds dans le fichier.
     */
    private void ecrireFonds(){
        try {
            FileWriter writer = new FileWriter(fichier);
            writer.write(fonds.toPlainString());
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    /**
     * Ajoute une valeur aux fonds.
     * @param valeur La valeur à ajouter en float.
     */
    public void add(float valeur) {
        fonds = fonds.add(BigDecimal.valueOf(valeur));
        ecrireFonds();
    }
    /**
     * Ajoute une valeur aux fonds.
     * @param valeur La valeur à ajouter en BigDecimal.
     */
    public void add(BigDecimal valeur) {
        fonds = fonds.add(valeur);
        ecrireFonds();
    }
    /**
     * Retourne les fonds actuels.
     * @return Les fonds en BigDecimal.
     */
    public BigDecimal getFonds() {
        return fonds;
    }
    /**
     * Retourne les fonds actuels en float.
     * @return Les fonds en float.
     */
    public float getFloatFonds() {
        return fonds.floatValue();
    }
    /**
     * Définit les fonds.
     * @param fonds La nouvelle valeur des fonds en BigDecimal.
     */
    public void setFonds(BigDecimal fonds) {
        this.fonds = fonds;
        ecrireFonds();
    }
    /**
     * Définit les fonds.
     * @param fonds La nouvelle valeur des fonds en float.
     */
    public void setFonds(float fonds) {
        this.fonds = BigDecimal.valueOf(fonds);
        ecrireFonds();
    }
    /**
     * Efface le fichier contenant les fonds.
     * @return true si le fichier a été supprimé avec succès, sinon false.
     */
    public boolean deleteFonds() {
        fonds = BigDecimal.ZERO;
        return fichier.delete();
    }
}
