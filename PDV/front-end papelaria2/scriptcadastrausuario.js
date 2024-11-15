// script.js
document.addEventListener("DOMContentLoaded", function() {
    const demoButton = document.createElement("button");
    demoButton.textContent = "Solicitar Demonstração Gratuita";
    demoButton.style.padding = "12px 24px";
    demoButton.style.backgroundColor = "#007bff";
    demoButton.style.color = "white";
    demoButton.style.border = "none";
    demoButton.style.borderRadius = "8px";
    demoButton.style.cursor = "pointer";
    demoButton.style.margin = "20px auto";
    demoButton.style.display = "block";
    demoButton.style.transition = "background 0.3s";
    
    demoButton.addEventListener("click", function() {
        alert("Obrigado por solicitar uma demonstração gratuita! Entraremos em contato em breve.");
    });
    
    // Adiciona o botão ao final da seção de preços
    const pricingSection = document.querySelector(".pricing");
    pricingSection.appendChild(demoButton);
});
