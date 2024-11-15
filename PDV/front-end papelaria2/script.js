const form = document.querySelector('form');
const usuarioInput = document.querySelector('#usuario');
const senhaInput = document.querySelector('#senha');

form.addEventListener('submit', (event) => {
    event.preventDefault();
    
    const usuario = usuarioInput.value;
    const senha = senhaInput.value;
    
   
    
    // Enviar os dados para o backend via POST
    fetch('http://localhost:8080/user', {
        headers: {
            "Accept": "application/json",
            "Content-Type": "application/json"
        },
        method: 'POST',
        body: JSON.stringify({
            usuario: usuario,
            senha: senha
        })
    })
    .then(response => response.text()) // Ler a resposta como texto
    .then(data => {
        console.log('Resposta do servidor:', data);
        
        // Verifica se o login foi bem-sucedido
        if (data === "usuario encontrado") {
            // Redirecionar para outra página
            console.log('Redirecionando para a página...');
            window.location.href = "menu.html"; // Substitua pela página correta
        } else {
            // Exibir mensagem de erro
            alert('Usuário ou senha incorretos');
        }
    })
    .catch(error => {
        console.error('Erro na requisição:', error);
    });
});
