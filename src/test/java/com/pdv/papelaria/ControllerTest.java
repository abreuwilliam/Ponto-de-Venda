package com.pdv.papelaria;

import com.pdv.papelaria.autenticacao.JwtAuthenticationFilter;
import com.pdv.papelaria.autenticacao.JwtUtil;
import com.pdv.papelaria.autenticacao.SecurityConfig;
import com.pdv.papelaria.controller.ProdutoController;
import com.pdv.papelaria.dto.ProdutoDto;
import com.pdv.papelaria.repository.ProdutoRepository;
import com.pdv.papelaria.repository.UsuarioRepository;
import com.pdv.papelaria.service.ProdutoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ProdutoController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(SecurityConfig.class)
public class ControllerTest {

    @MockBean
    private UsuarioRepository usuarioRepository;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProdutoService produtoService;

    @MockBean
    private JwtUtil jwtUtil; // MOCKA O JWT

    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter; // MOCKA O FILTRO

    @BeforeEach
    public void setUp() {}

    @Test
    public void TestController() throws Exception {
        ProdutoDto produtoDto = new ProdutoDto();
        produtoDto.setCodigoProduto(3L);
        produtoDto.setProduto("Caderno");
        produtoDto.setPreco(3.50);
        produtoDto.setQuantidadeEstoque(4);

        Mockito.when(produtoService.buscarPorCodigo(3L))
                .thenReturn(produtoDto);

        mockMvc.perform(get("/produto/3"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.produto").value("Caderno"))
                .andExpect(jsonPath("$.preco").value(3.50));
    }
}
