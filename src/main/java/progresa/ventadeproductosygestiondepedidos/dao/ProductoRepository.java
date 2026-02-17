package progresa.ventadeproductosygestiondepedidos.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import progresa.ventadeproductosygestiondepedidos.Entity.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
}
