package examen1;

public class Proceso {

	private int id;
	private long tiempoEjecucion;
	
	public Proceso(int id, long tiempo) {
		this.id = id;
		this.tiempoEjecucion = tiempo;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public long getTiempoEjecucion() {
		return tiempoEjecucion;
	}

	public void setTiempoEjecucion(long tiempoEjecucion) {
		this.tiempoEjecucion = tiempoEjecucion;
	}
	
}
