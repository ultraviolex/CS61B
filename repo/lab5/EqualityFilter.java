/**
 * TableFilter to filter for entries equal to a given string.
 *
 * @author Matthew Owen
 */
public class EqualityFilter extends TableFilter {

    public EqualityFilter(Table input, String colName, String match) {
        super(input);
        _index = input.colNameToIndex(colName);
        _match = match;

    }

    @Override
    protected boolean keep() {
        if (_next.getValue(_index).equals(_match)) {
            return true;
        }
        return false;
    }

    private int _index;
    private String _match;
}
