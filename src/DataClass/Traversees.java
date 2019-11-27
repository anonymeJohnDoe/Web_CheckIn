package DataClass;

/**
 * Created by cyril rocca Gr 2227 INPRES .
 */
public class Traversees {

    String _idTraversees;
    String _heureDep;
    String _destination;
    String _prix;
    String _separator;
    String _endofline;
    public Traversees(String separator, String endofline) {
        this._separator = separator;
        this._endofline = endofline;
    }

    public Traversees() {
    }

    @Override
    public String toString() {
        return  _separator + _idTraversees + _separator +
                _separator + _heureDep +
                _separator + _destination
                + _separator + _prix;
    }

    public String get_prix() {
        return _prix;
    }

    public void set_prix(String _prix) {
        this._prix = _prix;
    }

    public String get_idTraversees() {
        return _idTraversees;
    }

    public void set_idTraversees(String _idTraversees) {
        this._idTraversees = _idTraversees;
    }

    public String get_heureDep() {
        return _heureDep;
    }

    public void set_heureDep(String _heureDep) {
        this._heureDep = _heureDep;
    }

    public String get_destination() {
        return _destination;
    }

    public void set_destination(String _destination) {
        this._destination = _destination;
    }

    public String get_separator() {
        return _separator;
    }

    public void set_separator(String _separator) {
        this._separator = _separator;
    }

    public String get_endofline() {
        return _endofline;
    }

    public void set_endofline(String _endofline) {
        this._endofline = _endofline;
    }
}
