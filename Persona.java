package Capolavoro;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.Normalizer;

public class Persona {

    private String nome;
    private String cognome;
    private String comune;
    private String sesso;
    private Data data_di_nascita;

    public Persona() {}

    public String getNome() {return nome;}

    public void setNome(String nome) {this.nome = nome;}

    public String getCognome() {return cognome;}

    public void setCognome(String cognome) {this.cognome = cognome;}

    public String getComune() {return comune;}

    public void setComune(String comune) {this.comune = comune;}

    public String getSesso() {return sesso;}

    public void setSesso(String sesso) {
        if(sesso.equals("F") || sesso.equals("M")) this.sesso = sesso;
        else this.sesso = "null";
    }

    public Data getData_di_nascita() {return data_di_nascita;}

    public void setData_di_nascita(Data data_di_nascita) {this.data_di_nascita = data_di_nascita;}

    public String calcoloCognome(){
        cognome = togliAccenti(cognome);
        cognome = cognome.toUpperCase();
        int counter = 0;
        char[] charArray = new char[3];
        for(int i=0; i < cognome.length();i++){
            char caratteri = cognome.charAt(i);
            if(!vocali(caratteri) && counter <= 2){
                charArray[counter] = caratteri;
                counter += 1;
            }
        }
        if(counter <= 2){
            for(int i=0; i < cognome.length();i++){
                char caratteri = cognome.charAt(i);
                if(vocali(caratteri) && counter <= 2){
                    charArray[counter] = caratteri;
                    counter += 1;
                }
            }
        }
        if(counter <= 2){
            for(int i=0; i < cognome.length();i++){
                char caratteri = cognome.charAt(i);
                if(vocali(caratteri) && counter <= 2){
                    charArray[counter] = 'X';
                    counter += 1;
                }
            }
        }
        String a = new String(charArray);
        return a;
    }

    public String calcoloNome(){
        nome = togliAccenti(nome);
        nome = nome.toUpperCase().replace(" ","").replace("-","");
        int counter = 0;
        char[] charArray = new char[3];
        if(consonanti()>3)
        {
            int counter_finale = 0;
            for(int i=0; i < nome.length();i++){
                char caratteri = nome.charAt(i);
                if(!vocali(caratteri)){
                    if(!vocali(caratteri) && counter <=2 && counter_finale != 1){
                        charArray[counter] = caratteri;
                        counter += 1;
                    }
                    counter_finale += 1;
                }
            }
        }
        else
        {
            for(int i=0; i < nome.length();i++){
                char caratteri = nome.charAt(i);
                if(!vocali(caratteri) && counter <= 2){
                    charArray[counter] = caratteri;
                    counter += 1;
                }
            }
            if(counter <= 2){
                for(int i=0; i < nome.length();i++){
                    char caratteri = nome.charAt(i);
                    if(vocali(caratteri) && counter <= 2){
                        charArray[counter] = caratteri;
                        counter += 1;
                    }
                }
            }
            if(counter <= 2){
                for(int i=0; i < nome.length();i++){
                    char caratteri = nome.charAt(i);
                    if(vocali(caratteri) && counter <= 2){
                        charArray[counter] = 'X';
                        counter += 1;
                    }
                }
            }
        }
        String b = new String(charArray);
        return b;
    }

    public String calcoloData(){
        sesso = sesso.toUpperCase();
        String giorno = " ";
        if(data_di_nascita.getGiorno()<10 && sesso.equals("M")){
            giorno = "0" + data_di_nascita.getGiorno();
        }
        if(data_di_nascita.getGiorno()>=10 && sesso.equals("M")){
            giorno = data_di_nascita.getGiorno() + "";
        }
        if(sesso.equals("F")){
            giorno = (data_di_nascita.getGiorno()+40) + "";
        }
        return data_di_nascita.calcAnno() + data_di_nascita.calcMese() + giorno;
    }

    public String calcoloCodice_Comuni() throws IOException {
        String co = " ";
        char[] charArray = new char[4];
        int counter = 0;
        comune = comune.toUpperCase();
        FileReader f = new FileReader("comuni.txt");
        BufferedReader b = new BufferedReader(f);
        while(true) {
            String s = b.readLine();
            if (s == null) {
                b.close();
                charArray = calcoloCodice_Stati();
                break;
            }
            if (s.startsWith(comune)) {
                co = s;
                b.close();
                for(int i = (co.length()-4); i < co.length(); i++){
                    charArray[counter] = co.charAt(i);
                    counter += 1;
                }
            break;
            }
        }
        String e = new String(charArray);
        return e;
    }

    public char[] calcoloCodice_Stati() throws IOException {
        String st = " ";
        char[] charArray = new char[4];
        int counter = 0;
        comune = comune.toUpperCase();
        FileReader f = new FileReader("stati_codici.txt");
        BufferedReader b = new BufferedReader(f);
        while(true) {
            String s = b.readLine();
            if (s == null) {
                b.close();
                st = "null";
                break;
            }
            if (s.endsWith(comune)) {
                st = s;
                b.close();
                break;
            }
        }
        for(int i = 0; i < 4; i++){
            charArray[counter] = st.charAt(i);
            counter += 1;
        }
        return charArray;
    }

