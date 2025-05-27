public class Data {

    private int giorno;
    private int mese;
    private int anno;

    public Data() {}

    public Data(int giorno, int mese, int anno) {
        this.giorno = giorno;
        this.mese = mese;
        this.anno = anno;
    }

    public int getGiorno() {
        return giorno;
    }

    public void setGiorno(int giorno) {
        this.giorno = giorno;
    }

    public int getMese() {
        return mese;
    }

    public void setMese(int mese) {
        this.mese = mese;
    }

    public int getAnno() {
        return anno;
    }

    public void setAnno(int anno) {
        this.anno = anno;
    }

    public String calcAnno(){
        String an = anno + "";
        char[] charArray = new char[2];
        charArray[0] = an.charAt(2);
        charArray[1] = an.charAt(3);
        String c = new String(charArray);
        return c;
    }

    public String calcMese(){
        String d = "";
        switch(mese){
            case 1:d = "A";
                break;
            case 2:d = "B";
                break;
            case 3:d = "C";
                break;
            case 4:d = "D";
                break;
            case 5:d = "E";
                break;
            case 6:d = "H";
                break;
            case 7:d = "L";
                break;
            case 8:d = "M";
                break;
            case 9:d = "P";
                break;
            case 10:d = "R";
                break;
            case 11:d = "S";
                break;
            case 12:d = "T";
                break;
        }
        return d;
    }

    public int calcGiorniMese() {
        if (mese == 2) {
            if(Bisestile()) return 29;
            else return 28;
        } else if (mese == 4 || mese == 6 || mese == 9 || mese == 11) {
            return 30;
        } else {
            return 31;
        }
    }

    private boolean Bisestile() {
        return (anno % 4 == 0 && anno % 100 != 0) || (anno % 400 == 0);
    }


}
