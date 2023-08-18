package it.lozza;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;


public class ImporterPnrr extends Importer {

    public String getBandiPnrr(String mission) {
        String response = null;
        String url = "https://www.italiadomani.gov.it/content/sogei-ng/it/it/opportunita/bandi-amministrazioni-titolari/jcr:content/root/container/newnoticessearch.searchResults.html?";

        QueryString data = new QueryString();
        data.put("mission", mission);
        data.put("geographicArea", "Tutto%20il%20territorio%20nazionale&geographicArea=Lombardia");
        data.put("status", "In%20corso");
        data.put("orderby", "%40jcr%3Acontent%2Fstatus");
        data.put("sort", "asc");

        try {
            System.out.println("pagina 0 di PNRR "+mission);
            response = this.execGET(url, data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    public ArrayList<Bando> parseBandi(String bandiHtml) {
        ArrayList<Bando> bandi = new ArrayList<>();
        Bando bando = new Bando();
        Document doc = Jsoup.parse(bandiHtml);
        Elements root = doc.select("div[role^=row]");
        for(int r=0; r<root.size(); r++){
            Elements elem = doc.select("div[class~=description*]");
            for (int i = 0; i < elem.size(); i++) {
                bando.setDescription(elem.get(i).select("p[class^=text]").first().html());
            }

            elem = doc.select("div[class^=single-info]");
            for (int i = 0; i < elem.size(); i++) {
                bando.setTitle(elem.get(i).getElementsByTag("p").first().html());
            }

            elem = doc.select("div[class^=col-lg-2]");
            for (int i = 0; i < elem.size(); i++) {
                String label = elem.get(i).getElementsByTag("p").first().html();
                if (label.equals("Stato")) {
                    String stato = elem.get(i).select("div[class^=status-item]").first().html();
                    bando.setStato(stato.equalsIgnoreCase("in corso") ? Bando.STATO.APERTO.toString() : Bando.STATO.CHIUSO.toString());
                }else {
                    bando.setDataChiusura(elem.get(i).select("p[class^=text]").first().html());
                }
            }
            bando.setTipologia(Bando.TIPOLOGIA_BANDO);
            bando.setEnteErogatore(Bando.ENTI_EROGATORI.PNRR.toString());
            bandi.add(bando);
        }
        return bandi;
    }
}
