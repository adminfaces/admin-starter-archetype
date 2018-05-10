#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.infra.model;

/**
 * Created by rmpestano on 10/31/14.
 */
public enum SortOrder {

    ASCENDING, DESCENDING, UNSORTED;

    public boolean isAscending() {
        return ASCENDING.equals(this);
    }
}
