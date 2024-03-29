/**
 * TableFilter to filter for entries greater than a given string.
 *
 * @author Matthew Owen
 */
public class GreaterThanFilter extends TableFilter {

    public GreaterThanFilter(Table input, String colName, String ref) {
        super(input);
        _index = input.colNameToIndex(colName);
        _ref = ref;
    }

    @Override
    protected boolean keep() {
        if (_next.getValue(_index).compareTo(_ref) > 0) {
            return true;
        }
        return false;
    }

    private int _index;
    private String _ref;
}
