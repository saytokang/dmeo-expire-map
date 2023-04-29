package com.example.demoexpiremap.domain;

import lombok.Getter;

public class ExpirableValue<V> {

	@Getter
	private final V value;

	private final long expiredAt;

	public ExpirableValue(V v, long time) {
		this.value = v;
		this.expiredAt = System.currentTimeMillis() + time;
	}

	public boolean isExpired() {
		return System.currentTimeMillis() - expiredAt >= 0;
	}

}
