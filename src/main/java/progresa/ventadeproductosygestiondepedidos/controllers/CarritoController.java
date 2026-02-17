package progresa.ventadeproductosygestiondepedidos.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import progresa.ventadeproductosygestiondepedidos.dto.CarritoDTO;
import progresa.ventadeproductosygestiondepedidos.dto.Mensaje;
import progresa.ventadeproductosygestiondepedidos.service.TiendaService;

@RestController
@RequestMapping("/api/carrito")
public class CarritoController {
    @Autowired
    private TiendaService tiendaService;

    @PostMapping("/agregar")
    public ResponseEntity<?> agregar(@RequestBody CarritoDTO dto) {
        tiendaService.agregarProducto(dto.getUsuarioId(), dto.getProductoId(), dto.getCantidad());
        return new ResponseEntity(new Mensaje("Añadido"), HttpStatus.OK);
    }
}
