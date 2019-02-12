Feature: Autalização de Clientes

    Scenario: Inativar dependentes a partir do cliente
        When Eu inativo um cliente com dependente
        Then o cliente fica com o status 'false'
        And seus dependentes ficam com o status 'false'
