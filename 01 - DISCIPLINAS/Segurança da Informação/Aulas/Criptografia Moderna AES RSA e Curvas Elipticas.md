---
tipo: aula
disciplina: Segurança da Informação
tema: "Criptografia Moderna, Chaves Simetricas, Chaves Assimetricas e Curvas Elipticas"
status: concluido
---

# Criptografia Moderna: AES, RSA e Curvas Elipticas

> [!INFO] **Visao Geral do Modulo**
> A criptografia moderna garante a triade CIA (Confidencialidade, Integridade e Autenticidade) mais o Não-Repúdio. O design de sistemas seguros combina cifras simetricas velozes para dados em massa com cifras assimetricas para troca segura de chaves.

---

## 1. Criptografia Simetrica vs. Assimetrica

### Criptografia Simetrica (Chave Secreta Compartilhada):
- A mesma chave e usada para criptografar e descriptografar.
- **Vantagem:** Desempenho computacional altissimo (aceleracao por instrucoes de hardware como AES-NI na CPU).
- **Padrao de Ouro:** **AES (Advanced Encryption Standard)** com tamanho de bloco de 128 bits e chaves de 128, 192 ou 256 bits.
- **Modos de Operacao:**
  - *ECB (Electronic Codebook):* Vulneravel (padroes visuais vazam). Nunca usar em producao.
  - *CBC (Cipher Block Chaining):* Exige vetor de inicializacao (IV) aleatorio, mas nao provê integridade nativa.
  - *GCM (Galois/Counter Mode):* **Padrao moderno (AEAD — Authenticated Encryption with Associated Data)**. Garante confidencialidade e autenticidade/integridade simultaneamente atraves de uma tag de autenticacao imutavel.

### Criptografia Assimetrica (Par de Chaves Publica e Privada):
- A chave publica e compartilhada livremente para criptografar dados ou verificar assinaturas; a chave privada permanece em sigilo absoluto para descriptografar ou gerar assinaturas.
- **RSA:** Baseado na dificuldade matematica de fatoracao de produtos de numeros primos gigantescos. Exige chaves grandes ($\ge 2048$ ou $3072$ bits) para seguranca contemporanea.
- **ECC (Elliptic Curve Cryptography):** Baseado no problema do logaritmo discreto em curvas elipticas sobre corpos finitos. Uma chave ECC de **256 bits (como a curva secp256k1 do Bitcoin ou Ed25519)** oferece o mesmo nivel de seguranca criptografica que uma chave RSA de 3072 bits, consumindo uma fracao da memoria, largura de banda e poder de processamento.
