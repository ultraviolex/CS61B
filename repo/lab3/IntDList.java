import com.sun.tools.classfile.ConstantPool;

import java.awt.dnd.DnDConstants;

/**
 * Scheme-like pairs that can be used to form a list of integers.
 *
 * @author P. N. Hilfinger; updated by Vivant Sakore (1/29/2020)
 */
public class IntDList {

    /**
     * First and last nodes of list.
     */
    protected DNode _front, _back;

    /**
     * An empty list.
     */
    public IntDList() {
        _front = _back = null;
    }

    /**
     * @param values the ints to be placed in the IntDList.
     */
    public IntDList(Integer... values) {
        _front = _back = null;
        for (int val : values) {
            insertBack(val);
        }
    }

    /**
     * @return The first value in this list.
     * Throws a NullPointerException if the list is empty.
     */
    public int getFront() {
        return _front._val;
    }

    /**
     * @return The last value in this list.
     * Throws a NullPointerException if the list is empty.
     */
    public int getBack() {
        return _back._val;
    }

    /**
     * @return The number of elements in this list.
     */
    public int size() {
        // FIXME: Implement this method and return correct value
        int size = 0;
        for (DNode i = _front; i != null; i = i._next) {
            size++;
        }
        return size;
    }

    /**
     * @param i index of element to return,
     *          where i = 0 returns the first element,
     *          i = 1 returns the second element,
     *          i = -1 returns the last element,
     *          i = -2 returns the second to last element, and so on.
     *          You can assume i will always be a valid index, i.e 0 <= i < size for positive indices
     *          and -size <= i <= -1 for negative indices.
     * @return The integer value at index i
     */
    public int get(int i) {
        // FIXME: Implement this method and return correct value
        int value = 0;
        if (i >= 0) {
            for (DNode k = _front; k != null; k = k._next) {
                if (i == 0) {
                    value = k._val;
                }
                i--;
            }
        } else {
            for (DNode k = _back; k != null; k = k._prev) {
                if (i == -1) {
                    value = k._val;
                }
                i++;
            }
        }
        return value;
    }

    /**
     * @param d value to be inserted in the front
     */
    public void insertFront(int d) {
        DNode newFront = new DNode(d);
        DNode ogFront = _front;
        newFront._next = ogFront;
        if (ogFront != null) {
            ogFront._prev = newFront;
        }
        if (_back == null) {
            _back = newFront;
        }
        _front = newFront;
    }

    /**
     * @param d value to be inserted in the back
     */
    public void insertBack(int d) {
        // FIXME: Implement this method
        DNode newBack = new DNode(d);
        DNode ogBack = _back;
        newBack._prev = ogBack;
        if (ogBack != null) {
            ogBack._next = newBack;
        }
        if (_front == null) {
            _front = newBack;
        }
        _back = newBack;
    }

    /**
     * @param d     value to be inserted
     * @param index index at which the value should be inserted
     *              where index = 0 inserts at the front,
     *              index = 1 inserts at the second position,
     *              index = -1 inserts at the back,
     *              index = -2 inserts at the second to last position, and so on.
     *              You can assume index will always be a valid index,
     *              i.e 0 <= index <= size for positive indices (including insertions at front and back)
     *              and -(size+1) <= index <= -1 for negative indices (including insertions at front and back).
     */
    public void insertAtIndex(int d, int index) {
        if (index == -1 || index == size()) {
            insertBack(d);
        } else if (index == 0 || index == -1 * (size() + 1)) {
            insertFront(d);
        } else {
            DNode insertion = new DNode(d);
            DNode newPrev = _front;
            DNode newNext = _back;
            if (index > 0) {
                DNode k = _front;
                while (index > 0) {
                    newPrev = k;
                    newNext = k._next;
                    k = k._next;
                    index--;
                }
            } else {
                DNode k = _back;
                while (index < -1) {
                    newNext = k;
                    newPrev = k._prev;
                    k = k._prev;
                    index++;
                }
            }
            newPrev._next = insertion;
            insertion._prev = newPrev;
            newNext._prev = insertion;
            insertion._next = newNext;
        }
    }

    /**
     * Removes the first item in the IntDList and returns it.
     *
     * @return the item that was deleted
     */
    public int deleteFront() {
        // FIXME: Implement this method and return correct value
        int value = _front._val;
        if (size() == 1) {
            _front = null;
            _back = null;
        } else {
            _front = _front._next;
            _front._prev = null;
        }
        return value;
    }

    /**
     * Removes the last item in the IntDList and returns it.
     *
     * @return the item that was deleted
     */
    public int deleteBack() {
        // FIXME: Implement this method and return correct value
        int value = _back._val;
        if (size() == 1) {
            _front = null;
            _back = null;
        } else {
            _back = _back._prev;
            _back._next = null;
        }
        return value;
    }

    /**
     * @param index index of element to be deleted,
     *          where index = 0 returns the first element,
     *          index = 1 will delete the second element,
     *          index = -1 will delete the last element,
     *          index = -2 will delete the second to last element, and so on.
     *          You can assume index will always be a valid index,
     *              i.e 0 <= index < size for positive indices (including deletions at front and back)
     *              and -size <= index <= -1 for negative indices (including deletions at front and back).
     * @return the item that was deleted
     */
    public int deleteAtIndex(int index) {
        // FIXME: Implement this method and return correct value
        return 0;
    }

    /**
     * @return a string representation of the IntDList in the form
     * [] (empty list) or [1, 2], etc.
     * Hint:
     * String a = "a";
     * a += "b";
     * System.out.println(a); //prints ab
     */
    public String toString() {
        // FIXME: Implement this method to return correct value
        if (size() == 0) {
            return "[]";
        }
        String str = "[";
        DNode curr = _front;
        for (; curr._next != null; curr = curr._next) {
            str += curr._val + ", ";
        }
        str += curr._val + "]";
        return str;
    }

    /**
     * DNode is a "static nested class", because we're only using it inside
     * IntDList, so there's no need to put it outside (and "pollute the
     * namespace" with it. This is also referred to as encapsulation.
     * Look it up for more information!
     */
    static class DNode {
        /** Previous DNode. */
        protected DNode _prev;
        /** Next DNode. */
        protected DNode _next;
        /** Value contained in DNode. */
        protected int _val;

        /**
         * @param val the int to be placed in DNode.
         */
        protected DNode(int val) {
            this(null, val, null);
        }

        /**
         * @param prev previous DNode.
         * @param val  value to be stored in DNode.
         * @param next next DNode.
         */
        protected DNode(DNode prev, int val, DNode next) {
            _prev = prev;
            _val = val;
            _next = next;
        }
    }

}
