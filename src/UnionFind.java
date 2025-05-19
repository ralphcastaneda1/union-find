public class UnionFind {
    private int[] parents;
    private int[] size;

    /* Creates a UnionFind data structure holding N items. Initially, all
       items are in disjoint sets. */
    public UnionFind(int N) {
        parents = new int[N];
        size = new int[N];
        for (int i = 0; i < N; i++) {
            parents[i] = i;
            size[i] = 1;
        }
    }

    /* Returns the size of the set V belongs to. */
    public int sizeOf(int v) {
        if (v < 0) {
            throw new IllegalArgumentException("invalid root");
        }
        int root = find(v);
        return size[root];
    }

    /* Returns the parent of V. If V is the root of a tree, returns the
       negative size of the tree for which V is the root. */
    public int parent(int v) {
        if (v < 0 || v >= parents.length) {
            throw new IllegalArgumentException("invalid root");
        }
        if (v == parents[v]) {
            return -size[v];
        } else {
            return parents[v];
        }
    }

    /* Returns true if nodes/vertices V1 and V2 are connected. */
    public boolean connected(int v1, int v2) {
        if (v1 < 0 || v2 < 0 || v1 >= parents.length || v2 >= parents.length) {
            throw new IllegalArgumentException("invalid root");
        }
        int rootV1 = find(v1);
        int rootV2 = find(v2);
        return rootV1 == rootV2;
    }

    /* Returns the root of the set V belongs to. Path-compression is employed
       allowing for fast search-time. If invalid items are passed into this
       function, throw an IllegalArgumentException. */
    public int find(int v) {
        if (v < 0 || v >= parents.length) {
            throw new IllegalArgumentException("invalid root");
        }
        int indexTracker = v;
        while (indexTracker != parents[indexTracker]) {
            indexTracker = parents[indexTracker];
        }
        while (v != indexTracker) {
            int newParent = parents[v];
            parents[v] = indexTracker;
            v = newParent;
        }
        return indexTracker;
    }

    /* Connects two items V1 and V2 together by connecting their respective
       sets. V1 and V2 can be any element, and a union-by-size heuristic is
       used. If the sizes of the sets are equal, tie break by connecting V1's
       root to V2's root. Union-ing an item with itself or items that are
       already connected should not change the structure. */
    public void union(int v1, int v2) {
        if (v1 < 0 || v2 < 0 || v1 >= parents.length || v2 >= parents.length) {
            throw new IllegalArgumentException("invalid root");
        }
        int v1Root = find(v1);
        int v2Root = find(v2);
        if (v1Root != v2Root) {
            if (size[v1Root] <= size[v2Root]) {
                parents[v1Root] = v2Root;
                size[v2Root] += size[v1Root];
            } else {
                parents[v2Root] = v1Root;
                size[v1Root] += size[v2Root];
            }
        }
    }
}
