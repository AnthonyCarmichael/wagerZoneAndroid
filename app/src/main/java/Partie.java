public class Partie {
    private int _id_partie;
    private String _date_heure;
    private boolean _prolongation;
    private int _id_statut;

    public int get_id_partie() {
        return _id_partie;
    }

    public String get_date_heure() {
        return _date_heure;
    }

    public boolean is_prolongation() {
        return _prolongation;
    }

    public int get_id_statut() {
        return _id_statut;
    }
}
