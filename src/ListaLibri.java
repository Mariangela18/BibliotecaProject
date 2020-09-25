import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;


public class ListaLibri implements Serializable{

    private ArrayList<Libro> list;
    String ultimo_libro_aggiunto;
    Libro codice_trovato=null;


    public ListaLibri() {
        list= new ArrayList<Libro>();

    }

    public synchronized void Aggiungi(Libro b){
       ultimo_libro_aggiunto = new Date().toString();
       list.add(b);
    }

    public ArrayList<Libro> OttieniLista() {
        ArrayList<Libro> b_list = new ArrayList<>();
        b_list.addAll(list);
        OrdinaElenco();
        for (Libro b : list) {
            System.out.println(b);
        }
        return b_list;
    }


    public boolean EliminaLibro(String cod_archiviazione) {
        for (Libro b : list) {
            if (b.getCod_archiviazione().equals(cod_archiviazione)) {
                list.remove(b);
                return true;
            }
        }
        return false;
    }
    public void OrdinaElenco(){
        Collections.sort(list);
    }

    @Override
        public String toString(){
            String s;
            s = "--------Inizio elenco    ";
            s = s + "Data di aggiunta ----------" + ultimo_libro_aggiunto;
            for (Libro b : list) {
                s = s + "\n Codice---> " + b.getCod_archiviazione();
                s = s + "\n Titolo---> " + b.getTitolo();
                s = s + "\n Autore---> " + b.getAutore();
                s = s + "\n Editore---> " + b.getCasa_editrice();
            }
            s = s + "\n Fine elenco \n";
            return s;
        }
}




