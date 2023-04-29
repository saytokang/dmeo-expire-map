package com.example.demoexpiremap.domain;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class BizBox<K, V> {

	private final ConcurrentMap<K, ExpirableValue<V>> box;

	private final ScheduledExecutorService removeTimer;

	public BizBox() {
		box = new ConcurrentHashMap<>();
		removeTimer = Executors.newSingleThreadScheduledExecutor();
	}

	public void put(K k, V v, long duration) {
		var value = new ExpirableValue<>(v, duration);
		box.put(k, value);
	}

	public V get(K k) {
		return (V) box.get(k);
	}

	public void startCleanup() {
		// @formatter:off
        removeTimer.scheduleAtFixedRate(
            () -> box.entrySet().removeIf(v -> v.getValue().isExpired()),
            500, 100, TimeUnit.MILLISECONDS);
        // @formatter:on
	}

	public void print() {
		box.forEach((k, v) -> {
			log.info("{}:{}", k, v.getValue());
		});

	}

}
