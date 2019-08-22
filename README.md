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
  
  HistoricoVendaController:
  
1) list - via GET - Listagem de Todo o historico de vendas - retorna em JSON todas as informações como produto, valor total da compra;


Requisitos para funcionamento:

Ter o Eclipse/Intellij como IDE com Maven para isso somente importar como Projeto Maven;

Para ver os Web Services instalei o SwaggerUI para interagir com a API. Acesso local: http://localhost:8080/swagger-ui.html

Contato: Tarcisio Machado dos Reis - e-mail: tarcisio.reis.ti@gmail.com ou whatsapp 051 9 8490-4355.
