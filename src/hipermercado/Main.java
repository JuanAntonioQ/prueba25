package hipermercado;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        int pos = 0, cajasCerradas = 0, delay = 0;
        Scanner entradaEscaner = new Scanner (System.in);
        System.out.println("¿Cuantos clientes desea que se añadan a la cola?");
        int tamMax = entradaEscaner.nextInt ();
        System.out.println("¿Cuantas cajas desea en el hipermercado?");
        int nCajas = entradaEscaner.nextInt ();
        long start = 0;
        Caja[] caja = new Caja[nCajas];
        Cola cola = new Cola();
        Contabilidad contabilidad = new Contabilidad();
        for(int i=0;i<caja.length;i++){
            caja[i] = new Caja(cola,contabilidad);
        }
        for(int i=0;i<caja.length;i++){
            caja[i].start();
        }
        DuendeAveria duende = new DuendeAveria(caja);
        while (delay < 60000 && tamMax > 0 && cajasCerradas < caja.length) {
            try {
                start = System.currentTimeMillis();
                cola.añadirFinal();
                tamMax--;
                delay += (System.currentTimeMillis() - start);
                if(pos == caja.length){
                    pos = 0;
                }
                if (!caja[pos].isAlive()){
                    cajasCerradas++;
                    pos++;
                }

            } catch (Exception e) {

            }
        }
        try{
            for(int i = 0;i<caja.length;i++){
                caja[i].join();
            }
        }catch(Exception e){

        }
        System.out.println("Se ha recaudado: " + contabilidad.dameSaldo() + "€");
    }
}
