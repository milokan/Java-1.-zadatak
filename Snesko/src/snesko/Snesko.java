package snesko;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Snesko {
    Random rnd;
    String rijec;
    String underscore = "_ ";
    Snesko(){
        // ovde kreiramo novi objekat klase Random koji cemo koristiti kasnije 
        // u bilo kojoj metodi klase Snesko sa rnd.nextInt() ili neke slicne metode
        rnd = new Random();
        // donji red je dodat samo da bih vam pokazao kako se
        // koristi klasa Random. Najverovatnije cete ga moci kasnije ukloniti
        //int slucBr = slucajanBroj(10);
        //System.out.println("Primer generisanja broja 0 - 10: " + slucBr);
    }
    
    /**
     * Vraca slucajan broj u opsegu 0 - max
     * Napomena: Stavio sam da je ova metoda private jer nema smisla koristiti
     * je van ove klase - npr iz klase Igra kao snesko.slucajanBroj(10). 
     * Ova metoda je samo interna metoda koja se koristi u ostalim metodama 
     * klase Snesko - tek cemo govoriti o private, public i ostalim metodama
     * @param max maksimalna vrednost slucajnog broja koja moze biti vracena
     * @return slucajan broj 0-max
     */
    /*private int slucajanBroj(int max){
        int ret = rnd.nextInt(max + 1); //+1 zato sto nextInt vraca 0 - vrednost_parametra - 1
        return ret;
    }*/
    /**
     *  Ova funkcija ce biti pozvana iz klase Igra
     *  Moj savet (nije obavezno) je da dodajete novu metodu u ovoj klasi (slicnu metodi igraj)
     *  za svaki ili barem za vecinu delova programa opisanih u dokumentu 
     *  sa tekstom zadatka (meni, citanje fajla dictionary.txt i biranje reci,
     *  unos slova/reci od strane igraca, ...)
     */
    void igraj() throws FileNotFoundException, IOException{
        meni();
        odabir();
        odabir_rijeci();
        pogodi();
    }
    
    private void meni() throws FileNotFoundException, IOException {
        System.out.println("Dobrodosli u igru!");
        System.out.println("Izaberite opciju 1 ili 2.");
        System.out.println("------------------------------");
        System.out.println("1: Zapocni novu igru.");
        System.out.println("2: Dodaj novu rijec u rijecnik.");
        System.out.println("------------------------------"); 
    }
    
    private void odabir() throws FileNotFoundException, UnsupportedEncodingException, IOException {
        int izbor;
        Scanner sc = new Scanner(System.in);
        System.out.print("Biram: ");
        izbor = sc.nextInt();
        
        switch(izbor) {
            case 1:
                System.out.println("Imate 7 pokusaja da pogodite trazenu rijec. Srecno!");
                break;
            case 2:
                String rijec;
                Scanner sc1 = new Scanner(System.in);
                System.out.print("Unesite novu rijec: ");
                rijec = sc1.nextLine();
                System.out.println("Unijeli ste: " + rijec);
                
                String sP = System.getProperty("file.separator");
                File dictionary = new File("." + sP + "dictionary.txt");
                BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(dictionary), "UTF8"));
                String s;
                ArrayList<String> rir = new ArrayList<String>();
                while((s = in.readLine()) != null) {
                    rir.add(s);
                }
                in.close();
                if(rir.contains(rijec)) {
                    System.out.println("Rijec se vec nalazi u rijecniku!");
                } else {
                    rir.add(rijec);
                    PrintWriter out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(dictionary), "UTF8"));
                    for(String rir1 : rir) {
                        out.println(rir1);
                    }
                    out.flush();
                    out.close();
                    System.out.println("Dodana je nova rijec u rijecnik.");
                }
                meni();
                odabir();
                break;
        }
    }
    
    private void odabir_rijeci() throws FileNotFoundException, UnsupportedEncodingException, IOException {
        String sP = System.getProperty("file.separator");
        File dictionary = new File("." + sP + "dictionary.txt");
        BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(dictionary), "UTF8"));
        int linije = 0;
        String s;
        ArrayList<String> rir = new ArrayList<String>();
        while((s = in.readLine()) != null) {
            linije++;
            rir.add(s);
        }
        String[] rijeci = new String[rir.size()];
        rir.toArray(rijeci);
        
        int pozicija = rnd.nextInt(linije);
        rijec = rijeci[pozicija];
        //System.out.println(rijec); 
    }
    
    private void pogodi() throws IOException {
        int br_slova = rijec.length();
        String din_str = underscore.repeat(br_slova);
        System.out.println(din_str);
        int br_tacnih_unosa = 0;
        
        String s1 =  "  `======'";
        String s2 = "  (`^'^'`)";
        String s3 = "   (`^^')";
        String s4 = "    (  )";
        String s5 = ">--(`^^')";
        String s6 = "    (  )___/";
        String s7 = "    ('')___/";
        String s8 = "   _|==|_";
        //stringovi za iscrtavanje snjeska
        
        int promasaji = 0;
        ArrayList<String> isprobane_rijeci = new ArrayList<String>();
        ArrayList<String> din_str_ispis = new ArrayList<String>(rijec.length());
        
        Scanner sc = new Scanner(System.in);
        String unos;
        while(promasaji < 8) {
            System.out.print("Probajte slovo ili rijec: ");
            unos = sc.nextLine();
            if(unos.length() == 1) {
                if(isprobane_rijeci.contains(unos)) {
                    System.out.println("Slovo je vec iskorisceno!");
                } else {
                    isprobane_rijeci.add(unos);
                    if(rijec.contains(unos)) {
                        br_tacnih_unosa++;
                        char[] din_str_ch = din_str.toCharArray();
                        char slovo = unos.charAt(0);
                        for(int i = 0; i < rijec.length(); i++) {
                            if(slovo == rijec.charAt(i)) {
                                din_str_ch[2*i] = slovo;
                            }
                        }
                        
                        StringBuilder din_str1_b = new StringBuilder();
                        for(int j = 0; j < din_str_ch.length; j++) {
                            din_str1_b.append(din_str_ch[j]);
                        }
                        String din_str1 = din_str1_b.toString();
                        din_str_ispis.add(din_str1);
                        din_str = new String(din_str1);
                        din_str = din_str_ispis.get(br_tacnih_unosa - 1);
                        System.out.println(din_str);
                        String din_str_poredjenje = din_str.replaceAll("\\s", "");
                        if(rijec.equals(din_str_poredjenje)) {
                            System.out.println("Bravo! Pogodili ste trazenu rijec!");
                            System.out.println("Trazena rijec: " + rijec);
                            System.out.println("Da li zelite novu igru?");
                            System.out.println("1: Da.");
                            System.out.println("2: Ne.");
                            Scanner sc1 = new Scanner(System.in);
                            int izbor;
                            System.out.print("Biram: ");
                            izbor = sc1.nextInt();
                        
                            switch(izbor) {
                                case 1:
                                  igraj();
                                  break;
                                case 2:
                                  System.exit(0);
                                  break;
                            }
                        }
                    } else {
                        promasaji++;
                    }
                }
            } else {
                if(rijec.equals(unos)) {
                    System.out.println("Bravo! Pogodili ste trazenu rijec!");
                    System.out.println("Trazena rijec: " + rijec);
                    System.out.println("Da li zelite novu igru?");
                    System.out.println("1: Da.");
                    System.out.println("2: Ne.");
                    Scanner sc1 = new Scanner(System.in);
                    int izbor;
                    System.out.print("Biram: ");
                    izbor = sc1.nextInt();
                        
                    switch(izbor) {
                        case 1:
                            igraj();
                            break;
                        case 2:
                            System.exit(0);
                            break;
                    }
                } else {
                    promasaji++;
                    System.out.println("Pogresna rijec!");
                }
            }
            if(promasaji == 1) {
                System.out.println(s2);
                System.out.println(s1);
            } else if(promasaji == 2) {
                System.out.println(s3);
                System.out.println(s2);
                System.out.println(s1);
            } else if(promasaji == 3) {
                System.out.println(s4);
                System.out.println(s3);
                System.out.println(s2);
                System.out.println(s1);
            } else if(promasaji == 4) {
                System.out.println(s4);
                System.out.println(s5);
                System.out.println(s2);
                System.out.println(s1);
            } else if(promasaji == 5) {
                System.out.println(s6);
                System.out.println(s5);
                System.out.println(s2);
                System.out.println(s1);
            } else if(promasaji == 6) {
                System.out.println(s7);
                System.out.println(s5);
                System.out.println(s2);
                System.out.println(s1);
            } else if(promasaji == 7) {
                System.out.println(s8);
                System.out.println(s7);
                System.out.println(s5);
                System.out.println(s2);
                System.out.println(s1);
                System.out.println("Niste uspjeli pogoditi rijec. Vise srece drugi put!");
                System.out.println("Trazena rijec: " + rijec);
                System.out.println("Da li zelite novu igru?");
                System.out.println("1: Da.");
                System.out.println("2: Ne.");
                Scanner sc1 = new Scanner(System.in);
                int izbor;
                System.out.print("Biram: ");
                izbor = sc1.nextInt();
                        
                switch(izbor) {
                    case 1:
                        igraj();
                        break;
                    case 2:
                        System.exit(0);
                        break;
                }
            }
        }
    }
}
                    
                
             
