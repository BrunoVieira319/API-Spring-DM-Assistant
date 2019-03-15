package br.com.assist.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.assist.metrics.MetricsDto;
import br.com.assist.service.MetricsService;

@RestController
@RequestMapping(value = "/metrics")
public class MetricsController {

	private MetricsService service;
	
	public MetricsController() {
		service = new MetricsService();
	}
	
	@GetMapping
	public ResponseEntity<MetricsDto> buscarTodosPersonagens() {
		MetricsDto metricsDto = service.getMetrics();
		return new ResponseEntity<MetricsDto>(metricsDto, HttpStatus.OK);
	}
}
