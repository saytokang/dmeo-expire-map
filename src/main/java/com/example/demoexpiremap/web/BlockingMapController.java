package com.example.demoexpiremap.web;

import java.time.LocalTime;
import java.util.concurrent.TimeUnit;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demoexpiremap.service.BlockingMap;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequiredArgsConstructor
public class BlockingMapController {

	private final BlockingMap<String, String> map;

	@PutMapping("/b/write/{key}")
	public ResponseEntity<?> write(@PathVariable String key) {
		map.put(key, "ok: " + LocalTime.now());
		return ResponseEntity.ok().build();
	}

	@GetMapping(value = "/b/read/{key}")
	public ResponseEntity getMethodName(@PathVariable String key) throws InterruptedException {
		String rs = map.get(key, 3, TimeUnit.SECONDS);

		return ResponseEntity.ok().body(rs);
	}

}
