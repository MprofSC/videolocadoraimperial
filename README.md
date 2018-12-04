<p>Empresa: Videolocadora Imperial Ltda.</p>
<p>Equipe de Elaboração: Daniel Servero Estrazulas, Samuel Bristot Loli, Eliandro Luiz Minski.</p>
<p>Alterações:</p>

<table>
<thead>
<tr>
<th>Versão</th>
<th>Data</th>
<th>Nome</th>
<th>Alteração</th>
</tr>
</thead>
<tbody>
<tr>
<td>1.0</td>
<td>04/12/2018</td>
<td>Eliandro Minski</td>
<td>Criação do Documento</td>    
</tbody>
</table>

<p><strong>Sumário</strong></p>
<ol>
<li>Apresentação                      </li>
<li>Justificativa                     </li>
<li>Descrição Do Projeto              </li>
<li>Membros da Equipe                 </li>
<li>Processos e Métodos               </li>
<li>Acompanhamento                    </li>
</ol>


<p><strong>1 – Apresentação</strong></p>
<p>Este documento é parte referencial do projeto de software intitulado: Videolocadora Imperial, que visa atender as necessidades de gerenciamento e controle das atividades da empresa Videolocadora Imperial Ltda, que tem sua sede na cidade de Recife – PE e atua no ramo de locação de mídias digitais (filmes em DVD e Blu-Ray).</p>
<p><strong>2 – Justificativa</strong></p>
<p>A Imperial Locadora, já atua no ramo de locações de mídias digitais há mais de 4 anos, durante esse período a empresa controlava as locações através de um software proprietário, renovando sua licença anualmente. Com o crescimento da empresa e a insatisfação com o atual software, que não atende por completo as necessidades, principalmente na questão de controle financeiro, a empresa optou pela substituição do software atual, por um que se atenda suas necessidades por completo, levando em consideração um melhor desempenho, novas funcionalidades, custo de manutenção e atualização.</p>
<p><strong>3 – Descrição do Projeto</strong></p>
<p>Após reuniões realizadas entre os proprietários da empresa contratante e os colaboradores da área de desenvolvimento e engenharia de software, gerenciamento de projetos da empresa contratada, foram levantadas as seguintes necessidades que o novo software deve atender:<p>
<p> Cadastro de Clientes e Colaboradores: O módulo cadastro deverá ser o responsável pelo cadastro de novos clientes e colaboradores, registrando os seguintes atributos: Código; Nome; Endereço; Data De Nascimento; CPF; RG; E-mail; Sexo; Telefone comercial, residencial e celular; Local de Trabalho. Para o cadastro de colaborador, deve-se incluir também a informação da data de contratação e nível de acesso ao sistema. Este módulo deve permitir também a exclusão, bloqueio e alteração de dados cadastrais dos clientes.</p>
<p>Cadastro de Mídias: O sistema deve possuir um módulo que permita a inclusão, alteração e exclusão de mídias, que devem possuir os seguintes atributos: Código (cada exemplar terá código único), Título, Tipo de mídia (DVD ou Blu-Ray), Gênero, Duração, Ano de Lançamento, Categoria, Produtora, Principais Autores, Data de Aquisição, Fornecedor, Número da NF, Data da NF. O atributo categoria pode ser do tipo A,B,C,D ou E, para cada categoria existe um valor de locação, essa funcionalidade facilita a alteração de preços por grupos. O sistema deve possibilitar a criação de novos atributos, definidos pelo usuário.</p>
<p>Locação: A locação das mídias será feita através deste módulo próprio. O colaborador, uma vez logado e com permissões, poderá realizar as locações das mídias, para isso, deverá informar o nome ou código do cliente, data de locação, data de devolução, código da mídia. Uma vez realizado o preenchimento destes dados, o sistema imprime um comprovante em duas vias (uma via fica com o cliente e a outra com a locadora), neste comprovante impresso, consta todos os dados informados no ato da locação e mais o valor total das diárias, baseadas no soma do valor da categoria da mídia locada mais o valor de cada diária.</p>
<p>O módulo locação também será responsável pelo cadastro de devoluções de mídias. Quando a mídia é devolvida na locadora, o colaborador deve selecionar a opção “Devolução” e informar o código da mídia ou do cliente, indicar a(s) mídia(s) que estão sendo devolvidas, o sistema confere se a devolução está dentro do prazo estabelecido no ato da locação, caso esteja sendo devolvida com atraso, o sistema adiciona o valor da(s) diária(s) excedentes.</p>
<p>Financeiro e Relatórios: Neste módulo o colaborador com permissões de acesso, poderá realizar o fechamento do caixa, gerar relatórios financeiros como por exemplo: Quantidades ou valores de locações por período, por mídia ou gênero, quantidades de locações por cliente, estabelecendo períodos.</p>
<p>Relatórios estatísticos, também poderão ser gerados neste módulo, auxiliando a gerência na tomada de decisões para aquisição de novas mídias e período para realização de promoções, baseando-se nas informações do sistema, como por exemplo: Gêneros mais locados; Períodos de maiores locações; Preferências do cliente. Outro setor a beneficiar-se com esses relatórios e o setor de marketing, pois poderá realizar consultas e emissão de relatórios que auxiliem na interação com os clientes, como por exemplo, o envio de e-mail informando as novas mídias disponíveis na locadora, lançamentos e promoções, baseados nos dados de preferência do cliente.</p> 

