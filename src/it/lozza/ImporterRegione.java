package it.lozza;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class ImporterRegione extends Importer {

    public String getBandiRegione(int totalPageNumber, String target, boolean onlyActive) {
        String result = "";
        String url = "https://www.bandi.regione.lombardia.it/procedimenti/new/api/bandi/getBandiPaginati?";

        QueryString data = new QueryString();
        data.put("titolo", "");
        data.put("maxPageNum", "158");
        data.put("targetStr", target);
        data.put("descrizione", "");
        if(onlyActive){
            data.put("ricercaAvanzata", "true");
            data.put("stato[0]", "APERTO");
            data.put("stato[1]",  "IN%20APERTURA");
        }else{
            data.put("ricercaAvanzata", "false");
        }

        try {
            for (int p = 0; p <= totalPageNumber; p++) {
                data.put("pageNum", Integer.valueOf(p).toString());
                System.out.println("pagina " + p + " di Regione " + target);
                String bandiHTML = this.execPOST(url, data);
                result = result.concat(bandiHTML);
            }
        } catch (Exception e) {
            result = null;
            e.printStackTrace();
        }
        return result;
    }

    public ArrayList<Bando> parseBandi(String bandiHtml) {
        ArrayList<Bando> bandi = new ArrayList<>();
        Document doc = Jsoup.parse(bandiHtml);
        Elements cards = doc.select("div[class^=card card-bg card-big]");
        for (int i = 0; i < cards.size(); i++) {
            try {
                Bando bando = new Bando();
                String stato = Jsoup.parse(cards.get(i).select("small[class^=badge]").first().html()).text();
                switch (stato){
                    case "Aperto": bando.setStato(Bando.STATO.APERTO.toString());
                    case "In apertura": bando.setStato(Bando.STATO.IN_APERTURA.toString());
                    case "Chiuso": Bando.STATO.CHIUSO.toString();
                }
                try {
                    bando.setDataChiusura(cards.get(i).select("div[class^=etichetta mt-1]").first().getElementsByTag("strong").first().html());
                }catch(Exception e){
                    bando.setDataChiusura(null);
                }
                bando.setTipologia(cards.get(i).select("div[class^=etichetta mt-1]").first().select("p[class^=mt-1]").first().html());
                bando.setTitle(Jsoup.parse(cards.get(i).select("h4[class^=card-title]").first().html()).text());
                Elements descriptions = cards.get(i).select("p[class^=card-text]");
                bando.setDescription(Jsoup.parse(descriptions.get(0).html()).text());
                try {
                    bando.setCategoria(cards.get(i).select("span[class^=categoria]").first().getElementsByTag("strong").first().html());
                } catch (Exception e) {
                    bando.setCategoria(Bando.CATEG_ALL);
                }
                bando.setEnteErogatore(Bando.ENTI_EROGATORI.REGIONE.toString());
                bandi.add(bando);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(bandiHtml);
            }
        }

        return bandi;
    }

}
