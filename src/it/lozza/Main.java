package it.lozza;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        CollectionManager dm = new CollectionManager("bandi");
        try {
            dm.connect();
            dm.clear();
            dm.dropAllIndexes();

            ImporterRegione ir = new ImporterRegione();
            String bandiHTML = ir.getBandiRegione(35, "ENTI_E_OPERATORI", true);
            ArrayList<Bando> bandiRegione = ir.parseBandi(bandiHTML);
            dm.writeBando(bandiRegione);

            ImporterPnrr ip = new ImporterPnrr();
            List<String> urls = Arrays.asList("Rivoluzione%20verde%20e%20transizione%20ecologica", "Infrastrutture%20per%20una%20mobilit%C3%A0%20sostenibile");
            for (int i = 0; i < urls.size(); i++) {
                String html = ip.getBandiPnrr(urls.get(i));
                ArrayList<Bando> bandiPnrr = ip.parseBandi(html);
                dm.writeBando(bandiPnrr);
            }
            System.out.println("SIZE=" + dm.size());

            dm.createTextIndex(new ArrayList<>(Arrays.asList("title", "description")));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dm.disconnect();
        }
    }
}