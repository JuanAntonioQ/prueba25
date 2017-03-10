package hipermercado;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Vector;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Cola {


    private Calendar calendario;
    private int hora, minutos, segundos;
    private Vector<Cliente> cliente = new Vector<>();
    private boolean abierto = true;

    public Cola() {
    }

    public synchronized void añadirFinal() {
        try {
            if(abierto){
                wait(0,5000);
                calendario = new GregorianCalendar();
                hora = calendario.get(Calendar.HOUR_OF_DAY);
                minutos = calendario.get(Calendar.MINUTE);
                segundos = calendario.get(Calendar.SECOND);
                cliente.add(new Cliente());
                System.out.println("Se ha añadido a " + cliente.lastElement() + " a las: " + hora + ":" + minutos + ":" + segundos);
            }else{
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(Cola.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public synchronized void añadirPrincipio(Cliente cl) {
        try {
            wait(0,5000);
            cliente.add(0,cl);
            System.out.println("El cliente: " + cl.dameNombre() + " ha vuelto al principio de la cola");
        } catch (InterruptedException ex) {
            Logger.getLogger(Cola.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public synchronized Cliente sacar() {
        if (cliente.size() == 0) {
            return null;
        } else {
            Cliente primero = cliente.firstElement();
            cliente.removeElementAt(0);
            return primero;
        }
    }

    public synchronized void cerrar() {
        abierto = false;
    }

    public synchronized int tamañoMáximo() {
        return cliente.size();
    }
}