<p><strong>4 – Membros da Equipe</strong></p>
<p>Este projeto encontra-se sob responsabilidade da seguinte equipe:</p>

	Gerente do Projeto:
		Eliandro Luiz Minski - elm@cin.ufpe.br
	
	Desenvolvedores:
		Daniel Servero Estrazulas  - dse@cin.ufpe.br
		Samuel Bristot Loli  - sbl@cin.ufpe.br



<p><strong>5 – Processos e Métodos.</strong></p>
<p>A Extreme Programming (XP) será a metodologia de desenvolvimento usada para a realização desse projeto. Dentre as metodologias ágeis, esta foi a que a equipe optou, baseando-se no número de membros do time (3), prazo de entrega e demais características do projeto. As interações serão realizadas com um intervalo de cinco (5) dias, onde, a cada intervalo, será entregue as tarefas/issues, já cadastradas no GitHub e com suas respectivas capacidades definidas. As definições das capacidades, foram baseadas na sequência de Fibonacci (1,2,3,5,8) com pontuação máxima de 8 pontos.</p>
<p>O desenvolvimento será feito através da prática da programação em pares, ficando a cargo desta atividade os desenvolvedores Samuel e Daniel.</p>
<p>Inicialmente foram cadastradas no GitHub, 32 tarefas/issues, pré definidas dos tipos: Arquitetural, Documental e Testes/Desenvolvimento, para cada uma dessas tarefas, já ficou estabelecida sua “Complexidade”, “Pontuação” e “Prioridade”, bem como o responsável pela mesma. Esse número de tarefas/issues poderá sofrer alterações no decorrer do desenvolvimento do projeto.</p>
<p>A gerência do projeto, ficará a cargo de Eliandro Luiz Minski, decisão essa tomada após reunião entre os membros da equipe. Portanto, o mesmo será o responsável pela comunicação entre cliente e equipe do projeto, mediando as interações; acompanhamento das atividades para que a entrega das tarefas estejam dentro do prazo estabelecido e supervisão dos demais membros da equipe.</p>

<p><strong>6 – Acompanhamento<strong></p>

<p>As informações referentes às tarefas/issues, podem ser acessadas no site do GitHub, pelo link:</p>
<p>https://github.com/MprofSC/videolocadoraimperial/projects/1</p>
<p>No site do Heroku é possível acompanhar o desenvolvimento da aplicação, através do link:</p>
<p>https://vlimperial.herokuapp.com/</p>

<p>As interações com o cliente, serão realizados a princípio em três momentos:</p>
<p>1 - Interação: 01/12/ 2018;</p>
<p>2 - Interação: 06/12/2018;</p>
<p>3 - Interação  11/12/2018.</p>
