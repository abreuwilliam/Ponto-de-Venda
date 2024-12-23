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

    // Recuperar o token de autenticação do localStorage
    const token = localStorage.getItem('authToken');
    console.log("Token recuperado na busca:", token);  // Verifique o token aqui

    if (!token) {
        alert('Token de autenticação não encontrado. Faça login novamente.');
        return;
    }

    try {
        // Realizar a requisição com o token no cabeçalho
        const response = await fetch(`http://localhost:8080/caixa/${codigoProduto}`, {
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
        alert('Erro ao buscar o produto. Tente novamente mais tarde.');
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
        alert('Todos os campos são obrigatórios para realizar a alteração.');
        return;
    }

    const data = {
        codigoProduto,
        produto,
        preco: parseFloat(preco),
        quantidadeEstoque: parseInt(quantidadeEstoque),
    };

    try {
        const token = localStorage.getItem('authToken');

        if (!token) {
            alert('Token de autenticação não encontrado. Faça login novamente.');
            return;
        }

        const response = await fetch(`http://localhost:8080/alterar/${codigoProduto}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`, // Certifique-se de usar o formato correto aqui
            },
            body: JSON.stringify(data),
        });

        if (response.ok) {
            alert('Produto alterado com sucesso!');
        } else {
            const errorMsg = await response.text();
            alert(`Erro ao alterar o produto: ${errorMsg || 'Erro desconhecido'}`);
        }
    } catch (error) {
        console.error('Erro ao alterar o produto:', error);
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
            const token = localStorage.getItem('authToken');

            if (!token) {
                alert('Token de autenticação não encontrado. Faça login novamente.');
                return;
            }

            const response = await fetch(`http://localhost:8080/delete/${codigoProduto}`, {
                method: 'DELETE',
                headers: {
                    'Authorization': `Bearer ${token}`, // Garantir o uso correto aqui também
                },
            });

            if (response.ok) {
                alert('Produto excluído com sucesso!');
                limparCamposProduto();
            } else {
                alert('Erro ao excluir o produto.');
            }
        } catch (error) {
            console.error('Erro ao excluir o produto:', error);
            alert('Erro ao se comunicar com a API.');
        }
    }
}
    