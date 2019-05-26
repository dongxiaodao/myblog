package com.dongxiaodao.blog.basis.util;

import com.dongxiaodao.blog.basis.dto.Comment;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/** map类型的缓存 简单实现（未完成）
 * @author dongxiaodao
 * @date 2019/4/12 - 17:36
 */
public class MapCache {
//    单例模式
//    默认存储1024个缓存
//    共享常量，任何对象共享这个缓存类的
    private static final int DEFSULT_CACHES = 1024;
    public static final MapCache INS = new MapCache();
    public static MapCache single(){return INS;}

    /**
     *
     * 缓存池
     */
    private Map<String, CacheObject> cachePool;
    public MapCache(){
        this(DEFSULT_CACHES);
    }

    public MapCache(int count){
        cachePool = new ConcurrentHashMap<String, CacheObject>(count);
    }

//    往缓存里添加(永久保存)
    public void set(String key, Object value){
        this.set(key, value, -1);//永不过期
    }
    /**
     * 设置一个缓存并带过期时间,该方法并不直接调用，由上述方法调用（）
     *
     * @param key     缓存key
     * @param value   缓存value
     * @param expired 过期时间，单位为秒
     */
    public void set(String key, Object value, long expired){
        CacheObject cacheObject = new CacheObject(key,value,expired);
        cachePool.put(key,cacheObject);
    }

    public <T> T get(String key){
        CacheObject cacheObject = cachePool.get(key);
        if(cacheObject != null){
            long current_time = System.currentTimeMillis()/1000;
            //判断是否过期
            if(cacheObject.getExpired() <= 0 || cacheObject.getExpired() > current_time){
               return (T) cacheObject.getValue();
            }else {
//                此时已过期，删除该缓存
                delete(key);
//                返回为空
                return null;
            }

        }
        return null;
    }
//    删除缓存
    public void delete(String key) {
        cachePool.remove(key);
    }

    public void clean(){
        cachePool.clear();
    }

//    针对回复时频繁提交评论的问题
    public void specialSet(String type, String key, Object value, long expired){
        key = type + "_" + key;
        this.set(key, value, expired);
    }

    public <T> T specialGet(String type, String key){
        return this.get(type + "_" + key);
    }


    //    静态内部类 共用一个缓存内部类
    static class CacheObject{
        private String key;
        private Object value;
        //       有效时间
        private long expired;

        public CacheObject(String key, Object value, long expired) {
            this.key = key;
            this.value = value;
            this.expired = expired;
        }

        public String getKey() {
            return key;
        }

        public Object getValue() {
            return value;
        }

        public long getExpired() {
            return expired;
        }
    }
}
