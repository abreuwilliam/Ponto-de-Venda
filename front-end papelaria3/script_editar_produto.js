let timeout;

// Busca produto após digitação
document.getElementById('codigoProduto').addEventListener('input', function () {
    clearTimeout(timeout);
    timeout = setTimeout(buscarProduto, 1000);
});

async function buscarProduto() {
    const codigoProduto = document.getElementById('codigoProduto').value;
    const token = localStorage.getItem('authToken');

    if (!codigoProduto || !token) {
        alert('Insira o código e esteja logado.');
        return;
    }

    try {
        const response = await fetch(`https://ponto-de-venda-1.onrender.com/produto/${codigoProduto}`, {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${token}`,
            },
        });

        if (response.ok) {
            const produto = await response.json();
            document.getElementById('produto').value = produto.produto;
            document.getElementById('preco').value = produto.preco;
            document.getElementById('quantidadeEstoque').value = produto.quantidadeEstoque;

            // Habilita campos
            document.getElementById('produto').disabled = false;
            document.getElementById('preco').disabled = false;
            document.getElementById('quantidadeEstoque').disabled = false;
            document.getElementById('quantidadeNova').value = '';
        } else {
            alert('Produto não encontrado.');
        }
    } catch (error) {
        console.error('Erro ao buscar produto:', error);
        alert('Erro ao buscar o produto.');
    }
}

// Atualizar estoque com novo valor somado
document.getElementById('atualizarEstoqueBtn').addEventListener('click', async () => {
    const novaQuantidade = parseInt(document.getElementById('quantidadeNova').value) || 0;
    const estoqueAtual = parseInt(document.getElementById('quantidadeEstoque').value) || 0;

    const novoTotal = estoqueAtual + novaQuantidade;
    if (novaQuantidade === 0) {
        alert('Digite uma quantidade para adicionar ao estoque.');
        return;
    }

    await alterarProduto(novoTotal); // envia a nova quantidade já somada
    document.getElementById('quantidadeEstoque').value = novoTotal;
    document.getElementById('quantidadeNova').value = '';
    alert(`Estoque atualizado com sucesso! Novo total: ${novoTotal}`);
});


async function alterarProduto(quantidadeEstoqueAtualizada = null) {
    const codigoProduto = document.getElementById('codigoProduto').value;
    const produto = document.getElementById('produto').value;
    const preco = document.getElementById('preco').value;
    const token = localStorage.getItem('authToken');

    // Se a quantidade não foi passada, usa a do campo
    if (quantidadeEstoqueAtualizada === null) {
        quantidadeEstoqueAtualizada = parseInt(document.getElementById('quantidadeEstoque').value);
    }

    console.log("codigoProduto:", codigoProduto);
    console.log("produto:", produto);
    console.log("preco:", preco);
    console.log("quantidadeEstoqueAtualizada:", quantidadeEstoqueAtualizada);

    if (
        codigoProduto.trim() === '' ||
        produto.trim() === '' ||
        preco === '' || isNaN(Number(preco)) || Number(preco) <= 0 ||
        quantidadeEstoqueAtualizada === null || isNaN(Number(quantidadeEstoqueAtualizada)) || Number(quantidadeEstoqueAtualizada) < 0
    ) {
        alert('Todos os campos devem estar preenchidos corretamente.');
        return;
    }

    const data = {
        codigoProduto,
        produto,
        preco: parseFloat(preco),
        quantidadeEstoque: parseInt(quantidadeEstoqueAtualizada)
    };

    try {
        const response = await fetch(`https://ponto-de-venda-1.onrender.com/produto/${codigoProduto}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`,
            },
            body: JSON.stringify(data),
        });

        if (!response.ok) {
            const errorText = await response.text();
            throw new Error(errorText || 'Erro ao atualizar o produto');
        }

        console.log('Produto atualizado com sucesso!');
        alert('Produto atualizado com sucesso!');
    } catch (error) {
        console.error('Erro ao alterar o produto:', error);
        alert(`Erro ao alterar: ${error.message}`);
    }
}




// Função de exclusão
async function deletarProduto() {
    const codigoProduto = document.getElementById('codigoProduto').value;
    const token = localStorage.getItem('authToken');

    if (!codigoProduto || !token) {
        alert('Código e token obrigatórios.');
        return;
    }

    if (!confirm(`Confirma excluir o produto ${codigoProduto}?`)) return;

    try {
        const response = await fetch(`https://ponto-de-venda-1.onrender.com/produto/${codigoProduto}`, {
            method: 'DELETE',
            headers: {
                'Authorization': `Bearer ${token}`,
            },
        });

        if (response.ok) {
            alert('Produto excluído com sucesso.');
            limparCamposProduto();
        } else {
            alert('Erro ao excluir o produto.');
        }
    } catch (error) {
        console.error('Erro ao excluir:', error);
        alert('Erro na comunicação com o servidor.');
    }
}

// (opcional) função para limpar campos
function limparCamposProduto() {
    document.getElementById('codigoProduto').value = '';
    document.getElementById('produto').value = '';
    document.getElementById('preco').value = '';
    document.getElementById('quantidadeEstoque').value = '';
    document.getElementById('quantidadeNova').value = '';
}
