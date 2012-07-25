SELECT p.numero as pedido_numero, c.codigo as codigo_cliente, c.denominacion as cliente, p.fecha, pi.cantidad, pi.detalle, pi.observaciones FROM PEDIDO P JOIN CLIENTE C ON P.CODIGO_CLIENTE=C.CODIGO LEFT JOIN PEDIDO_ITEM PI ON P.NUMERO=PI.PEDIDO_NUMERO
    WHERE P.NUMERO = $P{NUMERO_PEDIDO}
	ORDER BY PI.ORDEN