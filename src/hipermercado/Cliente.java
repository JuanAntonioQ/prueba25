package hipermercado;
//NO MODIFIQUE ESTA CLASE
class Cliente{
    private String nombre;
    private double precioCarro;
    private static java.util.Random r=new java.util.Random();
    private static int nn=0;
    private static String[] nombres={"José","Pedro","Ana","Juan","Juana", "Antonio","María", "Francisco","Marta","Julio","Felipe","luis"};
    private static String[] apellidos={"Armas","Cabrera", "Díaz","García", "González","Hernández","López","Rodríguez", "Moreno", "Muñoz", "Martín"};
    public Cliente(){
        int nnombre=r.nextInt(nombres.length);
        int napellido1=r.nextInt(apellidos.length);
        int napellido2=r.nextInt(apellidos.length);
        this.nombre=nombres[nnombre]+" "+apellidos[napellido1]+" "+apellidos[napellido2];
        this.precioCarro=r.nextInt(10000)/100.0;
        nn++;
    }
    public String dameNombre(){
        return nombre;
    }

    public double damePrecioCarro(){
        return precioCarro;
    }
    public String toString(){
        return dameNombre()+": "+ damePrecioCarro()+"€";
    }
}
