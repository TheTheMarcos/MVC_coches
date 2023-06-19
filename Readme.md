# Arquitectura MVC

Aplicación que trabaja con objetos coches, modifica la velocidad y la muestra, tambien con la posibilidad de mostrar el coche al completo y 
se ha añadido eventos

---
### Diagrama de clases:

```mermaid
classDiagram
    class Coche {
        String: matricula
        String: modelo
        Integer: velocidad
    }
      class Controller{
          +main()
          +crearCoche(String, String)
          +subirVelocidad(String)
          +bajarVelocidad(String)
          +buscarCoche(String)
      }
      class View {
        +muestraVelocidad(String, Integer) 
        +muestraCoche(Coche, String)
      }
      class Model {
          ArrayList~Coche~: parking
          +crearCoche(String, String, String)
          +getCoche(String)
          +cambiarVelocidad(String, Integer)
          +getVelocidad(String)
      }
      
      class IU { mostrarVentana()}
      
      class Dialog { 
        +mostrarVelocidad(String, Integer) 
        +mostrarCoche(Coche, String)  
      }
    Controller "1" *-- "1" Model : association
    Controller "1" *-- "1" View : association
    Model "1" *-- "1..n" Coche : association
    View "1" *-- "1" IU : association
    View "1" *-- "1" Dialog : association
```

---

## Evento en el View

Cuando ocurre algo en la vista, el `controller` se tiene que enterar.
En el MVS la vista no se comunica con el modelo.

En el listener del botón llamamos al `controller` y este llama primero al `model` y al recibir los datos del model llama a la vista `view`.


```mermaid
sequenceDiagram
    actor usuario
    participant View
    participant Controller
    participant Model
    
    usuario->>View: click! Crear coche
    View->>Controller: el usuario quiere crear un coche
    activate Controller
    Controller->>Model: crea un coche, porfa
    activate Model
    Model-->>Controller: Coche
    deactivate Model
    Controller->>View: ok, coche creado!
    deactivate Controller
    View-->>usuario: tu coche se creó!
```
Pasa lo mismo con los métodos subir velocidad, bajar velocidad y ver coche pero cambiando el texto por el respectivo de esos métodos.

Ahora la parte de la Arquitectura de la vista, son tres clases:

```mermaid
sequenceDiagram
    autonumber
    actor usuario
    box gray Vista con JFrame
        participant IU
        participant Dialog
        participant View
        end
        
    participant Controller
    participant Model

    usuario->>IU: click! Crear coche
    IU->>Controller: crearCoche()
    activate Controller
    Controller->>Model: crearCoche
    activate Model
    Model-->>Controller: Coche
    deactivate Model
    Controller->>+View: mostrarVelocidad
    deactivate Controller
    View-->>-Dialog: mostrarVelocidad()
```
---
## Evento en la Vista con el Observer
Este sería el diagrama de secuencias explicado de forma general
```mermaid
sequenceDiagram
    actor usuario
    participant View
    participant Controller
    participant Model
    participant ObsExceso
    
    usuario->>View: click! Crear coche
    View->>Controller: el usuario quiere crear un coche
    activate Controller
    Controller->>Model: crea un coche, porfa
    activate Model
    Model-->>Controller: Coche
    deactivate Model
    Controller->>View: ok, coche creado!
    deactivate Controller
    View-->>usuario: tu coche se creó!
    
    usuario->>View: click! Subir velocidad
    View->>Controller: el usuario quiere subir la velocidad
    activate Controller
    Controller->>Model: sube la velocidad, porfa
    activate Model
    Model-->>ObsExceso: Sube la Velocidad
    activate ObsExceso
    ObsExceso-->>View: cambio en la velocidad
    View-->usuario: tu coche ha cambiado la velocidad (si la velocidad supera los 120km/h le sale un aviso)
```

---
## Evento con Observer pero con los nombres de los métodos
De esta manera sería el diagrama de secuencias explicado de forma mas específica
```mermaid
sequenceDiagram
    autonumber
    actor usuario        
    participant Controller
    participant Model
    participant ObsExceso

    usuario->>IU: click! Crear coche
    IU->>Controller: crearCoche()
    activate Controller
    Controller->>Model: crearCoche
    activate Model
    Model-->>Controller: Coche
    deactivate Model
    Controller->>+View: mostrarVelocidad
    deactivate Controller
    View-->>-Dialog: mostrarVelocidad()
    
    usuario->>IU: click! Subir velocidad
    IU->>Controller: subirVelocidad()
    activate Controller
    Controller->>Model: subirVelocidad()
    deactivate Controller
    activate Model
    Model-->>ObsExceso: Se ha cambiado la Velocidad
    deactivate Model
    activate ObsExceso
    ObsExceso->>+View: mostrarVelocidad() o mostrarExceso()
    deactivate ObsExceso
    View-->>-Dialog: mostrarVelocidad()
```
