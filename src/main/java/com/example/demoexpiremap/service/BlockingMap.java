package com.example.demoexpiremap.service;

import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;

@Component
public class BlockingMap<K, V> {

	private Map<K, ArrayBlockingQueue<V>> map = new ConcurrentHashMap<>();

	private BlockingQueue<V> getQueue(K key) {
		return map.computeIfAbsent(key, k -> new ArrayBlockingQueue<>(1));
	}

	public void put(K key, V value) {
		// can also use add(value) if you want an exception thrown
		if (!getQueue(key).offer(value)) {
			System.err.println("Ignoring duplicate key");
		}
	}

	public V get(K key) throws InterruptedException {
		return getQueue(key).take();
	}

	public V get(K key, long timeout, TimeUnit unit) throws InterruptedException {
		return getQueue(key).poll(timeout, unit);
	}

}
