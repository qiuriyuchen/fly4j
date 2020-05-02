this project is separated from my other project fly(a knowledge management system,it will be open source soon),it contains unrelated business:cache,file store,file back ....this features can support other projects,sun as my project backTollFx（a desktop system for back file,it is open source too）

## cache

```
    FlyCache flyCache = new FlyCacheJVM(1000);
    flyCache.put("akey", "avalue", 2);
    
    flyCache.get("akey")
    
    flyCache.ttl("akey")
```

## LimitRate

```
  FlyCache flyCache = new FlyCacheJVM(1000);
  LimitRate limitRate = new LimitRateImpl(flyCache, 20, 2);
  limitRate.isHotLimit("127.0.0.1")
```

