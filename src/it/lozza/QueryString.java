package it.lozza;

import java.util.HashMap;

public class QueryString extends HashMap<String, String> {


    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (Entry<String, String> entry : this.entrySet()) {
            sb.append(entry.getKey());
            sb.append("=");
            sb.append(entry.getValue());
            sb.append("&");
        }
        return sb.toString();
    }
}
