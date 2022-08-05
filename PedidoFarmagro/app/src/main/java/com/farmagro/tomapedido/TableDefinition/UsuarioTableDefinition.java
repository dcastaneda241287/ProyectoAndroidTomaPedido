package com.farmagro.tomapedido.TableDefinition;

import com.farmagro.tomapedido.droidpersistence.dao.TableDefinition;
import com.farmagro.tomapedido.modelo.Usuario;

public class UsuarioTableDefinition extends TableDefinition<Usuario> {
    public UsuarioTableDefinition() {
        super(Usuario.class);
    }
}
