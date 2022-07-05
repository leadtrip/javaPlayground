package wood.mike.redis;

import redis.clients.jedis.*;

import java.util.List;
import java.util.Set;

public class JedisMw {

    private static final String STRING_KEY = "STRING:KEY";
    private static final String LIST_KEY = "LIST:KEY";
    private static final String SET_KEY = "SET:KEY";
    private static final String SORTED_SET_KEY = "SORTEDSET:KEY";
    private static final String HASH_KEY = "HASH:KEY";
    private static final String TRANSACTION_SET_KEY = "TRANSACTIONSET:KEY";

    public static void main(String[] args) {
        new JedisMw().run();
    }

    private void run() {
        final JedisPoolConfig poolConfig = buildPoolConfig();
        JedisPool jedisPool = new JedisPool(poolConfig);
        try (Jedis jedis = jedisPool.getResource()) {
            strings(jedis);
            lists(jedis);
            sets(jedis);
            sortedSets(jedis);
            hashes(jedis);
            transactions(jedis);
            pipeline(jedis);
        }
    }


    private void strings( Jedis jedis ) {
        jedis.set( STRING_KEY, "testing123");
        System.out.println( jedis.get(STRING_KEY) );
        jedis.set( STRING_KEY, "hello mum");
        System.out.println( jedis.get(STRING_KEY) );
        jedis.del(STRING_KEY);
        System.out.println( jedis.get(STRING_KEY) );
    }

    private void lists(Jedis jedis) {
        jedis.del(LIST_KEY);
        jedis.lpush(LIST_KEY, "chicken");
        jedis.lpush(LIST_KEY, "beef");
        jedis.lpush(LIST_KEY, "pork");
        System.out.printf( "Item at index 1 %s%n", jedis.lindex( LIST_KEY, 1 ) );
        System.out.printf( "List length %s%n", jedis.llen(LIST_KEY) );

        jedis.rpush(LIST_KEY, "quorn", "lamb");
        jedis.lrange(LIST_KEY, 0, -1).forEach(System.out::println);
    }

    private void sets(Jedis jedis) {
        jedis.del(SET_KEY);
        jedis.sadd(SET_KEY, "bread");
        jedis.sadd(SET_KEY, "peanut butter");
        jedis.sadd(SET_KEY, "marmite");
        System.out.printf( "Element removed from set %s%n", jedis.srem(SET_KEY, "marmite") > 0 ? "successfully" : "failed" );
    }

    private void sortedSets(Jedis jedis) {
        jedis.del(SORTED_SET_KEY);
        jedis.zadd(SORTED_SET_KEY,  5, "Jaques Anquetil");
        jedis.zadd(SORTED_SET_KEY,  5, "Eddy Merckx");
        jedis.zadd(SORTED_SET_KEY,  5, "Bernard Hinault");
        jedis.zadd(SORTED_SET_KEY,  4, "Chris Froome");
        jedis.zadd(SORTED_SET_KEY,  3, "Greg LeMond");
        jedis.zadd(SORTED_SET_KEY,  2, "Alberto Contandor");
        jedis.zadd(SORTED_SET_KEY,  1, "Geraint Thomas");

        jedis.zrevrangeByScore(SORTED_SET_KEY, 5, 4).forEach(System.out::println);
    }

    private void hashes(Jedis jedis) {
        jedis.del(HASH_KEY);
        jedis.hset("company:99", "name", "Garmin");
        jedis.hset("company:99", "employees", "20000");
        jedis.hset("company:99", "location", "USA");

        jedis.hget("company:99", "name");
        jedis.hgetAll("company:99").values().forEach(System.out::println);
    }

    private void transactions(Jedis jedis) {
        Transaction t = jedis.multi();
        t.sadd(TRANSACTION_SET_KEY, "jon");
        t.sadd(TRANSACTION_SET_KEY, "ben");
        t.sadd(TRANSACTION_SET_KEY, "sid");
        t.exec();
    }

    private void pipeline(Jedis jedis) {
        Pipeline pipeline = jedis.pipelined();
        pipeline.zadd(SORTED_SET_KEY, 1, "Bradley Wiggins");
        pipeline.zadd(SORTED_SET_KEY, 2, "Fausto Coppi");
        Response<List<String>> singleTdfWinners = pipeline.zrevrangeByScore(SORTED_SET_KEY, 1, 1);
        pipeline.sync();

        System.out.printf("Single TDF winners %s", singleTdfWinners.get());
    }

    private JedisPoolConfig buildPoolConfig() {
        final JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(128);
        poolConfig.setMaxIdle(128);
        poolConfig.setMinIdle(16);
        poolConfig.setTestOnBorrow(true);
        poolConfig.setTestOnReturn(true);
        poolConfig.setTestWhileIdle(true);
        poolConfig.setNumTestsPerEvictionRun(3);
        poolConfig.setBlockWhenExhausted(true);
        return poolConfig;
    }
}
