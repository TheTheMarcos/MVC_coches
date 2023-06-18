import java.lang.module.ModuleDescriptor;

public class Controller {
    public static void main(String[] args) {

        // Crear tres coches

        Model.crearCoche("LaFerrari", "SBC 1234");
        Model.crearCoche("Alpine", "HYU 4567");
        Model.crearCoche("Aston Martin", "FGH 3333");

        Coche ferrari = Model.getCoche("SBC 1234");
        // modifica la velocidad
        Model.cambiarVelocidad("SBC 1234", 30);

        // recoje la velocidad y la muestra (tarea de la View)
        boolean hecho = View.muestraVelocidad("SBC 1234", Model.getVelocidad("SBC 1234"));

        // sube la velocidad
        Model.subirVelocidad("SBC 1234", 200);
        // recoje la velocidad y la muestra (tarea de la View)
        hecho = View.muestraVelocidad("SBC 1234", Model.getVelocidad("SBC 1234"));
        System.out.println(hecho);

        // baja la velocidad
        Model.bajarVelocidad("SBC 1234", 80);
        // recoje la velocidad y la muestr (tarea de la View)
        hecho = View.muestraVelocidad("SBC 1234", Model.getVelocidad("SBC 1234"));
        System.out.println(hecho);
        System.out.println(hecho);
    }
}