package it.lozza;

import org.bson.Document;

public class Bando extends Document {

    public static final String CATEG_ALL = "Tutte";
    public static final String TIPOLOGIA_BANDO = "Bando";

    public static enum STATO {APERTO, CHIUSO, IN_APERTURA};

    public static enum ENTI_EROGATORI {PNRR, REGIONE};


    public Bando() {
    }

    public void setTitle(String title) {
        this.append("title", title);
    }

    public String getTitle() {
        return this.get("title").toString();
    }

    public void setDescription(String description) {
        this.append("description", description);
    }

    public String getDescription() {
        return this.get("description").toString();
    }

    public void setDataChiusura(String dataChiusura) {
        this.append("dataChiusura", dataChiusura);
    }

    public String getDataChiusura() {
        return this.get("dataChiusura").toString();
    }

    public void setStato(String stato) {
        this.append("stato", stato);
    }

    public String getStato() {
        return this.get("stato").toString();
    }

    public void setCategoria(String categoria) {
        this.append("categoria", categoria);
    }

    public String getCategoria() {
        return this.get("categoria").toString();
    }

    public void setEnteErogatore(String enteErogatore) {
        this.append("enteErogatore", enteErogatore);
    }

    public String getEnteErogatore() {
        return this.get("enteErogatore").toString();
    }

    public void setTipologia(String tipologia) { this.append("tipologia", tipologia); }

    public String getTipologia() {
        return this.get("tipologia").toString();
    }

}
