// Klassi autor: Margus Štšjogolev

import java.io.Serializable;

public class Teema implements Serializable {


    private String teemaNimetus;
    private String teemaSisu;

    Teema(String teemaNimetus, String teemaSisu) {
        this.teemaNimetus = teemaNimetus;
        this.teemaSisu = teemaSisu;
    }

    public String getTeemaNimetus() {
        return teemaNimetus;
    }

    public String getTeemaSisu() {
        return this.teemaSisu;
    }

    public void setTeemaNimetus(String uusTeemaNimetus) {
        String suuredTähed = uusTeemaNimetus.toUpperCase();
        this.teemaNimetus = suuredTähed;
    }

    public void setTeemaSisu(String tekst) {
        this.teemaSisu = tekst;
    }

    public String toStringTeema() {
        return "teema: " + teemaNimetus;
    }


}
