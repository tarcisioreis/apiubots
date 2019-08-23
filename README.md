# apiubots

Projeto usando API contendo cadastro de cliente(http://www.mocky.io/v2/598b16291100004705515ec5) e historico de
vendas(http://www.mocky.io/v2/598b16861100004905515ec7)

Melhorias a serem implementadas a partir da API existente:

  Implementar a venda de vinhos a partir dos dados fornecidos pela API e incorporar novas funcionalidades como:

1) Liste os clientes ordenados pelo maior valor total em compras;

2) Mostre o cliente com maior compra única no último ano (2016);

3) Liste os clientes mais fiéis;

4) Recomende um vinho para um determinado cliente a partir do histórico de compras.


Descrição do Projeto:

Novo métodos para uso foram desenvolvidas em Spring Boot sem persistencia de dados somente usando a API existente, estrutura baseada em 2 camadas: Controller e Service, sendo a camada de dados usando DTO(Data Transfer Object) pela camada Service.

Motivo pela qual não foi criado banco de dados, como a API traz dados reais somente são manipulados a partir dos novos métodos e para evitar redundância de dados então, sempre consumimos os dados da API.

Métodos implementados:

  ClienteController:

1) list - via GET - Listagem de Todos os clientes - mostra codigo, nome e cpf;

2) listFidelidade - via GET - conforme item 3 das novas funcionalidades, Liste os clientes mais fiéis - retorna JSON com dados do Cliente, Preço Total da(s) compra(s) e Produtos que foram comprados ao menos uma vez;
  
  HistoricoVendaController:
  
1) list - via GET - Listagem de Todo o historico de vendas - retorna em JSON todas as informações como produto, valor total da compra;

2) listClienteMaiorValorTotalCompra - via GET - conforme item 1 das novas funcionalidades, Liste os clientes ordenados pelo maior valor total em compras - retorna JSON com dados do Cliente e Preço Total da(s) compra(s);

3) listClienteMaiorValorTotalCompraUnicaPorAno - via GET - parâmetro ano - conforme item 2 das novas funcionalidades, Mostre o cliente com maior compra única no último ano (2016) - Observação: somente passar o ano desejado como parâmetro no final da URL -
retorna JSON com dados do Cliente e Preço Total da compra efetuado no ano informado;

4) recomendarVinhoaClientePorPerfildeCompra - via GET - parâmetros variedade do vinho e nome do cliente - conforme item 4 das novas funcionalidades, Recomende um vinho para um determinado cliente a partir do histórico de compras - retorna JSON com dados do Cliente e se consumiu algum vinho da variedade informada, validações da existência do nome do cliente e variedade do vinho.


Requisitos para funcionamento:

Ter o Eclipse/Intellij como IDE com Maven para isso somente importar como Projeto Maven;

Para ver os Web Services instalei o SwaggerUI para interagir com a API. Acesso local: http://localhost:8080/swagger-ui.html

Acesso online publicado no Heroku(Plataforma SaSS) - https://apiubots.herokuapp.com/swagger-ui.html

Contato: Tarcisio Machado dos Reis - e-mail: tarcisio.reis.ti@gmail.com ou whatsapp 051 9 8490-4355.
