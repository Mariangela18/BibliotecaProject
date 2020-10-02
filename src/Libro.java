import java.io.Serializable;

public class Libro implements Serializable, Comparable<Libro> {
    private String cod_archiviazione;
    private String titolo;
    private String Autore;
    private String Casa_editrice;
    public String stato;
    public String data_prelievo;

    public String getCasa_editrice() {
        return Casa_editrice;
    }

    public void setCasa_editrice(String casa_editrice) {
        Casa_editrice = casa_editrice;
    }

    public String getCod_archiviazione() {
        return cod_archiviazione;
    }

    public void setCod_archiviazione(String cod_archiviazione) {
        this.cod_archiviazione = cod_archiviazione;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public String getAutore() {
        return Autore;
    }

    public void setAutore(String autore) {
        Autore = autore;
    }


    public String getStato() {
        return stato;
    }

    public void setStato(String nuovo_stato) {
        stato = nuovo_stato;
    }

    public String getData_prelievo() {
        return data_prelievo;
    }

    public void setData_prelievo(String data_prelievo) {
        this.data_prelievo = data_prelievo;
    }

    @Override
    public String toString() {
        return "Libro{" +
                "cod_archiviazione='" + cod_archiviazione + '\'' +
                ", titolo='" + titolo + '\'' +
                ", Autore='" + Autore + '\'' +
                ", Casa_editrice='" + Casa_editrice + '\'' +
                ", stato='" + stato + '\'' +
                ", data_prelievo='" + data_prelievo + '\'' +
                '}';
    }

    @Override
    public int compareTo(Libro b) {
        return getTitolo().compareTo(b.titolo);
    }
}
