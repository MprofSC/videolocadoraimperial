Feature: Conta de Usuário

    Scenario: Localizar usuário administrador
        When Eu procuro um usuário 'admin'
        Then o usuário é encontrado
        And o último nome é 'Administrator'
