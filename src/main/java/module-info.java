module servidorPayara {
    requires io.vavr;
    requires lombok;
    requires jakarta.jakartaee.api;
    requires org.apache.logging.log4j;




    opens dao;
    opens dao.modelos;
    opens EE.errores;
    opens EE.rest;
    opens servicios;

    exports dao;
    exports dao.modelos;
    exports EE.errores;
    exports EE.rest;
    exports servicios;
}