package br.com.assist.metrics;

public enum SingletonGetMagias {

	INSTANCE;

	private int contador = 0;

	public void inc() {
		contador++;
	}

	public int getContador() {
		return contador;
	}
}
