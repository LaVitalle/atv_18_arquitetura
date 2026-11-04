# Atividade 018 - Padrão Observer

Refatoração do sistema de monitoramento meteorológico utilizando o padrão **Observer**, incluindo o desafio adicional do painel de média.

## Estrutura

| Classe | Papel |
|---|---|
| `TemperatureSubject` | Sujeito: `addObserver`, `removeObserver` e `notifyObservers` |
| `WeatherStation` | Sujeito concreto: guarda a temperatura e a lista de observadores |
| `TemperatureObserver` | Observador: `update(double temperature)` |
| `CurrentConditionsDisplay` | Exibe toda temperatura recebida |
| `TemperatureAlert` | Exibe alerta só quando a temperatura é maior ou igual a 35 °C |
| `AverageTemperatureDisplay` | Desafio adicional: exibe a média das temperaturas recebidas desde a inscrição |
| `Main` | Demonstra cadastro, cadastro repetido, remoção e o painel de média |

A estação não aceita observador nulo, ignora o cadastro repetido da mesma instância e trata a remoção de quem não está cadastrado como uma operação sem efeito.

## Como executar

```bash
javac -d out src/*.java
java -cp out Main
```

## Questões para reflexão

**a) Qual problema existente no código inicial foi resolvido com o Observer?**

A `WeatherStation` recebia os painéis concretos no construtor e chamava o método específico de cada um. Para adicionar um painel novo era preciso alterar a estação, e não dava para incluir ou remover interessados durante a execução. Agora a estação só conhece a interface `TemperatureObserver` e mantém uma lista que pode mudar a qualquer momento.

**b) Quais abstrações e classes representam o sujeito, o sujeito concreto, o observador e os observadores concretos na sua implementação?**

- Sujeito: `TemperatureSubject`
- Sujeito concreto: `WeatherStation`
- Observador: `TemperatureObserver`
- Observadores concretos: `CurrentConditionsDisplay`, `TemperatureAlert` e `AverageTemperatureDisplay`

**c) Como o cadastro e a remoção de observadores alteram os destinatários das notificações durante a execução?**

A estação notifica quem está na lista no momento da mudança. Quando um observador é cadastrado, ele passa a receber as próximas atualizações (não recebe as anteriores). Quando é removido, deixa de receber. Na demonstração, depois que o alerta é removido, a temperatura de 38 °C chega só ao painel. Sem nenhum observador, a estação continua funcionando normalmente.

**d) A solução envia a temperatura no método `update()`, utilizando a abordagem push. Como seria uma alternativa pull, em que o observador consulta o estado do sujeito?**

No modelo pull, o `update()` não receberia a temperatura. Ele receberia a própria estação (por exemplo, `update(WeatherStation station)`) ou o observador guardaria uma referência para ela. Ao ser avisado, o observador chamaria algo como `station.getTemperature()` para buscar o dado de que precisa. Isso dá mais liberdade para cada observador pegar só o que interessa, mas cria uma dependência maior do observador com o sujeito.

**e) Como um observador lento ou que lança uma exceção pode afetar os demais em uma notificação síncrona? Que cuidados seriam necessários em um sistema real?**

Como a notificação chama os observadores um depois do outro na mesma thread, um observador lento atrasa todos os seguintes e a própria estação. Se ele lançar uma exceção, o laço é interrompido e os demais não são avisados. Num sistema real, seria preciso tratar exceções de cada observador separadamente (try/catch dentro do laço), usar notificação assíncrona ou filas para os observadores lentos, definir timeouts e, se a lista puder mudar durante a notificação, iterar sobre uma cópia dela ou usar uma coleção segura para concorrência.
