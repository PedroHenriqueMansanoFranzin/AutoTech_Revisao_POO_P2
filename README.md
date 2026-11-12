# AutoTech - Controle de Frota (Revisão Prova 2 de POO)

Projeto de **revisão para a 2ª prova de Programação Orientada a Objetos** (4º semestre - UniCesumar).

A ideia é ter num projeto só tudo que cai na prova: herança, classe abstrata, polimorfismo, encapsulamento, exceções personalizadas e os padrões Factory e Singleton.

O sistema cadastra os veículos de uma frota (carro de passeio e caminhão de carga), calcula a diária de cada um e no final mostra um relatório.

---

## Como rodar

Pelo terminal, na pasta do projeto:

```
javac -d bin src/br/com/autotech/*/*.java
java -cp bin br.com.autotech.main.AppSimulado
```

Ou só dar Run na classe `AppSimulado` no VS Code.

### Entrada

Um veículo por linha no formato `tipo placa ano extra`:

- **tipo**: 1 = carro de passeio, 2 = caminhão de carga
- **extra**: quantidade de passageiros (carro) ou toneladas (caminhão)
- digitar `FIM` encerra e mostra o relatório

Exemplo:

```
1 ABC1234 2020 4
2 XYZ9876 1995 0.5
3 QQQ1111 2021 2
FIM
```

---

## Estrutura do projeto

```
src/br/com/autotech/
├── model/
│   ├── Veiculo.java          -> classe abstrata (mãe)
│   ├── CarroPasseio.java     -> filha de Veiculo
│   ├── CaminhaoCarga.java    -> filha de Veiculo
│   └── Frota.java            -> lista de veículos + cálculos
├── exception/
│   ├── FrotaLotadaException.java
│   └── TipoInvalidoException.java
├── factory/
│   └── VeiculoFactory.java   -> padrão Factory
├── controller/
│   └── FrotaController.java  -> padrão Singleton
└── main/
    └── AppSimulado.java      -> main, leitura e relatório
```

Fluxo: `AppSimulado` lê a linha → chama o `FrotaController` → que pede pra `VeiculoFactory` criar o veículo → e adiciona na `Frota`.

---

## Cálculos (baseados no RA)

| O quê | Fórmula |
|---|---|
| D | último dígito do RA **antes do traço** |
| Taxa base | 50 + 5 × D |
| Capacidade da frota | 3 + (D % 3) |
| Diária carro | taxa base × 1,20 + 15 × passageiros |
| Diária caminhão | taxa base × 1,50 + 30 × toneladas |

Regras de validação (ficam nos construtores):

- ano menor que 2000 vira 2000
- carro tem no mínimo 2 passageiros
- caminhão tem no mínimo 1 tonelada
- placa nula vira texto vazio

### Exemplo com o meu RA (26003821-2)

- D = **1** (o dígito antes do `-`)
- Taxa base = 50 + 5 × 1 = **55.00**
- Capacidade = 3 + (1 % 3) = **4 veículos**
- Carro com 4 passageiros: 55 × 1,20 + 15 × 4 = 66 + 60 = **126.00**
- Caminhão com 0.5 t → vira 1 t: 55 × 1,50 + 30 × 1 = 82,50 + 30 = **112.50**

> Na prova o RA muda, então é bom saber refazer essa conta na mão.

---

## Resumo dos conceitos (o que estudar)

### 1. Encapsulamento
Atributos `private` e acesso só pelos getters. Em `Veiculo` os atributos ainda são `final`, ou seja, não mudam depois que o objeto é criado (por isso não tem setter).

### 2. Herança
`CarroPasseio` e `CaminhaoCarga` usam `extends Veiculo`. O construtor da filha chama `super(placa, ano, taxaBase)` **na primeira linha** pra montar a parte da classe mãe.

### 3. Classe e método abstrato
`Veiculo` é `abstract`: não dá pra fazer `new Veiculo(...)`. O método `calcularDiaria()` é abstrato (não tem corpo), então **toda classe filha é obrigada** a implementar ele.

### 4. Polimorfismo
A `Frota` guarda uma `List<Veiculo>`, mas dentro tem carros e caminhões. Quando chama `v.calcularDiaria()`, o Java executa a versão da classe real do objeto (sobrescrita com `@Override`). O `toString()` também é um exemplo de sobrescrita.

### 5. Exceções personalizadas
- `FrotaLotadaException` e `TipoInvalidoException` herdam de `Exception` → são **checked** (o compilador obriga a tratar com `try/catch` ou declarar com `throws`).
- `IllegalArgumentException` (usada quando o veículo é nulo) é **unchecked** (herda de `RuntimeException`, não precisa declarar).
- No `main` tem **multi-catch**: `catch (TipoInvalidoException | FrotaLotadaException e)`.
- Quando a entrada é inválida, o `sc.nextLine()` descarta o resto da linha pra não ficar em loop.

### 6. Padrão Factory (`VeiculoFactory`)
Centraliza a criação dos objetos: passa o tipo (1 ou 2) e ela devolve o veículo certo. Se o tipo não existir, lança `TipoInvalidoException`. O construtor é `private` porque a classe só tem método `static`.

### 7. Padrão Singleton (`FrotaController`)
Garante **uma única instância** no programa:
- construtor `private`
- atributo `static` guardando a instância
- método `getInstance()` que cria só se ainda for `null`
- `synchronized` pra não criar duas instâncias se tiver mais de uma thread

### 8. Outros detalhes que podem cair
- **BigDecimal** em vez de `double` pra valores em dinheiro (evita erro de arredondamento). `setScale(2, RoundingMode.HALF_UP)` deixa com 2 casas.
- **`Locale.setDefault(Locale.US)`** pro `Scanner` aceitar número com ponto (`2.5`).
- **Streams** na `Frota`: `map` + `reduce` pra somar as diárias, `filter` + `count` pra contar por tipo.
- **`instanceof` / `isInstance`**: usado no `contarPorTipo` pra saber de qual classe é o objeto.
- Média com frota vazia retorna 0 pra não dividir por zero.

---

## Perguntas pra treinar

1. Por que `Veiculo` é abstrata? O que acontece se tentar dar `new Veiculo()`?
   → Porque não existe um veículo "genérico", só carro ou caminhão. Dá erro de compilação.

2. O que acontece se `CarroPasseio` não implementar `calcularDiaria()`?
   → Erro de compilação, a não ser que ela também seja abstrata.

3. Qual a diferença entre exceção checked e unchecked? Dê um exemplo do projeto.
   → Checked precisa tratar ou declarar (`FrotaLotadaException`); unchecked não precisa (`IllegalArgumentException`).

4. Onde está o polimorfismo no projeto?
   → No `calcularDiaria()` chamado na lista `List<Veiculo>` da Frota e no relatório.

5. Quais são as 3 características de um Singleton?
   → Construtor privado, instância estática e método `getInstance()`.

6. Qual a vantagem de usar Factory?
   → O resto do código não depende das classes concretas; pra adicionar um tipo novo só mexe na factory.

7. Com D = 7, qual a taxa base e a capacidade?
   → Taxa = 50 + 35 = 85.00 e capacidade = 3 + (7 % 3) = 4.

8. Por que usar `BigDecimal` e não `double`?
   → `double` tem erro de precisão (ex: 0.1 + 0.2 ≠ 0.3), ruim pra dinheiro.
