# Parking-API

API REST de gerenciamento de estacionamento.


A exemplo de grandes estacionamentos de shoppings, aeroportos e edifícios específicos para estacionamento, que são divididos por pequenos lotes ou andares e recebem uma identificação para localização.


A API possui uma entity “area” que tem os seguintes atributos:

- ID: chave primária;

- Name: identificação do lote(número de lote e/ou andar);

- MaxQuantity: quantidade máxima de vagas no lote;

- Quantity: número de vagas ocupadas;

- ParkingType: enum que pode exibir os valores CAR, MOTORCYCLE ou BIKE, referente ao tipo de estacionamento para um determinado tipo de veículo.


Atualmente o API apenas registra, exclui um lote e exibe todos os lotes ou apenas um por id ou nome.


Para as próximas atualizações será criada uma nova entity chamada “vehicle” que terá atributos ID(que pode ser o número da placa), name(modelo do veículo), vehicleType(um enum com o tipo de veículo) e uma área(indicando qual lote o veículo vai estacionar) que terá um relacionamento do tipo many to one(muitos veículos para uma área). Será feita operações como verificar se o tipo de veículo corresponde ao tipo de estacionamento, para que um carro não estacione numa vaga de bicicleta por exemplo, incrementar ou decrementar a quantidade de vagas ocupadas em um lote quando um veículo for registrado ou deletado correspondendo a entrada ou a saída deste no estacionamento retornando uma mensagem quando o lote estiver completamente ocupado, e por fim exibir uma lista de veículos para localizar em qual lote está estacionado, para casos em que o motorista esquecer onde estacionou o carro por exemplo.
