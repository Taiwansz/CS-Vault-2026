import sqlite3
import bcrypt  

conn = sqlite3.connect('dados.db')


cursor = conn.cursor()


cursor.execute('''CREATE TABLE IF NOT EXISTS usuarios (
    id INTEGER PRIMARY KEY,
    username TEXT NOT NULL,
    password TEXT NOT NULL
                )''')


cursor.execute('''CREATE TABLE IF NOT EXISTS pedidos (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    produto TEXT,
                    preco REAL,
                    personalizacao TEXT,
                    usuario TEXT,
                    restaurante TEXT
                )''')



cursor.execute('''CREATE TABLE IF NOT EXISTS carrinho (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    produto TEXT,
                    quantidade INT,
                    preco REAL,
                    desconto REAL DEFAULT 0,
                    personalizacao TEXT,
                    usuario TEXT
                )''')

def cadastrar(cursor):
    novo_usuario = input("Digite o novo nome de usuário: ")
    nova_senha = input("Digite a nova senha: ")

    hashed = bcrypt.hashpw(nova_senha.encode('utf-8'), bcrypt.gensalt())


    cursor.execute("INSERT INTO usuarios (username, password) VALUES (?, ?)", (novo_usuario, hashed))
    conn.commit()
    print("╔══════════════════════════════════╗")
    print("║Usuário cadastrado com sucesso.   ║")
    print("╚══════════════════════════════════╝")    

def login(cursor):
    username = input("Digite o nome de usuário: ")
    password = input("Digite a senha: ")


    cursor.execute("SELECT password FROM usuarios WHERE username = ?", (username,))
    result = cursor.fetchone()
    if result and bcrypt.checkpw(password.encode('utf-8'), result[0]):
        print(" ╔══════════════════════╗")
        print(f" Bem-vindo, {username}!")
        print(" ╚══════════════════════╝")
        return username
    else:
        print(" ╔════════════════════════════════════╗")
        print(" ║Nome de usuário ou senha incorretos.║")
        print(" ╚════════════════════════════════════╝")
        return None

def dev():
    """
    Função para mostrar os créditos dos desenvolvedores.
    """
    Creditos_Desenvolvedor = ["Juan Pedro Souza de Oliveira", "Felipe Guarniere Pinete", "Matheus Sousa dos Santos"]

    print("Desenvolvedores:")
    for desenvolvedor in Creditos_Desenvolvedor:
        print("-", desenvolvedor)

def menu_login(cursor, conn):
    """
    Menu de login e cadastro.
    """
    while True: 
        print("╔════════════════════════════════════════╗")
        print("║              Menu de Acesso            ║")
        print("║════════════════════════════════════════║")
        print("║ 1. Login                               ║")
        print("║════════════════════════════════════════║")
        print("║ 2. Cadastro                            ║")
        print("║════════════════════════════════════════║")
        print("║ 4. Creditos                            ║")
        print("║════════════════════════════════════════║")
        print("║ 5. Sair                                ║")
        print("╚════════════════════════════════════════╝")

        escolha = input("Escolha uma opção: ")

        if escolha == '1':
            usuario_logado = login(cursor)
            if usuario_logado:
                return usuario_logado
        elif escolha == '2':
            cadastrar(cursor)
        elif escolha == '3':
            dev()
        elif escolha == '4':
            print("╔════════════════════╗")
            print("║Saindo do sistema...║")
            print("╚════════════════════╝")
            
            break
        else:
            print("╔════════════════════╗")    
            print("║Opção inválida      ║")
            print("╚════════════════════╝")

def main():
    """
    Função principal.
    """
    usuario_logado = None
    while not usuario_logado:
        usuario_logado = menu_login(cursor, conn) 
    carrinho = {
        "": {},
        "cupom": {
            "codigo": None,
            "desconto": 0.0
        }
    }

    while True:
        print("╔══════════════════════════════════════════════════════════════════════╗")
        print("║                              Bem-vindo                               ║")
        print("║                            Menu de Opções                            ║")
        print("╚══════════════════════════════════════════════════════════════════════╝")
        print("╔══════════════════════════════════════════════════════════════════════╗")
        print("║ 1. Selecionar Restaurante                                            ║")
        print("║══════════════════════════════════════════════════════════════════════║")
        print("║ 2. Carrinho                                                          ║")
        print("║══════════════════════════════════════════════════════════════════════║")
        print("║ 3. Finalizar Compra                                                  ║")
        print("║══════════════════════════════════════════════════════════════════════║")
        print("║ 4. Sair                                                              ║")
        print("╚══════════════════════════════════════════════════════════════════════╝")

        escolha = input("Escolha uma opção: ")

        if escolha == '1':
            Select_rest(carrinho, usuario_logado,cursor, conn)
        elif escolha == '2':
            verificar_carrinho(cursor, conn, usuario_logado)
        elif escolha == '3':
            finalizar_compra(carrinho, usuario_logado, cursor, conn)
        elif escolha == '4':
            print("Saindo...")
            return menu_login(cursor, conn)
        else:
            print("Opção inválida, digite novamente.")

