package com.farmagro.tomapedido.TableDefinition;

import com.farmagro.tomapedido.droidpersistence.dao.TableDefinition;
import com.farmagro.tomapedido.modelo.Producto;

public class ProductoTableDefinition extends TableDefinition<Producto> {
    public ProductoTableDefinition() {
        super(Producto.class);
    }
}
