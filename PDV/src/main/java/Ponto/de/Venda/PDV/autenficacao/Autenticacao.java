package Ponto.de.Venda.PDV.autenficacao;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Autenticacao {

    @Autowired
    private ServiceLogin serviceLogin;

    @Autowired
    private Usuario usuario; // Supondo que Usuario é um bean do tipo correto

    public String autentificacao() {
        try {
            // Converter a string JSON em um objeto Usuario
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonUsuario = usuario.getUsuario(); // Obter a string JSON do usuário

            // Converter JSON em objeto Usuario
            Usuario usuarioObj = objectMapper.readValue(jsonUsuario, Usuario.class);

            // Obter o nome do usuário e a senha
            String usuarioNome = usuarioObj.getUsuario();
            String senha = usuarioObj.getSenha();

            if (serviceLogin.pesquisarusuario(usuarioNome)  && (serviceLogin.pesquisarsenha(senha))) {
                return "usuario encontrado" ;
            } else {
                return "falha na autentificacao: "; // Corrigido a concatenação
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "usuario nao encontrado" ;
        }
    }
}
//walmir_1969@outlook.com
    //CAIXAprego1