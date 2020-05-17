// Klassi autor: Margus Štšjogolev

import java.io.Serializable;
import java.util.List;

public class Aine implements Serializable{

    private String aineNimetus;
    private List<String> salvestatudTeemad;

    Aine(String nimetus, List<String> salvestatudTeemad) {
        String suuredTähed = nimetus.toUpperCase();
        this.aineNimetus = suuredTähed;
        this.salvestatudTeemad = salvestatudTeemad;
    }

    public String getAineNimetus() {
        return aineNimetus;
    }

    public List<String> getSalvestatudTeemad() {
        return this.salvestatudTeemad;
    }

    public void setAineNimetus(String uusNimetus) {
        String suuredTähed = uusNimetus.toUpperCase();
        this.aineNimetus = suuredTähed;
    }

    public String toStringAine() {
        return "aine nimi: " + aineNimetus;
    }
}
