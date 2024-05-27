package com.example.wagerzone;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Scanner;

public class GestionFonds{
    private File fichier;
    static private String path = "/data/data/com.example.wagerzone/files/fonds.txt";
    private BigDecimal fonds;
    //Crée/ouvre le fichier contenant le
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
    private void ecrireFonds(){
        try {
            FileWriter writer = new FileWriter(fichier);
            writer.write(fonds.toPlainString());
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void add(float valeur) {
        fonds = fonds.add(BigDecimal.valueOf(valeur));
        ecrireFonds();
    }
    public void add(BigDecimal valeur) {
        fonds = fonds.add(valeur);
        ecrireFonds();
    }

    public BigDecimal getFonds() {
        return fonds;
    }
    public float getFloatFonds() {
        return fonds.floatValue();
    }

    public void setFonds(BigDecimal fonds) {
        this.fonds = fonds;
        ecrireFonds();
    }
    public void setFonds(float fonds) {
        this.fonds = BigDecimal.valueOf(fonds);
        ecrireFonds();
    }
    //Efface le fichier
    public boolean deleteFonds() {
        fonds = BigDecimal.ZERO;
        return fichier.delete();
    }
}
