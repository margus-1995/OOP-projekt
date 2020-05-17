// Klassi autor: Margus Štšjogolev

import java.io.*;
import java.nio.file.FileVisitResult;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        logimine();
    }

    public static void logimine() {
        System.out.println("(Programmist väljumiseks sisesta kasutajanimesse \"välju\" )");
        Scanner kasutajaNimi = new Scanner(System.in);
        System.out.print(" Tere tulemast konspekti! Palun sisesta kasutajanimi: ");
        String sisestus = kasutajaNimi.nextLine();
        if (sisestus.equals("") == false) {
            String välju = sisestus.toUpperCase();
            if (välju.equals("VÄLJU")) {
                System.out.println("Peatse kohtumiseni! ");
                System.exit(0);
            }
            try (InputStream sisse = new FileInputStream(sisestus + ".txt")) {
                logimineÕnnestus(sisestus);
            } catch (Exception e) {
                System.out.println("Sellist kasutajat pole olemas! ");
                proovimeUuesti();
            }
        } else {
            System.out.println("Kasutajatunnus peab olema vähemalt ühe tähemärgi pikkune, proovime uuesti...");
            logimine();
        }
    }

    public static void proovimeUuesti() {
        Scanner regamine = new Scanner(System.in);
        System.out.print("Kas soovite luua uus kasutaja (JAH/EI) ? ");
        String regamiseOtsus = regamine.nextLine();

        if (regamiseOtsus.toUpperCase().equals("JAH")) {
            kasutajaLoomine();
        } else if (regamiseOtsus.toUpperCase().equals("EI")) {
            System.out.println("Programm taaskäivitatakse! ");
            logimine();
        } else {
            System.out.println("Ei saanud sisendist aru, proovime uuesti...");
            proovimeUuesti();
        }
    }


    public static void kasutajaLoomine() {
        Scanner kasutaja = new Scanner(System.in);
        System.out.println("(selleks, et minna tagasi logimise juurde, sisesta \"tagasi\" )");
        System.out.print("Sisesta kasutajanimi: ");
        String kasutajaNimi = kasutaja.nextLine();
        String failiNimi = kasutajaNimi + ".txt";
        File uusKasutaja = new File(failiNimi);
        String tagasi = kasutajaNimi.toUpperCase();
        if (tagasi.equals("TAGASI"))
            logimine();
        else if (tagasi.equals("VÄLJU"))
            System.exit(0);
        else if (tagasi.equals("") == false) {
            try {
                if (uusKasutaja.createNewFile() == true) {
                    List<String> ained = new ArrayList<>();
                    Kasutaja uusIsend = new Kasutaja(kasutajaNimi, ained);
                    try {
                        List<Kasutaja> kasutajaHaldamine = new ArrayList<>();
                        File kasutajaSalvestamine = new File(failiNimi);
                        FileOutputStream salvestamine = new FileOutputStream(kasutajaSalvestamine);
                        ObjectOutputStream objektiSalvestamine = new ObjectOutputStream(salvestamine);
                        kasutajaHaldamine.add(uusIsend);
                        objektiSalvestamine.writeObject(kasutajaHaldamine);
                        objektiSalvestamine.close();
                        System.out.println("Kasutaja on loodud! ");
                        logimineÕnnestus(kasutajaNimi);
                    } catch (Exception e) {
                        System.out.println("Midagi läks valesti! Programm taaskäivitatakse!");
                        logimine();
                    }
                } else {
                    System.out.println("See kasutajanimi on juba võetud, proovi mõni teine! ");
                    kasutajaLoomine();
                }
            } catch (Exception e) {
                System.out.println("Midagi läks valesti! Programm taaskäivitatakse!");
                logimine();
            }
        } else {
            System.out.println("Ei saanud sisendist aru, proovime uuesti...");
            kasutajaLoomine();
        }
    }

    public static void logimineÕnnestus(String nimi) {
        System.out.println("Tere tulemast, " + nimi + "!");
        String failiNimi = nimi + ".txt";
        try  {
            File fail = new File(failiNimi);
            InputStream sisse = new FileInputStream(fail);
            ObjectInputStream loeObjektSisse = new ObjectInputStream(sisse);
            List<Kasutaja> jooksev = (List<Kasutaja>)loeObjektSisse.readObject();
            loeObjektSisse.close();
            Iterator<Kasutaja> kasutajaObjektina = jooksev.iterator();
            Kasutaja mina = kasutajaObjektina.next();
            Scanner soov = new Scanner(System.in);
            System.out.print("Kas soovid aineid vaadata või muuta? Sisesta (VAATA/MUUDA) ");
            String otsus = soov.nextLine().toUpperCase();
            if (otsus.equals("TAGASI"))
                logimine();
            else if (otsus.equals("VÄLJU"))
                System.exit(0);
            if (otsus.equals("VAATA")) {
                aineVaatamine(mina);
            } else if (otsus.equals("MUUDA")) {
                aineMuutmine(mina);
            } else {
                System.out.println("Ei saanud sisendist aru, proovime uuesti...");
                logimineÕnnestus(nimi);
            }

        }
        catch (Exception e) {
            System.out.println("Midagi läks valesti! Programm taaskäivitatakse!");
            System.out.println(e);
            logimine();
        }
    }

    public static void lisaAine(Kasutaja mina) {
        Scanner uusAine = new Scanner(System.in);
        System.out.print("Sisesta aine nimi: ");
        String sisestatudAine = uusAine.nextLine();
        if (sisestatudAine.toUpperCase().equals("TAGASI"))
            logimineÕnnestus(mina.getKasutajaNimi());
        if (sisestatudAine.equals("") == false) {
            for (int i = 0; i < mina.getSelleKasutajaAined().size(); i++) {
                if (mina.getSelleKasutajaAined().get(i).equals(sisestatudAine)) {
                    System.out.println("See aine on juba olemas! Proovi uuest...");
                    lisaAine(mina);
                }
            }
            mina.getSelleKasutajaAined().add(sisestatudAine);
            try {
                List<Kasutaja> kasutajaHaldamine = new ArrayList<>();
                File kasutajaSalvestamine = new File(mina.getKasutajaNimi() + ".txt");
                FileOutputStream salvestamine = new FileOutputStream(kasutajaSalvestamine);
                ObjectOutputStream objektiSalvestamine = new ObjectOutputStream(salvestamine);
                kasutajaHaldamine.add(mina);
                objektiSalvestamine.writeObject(kasutajaHaldamine);
                objektiSalvestamine.close();
                File uusFail = new File(sisestatudAine + ".txt");
                if (uusFail.createNewFile() == true) {
                    List<String> tühi = new ArrayList<>();
                    Aine uusIsend = new Aine(sisestatudAine, tühi);
                    try {
                        List<Aine> aineHaldamine = new ArrayList<>();
                        File aineSalvestamine = new File(sisestatudAine + ".txt");
                        FileOutputStream uueAineSalvestamine = new FileOutputStream(aineSalvestamine);
                        ObjectOutputStream aineObjektiSalvestamine = new ObjectOutputStream(uueAineSalvestamine);
                        aineHaldamine.add(uusIsend);
                        aineObjektiSalvestamine.writeObject(aineHaldamine);
                        aineObjektiSalvestamine.close();
                    } catch (Exception e) {
                        System.out.println("Midagi läks valesti! Programm taaskäivitatakse!");
                        System.out.println(e);
                        logimine();
                    }
                }
                System.out.println("Aine on lisatud!");
                logimineÕnnestus(mina.getKasutajaNimi());
            } catch (Exception e) {
                System.out.println("Midagi läks valesti! Programm taaskäivitatakse...");
                System.out.println(e);
                logimine();
            }
        } else {
            System.out.println("Ei saanud sisendist aru, proovime uuesti...");
            lisaAine(mina);
        }
    }

    public static void kustutaAine(Kasutaja mina) {
        Scanner uusAine = new Scanner(System.in);
        System.out.print("Sisesta aine nimi: ");
        String sisestatudAine = uusAine.nextLine();
        if (sisestatudAine.toUpperCase().equals("TAGASI"))
            logimineÕnnestus(mina.getKasutajaNimi());
        if (sisestatudAine.equals("") == false) {
            for (int i = 0; i < mina.getSelleKasutajaAined().size(); i++) {
                if (mina.getSelleKasutajaAined().get(i).equals(sisestatudAine)) {
                    mina.getSelleKasutajaAined().remove(sisestatudAine);
                    try {
                        List<Kasutaja> kasutajaHaldamine = new ArrayList<>();
                        File kasutajaSalvestamine = new File(mina.getKasutajaNimi() + ".txt");
                        FileOutputStream salvestamine = new FileOutputStream(kasutajaSalvestamine);
                        ObjectOutputStream objektiSalvestamine = new ObjectOutputStream(salvestamine);
                        kasutajaHaldamine.add(mina);
                        objektiSalvestamine.writeObject(kasutajaHaldamine);
                        objektiSalvestamine.close();
                        File aineFail = new File(sisestatudAine + ".txt");
                        if (aineFail.delete() == true) {
                            System.out.println("Aine on kustutatud!");
                            logimineÕnnestus(mina.getKasutajaNimi());
                        }
                    }
                    catch(Exception e){
                            System.out.println("Midagi läks valesti! Proovi uuesti....");
                            kustutaAine(mina);
                    }
                }
            }
        }
    }



    public static void aineMuutmine(Kasutaja mina) {
        Scanner soov = new Scanner(System.in);
        System.out.print("Kas soovid ainet (KUSTUTADA/LISADA) ? ");
        String otsus = soov.nextLine().toUpperCase();
        if (otsus.equals("KUSTUTADA"))
            kustutaAine(mina);
        else if (otsus.equals("LISADA"))
            lisaAine(mina);
        else {
            System.out.println("Ei saanud sisendist aru, proovime uuesti...");
            aineMuutmine(mina);
        }
    }

    public static void aineVaatamine(Kasutaja mina) {
        if (mina.getSelleKasutajaAined().size()==0) {
            System.out.println("Sul ei ole veel ühtegi ainet!");
            logimineÕnnestus(mina.getKasutajaNimi());
        }
        System.out.println("Sinu ained: ");
        for (int i = 0; i < mina.getSelleKasutajaAined().size(); i++) {
            System.out.println(mina.getSelleKasutajaAined().get(i));
        }
        Scanner valik = new Scanner(System.in);
        System.out.print("Millise aine teemasid tahad näha? ");
        String aine = valik.nextLine();
        if (aine.equals("") == false) {
            if (aine.toUpperCase().equals("VÄLJU"))
                System.exit(0);
            try {
                File fail = new File(aine + ".txt");
                InputStream sisse = new FileInputStream(fail);
                ObjectInputStream loeObjektSisse = new ObjectInputStream(sisse);
                List<Aine> jooksev = (List<Aine>)loeObjektSisse.readObject();
                Iterator<Aine> kasutajaObjektina = jooksev.iterator();
                loeObjektSisse.close();
                Aine valitud = kasutajaObjektina.next();
                if (valitud.getSalvestatudTeemad().size() <= 0) {
                    System.out.println("Selles aines ei ole ühtegi teemat salvestatud!");
                    Scanner otsus = new Scanner(System.in);
                    System.out.println("Kas sa soovid teemat lisada? ");
                    String lisamine = otsus.nextLine().toUpperCase();
                    if (lisamine.equals("JAH")) {
                        teemaLisamine(valitud);
                    } else if (lisamine.equals("EI")) {
                        aineVaatamine(mina);
                    } else {
                        System.out.println("Ei saanud sisendist aru, proovime uuesti...");
                        aineVaatamine(mina);
                    }
                } else {
                    for (int i = 0; i < valitud.getSalvestatudTeemad().size(); i++) {
                        System.out.println(valitud.getSalvestatudTeemad().get(i));
                    }
                    Scanner tahab = new Scanner(System.in);
                    System.out.print("Millist teemat soovid vaadata? ");
                    String teema = tahab.nextLine();
                    if (valitud.getSalvestatudTeemad().contains(teema)) {
                        File lahti = new File(teema + ".txt");
                        FileInputStream loeb = new FileInputStream(lahti);
                        ObjectInputStream objekt = new ObjectInputStream(loeb);
                        List<Teema> kasutatav = (List<Teema>)objekt.readObject();
                        objekt.close();
                        Iterator<Teema> iter = kasutatav.iterator();
                        Teema loendav = iter.next();
                        teemaVaatamine(loendav);
                    } else aineVaatamine(mina);
                }
            } catch (Exception e ) {
                System.out.println("Midagi läks valesti, proovime uuesti...");
                System.out.println(e);
                aineVaatamine(mina);
            }
        } else {
            System.out.println("Ei saanud sisendist aru, proovime uuesti...");
            aineVaatamine(mina);
        }
    }

    public static void teemaLisamine(Aine aine) {
        Scanner teema = new Scanner(System.in);
        System.out.print("Sisesta teema nimi: ");
        String sisestatudTeema = teema.nextLine();
        if (sisestatudTeema.toUpperCase().equals("TAGASI"))
            logimine();
        if (sisestatudTeema.equals("") == false) {
            Scanner tekst = new Scanner(System.in);
            System.out.println("Trüki tekst siia");
            String trükitudTekst = tekst.nextLine();
            aine.getSalvestatudTeemad().add(sisestatudTeema);
            Teema jooksev = new Teema(sisestatudTeema, trükitudTekst);
            try {
                File fail = new File(aine.getAineNimetus() + ".txt");
                FileOutputStream kirjutamine = new FileOutputStream(fail);
                ObjectOutputStream objektina = new ObjectOutputStream(kirjutamine);
                objektina.writeObject(aine);
                objektina.close();
                String uusTeema = sisestatudTeema + ".txt";
                File teemaFailina = new File(uusTeema);
                if (teemaFailina.createNewFile() == true) {
                    try {
                        List<Teema> nimekiri = new ArrayList<>();
                        File teemaSalvestamine = new File(uusTeema);
                        FileOutputStream salvestamine = new FileOutputStream(teemaSalvestamine);
                        ObjectOutputStream objektiSalvestamine = new ObjectOutputStream(salvestamine);
                        nimekiri.add(jooksev);
                        objektiSalvestamine.writeObject(nimekiri);
                        objektiSalvestamine.close();
                        System.out.println("Teema on salvestatud! ");
                        logimine();
                    } catch (Exception e) {
                        System.out.println("Midagi läks valesti! Programm taaskäivitatakse!");
                        logimine();
                    }
                }
            }catch (Exception e) {
                System.out.println("Esines tõrge! Programm taaskäivitatakse...");
                logimine();
            }
        } else {
            System.out.println("Ei saanud sisendist aru, proovime uuesti...");
            teemaLisamine(aine);
        }


    }

    public static void teemaVaatamine(Teema teema) {
        System.out.println("Teema nimetus: " + teema.getTeemaNimetus().toUpperCase());
        System.out.println("Teema sisu: ");
        System.out.println(teema.getTeemaSisu());
        logimine();
    }
}
