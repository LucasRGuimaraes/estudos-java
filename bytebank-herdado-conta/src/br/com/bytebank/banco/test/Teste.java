package br.com.bytebank.banco.test;

<<<<<<< HEAD
import br.com.bytebank.banco.modelo.Cliente;
import br.com.bytebank.banco.modelo.Conta;
import br.com.bytebank.banco.modelo.ContaCorrente;
import br.com.bytebank.banco.modelo.ContaPoupanca;

public class Teste {

	public static void main(String[] args) {

//		System.out.println("x");
//		System.out.println(3);
//		System.out.println(false);
		
		Object cc = new ContaCorrente(22, 33);
		Object cp = new ContaPoupanca(33, 22);
		Object cliente = new Cliente();
		
		System.out.println(cc);
		System.out.println(cp);
		
		//println(cliente);
	}
	
	static void println() {}
	static void println(int a) {}
	static void println(boolean valor) {}
	
	static void println(Object referencia) {
	}

=======
import java.util.Iterator;

public class Teste {

		public static void main(String[] args) {
			int [] idades = new int[5];
			
			for (int i = 0; i < idades.length; i++) {
				idades[i] = i * i;
			}
			
			for (int i = 0; i < idades.length; i++) {
				System.out.println(idades[i]);
			}
		}
>>>>>>> d3fa3d1ab09df924cdab7bac0e2bca47f40d4bd0
}
