package com.farmagro.tomapedido.TableDefinition;

import com.farmagro.tomapedido.droidpersistence.dao.TableDefinition;
import com.farmagro.tomapedido.modelo.Cliente;

public class ClienteTableDefinition extends TableDefinition<Cliente> {
    public ClienteTableDefinition() {
        super(Cliente.class);
    }
}