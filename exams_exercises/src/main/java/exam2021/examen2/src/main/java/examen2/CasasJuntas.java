package examen2;

/*
 * Alvaro Martinez Quiroga
 */

public class CasasJuntas {

	public static void main(String[] argv) {
		// la informacion que se imprime por pantalla se intercala en ocasiones
		System.out.println("Casa gatos 1:");
		new CasaParaGatos1().exec();
		System.out.println();
		System.out.println("---------------");
		System.out.println("Casa gatos 2:");
		new CasaParaGatos2().exec();
	}
	
}
