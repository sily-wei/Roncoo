package com.roncoo.common.utils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author weining
 * @date 2019/12/30 21:53
 */
public class JedisUtil {
    private static JedisPool jedisPool;
    private Jedis jedis;
    static {
        JedisPoolConfig poolConfig=new JedisPoolConfig();
        poolConfig.setMaxIdle(20); //最大空闲客户端连接对象
        poolConfig.setMaxTotal(1000);//最大的客户端连接对象
        poolConfig.setMaxWaitMillis(2000);//最多等待时间 毫秒
        jedisPool=new JedisPool(poolConfig,"39.105.189.141",6380);
    }

    public JedisUtil(){
        jedis= jedisPool.getResource();
        jedis.auth("qfjava");
    }
    //新增--字符串  Key不存在新增 存在修改
    public void addStr(String key,String val){
        jedis.set(key, val);
    }
    public void addStr(String key,String val,int seconds){
        jedis.psetex(key,seconds*1000, val);
    }
    //新增-list集合
    public void addList(String key,String... arr){
        jedis.lpush(key,arr);//添加到头部
        //  jedis.rpush();//从尾部添加
    }
    //新增-Set集合
    public void addSet(String key,String... arr){
        jedis.sadd(key, arr);
    }
    //新增-有序Set ZSet SortSet
    public void addZSet(String key,double score,String val){
        jedis.zadd(key, score, val);
    }
    //新增-Hash
    public void addHash(String key,String field,String val){
        jedis.hset(key, field, val);
    }
    //删除
    public void del(String key){
        jedis.del(key);
    }
    //删除集合数据
    public void delList(String key,String v){
        jedis.lrem(key,1,v);
    }

    //查询
    public String getStr(String key){
        return jedis.get(key);
    }
    public List<String> getList(String key){
        return jedis.lrange(key,0,-1);
    }
    public Set<String> getSet(String key){
        return jedis.smembers(key);
    }
    public Map<String,String> getHash(String key){
        return jedis.hgetAll(key);
    }

    //检查
    public boolean checkHash(String key,String f){
        return jedis.hexists(key, f);
    }
    public boolean checkKey(String key){
        return jedis.exists(key);
    }

    //设置有效期
    public void setExpire(String key,int seconds){
        jedis.expire(key,seconds);
    }
    //查询剩余有效期  毫秒
    public long ttl(String key){
        return jedis.ttl(key);
    }
    //模拟队列  入列
    public void push(String key,String val){
        jedis.rpush(key,val);
    }
    //出列
    public String pop(String key){
        return jedis.lpop(key);
    }
    //模糊匹配key
    public int keys(String key){
        return jedis.keys(key).size();
    }

    //获取长度
    public int getSetLen(String key){
        return jedis.smembers(key).size();
    }
    public int getListLen(String key){
        return jedis.llen(key).intValue();
    }

    public void delHash(String likeHash, String k) {
        jedis.hdel(likeHash,k);
    }
}
