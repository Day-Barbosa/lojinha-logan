package Loja.Controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import Loja.Exception.ProdutoNaoEncontrado;
import Loja.Model.Produto;
import Loja.Model.ProdutoPet;

class ProdutoControllerTest {

    private ProdutoController controller;
    private ProdutoPet racao;

    @BeforeEach
    void setUp() {
        controller = new ProdutoController();
        racao = new ProdutoPet(1, "Ração Premium", 89.90, 10, "Adulto", 3.0);
        controller.cadastrar(racao);
    }

    @Test
    @DisplayName("Busca por id retorna o produto cadastrado")
    void buscarPorIdRetornaProduto() {
        Produto encontrado = controller.buscarPorId(1);

        assertSame(racao, encontrado);
    }

    @Test
    @DisplayName("Busca por id inexistente lança ProdutoNaoEncontrado")
    void buscarPorIdInexistente() {
        ProdutoNaoEncontrado erro = assertThrows(ProdutoNaoEncontrado.class, () -> controller.buscarPorId(99));

        assertEquals("Produto nao encontrado.", erro.getMessage());
    }

    @Test
    @DisplayName("Atualizar altera dados gerais e específicos do ProdutoPet")
    void atualizarProdutoPet() {
        ProdutoPet atualizado = new ProdutoPet(1, "Ração Filhote", 99.90, 20, "Filhote", 1.5);

        controller.atualizar(atualizado);
        ProdutoPet produto = (ProdutoPet) controller.buscarPorId(1);

        assertEquals("Ração Filhote", produto.getNome());
        assertEquals(99.90, produto.getPreco(), 0.001);
        assertEquals(20, produto.getEstoque());
        assertEquals("Filhote", produto.getFaseDaVida());
        assertEquals(1.5, produto.getPesoEmKg(), 0.001);
    }

    @Test
    @DisplayName("Atualizar produto inexistente lança ProdutoNaoEncontrado")
    void atualizarProdutoInexistente() {
        ProdutoPet inexistente = new ProdutoPet(42, "Sachê", 4.50, 30, "Sênior", 0.085);

        assertThrows(ProdutoNaoEncontrado.class, () -> controller.atualizar(inexistente));
    }

    @Test
    @DisplayName("Deletar remove o produto da lista")
    void deletarRemoveProduto() {
        controller.deletar(1);

        assertThrows(ProdutoNaoEncontrado.class, () -> controller.buscarPorId(1));
    }

    @Test
    @DisplayName("Deletar produto inexistente lança ProdutoNaoEncontrado")
    void deletarProdutoInexistente() {
        assertThrows(ProdutoNaoEncontrado.class, () -> controller.deletar(99));
    }

    @Test
    @DisplayName("Movimentações de estoque pelo controller alteram o produto certo")
    void movimentarEstoquePeloController() {
        assertEquals("Estoque atualizado com sucesso.", controller.entradaEstoque(1, 5));
        assertEquals("Venda realizada com sucesso.", controller.saidaEstoque(1, 3));
        assertEquals(12, controller.buscarPorId(1).getEstoque());
    }

    @Test
    @DisplayName("Movimentar estoque de produto inexistente lança ProdutoNaoEncontrado")
    void movimentarEstoqueProdutoInexistente() {
        assertThrows(ProdutoNaoEncontrado.class, () -> controller.saidaEstoque(99, 1));
    }
}