def Select_rest(carrinho, usuario_logado, cursor, conn):
    """
    Seleciona um restaurante e adiciona produtos ao carrinho.
    """
    while True:
        print("╔════════════════════════════════════════════════╗")
        print("║              Selecionar Restaurante            ║")
        print("╚════════════════════════════════════════════════╝")
        print("╔════════════════════════════════════════════════╗")
        print("║ 1. ThSorvetes                                  ║")
        print("╚════════════════════════════════════════════════╝")

        restaurante = input("Digite o numero do restaurante correspondente: ")

        if restaurante == '1':
            Thfood(carrinho, usuario_logado, cursor, conn)
            return

def Thfood(carrinho, usuario_logado, cursor, conn):
    """
    Função do restaurante Thfood.
    """
    while True:
        print("╔════════════════════════════════════════════════╗")
        print("║                Bem-vindo ao Thfood             ║")
        print("╚════════════════════════════════════════════════╝")
        print("╔════════════════════════════════════════════════╗")
        print("║1 - Açaí                              R$15,50   ║")
        print("║════════════════════════════════════════════════║")
        print("║2 - Sorvete                           R$25,55   ║")
        print("║════════════════════════════════════════════════║")
        print("║3 - Milk Shake                        R$16,45   ║")
        print("║════════════════════════════════════════════════║")
        print("║4 - Sair                                        ║")
        print("╚════════════════════════════════════════════════╝")

        Thfood_RESTAURANTE = input("Digite o número do produto correspondente: ")

        if Thfood_RESTAURANTE == '4':
            return

        personalizacao = input("Digite suas personalizações (se houver): ")

    
        cursor.execute("SELECT COUNT(*) FROM carrinho WHERE produto = ? AND usuario = ?", (Thfood_RESTAURANTE, usuario_logado))
        count = cursor.fetchone()[0]

        if count > 0:
        
            cursor.execute("UPDATE carrinho SET quantidade = quantidade + 1 WHERE produto = ? AND usuario = ?", (Thfood_RESTAURANTE, usuario_logado))
            conn.commit()
            print("╔════════════════════════════╗")    
            print("║Item atualizado no carrinho!║")
            print("╚════════════════════════════╝")
        else:
 
            cursor.execute("INSERT INTO carrinho (produto, preco, personalizacao, usuario, quantidade) VALUES (?, ?, ?, ?, ?)",
                           (Thfood_RESTAURANTE, 15.50, personalizacao, usuario_logado, 1))
            conn.commit()
            print("Item adicionado ao carrinho com sucesso!")

def inserir_cupom_desconto(cursor, conn, usuario_logado):
    print("╔════════════════════════════════════════════════╗")
    print("║  Insira o valor do seu cupom de desconto       ║")
    print("║════════════════════════════════════════════════║")
    print("║        Para sua primeira compra digite:        ║")
    print("║                PRIMEIRACOMPRA                  ║")
    print("╚════════════════════════════════════════════════╝")

    cupom = input("Digite o seu cupom de desconto: ")
    
    if cupom == "PRIMEIRACOMPRA":
        try:
            cursor.execute("UPDATE carrinho SET desconto = ? WHERE usuario = ?", (15.00, usuario_logado))
            conn.commit()
            print("Cupom de desconto de R$15.00 para a primeira compra aplicado com sucesso!")
        except Exception as e:
            print(f"Erro ao aplicar o cupom de desconto: {e}")
    else:
        print("Cupom de desconto inválido. Por favor, tente novamente.")
        
        
