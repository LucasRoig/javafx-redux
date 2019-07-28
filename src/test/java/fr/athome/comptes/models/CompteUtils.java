package fr.athome.comptes.models;

import fr.athome.comptes.models.pojo.Compte;

public class CompteUtils {
    public static Compte validCompte(){
        return new Compte("user","pass");
    }
}
