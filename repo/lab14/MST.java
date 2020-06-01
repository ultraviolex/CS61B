import com.sun.xml.internal.xsom.impl.scd.Iterators;

import java.util.Arrays;
import java.util.Comparator;

/** Minimal spanning tree utility.
 *  @author Xiuhui Ming
 */
public class MST {

    /** Given an undirected, weighted, connected graph whose vertices are
     *  numbered 1 to V, and an array E of edges, returns an array of edges
     *  in E that form a minimal spanning tree of the input graph.
     *  Each edge in E is a three-element int array of the form (u, v, w),
     *  where 0 < u < v <= V are vertex numbers, and 0 <= w is the weight
     *  of the edge. The result is an array containing edges from E.
     *  Neither E nor the arrays in it may be modified.  There may be
     *  multiple edges between vertices.  The objects in the returned array
     *  are a subset of those in E (they do not include copies of the
     *  original edges, just the original edges themselves.) */
    public static int[][] mst(int V, int[][] E) {
        int[][] ECopy = new int[E.length][];
        int[][] result = new int[V - 1][3];
        System.arraycopy(E, 0, ECopy, 0, E.length);
        UnionFind T = new UnionFind(V);
        Arrays.sort(ECopy, EDGE_WEIGHT_COMPARATOR);
        int j = 0;
        for (int i = 0; i < ECopy.length; i += 1) {
            int u = ECopy[i][0];
            int v = ECopy[i][1];
            if (!T.samePartition(u, v)) {
                result[j] = ECopy[i];
                j += 1;
                T.union(u, v);
            }
        }
        return result;
    }

    /** An ordering of edges by weight. */
    private static final Comparator<int[]> EDGE_WEIGHT_COMPARATOR =
        new Comparator<int[]>() {
            @Override
            public int compare(int[] e0, int[] e1) {
                return e0[2] - e1[2];
            }
        };

}