    public String calcoloCarattereControllo(String cf){
        int counter = 0;
        for(int i = 0; i < cf.length(); i++){
            if(i % 2 == 0){
                counter += dispari(cf.charAt(i));
            }
            else{
                counter += pari(cf.charAt(i));
            }
        }
        int r = counter % 26;
        return resto(r);
    }

    private boolean vocali(char vocals){
        return vocals == 'A' || vocals == 'E' || vocals == 'I' || vocals == 'O' || vocals == 'U';
    }

    private int consonanti(){
        int conso = 0;
        for(int i=0; i < nome.length();i++){
            char caratteri = nome.charAt(i);
            if(!vocali(caratteri)){
                conso += 1;
            }
        }
        return conso;
    }

    private int dispari(char CFdispari){
        int d = 0;
        switch(CFdispari){
            case '0':d = 1;
                break;
            case '1':d = 0;
                break;
            case '2':d = 5;
                break;
            case '3':d = 7;
                break;
            case '4':d = 9;
                break;
            case '5':d = 13;
                break;
            case '6':d = 15;
                break;
            case '7':d = 17;
                break;
            case '8':d = 19;
                break;
            case '9':d = 21;
                break;
            case 'A':d = 1;
                break;
            case 'B':d = 0;
                break;
            case 'C':d = 5;
                break;
            case 'D':d = 7;
                break;
            case 'E':d = 9;
                break;
            case 'F':d = 13;
                break;
            case 'G':d = 15;
                break;
            case 'H':d = 17;
                break;
            case 'I':d = 19;
                break;
            case 'J':d = 21;
                break;
            case 'K':d = 2;
                break;
            case 'L':d = 4;
                break;
            case 'M':d = 18;
                break;
            case 'N':d = 20;
                break;
            case 'O':d = 11;
                break;
            case 'P':d = 3;
                break;
            case 'Q':d = 6;
                break;
            case 'R':d = 8;
                break;
            case 'S':d = 12;
                break;
            case 'T':d = 14;
                break;
            case 'U':d = 16;
                break;
            case 'V':d = 10;
                break;
            case 'W':d = 22;
                break;
            case 'X':d = 25;
                break;
            case 'Y':d = 24;
                break;
            case 'Z':d = 23;
                break;
        }
        return d;
    }

    private int pari(char CFpari){
        int d = 0;
        switch(CFpari){
            case '0':d = 0;
                break;
            case '1':d = 1;
                break;
            case '2':d = 2;
                break;
            case '3':d = 3;
                break;
            case '4':d = 4;
                break;
            case '5':d = 5;
                break;
            case '6':d = 6;
                break;
            case '7':d = 7;
                break;
            case '8':d = 8;
                break;
            case '9':d = 9;
                break;
            case 'A':d = 0;
                break;
            case 'B':d = 1;
                break;
            case 'C':d = 2;
                break;
            case 'D':d = 3;
                break;
            case 'E':d = 4;
                break;
            case 'F':d = 5;
                break;
            case 'G':d = 6;
                break;
            case 'H':d = 7;
                break;
            case 'I':d = 8;
                break;
            case 'J':d = 9;
                break;
            case 'K':d = 10;
                break;
            case 'L':d = 11;
                break;
            case 'M':d = 12;
                break;
            case 'N':d = 13;
                break;
            case 'O':d = 14;
                break;
            case 'P':d = 15;
                break;
            case 'Q':d = 16;
                break;
            case 'R':d = 17;
                break;
            case 'S':d = 18;
                break;
            case 'T':d = 19;
                break;
            case 'U':d = 20;
                break;
            case 'V':d = 21;
                break;
            case 'W':d = 22;
                break;
            case 'X':d = 23;
                break;
            case 'Y':d = 24;
                break;
            case 'Z':d = 25;
                break;
        }
        return d;
    }

    private String resto(int r){
        String d = "";
        switch(r){
            case 0:d = "A";
                break;
            case 1:d = "B";
                break;
            case 2:d = "C";
                break;
            case 3:d = "D";
                break;
            case 4:d = "E";
                break;
            case 5:d = "F";
                break;
            case 6:d = "G";
                break;
            case 7:d = "H";
                break;
            case 8:d = "I";
                break;
            case 9:d = "J";
                break;
            case 10:d = "K";
                break;
            case 11:d = "L";
                break;
            case 12:d = "M";
                break;
            case 13:d = "N";
                break;
            case 14:d = "O";
                break;
            case 15:d = "P";
                break;
            case 16:d = "Q";
                break;
            case 17:d = "R";
                break;
            case 18:d = "S";
                break;
            case 19:d = "T";
                break;
            case 20:d = "U";
                break;
            case 21:d = "V";
                break;
            case 22:d = "W";
                break;
            case 23:d = "X";
                break;
            case 24:d = "Y";
                break;
            case 25:d = "Z";
                break;

        }
        return d;
    }

    private static String togliAccenti(String input) {
        String normalizzatore = Normalizer.normalize(input, Normalizer.Form.NFD);
        return normalizzatore.replaceAll("\\p{M}", "");
    }

}

