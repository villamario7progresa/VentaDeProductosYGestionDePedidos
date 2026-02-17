package progresa.ventadeproductosygestiondepedidos.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import progresa.ventadeproductosygestiondepedidos.Entity.*;
import progresa.ventadeproductosygestiondepedidos.dao.CarritoRepository;
import progresa.ventadeproductosygestiondepedidos.dao.PedidoRepository;
import progresa.ventadeproductosygestiondepedidos.dao.ProductoRepository;
import progresa.ventadeproductosygestiondepedidos.dao.UsuarioRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class TiendaService {
    @Autowired
    private UsuarioRepository usuarioRepo;
    @Autowired private CarritoRepository carritoRepo;
    @Autowired private ProductoRepository productoRepo;
    @Autowired private PedidoRepository pedidoRepo;

    public void agregarProducto(Long userId, Long prodId, Integer cant) {
        Carrito carrito = carritoRepo.findByUsuarioId(userId).orElseGet(() -> {
            Carrito c = new Carrito();
            c.setUsuario(usuarioRepo.findById(userId).get());
            return carritoRepo.save(c);
        });
        Producto producto = productoRepo.findById(prodId).orElseThrow();
        CarritoItem item = new CarritoItem(null, cant, carrito, producto);
        carrito.getItems().add(item);
        carritoRepo.save(carrito);
    }

    public Pedido confirmarPedido(Long userId, String metodoPago) {
        Carrito carrito = carritoRepo.findByUsuarioId(userId).orElseThrow();
        Pedido pedido = new Pedido();
        pedido.setUsuario(carrito.getUsuario());

        List<DetallePedido> detalles = carrito.getItems().stream().map(item ->
                new DetallePedido(null, item.getCantidad(), item.getProducto().getPrecio(), pedido, item.getProducto())
        ).collect(Collectors.toList());

        pedido.setDetalles(detalles);
        pedido.setTotal(detalles.stream().mapToDouble(d -> d.getPrecioVenta() * d.getCantidad()).sum());
        pedido.setPago(new Pago(null, metodoPago, "PAGADO", pedido));

        carrito.getItems().clear();
        return pedidoRepo.save(pedido);
    }
}
