/**
 * TableFilter to filter for containing substrings.
 *
 * @author Matthew Owen
 */
public class SubstringFilter extends TableFilter {

    public SubstringFilter(Table input, String colName, String subStr) {
        super(input);
        _index = input.colNameToIndex(colName);
        _subStr = subStr;
    }

    @Override
    protected boolean keep() {
        if (_next.getValue(_index).contains(_subStr)) {
            return true;
        }
        return false;
    }

    private int _index;
    private String _subStr;
}
