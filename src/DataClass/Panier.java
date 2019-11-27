package DataClass;

/**
 * Created by cyril rocca Gr 2227 INPRES .
 */
public class Panier {

    String _id_panier;
    String _traversee_id;
    String _client_id;
    String _prix;

    public String get_id_panier() {
        return _id_panier;
    }

    public void set_id_panier(String _id_panier) {
        this._id_panier = _id_panier;
    }

    public String get_traversee_id() {
        return _traversee_id;
    }

    public void set_traversee_id(String _traversee_id) {
        this._traversee_id = _traversee_id;
    }

    public String get_client_id() {
        return _client_id;
    }

    public void set_client_id(String _client_id) {
        this._client_id = _client_id;
    }

    public String get_prix() {
        return _prix;
    }

    public void set_prix(String _prix) {
        this._prix = _prix;
    }

}
