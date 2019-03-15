package br.com.assist.service;

import br.com.assist.metrics.MetricsDto;
import br.com.assist.metrics.SingletonGetMagias;
import br.com.assist.metrics.SingletonGetPersonagens;

public class MetricsService {

	public MetricsDto getMetrics() {
		MetricsDto metricsDto = new MetricsDto();
		metricsDto.setPersonagensLoadings(SingletonGetPersonagens.INSTANCE.getContador());
		metricsDto.setMagiaLoadings(SingletonGetMagias.INSTANCE.getContador());
		return metricsDto;
	}
}
