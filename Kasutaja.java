// Klassi autor: Margus Štšjogolev

import java.io.Serializable;
import java.util.List;

public class Kasutaja implements Serializable {

    private String kasutajaNimi;
    private List<String> selleKasutajaAined;

    Kasutaja(String kasutajaNimi, List<String> selleKasutajaAined) {
        this.kasutajaNimi = kasutajaNimi;
        this.selleKasutajaAined = selleKasutajaAined;
    }

    public String getKasutajaNimi() {
        return this.kasutajaNimi;
    }

    public List<String> getSelleKasutajaAined() {
        return this.selleKasutajaAined;
    }

    public void setKasutajaNimi(String uusKasutajaNimi) {
        this.kasutajaNimi = uusKasutajaNimi;
    }
}
