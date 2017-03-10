package hipermercado;


import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Caja extends Thread {

    private Calendar calendario;
    private Cola cola;
    private static int ids;
    private int id, hora, minutos, segundos;
    private Cliente cliente;
    private Contabilidad contabilidad;
    private double dineroGanado = 0;
    private boolean isOpen = true;
    public Caja(Cola cola, Contabilidad contabilidad) {
        this.cola = cola;
        this.contabilidad = contabilidad;
        id = ids++;
    }

    public void interrupt() {
        isOpen = false;
    }

    public void run() {
        int delay = 0;
        cliente = cola.sacar();
        while (true) {
            if (cliente != null && isOpen) {
                while (cliente != null && isOpen) {
                    try {
                        calendario = new GregorianCalendar();
                        hora = calendario.get(Calendar.HOUR_OF_DAY);
                        minutos = calendario.get(Calendar.MINUTE);
                        segundos = calendario.get(Calendar.SECOND);
                        
                        System.out.println("Se ha sacado al cliente: " + cliente.dameNombre() + " de la caja nº: " + id + " a las: " + hora + ":" + minutos + ":" + segundos);
                        System.out.println("Quedan " + cola.tamañoMáximo() + " clientes en cola");
                        dineroGanado += cliente.damePrecioCarro();
                        System.out.println("Se esta atentiendo a " + cliente.dameNombre() + " en la caja: " + id + " a las: " + hora + ":" + minutos + ":" + segundos);
                        Thread.sleep((long)cliente.damePrecioCarro()*100);
                        
                        calendario = new GregorianCalendar();
                        hora = calendario.get(Calendar.HOUR_OF_DAY);
                        minutos = calendario.get(Calendar.MINUTE);
                        segundos = calendario.get(Calendar.SECOND);
                        
                        System.out.println("Se ha terminado de atender a " + cliente.dameNombre() + " en la caja: " + id + " y ha pagado: " + cliente.damePrecioCarro() + "€" + " a las: " + hora + ":" + minutos + ":" + segundos);
                        cliente = cola.sacar();
                    } catch (Exception ex) {
                        Logger.getLogger(Caja.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            } else {
                while (cliente == null && delay < 9999 && isOpen) {
                    try {
                        long start = System.currentTimeMillis();
                        Thread.sleep(0, 9999 - delay);
                        cliente = cola.sacar();
                        delay += System.currentTimeMillis() - start;
                    } catch (InterruptedException e) {
                        
                    }
                }
                if (delay >= 9999) {
                    break;
                }else{
                    delay = 0;
                }
                if (!isOpen){
                    System.out.println("La caja " + id + " se ha averiado");
                    if(cliente != null){
                        cola.añadirPrincipio(cliente);
                    }
                    break;
                }
            }
        }
        contabilidad.añadeSaldo(dineroGanado);
        System.out.println("La caja " + id + " se ha cerrado. El dinero recaudado es de: " + dineroGanado);
    }
}
