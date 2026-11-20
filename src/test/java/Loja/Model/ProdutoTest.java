package Loja.Model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProdutoTest {

    private Produto racao;

    @BeforeEach
    void setUp() {
        racao = new ProdutoPet(1, "Ração Premium", 89.90, 10, "Adulto", 3.0);
    }

    @Test
    @DisplayName("Entrada de estoque soma a quantidade informada")
    void entradaDeEstoqueSomaQuantidade() {
        String resultado = racao.entradaEstoque(5);

        assertEquals("Estoque atualizado com sucesso.", resultado);
        assertEquals(15, racao.getEstoque());
    }

    @Test
    @DisplayName("Entrada de estoque com observação inclui o texto na mensagem")
    void entradaDeEstoqueComObservacao() {
        String resultado = racao.entradaEstoque(2, "Lote 42");

        assertEquals("Estoque atualizado com sucesso. Observacao: Lote 42", resultado);
        assertEquals(12, racao.getEstoque());
    }

    @Test
    @DisplayName("Entrada de estoque com quantidade zero ou negativa é recusada")
    void entradaDeEstoqueInvalida() {
        assertEquals("Quantidade invalida.", racao.entradaEstoque(0));
        assertEquals("Quantidade invalida.", racao.entradaEstoque(-3));
        assertEquals(10, racao.getEstoque());
    }

    @Test
    @DisplayName("Saída de estoque subtrai a quantidade vendida")
    void saidaDeEstoqueSubtraiQuantidade() {
        String resultado = racao.saidaEstoque(4);

        assertEquals("Venda realizada com sucesso.", resultado);
        assertEquals(6, racao.getEstoque());
    }

    @Test
    @DisplayName("Saída maior que o estoque é recusada e não altera o saldo")
    void saidaMaiorQueEstoque() {
        String resultado = racao.saidaEstoque(11);

        assertEquals("Estoque insuficiente para realizar a venda.", resultado);
        assertEquals(10, racao.getEstoque());
    }

    @Test
    @DisplayName("Saída de todo o estoque zera o saldo")
    void saidaDeTodoEstoque() {
        racao.saidaEstoque(10);

        assertEquals(0, racao.getEstoque());
    }

    @Test
    @DisplayName("Saída com quantidade zero ou negativa é recusada")
    void saidaInvalida() {
        assertEquals("Quantidade invalida.", racao.saidaEstoque(0));
        assertEquals("Quantidade invalida.", racao.saidaEstoque(-1));
        assertEquals(10, racao.getEstoque());
    }

    @Test
    @DisplayName("ProdutoPet informa o tipo do produto")
    void tipoDoProdutoPet() {
        assertEquals("Produto para Gatos", racao.getTipo());
    }
}
