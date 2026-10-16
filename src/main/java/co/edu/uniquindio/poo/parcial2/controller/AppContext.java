package co.edu.uniquindio.poo.parcial2.controller;

import co.edu.uniquindio.poo.parcial2.model.AtencionPrioritaria;
import co.edu.uniquindio.poo.parcial2.model.Cliente;
import co.edu.uniquindio.poo.parcial2.model.ElementoServicio;
import co.edu.uniquindio.poo.parcial2.model.Empresa;
import co.edu.uniquindio.poo.parcial2.model.InstalacionRedFactory;
import co.edu.uniquindio.poo.parcial2.model.LegacySupport;
import co.edu.uniquindio.poo.parcial2.model.LegacySupportAdapter;
import co.edu.uniquindio.poo.parcial2.model.MantenimientoCorrectivoFactory;
import co.edu.uniquindio.poo.parcial2.model.MantenimientoPreventivoFactory;
import co.edu.uniquindio.poo.parcial2.model.NivelPrioridad;
import co.edu.uniquindio.poo.parcial2.model.PaqueteTecnologico;
import co.edu.uniquindio.poo.parcial2.model.RecuperacionInformacionFactory;
import co.edu.uniquindio.poo.parcial2.model.Servicio;
import co.edu.uniquindio.poo.parcial2.model.ServicioFactory;
import co.edu.uniquindio.poo.parcial2.model.Solicitud;
import co.edu.uniquindio.poo.parcial2.model.SoporteTecnico30Dias;
import co.edu.uniquindio.poo.parcial2.services.SolicitudService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AppContext {

    private static AppContext instancia;

    private Empresa empresa;
    private SolicitudService solicitudService;

    /**
     * Metodo constructor privado, crea la empresa, la fachada y carga los datos de prueba
     */
    private AppContext() {
        empresa = new Empresa("TechSolutions", new ArrayList<ElementoServicio>(),
                new ArrayList<Cliente>(), new ArrayList<Solicitud>());
        solicitudService = new SolicitudService(new LegacySupportAdapter(new LegacySupport()));
        cargarDatos();
    }

    /**
     * Metodo que entrega la unica instancia de AppContext
     * @return
     */
    public static AppContext getInstance() {
        if (instancia == null) {
            instancia = new AppContext();
        }
        return instancia;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public SolicitudService getSolicitudService() {
        return solicitudService;
    }

    /**
     * Metodo que genera un codigo de solicitud que no se repite, por ejemplo SOL-004
     * @return
     */
    public String generarCodigoSolicitud() {
        int numero = empresa.getSolicitudes().size() + 1;
        String codigo = formatearCodigo(numero);
        while (empresa.buscarSolicitud(codigo) != null) {
            numero++;
            codigo = formatearCodigo(numero);
        }
        return codigo;
    }

    private String formatearCodigo(int numero) {
        String codigo;
        if (numero < 10) {
            codigo = "SOL-00" + numero;
        } else if (numero < 100) {
            codigo = "SOL-0" + numero;
        } else {
            codigo = "SOL-" + numero;
        }
        return codigo;
    }

    /**
     * Metodo que carga los datos quemados: servicios, paquetes, clientes y solicitudes
     */
    private void cargarDatos() {
        // Servicios del catalogo creados con las fabricas
        ServicioFactory fabricaPreventivo = new MantenimientoPreventivoFactory();
        ServicioFactory fabricaCorrectivo = new MantenimientoCorrectivoFactory();
        ServicioFactory fabricaRedes = new InstalacionRedFactory();
        ServicioFactory fabricaRecuperacion = new RecuperacionInformacionFactory();

        Servicio preventivo = fabricaPreventivo.crearServicio("SRV-001");
        Servicio correctivo = fabricaCorrectivo.crearServicio("SRV-002");
        Servicio redes = fabricaRedes.crearServicio("SRV-003");
        Servicio recuperacion = fabricaRecuperacion.crearServicio("SRV-004");

        empresa.agregarElemento(preventivo);
        empresa.agregarElemento(correctivo);
        empresa.agregarElemento(redes);
        empresa.agregarElemento(recuperacion);

        // Servicio personalizado: correctivo con atencion prioritaria y soporte 30 dias
        ElementoServicio correctivoPersonalizado = new SoporteTecnico30Dias(new AtencionPrioritaria(correctivo));
        empresa.agregarElemento(correctivoPersonalizado);

        // Paquete de mantenimiento y paquete empresarial (paquete dentro de paquete)
        PaqueteTecnologico paqueteMantenimiento = new PaqueteTecnologico("Paquete de mantenimiento",
                new ArrayList<ElementoServicio>());
        paqueteMantenimiento.agregarElemento(preventivo);
        paqueteMantenimiento.agregarElemento(correctivo);

        PaqueteTecnologico paqueteEmpresarial = new PaqueteTecnologico("Paquete empresarial",
                new ArrayList<ElementoServicio>());
        paqueteEmpresarial.agregarElemento(paqueteMantenimiento);
        paqueteEmpresarial.agregarElemento(redes);
        paqueteEmpresarial.agregarElemento(recuperacion);

        empresa.agregarElemento(paqueteMantenimiento);
        empresa.agregarElemento(paqueteEmpresarial);

        // Clientes
        Cliente cliente1 = new Cliente("1094567890", "Laura Gómez Ríos", "laura.gomez@correo.com");
        Cliente cliente2 = new Cliente("1005432109", "Carlos Andrés Pérez", "carlos.perez@correo.com");
        Cliente cliente3 = new Cliente("41923456", "María Fernanda Ospina", "maria.ospina@correo.com");
        empresa.agregarCliente(cliente1);
        empresa.agregarCliente(cliente2);
        empresa.agregarCliente(cliente3);

        // Solicitud 1: presencial con tecnico disponible (MANTENIMIENTO)
        List<ElementoServicio> elementos1 = new ArrayList<ElementoServicio>();
        elementos1.add(paqueteEmpresarial);
        elementos1.add(correctivoPersonalizado);
        Solicitud solicitud1 = new Solicitud.Builder()
                .codigo("SOL-001")
                .fechaCreacion(LocalDate.now())
                .cliente(cliente1)
                .listaElementos(elementos1)
                .presencial(true)
                .especialidad("MANTENIMIENTO")
                .prioridad(NivelPrioridad.ALTA)
                .observaciones("Equipos de la oficina principal")
                .build();
        empresa.agregarSolicitud(solicitud1);

        // Solicitud 2: presencial sin tecnico disponible (RECUPERACION)
        List<ElementoServicio> elementos2 = new ArrayList<ElementoServicio>();
        elementos2.add(recuperacion);
        Solicitud solicitud2 = new Solicitud.Builder()
                .codigo("SOL-002")
                .fechaCreacion(LocalDate.now())
                .cliente(cliente2)
                .listaElementos(elementos2)
                .presencial(true)
                .especialidad("RECUPERACION")
                .fechaProgramada(LocalDate.now().plusDays(3))
                .build();
        empresa.agregarSolicitud(solicitud2);

        // Solicitud 3: no presencial
        List<ElementoServicio> elementos3 = new ArrayList<ElementoServicio>();
        elementos3.add(preventivo);
        Solicitud solicitud3 = new Solicitud.Builder()
                .codigo("SOL-003")
                .fechaCreacion(LocalDate.now())
                .cliente(cliente3)
                .listaElementos(elementos3)
                .presencial(false)
                .prioridad(NivelPrioridad.BAJA)
                .build();
        empresa.agregarSolicitud(solicitud3);
    }
}
