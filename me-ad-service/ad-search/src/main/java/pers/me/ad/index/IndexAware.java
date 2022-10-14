package pers.me.ad.index;

/**
 * @author Chris Wang
 * @version 1.0
 * @date 2022-10-13
 */
public interface IndexAware <K,V>{

    V get(K key);

    void add(K key,V value);

    void update(K key,V value);

    void delete(K key,V value);
}
