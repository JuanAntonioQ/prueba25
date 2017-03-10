package hipermercado;
public class Contabilidad{
    
    private double saldo;
    
    public Contabilidad(){
        this.saldo = 0;
    }
    
    public synchronized void añadeSaldo(double saldo){
        this.saldo += saldo;
    }
    
    public double dameSaldo(){
        return saldo;
    }
}