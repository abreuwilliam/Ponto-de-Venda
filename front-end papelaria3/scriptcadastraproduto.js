let produtos = [];
let editando = false;
let indiceEdicao = null;
let scannerAtivo = false;

// Exibe botão scanner apenas no mobile
document.addEventListener('DOMContentLoaded', () => {
    if (window.innerWidth <= 768) {
        document.getElementById('btnScanner').style.display = 'inline-block';
    }
});

// Salvar produto
function salvarProduto() {
    const id = document.getElementById("produtoId").value;
    const codigo = document.getElementById("codigo").value;
    const nome = document.getElementById("nome").value;
    const preco = document.getElementById("preco").value;
    const estoque = document.getElementById("estoque").value;

    if (editando) {
        produtos[indiceEdicao] = { codigo, nome, preco, estoque };
        editando = false;
        indiceEdicao = null;
        document.getElementById("formTitle").innerText = "Adicionar Produto";
    } else {
        produtos.push({ codigo, nome, preco, estoque });
    }

    enviarDados({ codigo, nome, preco, estoque });
    limparFormulario();
    atualizarTabela();
}

// Limpar formulário
function limparFormulario() {
    document.getElementById("produtoForm").reset();
    document.getElementById("produtoId").value = "";
}

// Atualizar tabela
function atualizarTabela() {
    const tabela = document.getElementById("tabelaProdutos").getElementsByTagName("tbody")[0];
    tabela.innerHTML = "";

    produtos.forEach(produto => {
        let linha = tabela.insertRow();
        linha.insertCell(0).innerText = produto.codigo;
        linha.insertCell(1).innerText = produto.nome;
        linha.insertCell(2).innerText = produto.preco;
        linha.insertCell(3).innerText = produto.estoque;
    });
}

// Enviar dados para o backend
function enviarDados(produto) {
    const token = localStorage.getItem('authToken');
    fetch('https://ponto-de-venda-1.onrender.com/produto/cadastro', {
        headers: {
            "Accept": "application/json",
            "Content-Type": "application/json",
            "Authorization": `Bearer ${token}`
        },
        method: 'POST',
        body: JSON.stringify({
            codigoProduto: produto.codigo,
            produto: produto.nome,
            preco: produto.preco,
            quantidadeEstoque: produto.estoque
        })
    })
    .then(response => response.text())
    .then(data => console.log('Resposta do servidor:', data))
    .catch(error => console.error('Erro ao enviar os dados:', error));
}

// Iniciar scanner
function startScanner() {
    if (scannerAtivo) return;

    const readerDiv = document.getElementById('reader');
    readerDiv.style.display = 'block';

    const html5QrCode = new Html5Qrcode("reader");
    html5QrCode.start(
        { facingMode: "environment" },
        {
            fps: 10,
            qrbox: { width: 250, height: 250 }
        },
        (decodedText) => {
            document.getElementById('codigo').value = decodedText;
            html5QrCode.stop().then(() => {
                readerDiv.style.display = 'none';
                scannerAtivo = false;
            });
        },
        (errorMessage) => {
            // erros silenciosos de leitura
        }
    ).then(() => {
        scannerAtivo = true;
    }).catch(err => {
        console.error("Erro ao iniciar scanner:", err);
        readerDiv.style.display = 'none';
    });
}
