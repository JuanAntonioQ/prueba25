/* NO MODIFIQUE ESTE CÓDIGO */
package hipermercado;
import java.util.Random;
import static java.lang.Math.log;
import java.util.concurrent.*;
public final class DuendeAveria extends Thread {
    private Random rnd=new Random();
    private Caja[] cajas;
    private long[] intervalos;
    private int[] cajaAveriada;
    private void println(String s) {
        System.out.println("\033[7m"+s+"\033[0m"); // video inverso
    }
    private static long tini = System.currentTimeMillis()+10*1000; // por poner algo
    public static long getT() { return System.currentTimeMillis()-tini; }

    public DuendeAveria(Caja[] c) {
        println("--DuendeAveria INVOCADO--");
        tini= System.currentTimeMillis();
        if(c.length == 0) return;
        rnd=new Random();
        cajas = c.clone();
        //Cálculo de intervalos de fallos
        intervalos=new long[cajas.length];
        cajaAveriada= new int[cajas.length];
        for(int i=0;i<cajaAveriada.length;i++){
            cajaAveriada[i]=i;
        }
        for(int i=0;i<intervalos.length;i++){
            double ran=rnd.nextDouble();
            intervalos[i]= (long)(-30*1000*log(1-ran));
        }
        for(int i=0;i<intervalos.length-1;i++){
            //Desordena las averiadas i <=> averiada
            int averiada = rnd.nextInt(cajaAveriada.length-i)+i;
            int aux=cajaAveriada[i];
            cajaAveriada[i]=cajaAveriada[averiada];
            cajaAveriada[averiada]=aux;
        }
        //Ordenación
        java.util.Arrays.sort(intervalos);
        //Diferencia=espera para siguiente avería;
        long anterior=intervalos[0];
        for(int i=1;i<intervalos.length;i++){
            intervalos[i]-=anterior;
            anterior += intervalos[i];
        }
        start();
    }
    public DuendeAveria(java.util.Collection<Caja> c) {
        this(c.toArray(new Caja[0]));
    }

    public void run() {
        final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        ScheduledFuture<?> segundero = scheduler.scheduleAtFixedRate(new Runnable() {
            int segundos = -1;
            public void run() { println(++segundos+"s"); }
        }, 0, 1, TimeUnit.SECONDS);
        try {
            for(int i=0; i<intervalos.length; i++){
                if(getT() > 60000) break;
                sleep(intervalos[i]);
                Caja caja=cajas[cajaAveriada[i]];
                if(caja==null){
                    println("Error: El Duende avería ha encontrado una caja a null");
                }else{
                    println("AVERÍA th:"+caja.getId());
                    caja.interrupt();
                }
            }
            println("--TODAS LAS CAJAS AVERIADAS--");
        } catch(InterruptedException e) {
            println("Duende avería: interrupción recibida");
        } finally {
            //println("Intento cancelar el segundero... ¡ahora!");
            segundero.cancel(false);
        }
    }
}
