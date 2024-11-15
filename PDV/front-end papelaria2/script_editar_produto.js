let timeout;

document.getElementById('codigoProduto').addEventListener('input', function() {
    // Limpar o timeout anterior
    clearTimeout(timeout);

    // Definir o tempo que você deseja esperar após a última digitação (por exemplo, 1 segundo = 1000 milissegundos)
    timeout = setTimeout(async function() {
        await buscarProduto();
    }, 1000); // 1000 ms = 1 segundo
});

async function buscarProduto() {
    const codigoProduto = document.getElementById('codigoProduto').value;

    if (!codigoProduto) {
        alert('Por favor, insira o código do produto.');
        return;
    }

    try {
        const response = await fetch(`http://localhost:8080/caixa/${codigoProduto}`);
        
        if (response.ok) {
            const produto = await response.json();
            document.getElementById('produto').value = produto.produto;
            document.getElementById('preco').value = produto.preco;
            document.getElementById('quantidadeEstoque').value = produto.quantidadeEstoque;

            // Ativar campos para edição
            document.getElementById('produto').disabled = false;
            document.getElementById('preco').disabled = false;
            document.getElementById('quantidadeEstoque').disabled = false;

            // Reinicializar o campo quantidadeNova para evitar somas repetidas
            document.getElementById('quantidadeNova').value = '';
        } else {
            alert('Produto não encontrado.');
        }
    } catch (error) {
        console.error('Erro:', error);
    }
}


// Evento para atualizar o estoque e salvar no banco de dados
document.getElementById('atualizarEstoqueBtn').addEventListener('click', async () => {
    const quantidadeNova = parseInt(document.getElementById('quantidadeNova').value) || 0;
    const quantidadeEstoqueAtual = parseInt(document.getElementById('quantidadeEstoque').value) || 0;
    const estoqueAtualizado = quantidadeEstoqueAtual + quantidadeNova;

    // Atualizar o campo de quantidade em estoque com o novo total
    document.getElementById('quantidadeEstoque').value = estoqueAtualizado;

    // Limpar o campo de quantidade nova após a atualização
    document.getElementById('quantidadeNova').value = '';

    // Chama a função alterarProduto para salvar no banco de dados, passando o estoqueAtualizado
    await alterarProduto(estoqueAtualizado);

    alert(`Estoque atualizado e salvo com sucesso! Novo total: ${estoqueAtualizado}`);
    console.log(estoqueAtualizado);
});



async function alterarProduto() {
    const codigoProduto = document.getElementById('codigoProduto').value;
    const produto = document.getElementById('produto').value;
    const preco = document.getElementById('preco').value;
    const quantidadeEstoque = document.getElementById('quantidadeEstoque').value;

    if (!codigoProduto || !produto || !preco || !quantidadeEstoque) {
        alert("Todos os campos são obrigatórios para realizar a alteração.");
        return;
    }

    const data = {
        codigoProduto: codigoProduto,
        produto: produto,
        preco: parseFloat(preco),
        quantidadeEstoque: parseInt(quantidadeEstoque)
    };

    try {
        const response = await fetch(`http://localhost:8080/alterar/${codigoProduto}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(data),
        });

        if (response.ok) {
            alert('Produto alterado com sucesso!');
        } else {
            let responseData = await response.text();
            alert(`Erro ao alterar o produto: ${responseData || 'Erro desconhecido'}`);
        }
    } catch (error) {
        console.error('Erro:', error);
        
    }
}

async function deletarProduto() {
    const codigoProduto = document.getElementById('codigoProduto').value;

    if (!codigoProduto) {
        alert('Por favor, insira o código do produto para excluir.');
        return;
    }

    const confirmDelete = confirm(`Tem certeza que deseja deletar o produto com código ${codigoProduto}?`);
    if (confirmDelete) {
        try {
            const response = await fetch(`http://localhost:8080/delete/${codigoProduto}`, {
                method: 'DELETE',
            });

            if (response.ok) {
                alert('Produto excluído com sucesso!');
                // Limpar os campos após a exclusão
                document.getElementById('codigoProduto').value = '';
                document.getElementById('produto').value = '';
                document.getElementById('preco').value = '';
                document.getElementById('quantidadeEstoque').value = '';
            } else {
                alert('Erro ao excluir o produto.');
            }
        } catch (error) {
            console.error('Erro:', error);
            alert('Erro ao se comunicar com a API.');
        }
    }
}
