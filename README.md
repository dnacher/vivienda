# README #

JDK 1.8
se necesita el trayNotification.jar

### What is this repository for? ###

* Viviendas es una aplicacion para la administracion de viviendas (gastos comunes y mantenimiento)
* 2.0


### Who do I talk to? ###

* Daniel Nacher
* Software soluciones

## Otros gastos pagos y no pagos por Unidad ##

SELECT u.nombre,u.apellido, u.block,u.torre,u.puerta,og.secuencia,og.descripcion,og.monto,og.fecha FROM vivienda.otrosgastos og, vivienda.unidad u
where og.pago='TRUE'
and og.unidad_idUnidad=u.idUnidad
and u.idUnidad=0;

SELECT u.nombre,u.apellido, u.block,u.torre,u.puerta,og.secuencia,og.descripcion,og.monto,og.fecha FROM vivienda.otrosgastos og, vivienda.unidad u
where og.pago='FALSE'
and og.unidad_idUnidad=u.idUnidad
and u.idUnidad=0;

## todos los gastos de todos los trabajos, gastos de los materiales ##

select tr.descripcion,tr.fechacreacion,tr.duracionreal,tr.activo,mat.nombre as material,tm.cantidad,lp.precio,sum(tm.cantidad * lp.precio) as total
from vivienda.listaprecios lp, vivienda.trabajo tr, vivienda.trabajoxmaterial tm,vivienda.material mat
where tr.idtrabajo=tm.trabajo_idtrabajo
and tm.material_idmaterial=mat.idmaterial
and lp.material_idmaterial=mat.idmaterial;

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