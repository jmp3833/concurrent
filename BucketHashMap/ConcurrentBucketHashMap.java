import java.util.* ;
import java.util.concurrent.* ;
import java.util.concurrent.locks.* ;

/*
 * A ConcurrentBucketHashMap implements a subset of the Map interface.
 * It would be relatively easy (but tedious) to add the
 * missing methods to bring this into conformance with the
 * Map interface.
 *
 * The idea is that a key/value Pair objects are placed in one
 * of N Buckets based on the hashcode of the key mod N. The
 * Buckets are contained in an array, and hashcode based selector
 * is the index into the array of the appropriate Bucket.
 *
 * In this version, all synchronization is done using built-in
 * Java synchronized blocks. This is inefficient, as it does not
 * support concurrent reading, and does not allow a consistent
 * computation of the size.
 */

public class ConcurrentBucketHashMap<K, V> {
    final int numberOfBuckets ;
    final List<Bucket<K, V>> buckets ;

    /*
     * Immutable Pairs of keys and values. Immutability means
     * we don't have to worry about the key or value changing
     * under our feet. However, when the mapping for a given key
     * changes, we need to create a new Pair object.
     *
     * This is a pure data holder class.
     */
    class Pair<K, V> {
        final K key ;
        final V value ;

        Pair(K key, V value) {
            this.key = key ;
            this.value = value ;
        }
    }

    /*
     * A Bucket holds all the key/value pairs in the map that have
     * the same hash code (modulo the number of buckets). The
     * object consists of an extensible "contents" list protected
     * with a ReadWriteLock "readWriteLock".
     */
    class Bucket<K, V> {
        private final List<Pair<K, V>> contents = new ArrayList<Pair<K, V>>();
        private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
        private final Lock readLock = readWriteLock.readLock();
        private final Lock writeLock = readWriteLock.writeLock();

        /*
         * Return the current Bucket size.
         */
        int size() {
            readLock.lock();
            try {
                System.out.println("Fetching size of individual bucket. size is: " + contents.size()); 
                return contents.size();
            } finally {
                readLock.unlock();
            }
        }

        /*
         * Get the Pair at location 'i' in the Bucket.
         */
        Pair<K, V> getPair(int i) {
            readLock.lock();
            try {
                return contents.get(i);
            } finally {
                readLock.unlock();
            }
        }

        /*
         * Replace the Pair at location 'i' in the Bucket.
         */
        void putPair(int i, Pair<K, V> pair) {
            writeLock.lock();
            try {
                contents.set(i, pair);
            } finally {
                writeLock.lock();
            }
        }

        /*
         * Add a Pair to the Bucket.
         */
        void addPair(Pair<K, V> pair) {
            writeLock.lock();
            try {
                System.out.println("Adding pair with key " + pair.key + " and value: " + pair.value); 
                contents.add(pair);
            } finally {
                System.out.println("Unlocking a write lock for the kv pair"); 
                writeLock.unlock();
            }
        }

        /*
         * Remove a Pair from the Bucket by position.
         */
        void removePair(int index) {
            writeLock.lock();
            try {
                System.out.println("removing contents at index " + index); 
                contents.remove(index);
            } finally {
                System.out.println("Releasing the write lock on bucket..."); 
                writeLock.unlock();
            }
        }
    }

    /*
     * Constructor for the ConcurrentBucketHashMap proper.
     */
    public ConcurrentBucketHashMap(int nbuckets) {
        numberOfBuckets = nbuckets ;
        buckets = new ArrayList<Bucket<K, V>>(nbuckets) ;

        for ( int i = 0 ; i < nbuckets ; i++ ) {
            buckets.add(new Bucket<K, V>()) ;
        }
    }

    /*
     * Does the map contain an entry for the specified
     * key?
     */
    public boolean containsKey(K key) {
        Bucket<K, V> theBucket = buckets.get(bucketIndex(key)) ;
        boolean contains ;
        contains = findPairByKey(key, theBucket) >= 0 ;
        return contains ;
    }

