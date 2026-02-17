package progresa.ventadeproductosygestiondepedidos.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "detalle_pedido")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DetallePedido {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer cantidad;
    private Double precioVenta;
    @ManyToOne @JoinColumn(name = "pedido_id") @JsonBackReference
    private Pedido pedido;
    @ManyToOne @JoinColumn(name = "producto_id")
    private Producto producto;
}