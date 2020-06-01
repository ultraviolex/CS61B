import java.util.ArrayList;
import java.util.Collections;

/** Disjoint sets of contiguous integers that allows (a) finding whether
 *  two integers are in the same set and (b) unioning two sets together.  
 *  At any given time, for a structure partitioning the integers 1 to N, 
 *  into sets, each set is represented by a unique member of that
 *  set, called its representative.
 *  @author Xiuhui Ming
 */
public class UnionFind {

    /** A union-find structure consisting of the sets { 1 }, { 2 }, ... { N }.
     */
    public UnionFind(int N) {
        parents = new int[N];
        sizes = new int[N];
        for (int i = 0; i < N; i += 1) {
            parents[i] = i + 1;
            sizes[i] = 1;
        }
    }

    /** Return the representative of the set currently containing V.
     *  Assumes V is contained in one of the sets.  */
    public int find(int v) {
        if (parents[v - 1] == v) {
            return v;
        }
        parents[v - 1] = find(parents[v - 1]);
        return parents[v - 1];
    }

    /** Return true iff U and V are in the same set. */
    public boolean samePartition(int u, int v) {
        return find(u) == find(v);
    }

    /** Union U and V into a single set, returning its representative. */
    public int union(int u, int v) {
        int uParent = find(u);
        int vParent = find(v);
        if (uParent == vParent) {
            return uParent;
        }
        else {
            if (sizes[uParent - 1] >= sizes[vParent - 1]) {
                parents[vParent - 1] = uParent;
                sizes[uParent - 1] = sizes[uParent - 1] + sizes[vParent - 1];
                return uParent;
            }
            else {
                parents[uParent - 1] = vParent;
                sizes[vParent - 1] = sizes[uParent - 1] + sizes[vParent - 1];
                return vParent;
            }
        }
    }

    int[] parents;
    int[] sizes;
}