    /*
     * How many pairs are in the map?
     */
    public int size() {
        int size = 0 ;

        for ( int i = 0 ; i < numberOfBuckets ; i++ ) {
            Bucket<K, V> theBucket =  buckets.get(i) ;
            size += theBucket.size() ;
        }
        return size ;
    }

    /*
     * Return the value associated with the given Key.
     * Returns null if the key is unmapped.
     */
    public V get(K key) {
        Bucket<K, V> theBucket = buckets.get(bucketIndex(key)) ;
        Pair<K, V>   pair      = null ;

        int index = findPairByKey(key, theBucket) ;

        if ( index >= 0 ) {
            pair = theBucket.getPair(index) ;
        }

        return (pair == null) ? null : pair.value ;
    }

    /*
     * Associates the given value with the key in the
     * map, returning the previously associated value
     * (or none if the key was not previously mapped).
     */
    public V put(K key, V value) {
        Bucket<K, V> theBucket = buckets.get(bucketIndex(key)) ;
        Pair<K, V>   newPair   = new Pair<K, V>(key, value) ;
        V            oldValue ;

        
        int index = findPairByKey(key, theBucket) ;

        if ( index >= 0 ) {
            Pair<K, V> pair = theBucket.getPair(index) ;

            theBucket.putPair(index, newPair) ;
            oldValue = pair.value ;
        } else {
            theBucket.addPair(newPair) ;
            oldValue = null ;
        }
        
        return oldValue ;
    }

    /*
     * Remove the mapping for the given key from the map, returning
     * the currently mapped value (or null if the key is not in
     * the map.
     */
    public V remove(K key) {
        Bucket<K, V> theBucket = buckets.get(bucketIndex(key)) ;
        V removedValue = null ;

        int index = findPairByKey(key, theBucket) ;

        if ( index >= 0 ) {
            Pair<K, V> pair = theBucket.getPair(index) ;

            theBucket.removePair(index) ;
            removedValue = pair.value ;
        }
        
        return removedValue ;
    }

    /****** PRIVATE METHODS ******/

    /*
     * Given a key, return the index of the Bucket
     * where the key should reside.
     */
    private int bucketIndex(K key) {
        return key.hashCode() % numberOfBuckets ;
    }

    /*
     * Find a Pair<K, V> for the given key in the given Bucket,
     * returning the pair's index in the Bucket (or -1 if
     * not found).
     *
     * Assumes the lock for the Bucket has been acquired.
     */
    private int findPairByKey(K key, Bucket<K, V> theBucket) {
        int size = theBucket.size() ;

        for ( int i = 0 ; i < size ; ++i ) {
            Pair<K, V> pair = theBucket.getPair(i) ;

            if ( key.equals(pair.key) ) {
                return i ;
            }
        }

        return (-1) ;
    }

    public static void main(String[] args) {
      
      //Number of buckets inside the hashmap data structure
      int nBuckets = 4;

      ConcurrentBucketHashMap<String, String> cbhm = new ConcurrentBucketHashMap<String, String>(nBuckets);          
     
      //Add elements to the map 
      cbhm.put("1", "Justin is Cool");
      cbhm.put("2", "Jesse is pretty alright");
      cbhm.put("3", "Tom Reichalmayer is the man");
      cbhm.put("4", "John renner is pretty okay");
     
      //Ensure each bucket can be appropriately accessed
      System.out.println("Element at index 1 = " + cbhm.get("1"));
      System.out.println("Element at index 2 = " + cbhm.get("2"));
      System.out.println("Element at index 3 = " + cbhm.get("3"));
      System.out.println("Element at index 4 = " + cbhm.get("4"));

      //Print initial size
      System.out.println("Bucket hash map size: " + cbhm.size());
      
      //Cause the issue where an element is removed and new elements are added in
      //which used to throw off the size. 
      System.out.println("Removing element 1 from the map..."); 
      cbhm.remove("1");

      System.out.println("Adding two new elements to the mix..."); 
      cbhm.put("5", "John Cena");
      cbhm.put("6", "Hulk Hogan");
     
      //Ensure that the size is correct
      System.out.println("Bucket hash map size = " + cbhm.size());

    }
}
