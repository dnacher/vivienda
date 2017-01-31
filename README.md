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