this project is separated from my other project fly(a knowledge management system,it will be open source soon),it contains unrelated business:cache,file store,file back ....this features can support other projects,sun as my project backTollFx（a desktop system for back file,it is open source too）

## cache

```
//1000 is the maxSize
FlyCache flyCache = new FlyCacheJVM(1000);
//2 is the cache life
flyCache.put("a", "123", 2);
// it will be return 123
flyCache.get("akey");
// it will be return left life
flyCache.ttl("akey");
```

## LimitRate

```
FlyCache flyCache = new FlyCacheJVM(1000);
// 20 is the between time ,2 is the limitNum
LimitRate limitRate = new LimitRateImpl(flyCache, 20, 2);
// return is limit
boolean isHot = limitRate.isHotLimit("127.0.0.1");
```