def verificar_carrinho(cursor, conn, usuario_logado):
    cursor.execute("SELECT produto, preco, desconto, personalizacao, quantidade FROM carrinho WHERE usuario = ?", (usuario_logado,))
    itens_carrinho = cursor.fetchall()

    if not itens_carrinho:
        print("╔════════════════════════════════════════════════╗")
        print("║   Não há itens no carrinho para esse usuário!  ║")
        print("╚════════════════════════════════════════════════╝")
    else:
        total = 0
        print("Itens no carrinho:")
        for item in itens_carrinho:
            produto, preco, desconto, personalizacao, quantidade = item
            subtotal = preco * quantidade  # Calcula o subtotal para o item considerando a quantidade
            print(f"Produto: {produto}, Quantidade: {quantidade}, Subtotal: R$ {subtotal:.2f}, Personalização: {personalizacao}, Desconto: R$ {desconto:.2f}")
            total += subtotal

        inserir_cupom = input("Deseja aplicar um cupom de desconto? (s/n): ")
        if inserir_cupom.lower() == 's':
            inserir_cupom_desconto(cursor, conn, usuario_logado)
            cursor.execute("SELECT desconto FROM carrinho WHERE usuario = ?", (usuario_logado,))
            desconto_total = cursor.fetchone()[0] or 0
            total_com_desconto = total - desconto_total
            print(" ╔════════════════════════════════════════════════╗")
            print(f" Desconto aplicado: R$ {desconto_total:.2f}       ")
            print(f" Total com desconto: R$ {total_com_desconto:.2f}  ")
            print(" ╚════════════════════════════════════════════════╝")
        else:
            total_com_desconto = total
        print(" ╔═══════════════════════════════════════════════╗")
        print(f"Total: R$ {total_com_desconto:.2f}              ")
        print(" ╚═══════════════════════════════════════════════╝")
def calcular_total_carrinho(cursor, usuario_logado):
    cursor.execute("SELECT preco FROM carrinho WHERE usuario = ?", (usuario_logado,))
    precos = cursor.fetchall()
    total = sum(preco[0] for preco in precos)
    return total


def finalizar_compra(carrinho, usuario_logado, cursor, conn):
    print(" ╔═══════════════════════════════════════════════╗")
    print(f" Selecione a forma de pagamento {usuario_logado} ")
    print(" ╚═══════════════════════════════════════════════╝")
    print("╔════════════════════════════════════════════════╗")
    print("║ Formas de Pagamento                            ║")
    print("║════════════════════════════════════════════════║")
    print("║ 1. Cartão de Crédito                           ║")
    print("║════════════════════════════════════════════════║")
    print("║ 2. Cartão de Débito                            ║")
    print("║════════════════════════════════════════════════║")
    print("║ 3. Dinheiro                                    ║")
    print("║════════════════════════════════════════════════║")
    print("║ 4. Voltar                                      ║")
    print("╚════════════════════════════════════════════════╝")

    opcao = input("Digite o número correspondente à forma de pagamento: ")

    if opcao == '1':
        print("╔════════════════════════════════════════════════════════╗")
        print("║Obrigado por escolher o pagamento com Cartão de Crédito!║")
        print("╚════════════════════════════════════════════════════════╝")
    elif opcao == '2':
        print("╔════════════════════════════════════════════════════════╗")    

        print("║Obrigado por escolher o pagamento por Cartão de Débito! ║")
        print("╚════════════════════════════════════════════════════════╝")
    elif opcao == '3':
        print("╔════════════════════════════════════════════════════════╗")
        print("║    Obrigado por escolher o pagamento em Dinheiro!      ║")
        print("╚════════════════════════════════════════════════════════╝")        
    elif opcao == '4':
        return
    else:
        print("╔══════════════════════════════════════════════════════════════╗")    
        print("║Opção inválida. Por favor, escolha uma das opções disponíveis.║")
        print("╚══════════════════════════════════════════════════════════════╝")    

 
    cursor.execute("SELECT produto, preco, personalizacao FROM carrinho WHERE usuario = ?", (usuario_logado,))
    pedidos = cursor.fetchall()

 
    for produto, preco, personalizacao in pedidos:
        cursor.execute("INSERT INTO pedidos (produto, preco, personalizacao, usuario) VALUES (?, ?, ?, ?)",
                       (produto, preco, personalizacao, usuario_logado))

  
    cursor.execute("DELETE FROM carrinho WHERE usuario = ?", (usuario_logado,))
    conn.commit()
    print("╔════════════════╗")  
    print("║Pedido efetuado.║")
    print("╚════════════════╝")
    

if __name__ == "__main__":
    main()

conn.commit()
conn.close()



