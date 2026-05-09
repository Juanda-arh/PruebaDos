/*
  Objetivo:
  Obtener los nombres de clientes que tienen inscrito algún producto disponible
  solo en las sucursales que visitan.

  Supuestos de tablas:
  - clientes(id_cliente, nombre)
  - inscripciones(id_cliente, id_producto)
  - producto_sucursal(id_producto, id_sucursal)
  - cliente_sucursal(id_cliente, id_sucursal)

  Regla:
  Para una inscripción cliente-producto, no debe existir ninguna sucursal donde
  el producto esté disponible que el cliente no visite.
*/

SELECT DISTINCT c.nombre
FROM clientes c
JOIN inscripciones i
  ON i.id_cliente = c.id_cliente
WHERE NOT EXISTS (
  -- Busca sucursales del producto inscrito que NO estén dentro de las visitas del cliente.
  SELECT 1
  FROM producto_sucursal ps
  WHERE ps.id_producto = i.id_producto
    AND NOT EXISTS (
      -- Si esta subconsulta no encuentra la sucursal, esa sucursal no pertenece al conjunto visitado.
      SELECT 1
      FROM cliente_sucursal cs
      WHERE cs.id_cliente = i.id_cliente
        AND cs.id_sucursal = ps.id_sucursal
    )
);
