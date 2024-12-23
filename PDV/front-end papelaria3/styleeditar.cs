/* Estilo para o formulário centralizado */
.form-container {
    max-width: 400px;
    margin: 0 auto;
    padding: 20px;
    background-color: #ffffff;
    border-radius: 8px;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
    text-align: center;
}

.form-container label {
    display: block;
    font-weight: bold;
    margin-bottom: 8px;
    color: #333;
}

.form-container input[type="text"] {
    width: 100%;
    padding: 10px;
    margin-bottom: 15px;
    border: 1px solid #ddd;
    border-radius: 5px;
}

.button-group {
    display: flex;
    gap: 10px;
    justify-content: center;
}

.btn-alterar, .btn-deletar {
    padding: 10px 20px;
    border: none;
    border-radius: 5px;
    font-size: 16px;
    cursor: pointer;
    color: #fff;
    transition: background-color 0.3s ease;
}

.btn-alterar {
    background-color: #4CAF50; /* Verde para alterar */
}

.btn-deletar {
    background-color: #f44336; /* Vermelho para deletar */
}

.btn-alterar:hover {
    background-color: #45a049;
}

.btn-deletar:hover {
    background-color: #e53935;
}
