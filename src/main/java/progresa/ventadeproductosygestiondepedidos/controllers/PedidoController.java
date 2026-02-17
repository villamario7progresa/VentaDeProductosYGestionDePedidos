package progresa.ventadeproductosygestiondepedidos.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import progresa.ventadeproductosygestiondepedidos.Entity.Pedido;
import progresa.ventadeproductosygestiondepedidos.dao.PedidoRepository;
import progresa.ventadeproductosygestiondepedidos.dto.Mensaje;
import progresa.ventadeproductosygestiondepedidos.service.TiendaService;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {
    @Autowired
    private TiendaService tiendaService;
    @Autowired private PedidoRepository pedidoRepo;

    @PostMapping("/confirmar")
    public ResponseEntity<?> confirmar(@RequestParam Long userId, @RequestParam String metodo) {
        try {
            return ResponseEntity.ok(tiendaService.confirmarPedido(userId, metodo));
        } catch (Exception e) {
            return new ResponseEntity(new Mensaje("Error en pedido"), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/historial/{userId}")
    public ResponseEntity<List<Pedido>> historial(@PathVariable Long userId) {
        return ResponseEntity.ok(pedidoRepo.findByUsuarioId(userId));
    }
}
