# README #

JDK 1.8
se necesita el trayNotification.jar

### What is this repository for? ###

* Viviendas es una aplicacion para la administracion de viviendas (gastos comunes y mantenimiento)
* 2.0


### Contribution guidelines ###

* Writing tests
* Code review
* Other guidelines

### Who do I talk to? ###

* Daniel Nacher
* Software soluciones

## select para reporte de trabajos ##

select u.nombre,u.apellido, u.block, u.torre, u.puerta,
t.fechaCreacion, t.fechaVisita,t.duracionEstimada,ur.nombre,e.nombre
from vivienda.unidad u, vivienda.trabajo t,vivienda.historialtrabajo ht, vivienda.urgencia ur, vivienda.estado e
where u.idUnidad=t.Unidad_idUnidad
and ht.Trabajo_idTrabajo=t.idTrabajo
and ur.idurgencia=t.urgencia_idurgencia
and t.estado_idestado=e.idestado;

## update tabla cuotaConvenio ##

ALTER TABLE `vivienda`.`cuotaconvenio` 
ADD COLUMN `fechaPago` DATE NULL AFTER `tieneBonificacion`;


## Para concatenar periodos ##

public static int concatenar(int y, int m){
        int periodo=0;
        if(m>9){
            periodo=Integer.valueOf(String.valueOf(y) + String.valueOf(m));
        }
        else{
            periodo=Integer.valueOf(String.valueOf(y) + "0" + String.valueOf(m));
        }        
        return periodo;
    }