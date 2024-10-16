const form = document.querySelector('form');
const usuario = document.querySelector('#usuario')
const senha = document.querySelector('#senha')
form.addEventListener('submit', (event) => {
    event.preventDefault();
    const iusuario = usuario.value;
    const isenha = senha.value;
    // Adicione aqui sua lógica de validação e envio
    console.log(iusuario , isenha);
});
fetch('http://localhost:8080/user', {
    headers: {
        "Accept": "application/json",
        "Content-Type": "application/json"
    },
    method: 'POST',
    body: JSON.stringify({
        // Usamos o valor capturado aqui
        
       usuario: usuario,
       senha: senha
       
    })
})
.then(function(res) { console.log(res); })
.catch(function(error) { console.error(error); });
