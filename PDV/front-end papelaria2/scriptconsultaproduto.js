document.addEventListener("DOMContentLoaded", () => {
    const tabela = document.getElementById("tabelaProdutos").getElementsByTagName("tbody")[0];
    const campoBusca = document.getElementById("buscarProduto");

    // Função para popular a tabela com produtos
    function popularTabela(produtos) {
        tabela.innerHTML = ""; // Limpa tabela antes de popular
        produtos.forEach(produto => {
            let linha = tabela.insertRow();
            linha.insertCell(0).innerText = produto.codigoProduto; // Use o campo correto
            linha.insertCell(1).innerText = produto.produto; // Use o campo correto
            linha.insertCell(2).innerText = `R$ ${produto.preco.toFixed(2)}`; // Formata o preço
            linha.insertCell(3).innerText = produto.quantidadeEstoque; // Use o campo correto
        });
    }

    // Função para buscar produtos na API
    async function consultaproduto(codigoProduto) {
        try {
            const response = await fetch('http://localhost:8080/consulta', {
                headers: {
                    "Accept": "application/json",
                    "Content-Type": "application/json"
                },
                method: 'POST',
                body: codigoProduto // Enviando apenas a string
            });

            console.log("Status da resposta:", response.status); // Log do status

            if (!response.ok) {
                throw new Error('Erro ao buscar produtos');
            }

            const produtos = await response.json();
            popularTabela(produtos);
        } catch (error) {
            console.error('Erro:', error);
        }
    }

    // Função de debounce para limitar a quantidade de requisições
    let debounceTimeout;
    function debounce(func, delay) {
        clearTimeout(debounceTimeout);
        debounceTimeout = setTimeout(func, delay);
    }

    // Função de busca
    function buscarProduto() {
        const codigoProduto = campoBusca.value.trim(); // Obtém o valor do input
        if (codigoProduto) {
            consultaproduto(codigoProduto); // Chama a função de consulta com o código do produto
        } else {
            console.log("Campo vazio");
            popularTabela([]); // Limpa a tabela se não houver entrada
        }
    }

    // Evento de digitação no campo de busca
    campoBusca.addEventListener("input", () => {
        debounce(() => {
            buscarProduto(); // Chama a função de busca com debounce
        }, 500); // Aguarda 500ms após a última digitação antes de realizar a busca
    });
});
