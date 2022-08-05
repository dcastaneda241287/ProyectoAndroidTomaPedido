package com.farmagro.tomapedido.TableDefinition;

import com.farmagro.tomapedido.droidpersistence.dao.TableDefinition;
import com.farmagro.tomapedido.modelo.Proveedor;

public class ProveedorTableDefinition extends TableDefinition<Proveedor> {
    public ProveedorTableDefinition() {
        super(Proveedor.class);
    }
}